package com.nablarch.example.proman.web.common.authentication;


import com.nablarch.example.proman.web.common.authentication.exception.AuthenticationFailedException;
import com.nablarch.example.proman.web.common.authentication.exception.PasswordExpiredException;
import com.nablarch.example.proman.web.common.authentication.encrypt.PasswordEncryptor;
import com.nablarch.example.proman.web.common.authentication.exception.UserIdLockedException;
import nablarch.core.repository.SystemRepository;

/**
 * Utility class for processes involved in authentication.
 *
 * @author Masaya Seko
 */
public final class AuthenticationUtil {

    /**
     * Component name for password encryption.
     */
    private static final String PASSWORD_ENCRYPTOR = "passwordEncryptor";

    /**
     * Authenticator component name.
     */
    private static final String AUTHENTICATOR = "authenticator";

    /**
     * Masked constructor.
     */
    private AuthenticationUtil() {
    }

    /**
     * Encrypts passwords.
     * <p/>
     * The {@link PasswordEncryptor} used for password encryption is acquired from {@link SystemRepository}
     * using the component name {@value #PASSWORD_ENCRYPTOR}.
     *
     * @param userId User ID
     * @param password Password (plain text)
     * @return Encrypted password
     * @see PasswordEncryptor#encrypt(String, String)
     */
    public static String encryptPassword(String userId, String password) {
        PasswordEncryptor passwordEncryptor = SystemRepository.get(PASSWORD_ENCRYPTOR);
        return passwordEncryptor.encrypt(userId, password);
    }

    /**
     * Authenticates the user.
     * <p/>
     * The {@link PasswordAuthenticator} used for user authentication is acquired from {@link SystemRepository}
     * using the component name {@value #AUTHENTICATOR}.
     *
     * @param userId User ID
     * @param password Password (plain text)
     * @throws AuthenticationFailedException When authentication fails
     * @throws UserIdLockedException When a user ID is locked
     * @throws PasswordExpiredException When a password is expired
     * @see PasswordAuthenticator#authenticate(String, String)
     */
    public static void authenticate(String userId, String password)
        throws AuthenticationFailedException, UserIdLockedException, PasswordExpiredException {
        PasswordAuthenticator authenticator = SystemRepository.get(AUTHENTICATOR);
        authenticator.authenticate(userId, password);
    }
}
