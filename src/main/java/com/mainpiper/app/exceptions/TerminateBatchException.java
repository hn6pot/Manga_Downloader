package com.mainpiper.app.exceptions;

import lombok.Getter;

@Getter
public class TerminateBatchException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static final int EXIT_CODE_NO_DRIVER = 100;
    public static final int EXIT_CODE_URL_MALFORMED = 101;
    public static final int EXIT_CODE_DOWNLOAD_ERROR = 102;

    public static final int EXIT_CODE_COMPRESSION_FAIL = 110;

    public static final int EXIT_CODE_NO_SOURCE_PROVIDED = 120;
    public static final int EXIT_CODE_WRONG_SOURCE_PROVIDED = 121;
    public static final int EXIT_CODE_MANGA_UNKNOWN_FOR_SOURCE_PROVIDED = 122;
    public static final int EXIT_CODE_ERROR_DURING_JSON_UPDATE = 122;
    public static final int EXIT_CODE_ERROR_UPDATE_MANGA = 123;

    public static final int EXIT_CODE_STRANGE_FILE_FOUNDED = 130;

    public static final int EXIT_CODE_SQL_ERROR = 140;

    public static final int EXIT_CODE_UNKNOWN = 1;

    private int exitCode;

    public TerminateBatchException(int code, String message) {
        super(message);
        exitCode = code;
    }

    public TerminateBatchException(int code, Throwable cause) {
        super(cause);
        exitCode = code;
    }

    public TerminateBatchException(int code, String message, Throwable cause) {
        super(message, cause);
        exitCode = code;
    }
}
