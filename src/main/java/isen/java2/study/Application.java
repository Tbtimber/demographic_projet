package isen.java2.study;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Application {

	public static void main(String[] args) {
		//TODO ask teacher about StatService
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
