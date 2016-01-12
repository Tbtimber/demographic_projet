package isen.java2.study.data.stat;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Matthieu on 12/01/2016.
 */
public class AverageAgeByState implements Stat {
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
        //TODO implement method
        while (set.next()) {
            String state = set.getString("state");
            int avg = set.getInt(2);
            System.out.println("State : " + state + ". Average age : " + avg);
        }
    }
}
