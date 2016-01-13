package isen.java2.study.data.stat;

import isen.java2.study.service.util.VCardListener;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Matthieu on 12/01/2016.
 */
public class MostCommonBloodType implements Stat {
    private VCardListener mListener;
    public MostCommonBloodType(VCardListener mListener) {
        this.mListener = mListener;
    }
    private static final String QUERY = "SELECT bloodtype, count(id) as total FROM person " +
            "GROUP BY bloodtype ORDER BY total DESC;";
    private static final String DESCRIPTION = "Most common blood type";



    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getQuery() {
        return QUERY;
    }

    @Override
    public void handle(ResultSet set) throws SQLException {
        //TODO are values correct ?
        //Precedent result : "Blood type :  - Number of people : 1106"
        set.next();
        String bloodT = set.getString("bloodtype");
        int number = set.getInt(2);
        //System.out.println("Blood type : " + bloodT + " - Number of people : "+ number);
        mListener.newThingsToSay("Blood type : " + bloodT + " - Number of people : "+ number+"\n");
        while (set.next()) {

        }
    }

}
