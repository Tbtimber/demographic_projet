package isen.java2.study.service;

import isen.java2.study.data.Person;
import isen.java2.study.service.util.FilesCrawler;
import isen.java2.study.service.util.VCardListener;
import isen.java2.study.service.util.VCardReader;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

/**
 * Created by Matthieu on 11/01/2016.
 */
//TODO prepare JUnit to test class
public class VCardRecorderService extends Thread{
    DBService dbService;
    Properties properties;
    VCardListener mListener;

    @Override
    public void run() {
        readAndSaveCards();
    }

    public VCardRecorderService(DBService dbService, Properties properties, VCardListener mListener) {
        this.dbService = dbService;
        this.properties = properties;
        this.mListener = mListener;
    }

    public void readAndSaveCards() {
        //TODO test implementation
        List<Path> files = FilesCrawler.getFiles(properties.getProperty("vcards.folder"));
        System.out.println("Starting registering into DB");
        for(Path p:files) {
            Person person = VCardReader.read(p);
            //mListener.newThingsToSay("Saving into Database: " + person.getFirstName() + " " + person.getLastName() + "\n");
            String phrase = "Saving into Database: " + person.getFirstName() + " " + person.getLastName() + "\n";
            mListener.newThingsToSay(phrase);
            try {
                sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dbService.save(person);
        }
        System.out.println("End of registering into DB");
    }
}
