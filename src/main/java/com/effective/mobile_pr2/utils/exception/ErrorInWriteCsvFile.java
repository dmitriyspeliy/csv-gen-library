package com.effective.mobile_pr2.utils.exception;


public class ErrorInWriteCsvFile extends Exception {

    private final String textException;
    private final String code;

    public ErrorInWriteCsvFile(String textException) {
        this.textException = textException;
        this.code = String.valueOf(400);
    }

    public ErrorInWriteCsvFile(String textException, String code) {
        this.textException = textException;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return "\nERROR MESSAGE : " + textException + "\n" +
                "ERROR CODE : " + code;
    }

}
