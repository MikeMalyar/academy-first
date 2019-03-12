import dataBase.DBConnection;
import exceptions.DateParserException;
import models.Person;
import models.WorkingPlace;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class DataBaseTests {

    @Test
    public void firstTest() {
        try {
            DBConnection connection = new DBConnection();

            LinkedList<Person> people = connection.getPersons();

            /*people.getFirst().setName("Joseph");
            connection.updatePerson(people.stream().filter(p -> p.getId() == 1).collect(Collectors.toList()).get(0));

            people = connection.getPersons();*/

            System.out.println(people);
        }
        catch (SQLException | DateParserException e) {
            e.printStackTrace();
        }
    }

}
