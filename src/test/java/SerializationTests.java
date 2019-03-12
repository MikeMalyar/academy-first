import exceptions.DeserializeException;
import exceptions.FormatNotFoundException;
import models.Company;
import models.JobPosition;
import models.Person;
import models.WorkingPlace;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SerializationTests {

    @Test
    public void xmlTest() {
        WorkingPlace place = new WorkingPlace();
        place.setTitle("SoftServe");
        place.setJobPosition(JobPosition.Developer);
        WorkingPlace place1 = new WorkingPlace();
        place1.setTitle("SharpMinds");
        place1.setJobPosition(JobPosition.ProjectManager);

        Person person = new Person();
        person.getWorkingPlaces().add(place1);
        Person person1 = new Person();
        person1.getWorkingPlaces().add(place1);

        Company company = new Company();
        company.getPeople().add(person);
        company.getPeople().add(person1);

        Company company1;

        try {
            company.serializeXML("src/test/resources/people.xml");

            company1 = new Company();
            company1.deserialize("src/test/resources/people.xml");

            Assert.assertEquals(person, person1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jsonTest() {
        WorkingPlace place = new WorkingPlace();
        place.setTitle("SoftServe");
        place.setJobPosition(JobPosition.Developer);
        WorkingPlace place1 = new WorkingPlace();
        place1.setTitle("SharpMinds");
        place1.setJobPosition(JobPosition.ProjectManager);

        Person person = new Person();
        person.getWorkingPlaces().add(place1);
        Person person1 = new Person();
        person1.getWorkingPlaces().add(place1);

        Company company = new Company();
        company.getPeople().add(person);
        company.getPeople().add(person1);

        Company company1;

        try {
            company.serializeJSON("src/test/resources/people.json");

            company1 = new Company();
            company1.deserialize("src/test/resources/people.json");

            Assert.assertEquals(person, person1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void yamlTest() {
        WorkingPlace place = new WorkingPlace();
        place.setTitle("SoftServe");
        place.setJobPosition(JobPosition.Developer);
        WorkingPlace place1 = new WorkingPlace();
        place1.setTitle("SharpMinds");
        place1.setJobPosition(JobPosition.ProjectManager);

        Person person = new Person();
        person.getWorkingPlaces().add(place1);
        Person person1 = new Person();
        person1.getWorkingPlaces().add(place1);

        Company company = new Company();
        company.getPeople().add(person);
        company.getPeople().add(person1);

        Company company1;

        try {
            company.serializeYAML("src/test/resources/people.yaml");

            company1 = new Company();
            company1.deserialize("src/test/resources/people.yaml");

            Assert.assertEquals(person, person1);
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
