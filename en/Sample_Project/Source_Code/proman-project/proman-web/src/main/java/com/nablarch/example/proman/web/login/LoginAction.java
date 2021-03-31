package com.nablarch.example.proman.web.login;

import com.nablarch.example.proman.entity.SystemAccount;
import com.nablarch.example.proman.entity.Users;
import com.nablarch.example.proman.web.common.authentication.AuthenticationUtil;
import com.nablarch.example.proman.web.common.authentication.context.LoginUserPrincipal;
import com.nablarch.example.proman.web.common.authentication.exception.AuthenticationException;
import nablarch.common.dao.UniversalDao;
import nablarch.common.web.interceptor.InjectForm;
import nablarch.common.web.session.SessionUtil;
import nablarch.core.message.ApplicationException;
import nablarch.core.message.MessageLevel;
import nablarch.core.message.MessageUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.interceptor.OnError;

/**
 * Authentication action.
 * <pre>
 * Authenticates system users.
 * Has a login/logout feature.
 * </pre>
 *
 * @author Masaya Seko
 */
public class LoginAction {

    /**
     * Displays login screen.
     *
     * @param request HTTP request
     * @param context Context for execution
     * @return HTTP response
     */
    public HttpResponse index(HttpRequest request, ExecutionContext context) {
        return new HttpResponse("/WEB-INF/view/login/login.jsp");
    }

    /**
     * Login.
     *
     * @param request HTTP request
     * @param context Context for execution
     * @return HTTP response
     */
    @OnError(type = ApplicationException.class, path = "/WEB-INF/view/login/login.jsp")
    @InjectForm(form = LoginForm.class)
    public HttpResponse login(HttpRequest request, ExecutionContext context) {

        LoginForm form = context.getRequestScopedVar("form");

        try {
            AuthenticationUtil.authenticate(form.getLoginId(), form.getUserPassword());
        } catch (AuthenticationException ignore) {
            // Password mismatch or other authentication error (user does not exist, etc.)
            throw new ApplicationException(MessageUtil.createMessage(
                    MessageLevel.ERROR, "errors.login"));
        }

        // If authentication is successful, the user is redirected to the home screen
        // after the session prior to login is discarded and the authentication information is stored in the (new) session.
        SessionUtil.invalidate(context);
        LoginUserPrincipal userContext = createLoginUserContext(form.getLoginId());
        SessionUtil.put(context, "userContext", userContext);
        return new HttpResponse(303, "redirect:///");
    }

    /**
     * Acquires authentication information.
     *
     * @param loginId Login ID
     * @return Authentication information
     */
    private LoginUserPrincipal createLoginUserContext(String loginId) {
        SystemAccount account = UniversalDao
                .findBySqlFile(SystemAccount.class,
                        "FIND_SYSTEM_ACCOUNT_BY_AK", new Object[]{loginId});
        Users users = UniversalDao.findById(Users.class, account.getUserId());

        LoginUserPrincipal userContext = new LoginUserPrincipal();
        userContext.setUserId(account.getUserId());
        userContext.setKanjiName(users.getKanjiName());
        userContext.setPmFlag(users.isPmFlag());
        userContext.setLastLoginDateTime(account.getLastLoginDateTime());

        return userContext;

    }

    /**
     * Logout.
     *
     * @param request HTTP request
     * @param context Context for execution
     * @return HTTP response
     */
    public HttpResponse logout(HttpRequest request, ExecutionContext context) {
        SessionUtil.invalidate(context);

        return new HttpResponse(303, "redirect:///app/login");
    }

}
