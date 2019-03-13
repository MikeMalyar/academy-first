import dataBase.DBConnection;
import exceptions.DateParserException;
import models.Course;
import models.JobPosition;
import models.Person;
import models.WorkingPlace;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;

public class DataBaseTests {

    private Person generatePerson() throws DateParserException {
        Person person = new Person();
        person.setName("Mike");
        person.setSurname("Malyarchuk");
        person.setBirthdate("1999-07-05");
        person.setAddress("Ruska str, 287");
        person.setPhoneNumber("+38 099 99 099 099");
        person.setEmail("___@gmail.com");
        person.getCourses().add(Course.Java);

        return person;
    }

    private WorkingPlace generateWorkingPlace() throws DateParserException {
        WorkingPlace place = new WorkingPlace();
        place.setBegin("2017-01-01");
        place.setEnd("2018-01-01");
        place.setTitle("SoftServe");
        place.setJobPosition(JobPosition.Developer);

        return place;
    }

    @Test
    public void insertAndSelectTest() {
        try {
            DBConnection connection = new DBConnection();

            WorkingPlace place = generateWorkingPlace();
            Person person = generatePerson();
            person.getWorkingPlaces().add(place);

            connection.insertPerson(person);

            LinkedList<Person> persons = connection.getPersons();
            persons.sort(Comparator.comparing(Person::getId).reversed());

            Person person1 = persons.getFirst();

            Assert.assertEquals(person1, person);

            connection.deletePersonById(person.getId());
        }
        catch (SQLException | DateParserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest() {
        try {
            DBConnection connection = new DBConnection();

            WorkingPlace place = generateWorkingPlace();
            Person person = generatePerson();
            person.getWorkingPlaces().add(place);

            connection.insertPerson(person);

            person.setName("Viktor");
            person.setSurname("Pelepiak");
            person.setBirthdate("1999-11-02");

            connection.updatePerson(person);

            LinkedList<Person> persons = connection.getPersons();
            persons.sort(Comparator.comparing(Person::getId).reversed());

            Person person1 = persons.getFirst();

            Assert.assertEquals(person1, person);

            connection.deletePersonById(person.getId());
        }
        catch (SQLException | DateParserException e) {
            e.printStackTrace();
        }
    }

}
