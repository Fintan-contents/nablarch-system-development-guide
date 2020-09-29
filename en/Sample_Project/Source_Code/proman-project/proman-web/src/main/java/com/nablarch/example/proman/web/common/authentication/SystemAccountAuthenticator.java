package com.nablarch.example.proman.web.common.authentication;

import com.nablarch.example.proman.entity.SystemAccount;
import com.nablarch.example.proman.web.common.authentication.exception.AuthenticationFailedException;
import com.nablarch.example.proman.web.common.authentication.exception.PasswordExpiredException;
import com.nablarch.example.proman.web.common.authentication.encrypt.PasswordEncryptor;
import com.nablarch.example.proman.web.common.authentication.exception.UserIdLockedException;
import nablarch.common.dao.NoDataException;
import nablarch.common.dao.UniversalDao;
import nablarch.core.date.SystemTimeUtil;
import nablarch.core.db.connection.AppDbConnection;
import nablarch.core.db.statement.SqlPStatement;
import nablarch.core.db.transaction.SimpleDbTransactionExecutor;
import nablarch.core.db.transaction.SimpleDbTransactionManager;
import nablarch.core.util.DateUtil;

import java.util.Calendar;

/**
 * Class for password authentication for account information saved in a database. <br>
 * <br>
 * Below are the characteristics of the PasswordAuthenticator.
 * <ul>
 * <li>Enables password authentication using account information saved in a database. </li>
 * <li>Enables the expiration dates of passwords to be checked during authentication. </li>
 * <li>Locks user IDs if authentication fails consecutively the specified number of times. (The locking feature is only enabled if a permissible number of failures (greater than 0) is specified.)</li>
 * </ul>
 * Transactions are internally committed by PasswordAuthenticator as a database update process is required during the authentication process regardless of whether authentication is successful or fails. <br>
 * For this reason, {@link SimpleDbTransactionManager} must be set for PasswordAuthenticator so that the transaction that is used is separated from individual applications.
 * This stops PasswordAuthenticator's transaction control from affecting the processes of individual applications.
 *
 * @author Masaya Seko
 */
public class SystemAccountAuthenticator implements PasswordAuthenticator {

    /** Number of authentication failures before locking the user ID */
    private int failedCountToLock;

    /** {@link PasswordEncryptor} used for password encryption */
    private PasswordEncryptor passwordEncryptor;

    /** {@link SimpleDbTransactionManager} used for transaction control of databases */
    private SimpleDbTransactionManager dbManager;

    /** Prefix of SQL_ID */
    private static final String SQL_ID_PREFIX = SystemAccountAuthenticator.class.getName() + '#';

    /** Default constructor. */
    public SystemAccountAuthenticator() {
        failedCountToLock = 0;
    }

    /**
     * Set the number of authentication failures before locking the user ID.
     *
     * @param failedCountToLock Number of authentication failures before locking the user ID
     */
    public void setFailedCountToLock(int failedCountToLock) {
        this.failedCountToLock = failedCountToLock;
    }

