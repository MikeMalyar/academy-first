package serialization;

import models.Company;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class YAMLCompany implements Serialization {

    private Logger log = Logger.getLogger(XMLCompany.class.getName());

    @Override
    public void serialize(Object object, String path) {
        Company company = (Company)object;

        try {
            FileWriter fw = new FileWriter(new File(path));

            Yaml yaml = new Yaml();

            yaml.dump(company, fw);
        }
        catch (IOException e) {
            log.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public Company deserialize(String path) {
        Company company = null;

        try {
            File file = new File(path);

            InputStream inputStream = new FileInputStream(file);

            Yaml yaml = new Yaml(new Constructor(Company.class));

            company = yaml.loadAs(inputStream, Company.class);
        }
        catch (IOException e) {
            log.log(Level.WARNING, e.getMessage());
        }

        return company;
    }
}
