package isen.java2.study.service.view;

import isen.java2.study.Application;
import isen.java2.study.service.DBService;
import isen.java2.study.service.StatService;
import isen.java2.study.service.VCardRecorderService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Matthieu on 13/01/2016.
 */
public class MainViewController {
    //Model element !
    private Properties property;
    private DBService dbService;
    private VCardRecorderService vCardRecorderService;
    private StatService statService;


    //FXML element
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
    private Spinner<Integer> LastnamesSpinner;

    @FXML
    public void initialize() {
        property = loadProperties();
        dbService = new DBService(property);
        vCardRecorderService = new VCardRecorderService(dbService,property);
        statService = new StatService(dbService);
    }

    @FXML
    public void handleReadVCard() {
        vCardRecorderService.readAndSaveCards();
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
}
