package com.nablarch.example.proman.web.common.handler;

import nablarch.common.web.session.SessionUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.Handler;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

import java.util.Objects;

/**
 * ログイン状態チェックハンドラ 。
 *
 * @author Masaya Seko
 */
public class LoginUserPrincipalCheckHandler implements Handler<HttpRequest, Object> {

    /**
     * セッションからユーザ情報を取得できなかった場合は、ログイン画面を表示。
     *
     * @param request リクエストデータ
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
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
