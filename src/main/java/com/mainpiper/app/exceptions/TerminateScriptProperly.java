package com.mainpiper.app.exceptions;

public class TerminateScriptProperly extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TerminateScriptProperly(String message) {
        super(message);
    }

    public TerminateScriptProperly() {
        super();
    }

}
