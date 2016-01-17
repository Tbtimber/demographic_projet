package isen.java2.study.data.stat;

import isen.java2.study.service.util.VCardListener;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * State showing the average of the population per state
 * Created by Matthieu on 12/01/2016.
 */
public class AverageAgeByState implements Stat {
    private VCardListener mListener;
    public AverageAgeByState(VCardListener mListener) {
        this.mListener = mListener;
    }

    private static final String QUERY = "SELECT state, AVG(YEAR(CURRENT_TIMESTAMP) - YEAR(dateofbirth) - " +
            "(RIGHT(CURRENT_TIMESTAMP, 5) < RIGHT(dateofbirth, 5))) as age FROM person GROUP BY state  ORDER BY age DESC;";
    private static final String DESCRIPTION = "Average Age by State";
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getQuery() {
        return QUERY;
    }

    @Override
    public void handle(ResultSet set) throws SQLException {
        while (set.next()) {
            String state = set.getString("state");
            int avg = set.getInt(2);
            //System.out.println("State : " + state + ". Average age : " + avg);
            mListener.newThingsToSay("State : " + state + ". Average age : " + avg+"\n");
            mListener.addAgeChartValue(state,avg);
        }
    }
}
