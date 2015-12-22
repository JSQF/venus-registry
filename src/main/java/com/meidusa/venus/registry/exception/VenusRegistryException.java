package com.meidusa.venus.registry.exception;

/**
 * Created by huawei on 12/18/15.
 */
public class VenusRegistryException extends Exception {
    public VenusRegistryException() {
        super();
    }

    public VenusRegistryException(String message) {
        super(message);
    }

    public VenusRegistryException(String message, Throwable cause) {
        super(message, cause);
    }

    public VenusRegistryException(Throwable cause) {
        super(cause);
    }
}
