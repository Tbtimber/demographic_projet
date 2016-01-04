package isen.java2.study.service.util;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import isen.java2.study.data.Person;

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
		// TODO return all lines of the file described in "path" parameter
		return null;
	}

	private static Person parseN(Person person, String line) {
		// TODO if line begins with "N:", then split the line with ";" separator
		// TODO the first element is the lastName, the second is the first name
		// TODO if the fourth element is equal to "Mr", set the sex to Sex.MALE,
		// Sex.FEMALE otherwise
		// TODO return the person you got as parameter, with the new values
		// inside
		return person;
	}

	private static Person parseEmail(Person person, String line) {
		// TODO if line begins with "EMAIL:", apply the correct substring to
		// retrieve the email
		// TODO return the person you got as parameter, with the new values
		// inside
		return person;
	}

	private static Person parseAddress(Person person, String line) {
		// TODO if line begins with "ADR:", then split the line with ";"
		// separator
		// TODO the third element is the streetName
		// TODO the fourth element is the city
		// TODO the fifth element is the state
		// TODO return the person you got as parameter, with the new values
		// inside
		return person;
	}

	private static Person parseDateOfBirth(Person person, String line) throws ParseException {
		// TODO if line begins with "BDAY:", parse the date thanks to
		// LocalDate.parse() and the given formatter
		// TODO return the person you got as parameter, with the new values
		// inside
		return person;
	}

	private static Person parseBloodType(Person person, String line) {
		// TODO if line begins with "CATEGORIES:", apply the correct substring
		// to retrieve the email
		// TODO return the person you got as parameter, with the new values
		// inside
		return person;
	}

}
