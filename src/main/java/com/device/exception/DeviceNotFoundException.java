package com.device.exception;

public class DeviceNotFoundException extends RuntimeException {
    private static final long serialVerisionUID = 1;
    public DeviceNotFoundException(String message) {
        super(message);
    }
}
