package dateAdapter;

import exceptions.DateParserException;
import exceptions.FormatNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateParser {

    public final static String[] formats = {
            "yyyy-MM-dd", "yyyy/MM/dd",
    };

    public static LocalDate parse(String date) throws DateParserException
    {
        Logger log = Logger.getLogger(DateParser.class.getName());

        LocalDate localDate = null;

        for(String format : formats)
        {
            try
            {
                localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
            }
            catch (DateTimeParseException e)
            {
                log.log(Level.WARNING, e.getMessage());
            }
        }

        if(localDate == null)
            throw new DateParserException(date);

        return localDate;
    }

}
