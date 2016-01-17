package isen.java2.study.data.stat;

import isen.java2.study.service.util.VCardListener;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Stat representing CommonLastnames by state meaning if a name appear N or more times it will be stocked
 * Created by Matthieu on 12/01/2016.
 */
public class CommonLastnamesByState implements Stat {
    private VCardListener mListener;
    public CommonLastnamesByState(int n, VCardListener mListener) {
        this.mListener = mListener;
        this.n = n;
    }

    public static final int DEFAULT_N_VALUE = 2;


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
        while (set.next()) {
            //System.out.println("Lastname : " + set.getString(1) + " - State: " + set.getString(2));
            mListener.newThingsToSay("Lastname : " + set.getString(1) + " - State: " + set.getString(2)+"\n");
        }
    }
}
