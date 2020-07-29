package com.nablarch.example.proman.web.common.authentication.exception;

/**
 * Exception occurring when authentication fails due to a mismatch in account information.
 * <p/>
 * The user ID of the applicable user is maintained.
 * @author Nabu Rakutaro
 */
public class AuthenticationFailedException extends AuthenticationException {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** User ID */
    private final String userId;

    /**
     * Constructor.
     * @param userId: User ID
     */
    public AuthenticationFailedException(String userId) {
        this.userId = userId;
    }

    /**
     * Acquires user ID.
     * @return: User ID
     */
    public String getUserId() {
        return userId;
    }
}
