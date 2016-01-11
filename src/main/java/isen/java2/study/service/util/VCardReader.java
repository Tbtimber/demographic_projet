package isen.java2.study.service.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import isen.java2.study.data.Person;
import isen.java2.study.data.Sex;

public class VCardReader {

	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	public static Person read(Path path) {
		Person person = new Person();
		try {
			List<String> lines = getAllLines(path);
			for (String line : lines) {
				person = parseN(person, line);
				person = parseEmail(person, line);
				person = parseAddress(person, line);
				person = parseDateOfBirth(person, line);
				person = parseBloodType(person, line);
			}
		} catch (IOException | ParseException e) {
			System.err.println("Error while reading VCard content");
		}
		return person;

	}

	private static List<String> getAllLines(Path path) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        if(Files.exists(path)) {
            FileReader fr = new FileReader(path.toFile());
            BufferedReader br = new BufferedReader(fr);
            String currentLine = null;
            while ((currentLine = br.readLine()) != null) {
                lines.add(currentLine);
            }
        }
		return lines;
	}

	private static Person parseN(Person person, String line) {
        //String trimedLine = line.trim();
        String trimedLine = line;
        if(trimedLine.startsWith("N:")) {
            trimedLine = trimedLine.substring(2);
            String infos[] = trimedLine.split(";");
            if(infos.length == 4) {
                person.setLastName(infos[0]);
                person.setFirstName(infos[1]);
                if(infos[3].matches("Mr"))
                    person.setSex(Sex.MALE);
                else
                    person.setSex(Sex.FEMALE);
            }
        }
		return person;
	}

	private static Person parseEmail(Person person, String line) {
        //String trimedLine = line.trim();
        String trimedLine = line;
        if(trimedLine.startsWith("EMAIL:")) {
            trimedLine = trimedLine.substring(6);
            person.setEmail(trimedLine);
        }
		return person;
	}

	private static Person parseAddress(Person person, String line) {
        String trimedLine = line.trim();
        //String trimedLine = line;
        if(trimedLine.startsWith("ADR:")) {
            String infos[] = trimedLine.substring(4).split(";");
            if(infos.length >= 5) {
                person.setStreetName(infos[2]);
                person.setCity(infos[3]);
                person.setState(infos[4]);
            }
        }
		return person;
	}

	private static Person parseDateOfBirth(Person person, String line) throws ParseException {
        //String trimedLine = line.trim();
        String trimedLine = line;
        if(trimedLine.startsWith("BDAY:")) {
            person.setDateOfBirth(LocalDate.parse(trimedLine.substring(5), DateTimeFormatter.ofPattern("yyyyMMdd")));
        }
		return person;
	}

	private static Person parseBloodType(Person person, String line) {
        //String trimmedLine = line.trim();
        String trimedLine = line;
        if(trimedLine.startsWith("CATEGORIES:")) {
            trimedLine = trimedLine.substring(11);
            person.setBloodType(trimedLine);
        }
		return person;
	}

}
