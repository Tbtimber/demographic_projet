package isen.java2.study.service.view;

import isen.java2.study.Application;
import isen.java2.study.data.stat.AverageAgeByState;
import isen.java2.study.data.stat.CommonLastnamesByState;
import isen.java2.study.data.stat.MostCommonBloodType;
import isen.java2.study.data.stat.Stat;
import isen.java2.study.service.DBService;
import isen.java2.study.service.StatService;
import isen.java2.study.service.VCardRecorderService;
import isen.java2.study.service.util.VCardListener;
import javafx.application.Preloader;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Matthieu on 13/01/2016.
 */
public class MainViewController implements VCardListener, ChangeListener{
    //Model element !
    private Properties property;
    private DBService dbService;
    private VCardRecorderService vCardRecorderService;
    private StatService statService;

    StringProperty stringProperty;

    //FXML element
    @FXML
    private BarChart barChart;
    @FXML
    private Button clearText;
    @FXML
    private TextArea textArea;
    @FXML
    private Button readVCardButton;
    @FXML
    private Button getStatButton;
    @FXML
    private RadioButton lastNames;
    @FXML
    private RadioButton age;
    @FXML
    private RadioButton bloodType;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TextField textField;

    @FXML
    public void initialize() {
        stringProperty = new SimpleStringProperty("Welcome :) \n");
        stringProperty.addListener(this);

        property = loadProperties();
        dbService = new DBService(property);

        statService = new StatService(dbService);
        textArea.setText("Welcome :) \n");


    }

    @FXML
    public void handleReadVCard() {
        //vCardRecorderService.readAndSaveCards();
        //vCardRecorderService.start();
        try {
            VCardRecorderService recorderService = new VCardRecorderService(dbService,property,this, stringProperty);
            //recorderService.setDaemon(true);
            //recorderService.start();
            //recorderService.readAndSaveCards();
            recorderService.messageProperty().addListener(this);
            Thread th = new Thread(recorderService);
            th.setDaemon(true);
            progressBar.progressProperty().bind(recorderService.progressProperty());
            th.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void handleClear() {
        textArea.clear();
    }

    private static Properties loadProperties() {
        System.out.println("Loading properties...");
        Properties properties = new Properties();
        InputStream fileStream = Application.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(fileStream);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier de propriétés");
        }
        return properties;
    }

    @FXML
    public void handleGetStats() {
        List<Stat> stats = new ArrayList<>();
        if(age.isSelected())
            stats.add(new AverageAgeByState(this));
        if(bloodType.isSelected()) {
            stats.add(new MostCommonBloodType(this));
        }
        if(lastNames.isSelected()) {
            stats.add(new CommonLastnamesByState(2, this));
        }
        statService.printStats(stats, this);
    }

    @FXML
    public void handleProgressBar() {
    }

    @FXML
    public void handleIterationValue() {
        if(lastNames.isSelected()) {
            textField.setEditable(true);
        } else {
            textField.setEditable(false);
        }
    }

    @Override
    public synchronized void newThingsToSay(String phrase) {
        textArea.appendText(phrase);
        textArea.setScrollTop(Double.MAX_VALUE);
        textArea.appendText("");
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        newThingsToSay((String) newValue);
    }
}
