package com.bank.abc.simdata.models;

public class ExecutorResult {
    private String phoneNumber;
    private boolean isTimeout;

    public ExecutorResult(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        isTimeout = false;
    }

    public boolean isTimeout() {
        return isTimeout;
    }

    public void setTimeout(boolean timeout) {
        isTimeout = timeout;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
