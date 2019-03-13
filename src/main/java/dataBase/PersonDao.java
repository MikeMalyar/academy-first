package dataBase;

import dateAdapter.DateParser;
import exceptions.DateParserException;
import models.Course;
import models.Person;
import models.WorkingPlace;

import java.sql.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonDao {

    private Connection connection;
    private Logger log = Logger.getLogger(PersonDao.class.getName());

    public PersonDao() {
        try {
            connection = new DBConnection().getConnection();
        }
        catch (SQLException e) {
            log.log(Level.WARNING, e.getMessage());
        }
    }

    public List<Person> getAll() {
        List<Person> people = new LinkedList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select * from \"person\"");

            while(set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                String surname = set.getString("surname");
                String address = set.getString("address");
                String email = set.getString("email");
                String phoneNumber = set.getString("phoneNumber");
                Date date = set.getDate("birthdate");
                Array array = set.getArray("courses");

                String mas[] = (String[])array.getArray();

                LocalDate birthdate = DateParser.parse(date.toString());

                Person person = new Person();

                person.setId(id);
                person.setName(name);
                person.setSurname(surname);
                person.setAddress(address);
                person.setEmail(email);
                person.setPhoneNumber(phoneNumber);
                person.setBirthdate(birthdate);

                LinkedList<Course> courses = new LinkedList<>();
                for(String str : mas) {
                    for(Course c : Course.values()) {
                        if(c.toString().equals(str)) {
                            courses.add(c);
                        }
                    }
                }
                person.setCourses(courses);

                person.setWorkingPlaces((LinkedList<WorkingPlace>) new WorkingPlaceDao().getAllByPersonId(person.getId()));

                people.add(person);
            }
        }
        catch (SQLException | DateParserException e) {
            log.log(Level.WARNING, e.getMessage());
        }

        return people;
    }

    public void insert(Person person) {
        try {
            Statement statement = connection.createStatement();

            String values = "";
            values += "'" + person.getName() + "', ";
            values += "'" + person.getSurname() + "', ";
            values += "'" + person.getBirthdate() + "', ";
            values += "'" + person.getAddress() + "', ";
            values += "'" + person.getEmail() + "', ";
            values += "'" + person.getPhoneNumber() + "', ";
            values += "'{";
            for (Course course : person.getCourses()) {
                values = values.concat(course.toString());
                if (course != person.getCourses().getLast()) {
                    values = values.concat(",");
                }
            }
            values += "}'";

            String sql = "insert into \"person\" (\"name\", \"surname\", \"birthdate\", \"address\", \"email\", \"phoneNumber\", \"courses\")\n" +
                    "values (" + values + ");";

            statement.execute(sql);

            LinkedList<Person> persons = (LinkedList<Person>) getAll();
            persons.sort(Comparator.comparing(Person::getId).reversed());

            for(WorkingPlace place : person.getWorkingPlaces()) {
                new WorkingPlaceDao().insert(place, persons.getFirst().getId());
            }

            person.setId(persons.getFirst().getId());
        }
        catch (SQLException e) {
            log.log(Level.WARNING, e.getMessage());
        }
    }

    public void update(Person person) {
        try {
            Statement statement = connection.createStatement();

            String values = "";

            values += "\"name\" = " + "'" + person.getName() + "', ";
            values += "\"surname\" = " + "'" + person.getSurname() + "', ";
            values += "\"birthdate\" = " + "'" + person.getBirthdate() + "', ";
            values += "\"address\" = " + "'" + person.getAddress() + "', ";
            values += "\"email\" = " + "'" + person.getEmail() + "', ";
            values += "\"phoneNumber\" = " + "'" + person.getPhoneNumber() + "', ";

            values += "\"courses\" = " + "'{";
            for (Course course : person.getCourses()) {
                values = values.concat(course.toString());
                if (course != person.getCourses().getLast()) {
                    values = values.concat(",");
                }
            }
            values += "}'";

            String sql = "update \"person\" set " + values +
                    "where \"id\" = " + person.getId() + ";";

            statement.execute(sql);

            for(WorkingPlace place : person.getWorkingPlaces()) {
                new WorkingPlaceDao().update(place);
            }
        }
        catch (SQLException e) {
            log.log(Level.WARNING, e.getMessage());
        }
    }

    public void deleteById(int personId) {
        try {
            Statement statement = connection.createStatement();

            WorkingPlaceDao dao = new WorkingPlaceDao();

            LinkedList<WorkingPlace> places = (LinkedList<WorkingPlace>) dao.getAllByPersonId(personId);

            places.forEach(p ->
                    dao.deleteById(p.getId())
            );

            statement.execute("delete from \"person\" where \"id\" = " + personId);

        }
        catch (SQLException e) {
            log.log(Level.WARNING, e.getMessage());
        }
    }

}
