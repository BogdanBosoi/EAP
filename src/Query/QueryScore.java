package Query;

import java.sql.*;

public class QueryScore {

    String name;
    int score;
    String levels;
    String querry = "";
    String formQuerry(int type) {
        if(type == 1) {
            return "INSERT INTO scores VALUES(\"" + name + "\"," + score + ",\"" + levels + "\")";
        }
        return "SELECT * FROM scores";
    }

    public void start() {
        try {

            String url = "jdbc:mysql://localhost:3306/score_table";
            String username = "root";
            String password = "12345678";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            Statement st = con.createStatement();
            st.executeUpdate(formQuerry(1));

            st.close();
            con.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public QueryScore(String name, int score, String levels) {
        this.name = name;
        this.score = score;
        this.levels = levels;


        start();
    }


}
