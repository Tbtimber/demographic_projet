package isen.java2.study.service;

import isen.java2.study.service.util.FilesCrawler;
import isen.java2.study.service.util.VCardReader;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

/**
 * Created by Matthieu on 11/01/2016.
 */
//TODO prepare JUnit to test class
public class VCardRecorderService {
    DBService dbService;
    Properties properties;

    public VCardRecorderService(DBService dbService, Properties properties) {
        this.dbService = dbService;
        this.properties = properties;
    }

    public void readAndSaveCards() {
        //TODO implement the method (see page 7)
        List<Path> files = FilesCrawler.getFiles(properties.getProperty("vcards.folder"));
        for(Path p:files) {
            dbService.save(VCardReader.read(p));
        }
    }
}
