package com.nablarch.example.proman.web.common.authentication.exception;

/**
 * Exception occurring when the user ID is locked during user authentication.
 * <p/>
 * The user ID of the applicable user and the number of authentication failures before locking the user ID is retained.
 * @author Nabu Rakutaro
 */
public class UserIdLockedException extends AuthenticationException {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** User ID */
    private final String userId;

    /** Number of authentication failures before locking the user ID */
    private final int failedCountToLock;

    /**
     * Constructor.
     * @param userId: User ID
     * @param failedCountToLock: Number of authentication failures before locking the user ID
     */
    public UserIdLockedException(String userId, int failedCountToLock) {
        this.userId = userId;
        this.failedCountToLock = failedCountToLock;
    }

    /**
     * Acquires user ID.
     * @return: User ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Acquires the number of authentication failures before locking the user ID.
     * @return: Number of authentication failures before locking the user ID
     */
    public int getFailedCountToLock() {
        return failedCountToLock;
    }
}
