package com.nablarch.example.proman.web.common.authentication.exception;

/**
 * Exception occurring when user authentication fails. <br>
 * <br>
 * An exception class inheriting this class is created according to the authentication method. <br>
 * The information required to create the message to be submitted to the user(s) is retained in this class and its subclasses, and the message is not sent.
 * @author Masaya Seko
 */
public class AuthenticationException extends RuntimeException {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
}
