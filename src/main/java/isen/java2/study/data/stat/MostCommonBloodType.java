package isen.java2.study.data.stat;

import isen.java2.study.service.util.VCardListener;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Stat giving the most common blood types
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
        while (set.next()) {
            String bloodT = set.getString("bloodtype");
            int number = set.getInt(2);
            mListener.newThingsToSay("Blood type : " + bloodT + " - Number of people : "+ number+"\n");
            mListener.addBloodPieValue(bloodT,number);
        }
    }

}
