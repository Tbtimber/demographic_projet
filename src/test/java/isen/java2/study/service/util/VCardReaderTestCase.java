package isen.java2.study.service.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.junit.Test;

import isen.java2.study.data.Person;
import isen.java2.study.data.Sex;

public class VCardReaderTestCase {

	@Test
	public void shouldReadVCard() throws Exception {
		// GIVEN
		URL vcardURL = this.getClass().getClassLoader().getResource("vcard.vcf");
		Path vcardPath = Paths.get(vcardURL.toURI());
		// WHEN
		Person person = VCardReader.read(vcardPath);
		// THEN
		assertThat(person.getLastName()).isEqualTo("Stark");
		assertThat(person.getFirstName()).isEqualTo("Anthony");
		assertThat(person.getSex()).isEqualTo(Sex.MALE);
		assertThat(person.getCity()).isEqualTo("San Diego");
		assertThat(person.getStreetName()).isEqualTo("9826 La Jolla Farms Road");
		assertThat(person.getState()).isEqualTo("California");
		assertThat(person.getEmail()).isEqualTo("tony@shield.org");
		assertThat(person.getDateOfBirth()).isEqualTo(LocalDate.of(1965, 04, 04));
		assertThat(person.getBloodType()).isEqualTo("AB+");
	}

	@Test
	public void shouldReadSex() throws Exception {
		// GIVEN
		URL vcardURL = this.getClass().getClassLoader().getResource("vcard2.vcf");
		Path vcardPath = Paths.get(vcardURL.toURI());
		// WHEN
		Person person = VCardReader.read(vcardPath);
		// THEN
		assertThat(person.getSex()).isEqualTo(Sex.FEMALE);
	}

}
