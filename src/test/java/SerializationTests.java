import exceptions.DateParserException;
import exceptions.DeserializeException;
import exceptions.FormatNotFoundException;
import models.*;
import org.testng.Assert;
import org.testng.annotations.Test;

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
    public void xmlTest() {
        try {
            WorkingPlace place = generateWorkingPlace();
            Person person = generatePerson();
            person.getWorkingPlaces().add(place);

            Company company = new Company();
            company.getPeople().add(person);

            Company company1;

            company.serializeXML("src/test/resources/people.xml");

            company1 = new Company();
            company1.deserialize("src/test/resources/people.xml");

            Assert.assertEquals(company, company1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jsonTest() {
        try {
            WorkingPlace place = generateWorkingPlace();
            Person person = generatePerson();
            person.getWorkingPlaces().add(place);

            Company company = new Company();
            company.getPeople().add(person);

            Company company1;

            company.serializeJSON("src/test/resources/people.json");

            company1 = new Company();
            company1.deserialize("src/test/resources/people.json");

            Assert.assertEquals(company, company1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void yamlTest() {
        try {
            WorkingPlace place = generateWorkingPlace();
            Person person = generatePerson();
            person.getWorkingPlaces().add(place);

            Company company = new Company();
            company.getPeople().add(person);

            Company company1;

            company.serializeYAML("src/test/resources/people.yaml");

            company1 = new Company();
            company1.deserialize("src/test/resources/people.yaml");

            Assert.assertEquals(company, company1);
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

        DeserializeException e = new DeserializeException("xml", "src/test/resources/exceptions.xml"), e1 = new DeserializeException("", "");

        Company company = new Company();

        try {
            company.deserialize("src/test/resources/exceptions.xml");
        }
        catch (DeserializeException de) {
            e1 = de;
        }
        catch (FormatNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        Assert.assertEquals(company, new Company());

        Assert.assertEquals(e1.toString(), e.toString());
    }

}
