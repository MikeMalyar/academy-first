package dataBase;

import dateAdapter.DateParser;
import exceptions.DateParserException;
import models.Course;
import models.JobPosition;
import models.Person;
import models.WorkingPlace;

import java.sql.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Properties;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/company";
    private static final String USER = "postgres";
    private static final String PASSWORD = "05071999";

    private Connection connection;

    public DBConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", USER);
        properties.setProperty("password", PASSWORD);
        properties.setProperty("autoReconnect", "true");

        connection = DriverManager.getConnection(URL, properties);
    }

    private LinkedList<WorkingPlace> getWorkingPlaces(int person_id) throws SQLException , DateParserException {
        LinkedList<WorkingPlace> workingPlaces = new LinkedList<>();

        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("select * from \"workingPlace\" where \"personId\" = " + person_id);

        while(set.next()) {
            int id = set.getInt("id");
            String title = set.getString("title");
            String position = set.getString("jobPosition");
            Date date1 = set.getDate("begin");
            Date date2 = set.getDate("end");

            LocalDate begin = DateParser.parse(date1.toString());
            LocalDate end = DateParser.parse(date2.toString());

            JobPosition jobPosition = null;
            for(JobPosition j : JobPosition.values()) {
                if(j.toString().equals(position)) {
                    jobPosition = j;
                    break;
                }
            }

            WorkingPlace place = new WorkingPlace();

            place.setId(id);
            place.setBegin(begin);
            place.setEnd(end);
            place.setTitle(title);
            place.setJobPosition(jobPosition);

            workingPlaces.add(place);
        }

        return workingPlaces;
    }

    public LinkedList<Person> getPersons() throws SQLException, DateParserException {
        LinkedList<Person> people = new LinkedList<>();

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

            person.setWorkingPlaces(getWorkingPlaces(person.getId()));

            people.add(person);
        }

        return people;
    }

    private void insertWorkingPlace(WorkingPlace place, int personId) throws SQLException {
        Statement statement = connection.createStatement();

        String values = "";
        values += "'" + place.getBegin() + "', ";
        values += "'" + place.getEnd() + "', ";
        values += "'" + place.getTitle() + "', ";
        values += "'" + place.getJobPosition() + "', ";
        values += "'" + personId + "'";

        String sql = "insert into \"workingPlace\" (\"begin\", \"end\", \"title\", \"jobPosition\", \"personId\")\n" +
                "values (" + values + ");";

        statement.execute(sql);
    }

    public void insertPerson(Person person) throws SQLException {
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

        try {
            LinkedList<Person> persons = getPersons();
            persons.sort(Comparator.comparing(Person::getId).reversed());

            for(WorkingPlace place : person.getWorkingPlaces()) {
                insertWorkingPlace(place, persons.getFirst().getId());
            }

            person.setId(persons.getFirst().getId());
        }
        catch (DateParserException de) {

        }
    }

    private void updateWorkingPlace(WorkingPlace place) throws SQLException {
        Statement statement = connection.createStatement();

        String values = "";
        values += "\"begin\" = " + "'" + place.getBegin() + "', ";
        values += "\"end\" = " + "'" + place.getEnd() + "', ";
        values += "\"title\" = " + "'" + place.getTitle() + "', ";
        values += "\"jobPosition\" = " + "'" + place.getJobPosition() + "'";

        String sql = "update \"workingPlace\" set " + values +
                "where \"id\" = " + place.getId() + ";";

        statement.execute(sql);
    }

    public void updatePerson(Person person) throws SQLException {
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
            updateWorkingPlace(place);
        }
    }

}
