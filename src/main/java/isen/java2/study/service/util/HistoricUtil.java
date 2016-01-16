package isen.java2.study.service.util;

import javafx.scene.chart.XYChart;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Matthieu on 16/01/2016.
 */
public class HistoricUtil {
    private static int linesAdded = 0;
    private static int currentSessionIndex = -1;

    private static final Path FILEPATH = Paths.get("c:/isen/java2/project/historic.txt");

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



    public static void writeNewValueToFile() {
        try {
            FileWriter fw = new FileWriter(FILEPATH.toFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(Integer.toString(currentSessionIndex )+ ";" + Integer.toString(linesAdded));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setSessionIndex() {
        try {
            List<String> lines = VCardReader.getAllLines(FILEPATH);
            currentSessionIndex = lines.size();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    public static void concatLinesAdded(int linesAdded) {
        HistoricUtil.linesAdded += linesAdded;
    }
    public static int getLinesAdded() {
        return linesAdded;
    }

}
