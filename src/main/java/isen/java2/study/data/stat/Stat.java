package isen.java2.study.data.stat;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface for Stat type class
 * Created by Matthieu on 11/01/2016.
 */

public interface Stat {
    String getDescription();
    String getQuery();
    void handle(ResultSet set) throws SQLException;
}
