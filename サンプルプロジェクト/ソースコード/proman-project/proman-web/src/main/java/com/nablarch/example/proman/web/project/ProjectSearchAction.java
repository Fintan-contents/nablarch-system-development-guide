package com.nablarch.example.proman.web.project;

import nablarch.common.web.interceptor.InjectForm;
import nablarch.common.web.session.SessionUtil;
import nablarch.core.beans.BeanUtil;
import nablarch.core.message.ApplicationException;
import nablarch.core.message.MessageLevel;
import nablarch.core.message.MessageUtil;
import nablarch.core.util.StringUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.interceptor.OnError;

import java.util.List;

/**
 * プロジェクト検索
 */
public class ProjectSearchAction {
    /** 詳細画面から検索画面に戻った際に利用する検索条件をセッションに格納するキー */
    private static final String CONDITION_DTO_SESSION_KEY = "searchCondition";
    /** 検索画面 */
    private static final String SEARCH_JSP_PATH = "/WEB-INF/view/project/search.jsp";
    /** 詳細画面 */
    private static final String DETAIL_JSP_PATH = "/WEB-INF/view/project/detail.jsp";

    /**
     * プロジェクト検索画面初期表示
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    public HttpResponse search(HttpRequest request, ExecutionContext context) {
        SessionUtil.delete(context, CONDITION_DTO_SESSION_KEY);
        // 事業部と部門をリクエストスコープに設定する
        setOrganizationAndDivisionToRequestScope(context);
        return new HttpResponse(SEARCH_JSP_PATH);
    }

    /**
     * 一覧検索
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    @InjectForm(form = ProjectSearchForm.class, prefix = "form")
    @OnError(type = ApplicationException.class, path = "forward://search")
    public HttpResponse list(HttpRequest request, ExecutionContext context) {
        // ユーザー入力値をDTOに詰め替える
        ProjectSearchForm form = context.getRequestScopedVar("form");
        if (StringUtil.isNullOrEmpty(form.getPageNumber())) {
            // ページングの場合はページ番号が送られるが検索ボタン押下では空のためここで設定
            form.setPageNumber("1");
        }
        ProjectSearchConditionDto condition = BeanUtil.createAndCopy(ProjectSearchConditionDto.class, form);

        // 検索実行
        searchProjectAndSetToRequestScope(context, condition);
        // 事業部と部門をリクエストスコープに設定する
        setOrganizationAndDivisionToRequestScope(context);

        // 詳細画面から戻った際に利用できるよう検索条件をセッションに格納
        SessionUtil.put(context, CONDITION_DTO_SESSION_KEY, condition);

        return new HttpResponse(SEARCH_JSP_PATH);
    }

    /**
     * 検索画面に戻る
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    @OnError(type = ApplicationException.class, path = "forward://search")
    public HttpResponse backToList(HttpRequest request, ExecutionContext context) {
        // 画面遷移前に表示していた検索条件で検索実行
        ProjectSearchConditionDto condition = SessionUtil.get(context, "searchCondition");
        searchProjectAndSetToRequestScope(context, condition);

        // 入力項目に値を復元するためリクエストスコープにフォームを設定する
        ProjectSearchForm form = BeanUtil.createAndCopy(ProjectSearchForm.class, condition);
        context.setRequestScopedVar("form", form);

        // 事業部と部門をリクエストスコープに設定する
        setOrganizationAndDivisionToRequestScope(context);
        return new HttpResponse(SEARCH_JSP_PATH);
    }


    /**
     * 詳細画面表示
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    @InjectForm(form = ProjectDetailInitialForm.class)
    public HttpResponse detail(HttpRequest request, ExecutionContext context) {
        ProjectDetailInitialForm form = context.getRequestScopedVar("form");
        ProjectService service = new ProjectService();
        context.setRequestScopedVar("project", service.findProjectById(Integer.parseInt(form.getProjectId())));
        // TODO 顧客管理システムとの連携ができたら修正
        context.setRequestScopedVar("clientName", "ダミー株式会社");
        return new HttpResponse(DETAIL_JSP_PATH);
    }

    /**
     * プロジェクト一覧をリクエストスコープに設定する。
     *
     * @param context   実行コンテキスト
     * @param condition 検索条件
     */
    private void searchProjectAndSetToRequestScope(ExecutionContext context, ProjectSearchConditionDto condition) {
        ProjectService service = new ProjectService();
        List<ProjectWithOrganizationDto> searchResult = service.listProject(condition);
        if (searchResult.isEmpty()) {
            throw new ApplicationException(
                    MessageUtil.createMessage(MessageLevel.ERROR, "errors.nothing.search", "プロジェクト"));
        }
        context.setRequestScopedVar("searchResult", searchResult);
    }

    /**
     * 事業部と部門をリクエストスコープに設定する。
     *
     * @param context 実行コンテキスト
     */
    private void setOrganizationAndDivisionToRequestScope(ExecutionContext context) {
        ProjectService service = new ProjectService();
        context.setRequestScopedVar("topOrganization", service.findAllDivision());
        context.setRequestScopedVar("subOrganization", service.findAllDepartment());
    }

}
