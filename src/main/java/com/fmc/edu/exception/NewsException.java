package com.fmc.edu.exception;

/**
 * Created by Yu on 2015/5/24.
 */
public class NewsException extends Exception {
    private String[] mArgs;

    public NewsException(String msg) {
        super(msg);
    }

    public NewsException(String msg, String... pArgs) {
        super(msg);
        this.mArgs = pArgs;
    }

    public String[] getArgs() {
        return mArgs;
    }
}
