package exceptions;

public class DateParserException extends Exception {

    private String date;

    public DateParserException(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Date format " + date + " hasn't been defined.";
    }
}
