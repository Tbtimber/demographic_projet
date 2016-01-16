package isen.java2.study.service;

import isen.java2.study.data.Person;
import isen.java2.study.service.util.FilesCrawler;
import isen.java2.study.service.util.VCardListener;
import isen.java2.study.service.util.VCardReader;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

/**
 * Created by Matthieu on 11/01/2016.
 */
//TODO prepare JUnit to test class
public class VCardRecorderService extends Task{
    DBService dbService;
    Properties properties;
    VCardListener mListener;
    StringProperty stringProperty;

    public VCardRecorderService(DBService dbService, Properties properties, VCardListener mListener, StringProperty stringProperty) {
        this.dbService = dbService;
        this.properties = properties;
        this.mListener = mListener;
        this.stringProperty = stringProperty;
    }


    public synchronized void readAndSaveCards() {
        //TODO test implementation
        List<Path> files = FilesCrawler.getFiles(properties.getProperty("vcards.folder"));
        System.out.println("Starting registering into DB");
        int i = 0;
        for(Path p:files) {
            Person person = VCardReader.read(p);
            //mListener.newThingsToSay("Saving into Database: " + person.getFirstName() + " " + person.getLastName() + "\n");
            String phrase = "Saving into Database: " + person.getFirstName() + " " + person.getLastName() + "\n";
            //mListener.newThingsToSay(phrase);
            i++;
           // stringProperty.setValue(phrase);
            updateProgress(i,3000);
            updateMessage(phrase);
            updateTitle(phrase);
            dbService.save(person);
        }
        System.out.println("End of registering into DB");
    }

    @Override
    protected Object call() throws Exception {
        readAndSaveCards();
        return null;
    }


}
