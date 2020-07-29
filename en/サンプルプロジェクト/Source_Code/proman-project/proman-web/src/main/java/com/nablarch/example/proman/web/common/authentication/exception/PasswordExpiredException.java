package com.nablarch.example.proman.web.common.authentication.exception;

import java.util.Date;

/**
 * Exception occurring when the password is found to be expired during user authentication.
 * <p/>
 * The user ID of the user, the expiration date of the password and the business date used for the check are retained.
 * @author Nabu Rakutaro
 */
public class PasswordExpiredException extends AuthenticationException {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** User ID */
    private final String userId;

    /** Expiration date of password */
    private final Date passwordExpirationDate;

    /** Business date */
    private final Date businessDate;

    /**
     * Constructor.
     * @param userId: User ID
     * @param passwordExpirationDate: Expiration date of password
     * @param businessDate: Business date
     */
    public PasswordExpiredException(String userId, Date passwordExpirationDate, Date businessDate) {
        this.userId = userId;
        this.businessDate = businessDate;
        this.passwordExpirationDate = passwordExpirationDate;
    }

    /**
     * Acquires user ID.
     * @return: User ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Acquires expiration date of password.
     * @return: Expiration date of password
     */
    public Date getPasswordExpirationDate() {
        return passwordExpirationDate;
    }

    /**
     * Acquires business date.
     * @return: Business date
     */
    public Date getBusinessDate() {
        return businessDate;
    }
}
