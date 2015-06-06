package com.fmc.edu.exception;

/**
 * Created by Yu on 2015/5/16.
 */
public class IdentityCodeException extends Exception {
    private String[] mArgs;

    public IdentityCodeException(String msg) {
        super(msg);
    }

    public IdentityCodeException(String msg, String... pArgs) {
        super(msg);
        this.mArgs = pArgs;
    }

    public String[] getArgs() {
        return mArgs;
    }
}
