package com.fmc.edu.exception;

/**
 * Created by Yu on 5/25/2015.
 */
public class EncryptException extends Exception {
    private String[] mArgs;

    public EncryptException(String msg) {
        super(msg);
    }

    public EncryptException(String msg, String... pArgs) {
        super(msg);
        this.mArgs = pArgs;
    }

    public String[] getArgs() {
        return mArgs;
    }
}
