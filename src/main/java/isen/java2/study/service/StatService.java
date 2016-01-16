package isen.java2.study.service;

import isen.java2.study.data.stat.Stat;
import isen.java2.study.service.util.VCardListener;

import java.util.List;

/**
 *
 * Created by Matthieu on 11/01/2016.
 */
//TODO Create Junit for this class
public class StatService {
    DBService dbService;

    public StatService(DBService dbService, List<Stat> mstats, VCardListener mListener) {
        this.dbService = dbService;
    }



    public StatService(DBService dbService) {
        this.dbService = dbService;
    }

    public void printStats(List<Stat> stats, VCardListener mListener) {
        //TODO test implementation
        for(Stat st:stats) {
            //System.out.println("\n\n=====" + st.getDescription() + "=====");
            mListener.newThingsToSay("\n\n=====" + st.getDescription() + "=====\n");
            dbService.executeStat(st);
        }
    }
}
