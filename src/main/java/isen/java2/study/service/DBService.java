package isen.java2.study.service;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import isen.java2.study.data.Person;
import isen.java2.study.data.stat.Stat;

import java.util.Properties;

/**
 * Created by Matthieu on 11/01/2016.
 */
public class DBService {
    private static final String SAVE_QUERY = "INSERT INTO `isen_project`.`person` (`lastname`, `firstname`, `sex`, " +
            "`streetname`, `state`, `city`, `bloodtype`, `dateofbirth`) VALUES (?, ?,?, ?, ?, ?, ?, ?)";
    MysqlDataSource dataSource;

    public DBService(Properties properties) {
        //TODO implement the constructor (see page 5-6)
    }

    public void save(Person person) {
        //TODO implement the method (see page 6)
    }

    public void executeStat(Stat stat) {
        //TODO implement the method (see page 6)
    }
}

