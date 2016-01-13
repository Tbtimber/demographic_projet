package isen.java2.study;

import isen.java2.study.data.stat.AverageAgeByState;
import isen.java2.study.data.stat.CommonLastnamesByState;
import isen.java2.study.data.stat.MostCommonBloodType;
import isen.java2.study.data.stat.Stat;
import isen.java2.study.service.DBService;
import isen.java2.study.service.StatService;
import isen.java2.study.service.VCardRecorderService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Application {

	public static void main(String[] args) {
		Properties property = loadProperties();
		DBService dbService = new DBService(property);
		VCardRecorderService vCardRecorderService = new VCardRecorderService(dbService, property);
		StatService statService = new StatService(dbService);
		List<Stat> stats = new ArrayList<>();
		stats.add(new AverageAgeByState());
		stats.add(new MostCommonBloodType());
		stats.add(new CommonLastnamesByState(2));
		vCardRecorderService.readAndSaveCards();
		statService.printStats(stats);

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
