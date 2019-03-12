package models;

import dateAdapter.LocalDateAdapterJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.DeserializeException;
import exceptions.FormatNotFoundException;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.*;
import java.time.LocalDate;
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

    public void deserialize(String path) throws IOException, FormatNotFoundException, DeserializeException {
        String flag = findFormat(path);

        try {
            switch (flag) {
                case "xml":
                    deserializeXML(path);
                    break;
                case "json":
                    deserializeJSON(path);
                    break;
                case "yaml":
                    deserializeYAML(path);
                    break;
                default:
                    throw new FormatNotFoundException(path);
            }
        }
        catch (JAXBException | IOException e)
        {
            throw new DeserializeException(flag, path);
        }
    }

    public void serializeXML(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(this, new File(path));
    }

    public void deserializeXML(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Company company = (Company) jaxbUnmarshaller.unmarshal(new File(path));
        people = company.people;
    }

    public void serializeJSON(String path) throws IOException {
        File file = new File(path);

        FileWriter fw = new FileWriter(file);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapterJson())
                .create();

        fw.write(gson.toJson(this));

        fw.close();
    }

    public void deserializeJSON(String path) throws IOException {
        File file = new File(path);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapterJson())
                .create();

        Company company = gson.fromJson(new FileReader(file), Company.class);

        people = company.getPeople();
    }

    public void serializeYAML(String path) throws IOException {
        FileWriter fw = new FileWriter(new File(path));

        Yaml yaml = new Yaml();

        yaml.dump(this, fw);
    }

    public void deserializeYAML(String path) throws IOException {
        File file = new File(path);

        InputStream inputStream = new FileInputStream(file);

        Yaml yaml = new Yaml(new Constructor(Company.class));

        Company company = yaml.loadAs(inputStream, Company.class);
        people = company.people;
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
