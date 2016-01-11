package isen.java2.study.service;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import isen.java2.study.data.Person;
import isen.java2.study.data.Sex;
import isen.java2.study.data.stat.Stat;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Matthieu on 11/01/2016.
 */
public class DBService {
    private static final String SAVE_QUERY = "INSERT INTO `isen_project`.`person` (`lastname`, `firstname`, `sex`, " +
            "`streetname`, `state`, `city`, `bloodtype`, `dateofbirth`) VALUES (?, ?,?, ?, ?, ?, ?, ?)";
    MysqlDataSource dataSource;

    public DBService(Properties properties) {
        dataSource = new MysqlDataSource();
        dataSource.setServerName(properties.getProperty("db.server"));
        dataSource.setPort(Integer.parseInt(properties.getProperty("db.port")));
        dataSource.setUser(properties.getProperty("db.username"));
        dataSource.setPassword(properties.getProperty("db.password"));
        dataSource.setDatabaseName(properties.getProperty("db.schema"));
    }

    public void save(Person person) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(SAVE_QUERY)){
            stmt.setString(1, person.getLastName());
            stmt.setString(2, person.getFirstName());
            String sex = null;
            System.out.println("Saving into DB: " + person.getFirstName() + " " + person.getLastName());
            if(person.getSex() == Sex.MALE)
                sex = "MALE";
            else
                sex = "FEMALE";
            stmt.setString(3, sex);
            stmt.setString(4, person.getStreetName());
            stmt.setString(5, person.getState());
            stmt.setString(6, person.getCity());
            stmt.setString(7, person.getBloodType());
            stmt.setDate(8, Date.valueOf(person.getDateOfBirth()));
            int nbLines = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeStat(Stat stat) {
        //TODO implement the method (see page 6)
    }
}

