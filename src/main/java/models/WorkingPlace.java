package models;

import dateAdapter.DateParser;
import dateAdapter.LocalDateAdapterXml;
import exceptions.DateParserException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WorkingPlace {

    private int id;
    @XmlJavaTypeAdapter(LocalDateAdapterXml.class)
    private LocalDate begin;
    @XmlJavaTypeAdapter(LocalDateAdapterXml.class)
    private LocalDate end;
    private String title;
    private JobPosition jobPosition;

    public WorkingPlace() {
        begin = LocalDate.now();
        end = LocalDate.now();
        title = "";
        jobPosition = JobPosition.DEVELOPER;
    }

    public WorkingPlace(int id, LocalDate begin, LocalDate end, String title, JobPosition jobPosition) {
        this.id = id;
        this.begin = begin;
        this.end = end;
        this.title = title;
        this.jobPosition = jobPosition;
    }

    @Override
    public String toString() {
        return "WorkingPlace{" +
                "id=" + id +
                ", begin=" + begin +
                ", end=" + end +
                ", title='" + title + '\'' +
                ", jobPosition=" + jobPosition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkingPlace place = (WorkingPlace) o;
        return Objects.equals(id, place.id) &&
                Objects.equals(begin, place.begin) &&
                Objects.equals(end, place.end) &&
                Objects.equals(title, place.title) &&
                Objects.equals(jobPosition, place.jobPosition);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBegin() {
        return begin.toString();
    }

    public void setBegin(String begin) throws DateParserException {
        this.begin = DateParser.parse(begin);
    }

    public LocalDate getBeginLocal() {
        return begin;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end.toString();
    }

    public void setEnd(String end) throws DateParserException {
        this.end = DateParser.parse(end);
    }

    public LocalDate getEndLocal() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(JobPosition jobPosition) {
        this.jobPosition = jobPosition;
    }
}
