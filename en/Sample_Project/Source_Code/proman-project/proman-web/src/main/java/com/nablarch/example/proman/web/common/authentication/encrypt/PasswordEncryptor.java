package com.nablarch.example.proman.web.common.authentication.encrypt;

/**
 * Interface for encrypting password. <br>
 * <br>
 * Creates class for implementation of this interface for each encryption algorithm.
 *
 * @author Nabu Rakutaro
 */
public interface PasswordEncryptor {

    /**
     * Encrypts passwords.
     * @param saltSeed Character string used to generate salt to be used for password encryption
     * @param password Unencrypted password
     * @return Encrypted password
     */
    String encrypt(String saltSeed, String password);
}
