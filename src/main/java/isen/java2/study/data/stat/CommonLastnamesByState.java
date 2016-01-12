package isen.java2.study.data.stat;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Matthieu on 12/01/2016.
 */
public class CommonLastnamesByState implements Stat {
    private static final String QUERY = "SELECT bloodtype, count(id) as total FROM person " +
            "GROUP BY bloodtype ORDER BY total DESC;";
    private static final String DESCRIPTION = "Common last names by State";
    private int N; //Number of occurence of a name to be listed


    public CommonLastnamesByState(int n) {
        N = n;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION + "("+N+"occurence min)";
    }

    @Override
    public String getQuery() {
        return QUERY;
    }

    @Override
    public void handle(ResultSet set) throws SQLException {
        //TODO implement method
    }
}
