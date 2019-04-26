package service;

public class ProgException extends Exception {

    String str1;

    ProgException(String str1) {
        this.str1 = str1;
    }

    public String getMessage() {
        return ("Exception Occured: " + str1);

    }
}
