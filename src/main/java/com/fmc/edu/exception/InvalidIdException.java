package com.fmc.edu.exception;

/**
 * Created by Yu on 5/20/2015.
 */
public class InvalidIdException extends Exception {
    public InvalidIdException(Object pId) {
        super("不合法的id:" + pId);
    }

    public InvalidIdException(String msg, Object pId) {
        super(msg + pId);
    }
}
