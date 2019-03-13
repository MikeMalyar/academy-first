import dataBase.PersonDao;
import models.Course;
import models.JobPosition;
import models.Person;
import models.WorkingPlace;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;

public class DataBaseTests {

    private Person generatePerson() {
        Person person = new Person();
        person.setName("Mike");
        person.setSurname("Malyarchuk");
        person.setBirthdate(LocalDate.now().withYear(1999).withMonth(7).withDayOfMonth(5));
        person.setAddress("Ruska str, 287");
        person.setPhoneNumber("+38 099 99 099 099");
        person.setEmail("___@gmail.com");
        person.getCourses().add(Course.JAVA);

        return person;
    }

    private WorkingPlace generateWorkingPlace() {
        WorkingPlace place = new WorkingPlace();
        place.setBegin(LocalDate.now().withYear(2017).withMonth(1).withDayOfMonth(1));
        place.setEnd(LocalDate.now().withYear(2018).withMonth(1).withDayOfMonth(1));
        place.setTitle("SoftServe");
        place.setJobPosition(JobPosition.DEVELOPER);

        return place;
    }

    @Test
    public void insertAndSelectTest() {
        PersonDao dao = new PersonDao();

        WorkingPlace place = generateWorkingPlace();
        Person expected = generatePerson();
        expected.getWorkingPlaces().add(place);

        dao.insert(expected);

        LinkedList<Person> persons = (LinkedList<Person>) dao.getAll();
        persons.sort(Comparator.comparing(Person::getId).reversed());

        Person actual = persons.getFirst();

        Assert.assertEquals(actual, expected);

        dao.deleteById(expected.getId());
    }

    @Test
    public void updateTest() {
        PersonDao dao = new PersonDao();

        WorkingPlace place = generateWorkingPlace();
        Person expected = generatePerson();
        expected.getWorkingPlaces().add(place);

        dao.insert(expected);

        expected.setName("Viktor");
        expected.setSurname("Pelepiak");
        expected.setBirthdate(LocalDate.now().withYear(1999).withMonth(11).withDayOfMonth(2));

        dao.update(expected);

        LinkedList<Person> persons = (LinkedList<Person>) dao.getAll();
        persons.sort(Comparator.comparing(Person::getId).reversed());

        Person actual = persons.getFirst();

        Assert.assertEquals(actual, expected);

        dao.deleteById(expected.getId());

    }

}
