package isen.java2.study.data.stat;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Matthieu on 12/01/2016.
 */
public class MostCommonBloodType implements Stat {
    private static final String QUERY = "SELECT lastname, state, count(id)as total FROM person GROUP BY state,lastname " +
            "HAVING total > 4 ORDER BY total DESC, state;";
    private static final String DESCRIPTION = "Most common blood type";
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
    }
}
