package isen.java2.study.data.stat;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Matthieu on 11/01/2016.
 */
//TODO implement different stat classes + tests
public interface Stat {
    String getDescription();
    String getQuery();
    void handle(ResultSet set) throws SQLException;
}
