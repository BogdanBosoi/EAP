package Query;

import java.util.ArrayList;

public class DatabaseRow implements Comparable<DatabaseRow> {

    String name;
    String level;
    int score;

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public DatabaseRow(String name, int score, String level) {
        this.name = name;
        this.score = score;
        this.level = level;

    }

    @Override
    public int compareTo(DatabaseRow o) {
        return o.score - score;
    }
}
