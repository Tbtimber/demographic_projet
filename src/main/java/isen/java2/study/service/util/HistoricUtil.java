package isen.java2.study.service.util;

import javafx.scene.chart.XYChart;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Classutil that will manage the historic.tx file and give the data to the chart when saving
 *
 * Created by Matthieu on 16/01/2016.
 */
public class HistoricUtil {
    private static int linesAdded = 0;
    private static int currentSessionIndex = -1;

    private static final Path FILEPATH = Paths.get("historic.txt");
    //private static final Path FILEPATH = Paths.get("c:/isen/java2/project/historic.txt");

    public static void addDataToSerie(XYChart.Series serie){
        try {
            List<String> lines = VCardReader.getAllLines(FILEPATH);
            for(String l: lines) {
                String trimL = l.trim();
                String[] line = trimL.split(";");
                if(line.length >= 2) {
                    serie.getData().add(new XYChart.Data(Integer.parseInt(line[0]), Integer.parseInt(line[1])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkHistoricfileExistence() {
        if (!Files.exists(FILEPATH)) {
            try {
                FILEPATH.toFile().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeNewValueToFile() {
         checkHistoricfileExistence();
        try {
            FileWriter fw = new FileWriter(FILEPATH.toFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(System.getProperty("line.separator") + Integer.toString(currentSessionIndex )+ ";" + Integer.toString(linesAdded));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setSessionIndex() {
        try {
            List<String> lines = VCardReader.getAllLines(FILEPATH);
            if (lines.size() == 0)
                currentSessionIndex = 1;
            else
                currentSessionIndex = lines.size();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    public static void concatLinesAdded(int linesAdded) {
        HistoricUtil.linesAdded += linesAdded;
    }
}
