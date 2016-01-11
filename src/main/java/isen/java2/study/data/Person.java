package isen.java2.study.data;

import java.time.LocalDate;

public class Person {

	private String lastName;

	private String firstName;

	private Sex sex;

	private String streetName;

	private String city;

	private String state;

	private String email;

	private LocalDate dateOfBirth;

	private String bloodType;

	public Person(String lastName, String firstName, Sex sex, String streetName, String city, String state,
			String email, LocalDate dateOfBirth, String bloodType) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.sex = sex;
		this.streetName = streetName;
		this.city = city;
		this.state = state;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.bloodType = bloodType;
	}

	public Person() {
		super();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	@Override
	public String toString() {
		return "Person [lastName=" + lastName + ", firstName=" + firstName + ", sex=" + sex + ", streetName="
				+ streetName + ", city=" + city + ", state=" + state + ", email=" + email + ", dateOfBirth="
				+ dateOfBirth + ", bloodType=" + bloodType + "]";
	}

}