    /**
     * Set the {@link PasswordEncryptor} used for password encryption.
     *
     * @param passwordEncryptor {@link PasswordEncryptor} used for password encryption
     */
    public void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
    }

    /**
     * Set {@link SimpleDbTransactionManager} used for transaction control of databases.
     *
     * @param dbManager {@link SimpleDbTransactionManager} used for transaction control of databases
     */
    public void setDbManager(SimpleDbTransactionManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * The user is authenticated using the account information.
     *
     * @param userId User ID
     * @param password Password
     *
     * @throws AuthenticationFailedException When a user matching the user ID or password cannot be found
     * @throws UserIdLockedException When a user ID is locked. Authentication is still not performed when this exception is thrown.
     * @throws PasswordExpiredException When a password is expired. When this exception is thrown, authentication using the old password is successful.
     */
    @Override
    public void authenticate(final String userId, final String password)
            throws AuthenticationFailedException, UserIdLockedException, PasswordExpiredException {

        if (userId == null || password == null) {
            throw new AuthenticationFailedException(userId);
        }

        // The time is truncated from the current date/time as the expiration date is managed only as a date.
        final java.sql.Date sysDate = convert(DateUtil.getDate(SystemTimeUtil.getDateString()));
        final SystemAccount account;
        try {
            account = UniversalDao.findBySqlFile(
                    SystemAccount.class,
                    "FIND_SYSTEM_ACCOUNT", new Object[]{userId, sysDate});
        } catch (NoDataException ignored) {
            // When a user matching the user ID cannot be found
            throw new AuthenticationFailedException(userId);
        }

        authenticate(account, password, sysDate);
    }

    /**
     * The system account is authenticated using the password.
     *
     * @param account System account
     * @param password Password
     * @param businessDate Business date
     *
     * @throws AuthenticationFailedException When a user matching the user ID or password cannot be found
     * @throws UserIdLockedException When a user ID is locked. Authentication is still not performed when this exception is thrown.
     * @throws PasswordExpiredException When a password is expired. When this exception is thrown, authentication using the old password is successful.
     */
    private void authenticate(SystemAccount account, String password, java.sql.Date businessDate)
            throws AuthenticationFailedException, UserIdLockedException, PasswordExpiredException {

        if (account.getFailedCount() > failedCountToLock) {
            throw new UserIdLockedException(String.valueOf(account.getUserId()), failedCountToLock);
        }

        // The input password is encrypted according to the encryption logic
        String encryptedPassword = passwordEncryptor.encrypt(String.valueOf(account.getUserId()), password);

        // The account is authenticated.
        // For the purposes of this sample, it makes authentication judgments based only on whether the encrypted password matches.
        if (!account.getUserPassword().equals(encryptedPassword)) {

            // When recording the consecutive number of failed logins,
            // 1 is added to the current number of failures.
            // If a failure is not recorded, the current number of failures remains as it is (does not change).
            @SuppressWarnings("NumericCastThatLosesPrecision")
            short failedCount = isChecksFailedCount()
                    ? (short) (account.getFailedCount() + Short.valueOf("1"))
                    : account.getFailedCount();
            updateAuthenticationFailed(account.getUserId(), failedCount);
            throw new AuthenticationFailedException(String.valueOf(account.getUserId()));
        }

        // Information indicating a successful login is retained as authentication itself is already successful.
        updateAuthenticationSucceed(account.getUserId());

        // Judgment of expiration of password
        // An exception is sent if the password is expired.
        if (isExpiredPassword(account, businessDate)) {

            throw new PasswordExpiredException(
                    String.valueOf(account.getUserId()),
                    account.getPasswordExpirationDate(),
                    businessDate);
        }
    }

    /**
     * Indicates whether the password is expired at the reference date/time for judgment.
     *
     * @param account Account being judged
     * @param businessDate Reference date/time for judgment (yyyyMMdd)
     * @return True if the password is expired
     */
    private boolean isExpiredPassword(SystemAccount account, java.util.Date businessDate) {
        return businessDate.compareTo(account.getPasswordExpirationDate()) > 0;
    }

    /**
     * Acquires information on whether the consecutive number of authentication failures is checked.
     *
     * @return True if the consecutive number of authentication failures is checked, false if it is not
     */
    private boolean isChecksFailedCount() {
        return failedCountToLock > 0;
    }

    /**
     * The system account is updated when authentication is successful.
     *
     * @param id ID to identify the system account to be updated
     */
    private void updateAuthenticationSucceed(final Integer id) {
        // The system account update process needs to be performed separately from business transactions
        // as it is performed regardless of whether login is successful.
        new SimpleDbTransactionExecutor<Void>(dbManager) {
            @Override
            public Void execute(final AppDbConnection connection) {
                final SqlPStatement statement = connection.prepareStatementBySqlId(
                        SQL_ID_PREFIX + "RESET_FAILED_COUNT");
                statement.setInt(1, failedCountToLock);
                statement.setTimestamp(2, SystemTimeUtil.getTimestamp());
                statement.setLong(3, id);
                statement.executeUpdate();
                return null;
            }
        } .doTransaction();
    }

    /**
     * The system account is updated when authentication fails.
     * <pre>
     * For the purposes of this sample, it only updates the number of failed authentications and locks accounts when authentication fails enough times to activate locking.
     * Nothing happens in this method if the account is not locked when authentication fails.
     * </pre>
     * @param id ID to identify the system account to be updated
     * @param failedCount Number of failures
     */
    private void updateAuthenticationFailed(final Integer id, final Short failedCount) {

        if (!isChecksFailedCount()) {
            // Nothing happens on failure if the account is not locked.
            return;
        }

        new SimpleDbTransactionExecutor<Void>(dbManager) {
            @Override
            public Void execute(final AppDbConnection connection) {
                final SqlPStatement statement = connection.prepareStatementBySqlId(
                        SQL_ID_PREFIX + "UPDATE_FAILED_COUNT");
                statement.setShort(1, failedCount);
                statement.setLong(2, id);
                statement.executeUpdate();
                return null;
            }
        } .doTransaction();
    }


    /**
     * date-type conversion
     *
     * @param d {@link java.util.Date} type date 
     * @return {@link java.sql.Date} type date 
     */
    private static java.sql.Date convert(java.util.Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new java.sql.Date(cal.getTimeInMillis());
    }
}

