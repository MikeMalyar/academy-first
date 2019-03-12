package models;

import dateAdapter.DateParser;
import dateAdapter.LocalDateAdapterXml;
import exceptions.DateParserException;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

    private int id;
    private String name;
    private String surname;
    @XmlJavaTypeAdapter(LocalDateAdapterXml.class)
    private LocalDate birthdate;
    private String address;
    private String phoneNumber;
    private String email;

    @XmlElementWrapper(name="courses")
    @XmlElements({
            @XmlElement(name = "course", type = Course.class)
    })
    private LinkedList<Course> courses;

    @XmlElementWrapper(name="workingPlaces")
    @XmlElements({
            @XmlElement(name = "workingPlace", type = WorkingPlace.class)
    })
    private LinkedList<WorkingPlace> workingPlaces;

    public Person() {
        courses = new LinkedList<>();

        name = "John";
        surname = "Doe";
        birthdate = LocalDate.now();
        address = "London";
        workingPlaces = new LinkedList<>();
        phoneNumber = "+38 050 05 05 050";
        email = "john_doe@gmail.com";
        courses.add(Course.Java);
        courses.add(Course.Python);
    }

    public Person(int id, String name, String surname, LocalDate birthdate, String address, String phoneNumber, String email, LinkedList<Course> courses, LinkedList<WorkingPlace> workingPlaces) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.courses = courses;
        this.workingPlaces = workingPlaces;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthdate=" + birthdate +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", courses=" + courses +
                ", workingPlaces=" + workingPlaces +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(name, person.name) &&
                Objects.equals(surname, person.surname) &&
                Objects.equals(birthdate, person.birthdate) &&
                Objects.equals(address, person.address) &&
                Objects.equals(phoneNumber, person.phoneNumber) &&
                Objects.equals(email, person.email) &&
                Objects.equals(courses, person.courses) &&
                workingPlaces.containsAll(person.workingPlaces);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LinkedList<Course> getCourses() {
        return courses;
    }

    public void setCourses(LinkedList<Course> courses) {
        this.courses = courses;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthdate() {
        return birthdate.toString();
    }

    public void setBirthdate(String birthdate) throws DateParserException{
        this.birthdate = DateParser.parse(birthdate);
    }

    public LocalDate getBirthdateLocal() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LinkedList<WorkingPlace> getWorkingPlaces() {
        return workingPlaces;
    }

    public void setWorkingPlaces(LinkedList<WorkingPlace> workingPlaces) {
        this.workingPlaces = workingPlaces;
    }

}
