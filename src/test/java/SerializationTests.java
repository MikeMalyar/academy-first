import exceptions.DateParserException;
import models.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import serialization.JSONCompany;
import serialization.XMLCompany;
import serialization.YAMLCompany;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SerializationTests {

    private Person generatePerson() throws DateParserException {
        Person person = new Person();
        person.setName("Mike");
        person.setSurname("Malyarchuk");
        person.setBirthdate("1999-07-05");
        person.setAddress("Ruska str, 287");
        person.setPhoneNumber("+38 099 99 099 099");
        person.setEmail("___@gmail.com");
        person.getCourses().add(Course.JAVA);

        return person;
    }

    private WorkingPlace generateWorkingPlace() throws DateParserException {
        WorkingPlace place = new WorkingPlace();
        place.setBegin("2017-01-01");
        place.setEnd("2018-01-01");
        place.setTitle("SoftServe");
        place.setJobPosition(JobPosition.DEVELOPER);

        return place;
    }

    @Test
    public void xmlTest() {
        try {
            XMLCompany xml = new XMLCompany();

            WorkingPlace place = generateWorkingPlace();
            Person person = generatePerson();
            person.getWorkingPlaces().add(place);

            Company expected = new Company();
            expected.getPeople().add(person);

            Company actual;

            xml.serialize(expected, "src/test/resources/people.xml");

            actual = xml.deserialize("src/test/resources/people.xml");

            Assert.assertEquals(expected, actual);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jsonTest() {
        try {
            JSONCompany json = new JSONCompany();

            WorkingPlace place = generateWorkingPlace();
            Person person = generatePerson();
            person.getWorkingPlaces().add(place);

            Company expected = new Company();
            expected.getPeople().add(person);

            Company actual;

            json.serialize(expected, "src/test/resources/people.json");

            actual = json.deserialize("src/test/resources/people.json");

            Assert.assertEquals(expected, actual);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void yamlTest() {
        try {
            YAMLCompany yaml = new YAMLCompany();

            WorkingPlace place = generateWorkingPlace();
            Person person = generatePerson();
            person.getWorkingPlaces().add(place);

            Company expected = new Company();
            expected.getPeople().add(person);

            Company actual;

            yaml.serialize(expected, "src/test/resources/people.yaml");

            actual = yaml.deserialize("src/test/resources/people.yaml");

            Assert.assertEquals(expected, actual);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void exceptionsTest() throws IOException {
        File file = new File("src/test/resources/exceptions.xml");
        FileWriter fw = new FileWriter(file);

        fw.write("<company> + \n + <people> + \n + <person>");

        fw.close();

        Company expected = new Company();

        Assert.assertEquals(expected, new Company());
    }

}
