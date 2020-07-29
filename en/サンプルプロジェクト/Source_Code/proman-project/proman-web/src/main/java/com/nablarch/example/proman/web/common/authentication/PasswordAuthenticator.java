package com.nablarch.example.proman.web.common.authentication;

import com.nablarch.example.proman.web.common.authentication.exception.AuthenticationException;

/**
 * User authentication interface.
 * <p/>
 * Creates class for implementation of this interface for each authentication method.
 * @author Nabu Rakutaro
 */
public interface PasswordAuthenticator {

    /**
     * The user is authenticated using the account information.
     * <p/>
     * A method argument and an exception that may be sent must be prescribed for each authentication method in the implementation class.
     *
     * @param userId: User ID
     * @param password: Password
     * @throws AuthenticationException: Authentication exception
     */
    void authenticate(String userId, String password) throws AuthenticationException;
}
