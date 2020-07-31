package com.nablarch.example.proman.web.common.handler;

import nablarch.common.dao.NoDataException;
import nablarch.common.web.session.SessionKeyNotFoundException;
import nablarch.fw.ExecutionContext;
import nablarch.fw.Handler;
import nablarch.fw.web.HttpErrorResponse;
import nablarch.fw.web.HttpResponse.Status;

import javax.persistence.OptimisticLockException;

/**
 * Handler to transition to the applicable error screen when a specific exception is sent.
 * <p/>
 * Placing this handler before the business action handler makes it possible to determine the exception class sent by the business action method
 * and transition to the desired error screen for each exception.
 * <p/>
 * Reference information Refer to the information below to determine the HTTP status code to be used in each situation <br>
 * @see <a href="http://qiita.com/kawasima/items/e48180041ace99842779">How to select an HTTP status code</a>
 * @author Nabu Rakutaro
 */
public class PromanErrorForwardHandler implements Handler<Object, Object> {

    @Override
    public Object handle(Object data, ExecutionContext context) {

        try {
            // Determines the exception sent by the action handler indicated afterwards, using a try-catch statement
            return context.handleNext(data);
        } catch (SessionKeyNotFoundException e) {
            throw new HttpErrorResponse(Status.BAD_REQUEST.getStatusCode(),
                    "/WEB-INF/view/errorPages/SESSION_KEY_NOT_FOUND_ERROR.jsp", e);
        } catch (NoDataException e) {
            throw new HttpErrorResponse(Status.NOT_FOUND.getStatusCode(),
                    "/WEB-INF/view/errorPages/PAGE_NOT_FOUND_ERROR.jsp", e);
        } catch (OptimisticLockException e) {
            throw new HttpErrorResponse(Status.BAD_REQUEST.getStatusCode(),
                    "/WEB-INF/view/errorPages/OPTIMISTIC_LOCK_ERROR.jsp", e);
        }
    }
}
