package exceptions;

public class FormatNotFoundException extends Exception {

    private String path;

    public FormatNotFoundException(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Format hasn't been defined for file " + path + ".";
    }
}
