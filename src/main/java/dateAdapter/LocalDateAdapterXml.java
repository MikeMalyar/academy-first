package dateAdapter;

import exceptions.DateParserException;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdapterXml extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String v) {
        try {
            return DateParser.parse(v);
        } catch (DateParserException e) {
            return null;
        }
    }

    @Override
    public String marshal(LocalDate v) {
        return v.toString();
    }
}

