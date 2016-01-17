package isen.java2.study.service.view;

import isen.java2.study.Application;
import isen.java2.study.data.stat.AverageAgeByState;
import isen.java2.study.data.stat.CommonLastnamesByState;
import isen.java2.study.data.stat.MostCommonBloodType;
import isen.java2.study.data.stat.Stat;
import isen.java2.study.service.DBService;
import isen.java2.study.service.StageService;
import isen.java2.study.service.StatService;
import isen.java2.study.service.VCardRecorderService;
import isen.java2.study.service.util.HistoricUtil;
import isen.java2.study.service.util.VCardListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.beans.EventHandler;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Main view controller class
 * Created by Matthieu on 13/01/2016.
 */
public class  MainViewController implements VCardListener, ChangeListener{
    //Model element !
    private Properties property;
    private DBService dbService;
    private VCardRecorderService vCardRecorderService;
    private StatService statService;

    StringProperty stringProperty;

    //Age chart variables
    private CategoryAxis ageXAxis;
    private NumberAxis ageYAxis;
    private XYChart.Series seriesAge;


    //Blood chart variables
    private ObservableList<PieChart.Data> bloodPieData;




    //FXML element
    @FXML
    private PieChart bloodPie;
    @FXML
    private BarChart barChartAge;
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
    private Button emptyDatabseButton;


    @FXML
    private Button gotoHistoric;

    private Scene prepareHistoricScene() {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        HistoricUtil.writeNewValueToFile();

        xAxis.setLabel("Session index");
        yAxis.setLabel("Lines added to Database");

        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis,yAxis);
        lineChart.setTitle("Lines added to Database per session");
        XYChart.Series serie = new XYChart.Series();
        serie.setName("Lines");
        HistoricUtil.addDataToSerie(serie);
        lineChart.getData().add(serie);

        return new Scene(lineChart, 760,573);
    }


    @FXML
    public void handleTruncateButton() {
        newThingsToSay("Started truncating peson table\n.......................\n");
        dbService.truncatePersonTable();
        newThingsToSay("Table: 'person' emptied\n");
    }

    @FXML
    public void handleGoToHistoric() {
        StageService.getInstance().getPrimaryStage().setScene(prepareHistoricScene());
    }

    public void updateBarChart() {
        barChartAge.getData().add(seriesAge);
    }

    public void clearAgeChart() {
        seriesAge.getData().clear();
        barChartAge.getData().clear();
    }

    public void addAgeChartValue(String category, int ageValue) {
        seriesAge.getData().add(new XYChart.Data(category, ageValue));
    }

    @Override
    public void addBloodPieValue(String category, int ageValue) {
        bloodPieData.add(new PieChart.Data(category, ageValue));
    }

    public void updateBloodPie() {
        bloodPie.setData(bloodPieData);
    }



    public void clearBloodPie() {
        bloodPieData.clear();
        bloodPie.getData().clear();
    }

    @FXML
    public void initialize() {
        HistoricUtil.setSessionIndex();

        stringProperty = new SimpleStringProperty("Welcome :) \n");
        stringProperty.addListener(this);

        property = loadProperties();
        dbService = new DBService(property);

        statService = new StatService(dbService);
        textArea.setText("Welcome :) \n");

        //Initialize the age chart
        seriesAge = new XYChart.Series<String,Integer>();
        NumberAxis ageYAxis = new NumberAxis();
        ageXAxis = new CategoryAxis();


        ageXAxis.setLabel("State");
        ageYAxis.setLabel("Age");
        seriesAge.setName("Age by state");


        //Initialize the blood pie chart
        bloodPieData = FXCollections.observableArrayList();
    }

    @FXML
    public void handleReadVCard() {
        try {
            VCardRecorderService recorderService = new VCardRecorderService(dbService,property,this, stringProperty);
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
        if(age.isSelected()) {
            clearAgeChart();
            stats.add(new AverageAgeByState(this));
            updateBarChart();
        }
        if(bloodType.isSelected()) {
            clearBloodPie();
            stats.add(new MostCommonBloodType(this));
            updateBloodPie();
        }
        if(lastNames.isSelected()) {
            stats.add(new CommonLastnamesByState(getLastnameValue(), this));
        }
        statService.printStats(stats, this);
    }

    private int getLastnameValue() {
        try {
            int val = Integer.parseInt(textField.getText());
            if(val > 1) {
                return val;
            } else {
                return CommonLastnamesByState.DEFAULT_N_VALUE;
            }
        } catch (NumberFormatException e) {
           // e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(StageService.getInstance().getPrimaryStage());
            alert.setTitle("Error");
            alert.setHeaderText("Error: The value entered is incorrect");
            alert.setContentText("The default value will be used for the moment : " + CommonLastnamesByState.DEFAULT_N_VALUE);
            alert.show();
            return CommonLastnamesByState.DEFAULT_N_VALUE;
        }
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
