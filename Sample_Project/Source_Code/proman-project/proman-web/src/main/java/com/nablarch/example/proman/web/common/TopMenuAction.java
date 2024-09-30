package com.nablarch.example.proman.web.common;

import nablarch.common.web.session.SessionUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

/**
 * Topメニューアクション。
 *
 * @author Masaya Seko
 */
public class TopMenuAction {

    /**
     * Topメニュー画面を表示。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    public HttpResponse index(HttpRequest request, ExecutionContext context) {
        SessionUtil.delete(context, "searchForm");
        return new HttpResponse("/WEB-INF/view/common/topMenu.jsp");
    }

}
