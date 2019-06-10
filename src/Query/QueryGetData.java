package Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class QueryGetData {

    ArrayList <String> names;
    ArrayList <Integer> scores;
    ArrayList <String> levels;

    ArrayList<DatabaseRow> databaseRows;

    public QueryGetData(ArrayList<DatabaseRow> databaseRows) {
        this.databaseRows = databaseRows;
        start();
    }

    void start() {

        try {

            String query = "SELECT * FROM scores";

            String url = "jdbc:mysql://localhost:3306/score_table";
            String username = "root";
            String password = "12345678";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {

                String name = rs.getString("playerName");
                //names.add(name);
                String level = rs.getString("levels");
                //levels.add(level);
                int score = rs.getInt("score");
                //scores.add(score);
                databaseRows.add(new DatabaseRow(name, score, level));
            }

            Collections.sort(databaseRows);


            st.close();
            con.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
