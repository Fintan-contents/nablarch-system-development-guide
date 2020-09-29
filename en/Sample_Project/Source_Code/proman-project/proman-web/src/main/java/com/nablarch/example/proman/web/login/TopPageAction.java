package com.nablarch.example.proman.web.login;

import nablarch.common.web.session.SessionUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

/**
 * Home menu action.
 *
 * @author Masaya Seko
 */
public class TopPageAction {

    /**
     * Displays home menu screen.
     *
     * @param request HTTP request
     * @param context Context for execution
     * @return HTTP response
     */
    public HttpResponse index(HttpRequest request, ExecutionContext context) {
        SessionUtil.delete(context, "searchForm");
        return new HttpResponse("/WEB-INF/view/common/topPage.jsp");
    }

}
