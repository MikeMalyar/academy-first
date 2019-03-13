package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dateAdapter.LocalDateAdapterJson;
import models.Company;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONCompany implements Serialization {

    private Logger log = Logger.getLogger(JSONCompany.class.getName());

    @Override
    public void serialize(Object object, String path) {
        Company company = (Company)object;

        try {
            File file = new File(path);

            FileWriter fw = new FileWriter(file);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapterJson())
                    .create();

            fw.write(gson.toJson(company));

            fw.close();
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

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapterJson())
                    .create();

            company = gson.fromJson(new FileReader(file), Company.class);
        }
        catch (IOException e) {
            log.log(Level.WARNING, e.getMessage());
        }

        return company;
    }
}
