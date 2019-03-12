package exceptions;

public class DeserializeException extends Exception {

    private String format;
    private String path;

    public DeserializeException(String format, String path) {
        this.format = format;
        this.path = path;
    }

    @Override
    public String toString() {
        return "An error occurred while deserializing file " + path + " using " + format.toUpperCase() + " format.";
    }
}
