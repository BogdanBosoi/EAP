package Engine;
import Entity.*;
import GUI.*;
import GUI.Menu;

// Aici vin mecanicile de baza

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game extends Canvas implements Runnable{

    private boolean loading = true;

    public int maxEntryNumber = 6;

    public int startEntryNumber = 0;

    public static int gameState = 0;
    /*
        0 = meniu
        1 = bag un joculet
        2 = bag scor
        3 = ecranul cu highscore
        4 = probabil level design
        5 = exit
    */

    private static final long serialVersionUID = 1L;
    public int[][] lightMap = new int[64][64];
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private Camera camera;
    private SpriteSheet ss;

    private Menu menu;
    private Highscore highscore;

    private SpriteSheet il;
    public int numberOfEnemies = 0;
    public static int currentLevel = 0;
    private static int globalIlumination = 0;

    private boolean lost = false;
    public boolean scoreLoaded = false;
    private BufferedImage level;
    private BufferedImage sprite_sheet;
    private BufferedImage floor;
    private BufferedImage light_sheet;
    private BufferedImage lightTile;
    private boolean portalAppeared = false;
    private Font font;
    private Random r = new Random();
    private int portalNumber = 0;

    private ArrayList<String> levels;

    {
        levels = new ArrayList<>();
    }

    private int tempTicks = 0;

    public int mana = 300;
    public int hp = 100;
    public int maximumMana = 100;
    public int score = 0;

    private void loadLevels() {

        try {
            Scanner file = new Scanner(new File("levels/lvloader.txt"));
            String pathToLevel;
            while(file.hasNextLine()) {
                pathToLevel = file.nextLine();
                if(pathToLevel.toCharArray()[pathToLevel.length() - 1] == '\n')
                    pathToLevel.substring(0, pathToLevel.length() - 1);
                pathToLevel += ".png";
                levels.add(pathToLevel);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(levels.size() != 0)
            levels.add(levels.get(levels.size() - 1));

    }

    private void initLightMap() {
        for(int i = 0; i < 64; i++)
            for(int j = 0; j < 64; j++)
                lightMap[i][j] = 0;
    }

    private void lightReset() {

        for(int i = 0; i < 64; i++)
            for(int j = 0; j < 64; j++)
                lightMap[i][j] = globalIlumination;
    }

    public void light(int x, int y) {

        int tileX = x / 32;
        int tileY = y / 32;

        lightMap[tileX][tileY] = 3;
        int[] di;
        di = new int[]{0, 1, 1, -1, 0, 1, 2, 0, 2, 1, -1, -1, -2, -2, 0, 1, 3, 3, 0, 1, -1, 2, -1, 2};
        int[] dj;
        dj = new int[]{1, 0, 1, 0, -1, -1, 0, 2, 1, 2, 0, 1, 0, 1, 3, 3, 0, 1, -2, -2, -1, -1, 2, 2};
        /// pana la 3 am lightlevel = 3, restul 2
        for(int i = 0; i < 3; i++)
            if(tileX + di[i] >= 0 && tileX + di[i] <= 63 && tileY + dj[i] >= 0 && tileY + dj[i] <= 63)
                lightMap[tileX + di[i]][tileY + dj[i]] = Math.max(lightMap[tileX + di[i]][tileY + dj[i]], 3);
        for(int i = 3; i < 12; i++)
            if(tileX + di[i] >= 0 && tileX + di[i] <= 63 && tileY + dj[i] >= 0 && tileY + dj[i] <= 63)
                lightMap[tileX + di[i]][tileY + dj[i]] = Math.max(lightMap[tileX + di[i]][tileY + dj[i]], 2);
        for(int i = 12; i < 24; i++)
            if(tileX + di[i] >= 0 && tileX + di[i] <= 63 && tileY + dj[i] >= 0 && tileY + dj[i] <= 63)
                lightMap[tileX + di[i]][tileY + dj[i]] = Math.max(lightMap[tileX + di[i]][tileY + dj[i]], 1);

    }

    public Game() {
        loadLevels();
        new Window(1024, 768, "Gateseeker", this);

        handler = new Handler();
        camera = new Camera(0, 0);
        initLightMap();

        this.addKeyListener(new KeyInput(handler));

        for(var p : levels)
            System.out.println(p);

        font = new Font("Serif",Font.PLAIN, 24);

        BufferedImageLoader loader = new BufferedImageLoader();


        sprite_sheet = loader.loadImage("/sprite_sheet.png");
        light_sheet = loader.loadImage("/light_sheet.png");


        ss = new SpriteSheet(sprite_sheet);
        il = new SpriteSheet(light_sheet);
        level = loader.loadImageFromFile(levels.get(currentLevel));
        floor = ss.grabImage(4, 2, 32, 32);
        lightTile = il.grabImage(1, 1, 32, 32);

        this.addMouseListener(new MouseInput(handler, camera, this, ss, il));

        menu = new Menu(this, highscore);
        highscore = new Highscore(this, menu);

        menu.start();
        start();
        loadLevel(level);
    }

    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while ( isRunning ) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();

                if(numberOfEnemies < 12 && numberOfEnemies != 0 && tempTicks == 0)
                    portalAppeared = true;

                if(portalAppeared)
                    tempTicks++;

                if(tempTicks >= 1000)
                    portalAppeared = false;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
            /*try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        stop();


    }



    public void tick() {
        if(gameState == 1) {
            if ( currentLevel > levels.size() || hp <= 0){
                handler.object.clear();
                portalAppeared = false;
                portalNumber = 0;
                tempTicks = 0;
                gameState = 2;
            }
            if (!loading) {

                if(gameState != 2) {
                    lightReset();
                    for (int i = 0; i < handler.object.size(); i++)
                        if (handler.object.get(i).getId() == ID.Player)
                            camera.tick(handler.object.get(i));

                    handler.tick();

                    if (portalAppeared && portalNumber == 0) {
                        portalNumber++;
                        Portal tempPortal = new Portal(r.nextInt(63) * 32, r.nextInt(31) * 32, ID.Portal, ss, il, true, 3, this);
                        for (int i = 0; i < handler.object.size(); i++)
                            if (handler.object.get(i).getId() != ID.Portal)
                                if (tempPortal.getBounds().intersects(handler.object.get(i).getBounds())) {
                                    tempPortal.x = r.nextInt(63) * 32;
                                    tempPortal.y = r.nextInt(31) * 32;
                                    i = 0;
                                }
                        handler.object.add(tempPortal);


                    }
                }
            } else {
                if (currentLevel < levels.size() - 1) {
                    BufferedImageLoader loader = new BufferedImageLoader();

                    level = loader.loadImageFromFile(levels.get(currentLevel));

                    loadLevel(level);
                }
                else
                    gameState = 2;
            }
        } else if(gameState == 2) {
                if(!scoreLoaded) {
                    loadLevel(level);
                    scoreLoaded = true;
                }

        } else if (gameState == 3) {

        }
        else if (gameState == 6) {
            System.exit(1);
        }

    }

    public void render() {


        BufferStrategy bs = this.getBufferStrategy();
        if ( bs == null ) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setFont(font);

        if(gameState == 1) {
            Graphics2D g2d = (Graphics2D) g; // Translatez din coordonatele globale in alea de camera


            // Draw Event

            g2d.translate(-camera.getX(), -camera.getY());

            for (int xx = 0; xx < 2048; xx += 32)
                for (int yy = 0; yy < 2048; yy += 32) {
                    g.drawImage(floor, xx, yy, null);
                    //int p = lightMap[xx / 32][yy / 32] + 1;

                }

            handler.render(g);
            for (int xx = 0; xx < 2048; xx += 32)
                for (int yy = 0; yy < 2048; yy += 32) {
                    if (il != null)
                        lightTile = il.grabImage(lightMap[xx / 32][yy / 32] + 1, 1, 32, 32);
                    g.drawImage(lightTile, xx, yy, null);
                }

            g2d.translate(camera.getX(), camera.getY());

            g.setColor(Color.gray);
            g.fillRect(5, 5, 200, 32);
            g.setColor(Color.green);
            g.fillRect(5, 5, hp * 2, 32);
            g.setColor(Color.black);
            g.drawRect(5, 5, 200, 32);

            g.setColor(Color.gray);
            g.fillRect(5, 40, 200, 32);
            g.setColor(Color.blue);
            g.fillRect(5, 40, mana * 2, 32);
            g.setColor(Color.black);
            g.drawRect(5, 40, 200, 32);

            g.setColor(Color.white);

            g.drawString("Level: " + currentLevel, 5, 100);
            g.drawString("Score: " + score, 5, 130);
            g.drawString("Number of Enemies: " + numberOfEnemies, 5, 160);

            if (portalAppeared)
                g.drawString("A portal has been spawned!", 5, 190);


            // End of Draw Event
        } else if (gameState == 0) {
            BufferedImageLoader bgLoader = new BufferedImageLoader();
            BufferedImage bg = bgLoader.loadImage("/bg.png");
            g.drawImage(bg, 0, 0, null);
            g.setColor(Color.WHITE);
            menu.render(g);
        } else if (gameState == 3) {
            BufferedImageLoader bgLoader = new BufferedImageLoader();
            BufferedImage bg = bgLoader.loadImage("/bg.png");
            g.drawImage(bg, 0, 0, null);
            g.setColor(Color.WHITE);
            g.setFont(font);
            highscore.render(g);
        }


        g.dispose();
        bs.show();

    }

    // Incarc nivelul

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    private void loadLevel(BufferedImage image) {

        if(gameState == 1) {
            if (loading) {

                System.out.println("intru\n");
                loading = false;
                portalAppeared = false;
                portalNumber = 0;
                tempTicks = 0;
                handler.object.clear();

           /* for(var iterator : handler.object)
                if(iterator.getId() != ID.Player)
                    handler.object.remove(iterator);*/
                int sizeHandler = handler.object.size();
                if (sizeHandler != 0)
                    handler.wizardPosition = handler.object.get(0);
                currentLevel++;
                numberOfEnemies = 0;
                int w = image.getWidth();
                int h = image.getHeight();
                for (int xx = 0; xx < w; ++xx)
                    for (int yy = 0; yy < h; ++yy) {
                        int pixel = image.getRGB(xx, yy);
                        int red = (pixel >> 16) & 0xff;     // iau primii 8 biti din secventa aia pentru pixel aka red
                        int green = (pixel >> 8) & 0xff;    // urmatorii 8 biti
                        int blue = (pixel) & 0xff;          // ultimii 8 biti

                        if (red == 255)
                            handler.addObject(new Block(xx * 32, yy * 32, ID.Block, ss, il, false, 2, handler));

                        if (blue == 255 && green == 0) {
                            handler.addObject(new Wizard(xx * 32, yy * 32, ID.Player, handler, this, ss, il, true, 3));
                            handler.wizardPosition = handler.object.get(handler.object.size() - 1);
                        }

                        if (green == 255 && blue == 0) {
                            handler.addObject(new Enemy(xx * 32, yy * 32, ID.Enemy, handler, ss, il, false, 0, this));
                            numberOfEnemies++;
                        }

                        if (green == 255 && blue == 255)
                            handler.addObject(new Body(xx * 32, yy * 32, ID.Body, ss, il, false, 0, handler));


                    }

            }
        } else if (gameState == 2) {

            StringBuilder levelsToWrite = new StringBuilder();

            for(int i = 0; i < currentLevel; i++)
                levelsToWrite.append(levels.get(i)).append(" ");

            new Score(score, levelsToWrite.toString(), this, handler, menu);
            setLoading(true);
            currentLevel = 0;



        }

    }

    public static void main(String[] args) {

        new Game();

    }



}
