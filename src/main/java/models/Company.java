package models;

import exceptions.FormatNotFoundException;
import serialization.JSONCompany;
import serialization.XMLCompany;
import serialization.YAMLCompany;

import javax.xml.bind.annotation.*;
import java.io.*;
import java.util.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Company {

    @XmlElementWrapper(name="people")
    @XmlElements({
            @XmlElement(name = "person", type = Person.class)
    })
    private List<Person> people;

    public Company() {
        people = new LinkedList<>();
    }

    private String findFormat(String path) throws IOException {
        String res = "";

        File file = new File(path);

        FileReader fr = new FileReader(file);
        Scanner in = new Scanner(fr);

        while(in.hasNextLine())
        {
            String str = in.nextLine();

            if(str.contains("<people>"))
            {
                res = "xml";
                break;
            }
            if(str.contains("\"people\":["))
            {
                res = "json";
                break;
            }
            if(str.contains("people:"))
            {
                res = "yaml";
                break;
            }
        }

        fr.close();

        return res;
    }

    public void deserialize(String path) throws IOException, FormatNotFoundException {
        String flag = findFormat(path);


        switch (flag) {
            case "xml":
                XMLCompany xml = new XMLCompany();
                people = xml.deserialize(path).people;
                break;
            case "json":
                JSONCompany json = new JSONCompany();
                people = json.deserialize(path).people;
                break;
            case "yaml":
                YAMLCompany yaml = new YAMLCompany();
                people = yaml.deserialize(path).people;
                break;
            default:
                throw new FormatNotFoundException(path);
        }

    }

    @Override
    public String toString() {
        return "Company{" +
                "people=" + people +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company list = (Company) o;
        return people.containsAll(list.people);
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }
}
