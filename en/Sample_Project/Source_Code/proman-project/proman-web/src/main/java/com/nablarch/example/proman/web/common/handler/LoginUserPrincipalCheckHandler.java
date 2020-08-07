package com.nablarch.example.proman.web.common.handler;

import nablarch.common.web.session.SessionUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.Handler;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

import java.util.Objects;

/**
 * Handler to check login status.
 *
 * @author Masaya Seko
 */
public class LoginUserPrincipalCheckHandler implements Handler<HttpRequest, Object> {

    /**
     * The login screen is displayed if the user information cannot be acquired from the session.
     *
     * @param request Request data
     * @param context Context for execution
     * @return HTTP response
     */
    @Override
    public Object handle(HttpRequest request, ExecutionContext context) {
        if (SessionUtil.orNull(context, "userContext") == null
                && !Objects.equals(request.getRequestPath(), "/app/login")) {
            return new HttpResponse("/WEB-INF/view/login/login.jsp");
        }
        return context.handleNext(request);
    }
}
