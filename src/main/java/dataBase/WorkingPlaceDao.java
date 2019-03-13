package dataBase;

import dateAdapter.DateParser;
import exceptions.DateParserException;
import models.JobPosition;
import models.WorkingPlace;

import java.sql.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkingPlaceDao {

    private Connection connection;
    private Logger log = Logger.getLogger(WorkingPlaceDao.class.getName());

    public WorkingPlaceDao() {
        try {
            connection = new DBConnection().getConnection();
        }
        catch (SQLException e) {
            log.log(Level.WARNING, e.getMessage());
        }
    }

    public List<WorkingPlace> getAllByPersonId(int personId) {
        List<WorkingPlace> workingPlaces = new LinkedList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select * from \"workingPlace\" where \"personId\" = " + personId);

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
        }
        catch (SQLException | DateParserException e) {
            log.log(Level.WARNING, e.getMessage());
        }

        return workingPlaces;
    }

    public void insert(WorkingPlace place, int personId) {
        try {
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

            List<WorkingPlace> places = getAllByPersonId(personId);
            places.sort(Comparator.comparing(WorkingPlace::getId).reversed());

            place.setId(places.get(0).getId());

        }
        catch(SQLException e) {
            log.log(Level.WARNING, e.getMessage());
        }
    }

    public void update(WorkingPlace place) {
        try {
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
        catch (SQLException e) {
            log.log(Level.WARNING, e.getMessage());
        }
    }

    public void deleteById(int placeId) {
        try {
            Statement statement = connection.createStatement();

            statement.execute("delete from \"workingPlace\" where \"id\" = " + placeId);
        }
        catch (SQLException e) {
            log.log(Level.WARNING, e.getMessage());
        }
    }
}
