package com.vironit.onlinepharmacy.dao;

/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository}
 */
@Deprecated
public class DaoException extends RuntimeException {

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
