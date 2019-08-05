package com.nablarch.example.proman.web.action;

import com.nablarch.example.proman.entity.Project;
import com.nablarch.example.proman.web.dto.ProjectWithOrganizationDto;
import com.nablarch.example.proman.web.form.ProjectDeleteInitialForm;
import com.nablarch.example.proman.web.service.ProjectService;
import nablarch.common.web.interceptor.InjectForm;
import nablarch.common.web.session.SessionUtil;
import nablarch.common.web.token.OnDoubleSubmission;
import nablarch.core.beans.BeanUtil;
import nablarch.core.message.ApplicationException;
import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.interceptor.OnError;

/**
 * プロジェクト削除
 * @author TIS
 */
public class ProjectDeleteAction {

    /**
     * 削除情報確認画面を表示。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    @InjectForm(form = ProjectDeleteInitialForm.class)
    @OnError(type = ApplicationException.class, path = "/WEB-INF/view/project/confirmationOfDelete.jsp")
    public HttpResponse confirmDeletion(HttpRequest request, ExecutionContext context) {

        ProjectDeleteInitialForm projectDeleteInitialForm = context.getRequestScopedVar("form");

        // プロジェクト詳細画面から取得するプロジェクトIDでプロジェクト情報を検索
        ProjectService service = new ProjectService();
        ProjectWithOrganizationDto projectWithOrganizationDto =
                service.findProjectWithOrganizationById(Integer.valueOf(projectDeleteInitialForm.getProjectId()));

        // プロジェクト情報をDBから取得してリクエストスコープに設定する
        context.setRequestScopedVar("form", projectWithOrganizationDto);
        // プロジェクト情報をDBから取得してセッションに設定する
        SessionUtil.put(context, "project", BeanUtil.createAndCopy(Project.class, projectWithOrganizationDto));

        // 削除情報確認画面を表示
        return new HttpResponse("/WEB-INF/view/project/confirmationOfDelete.jsp");
    }

    /**
     * プロジェクト詳細画面へ戻る。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    public HttpResponse backToDetail(HttpRequest request, ExecutionContext context) {
        return new HttpResponse("/WEB-INF/view/project/detail.jsp");
    }

    /**
     * 削除処理。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    @OnDoubleSubmission
    public HttpResponse delete(HttpRequest request, ExecutionContext context) {
        final Project project = SessionUtil.delete(context, "project");
        ProjectService service = new ProjectService();
        service.deleteProject(project);
        return new HttpResponse(303, "redirect://completionDelete");
    }

    /**
     * 削除完了画面を表示。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    public HttpResponse completionDelete(HttpRequest request, ExecutionContext context) {
        return new HttpResponse("/WEB-INF/view/project/completionOfDelete.jsp");
    }

}
