package isen.java2.study.data.stat;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Matthieu on 12/01/2016.
 */
public class CommonLastnamesByState implements Stat {
    private static final String QUERY_1 = "SELECT lastname, state, count(id)as total FROM person GROUP BY state," +
            "lastname HAVING total > ";
    private static final String QUERY_2 =" ORDER BY total DESC, state;";
    private static final String DESCRIPTION = "Common last names by State";
    private int n; //Number of occurence of a name to be listed


    public CommonLastnamesByState(int n) {
        this.n = n;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION + "("+n+"occurence min)";
    }

    @Override
    public String getQuery() {
        return QUERY_1 + n + QUERY_2;
    }

    @Override
    public void handle(ResultSet set) throws SQLException {
        //TODO implement method
        while (set.next()) {
            System.out.println("Lastname : " + set.getString(1) + " - State: " + set.getString(2));
        }
    }
}
