package com.nablarch.example.proman.web.common.handler.threadcontext;

import com.nablarch.example.proman.web.common.authentication.context.LoginUserPrincipal;
import nablarch.common.handler.threadcontext.UserIdAttribute;
import nablarch.common.web.session.SessionUtil;
import nablarch.fw.ExecutionContext;

/**
 * スレッドコンテキストに保持するユーザID属性。
 *
 * @author Masaya Seko
 */
public class SessionStoreUserIdAttribute extends UserIdAttribute {
    @Override
    protected Object getUserIdSession(ExecutionContext ctx, String skey) {
        LoginUserPrincipal userContext = SessionUtil.orNull(ctx, "userContext");
        if (userContext == null) {
            return null;
        }
        return String.valueOf(userContext.getUserId());
    }
}
