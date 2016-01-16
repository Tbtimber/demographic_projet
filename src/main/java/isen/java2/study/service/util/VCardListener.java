package isen.java2.study.service.util;

/**
 * Created by Matthieu on 13/01/2016.
 */
public interface VCardListener {
    void newThingsToSay(String addToTextArea) ;
    void updateBarChart();
    void addAgeChartValue(String category, int ageValue);
}
