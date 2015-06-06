package com.fmc.edu.exception;

/**
 * Created by Yu on 2015/5/15.
 */
public class ProfileException extends Exception {
    private String[] mArgs;

    public ProfileException(String msg) {
        super(msg);
    }

    public ProfileException(String msg, String... pArgs) {
        super(msg);
        this.mArgs = pArgs;
    }

    public String[] getArgs() {
        return mArgs;
    }
}
