package serialization;

import models.Company;

public interface Serialization {

    public void serialize(Object object, String path);
    public Company deserialize(String path);

}
