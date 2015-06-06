package com.fmc.edu.exception;

/**
 * Created by dylanyu on 5/12/2015.
 */
public class LoginException extends Exception {
    private String[] mArgs;

    public LoginException(String msg) {
        super(msg);
    }

    public LoginException(String msg, String... pArgs) {
        super(msg);
        this.mArgs = pArgs;
    }

    public String[] getArgs() {
        return mArgs;
    }
}
