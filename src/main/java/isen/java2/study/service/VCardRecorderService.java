package isen.java2.study.service;

import java.util.Properties;

/**
 * Created by Matthieu on 11/01/2016.
 */
public class VCardRecorderService {
    DBService dbService;
    Properties properties;

    public VCardRecorderService(DBService dbService, Properties properties) {
        this.dbService = dbService;
        this.properties = properties;
    }

    public void readAndSaveCard() {
        //TODO implement the method (see page 7)
    }
}
