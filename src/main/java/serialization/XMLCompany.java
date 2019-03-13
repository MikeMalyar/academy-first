package serialization;

import models.Company;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XMLCompany implements Serialization {

    private Logger log = Logger.getLogger(XMLCompany.class.getName());

    @Override
    public void serialize(Object object, String path) {
        Company company = (Company)object;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(company, new File(path));
        }
        catch (JAXBException e) {
            log.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public Company deserialize(String path) {
        Company company = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            company = (Company) jaxbUnmarshaller.unmarshal(new File(path));
        }
        catch (JAXBException e) {
            log.log(Level.WARNING, e.getMessage());
        }

        return company;
    }
}
