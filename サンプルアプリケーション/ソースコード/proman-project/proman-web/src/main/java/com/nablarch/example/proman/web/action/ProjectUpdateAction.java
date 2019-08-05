package com.nablarch.example.proman.web.action;

import com.nablarch.example.proman.entity.Organization;
import com.nablarch.example.proman.entity.Project;
import com.nablarch.example.proman.web.dto.ProjectWithOrganizationDto;
import com.nablarch.example.proman.web.form.ProjectUpdateForm;
import com.nablarch.example.proman.web.form.ProjectUpdateInitialForm;
import com.nablarch.example.proman.web.service.ProjectService;
import nablarch.common.web.interceptor.InjectForm;
import nablarch.common.web.session.SessionUtil;
import nablarch.common.web.token.OnDoubleSubmission;
import nablarch.core.beans.BeanUtil;
import nablarch.core.message.ApplicationException;
import nablarch.core.util.DateUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.interceptor.OnError;

import java.util.List;

/**
 * プロジェクト更新
 *
 * @author TIS
 */
public class ProjectUpdateAction {

    /**
     * プロジェクト更新初期画面を表示。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    @InjectForm(form = ProjectUpdateInitialForm.class)
    public HttpResponse index(HttpRequest request, ExecutionContext context) {

        ProjectUpdateInitialForm updateInitialForm = context.getRequestScopedVar("form");

        // プロジェクト詳細画面から取得するプロジェクトIDでプロジェクト情報を検索
        ProjectService service = new ProjectService();
        ProjectWithOrganizationDto projectWithOrganizationDto =
                service.findProjectWithOrganizationById(Integer.valueOf(updateInitialForm.getProjectId()));

        // 事業部/部門のプルダウンをDBから取得してリクエストスコープに設定する
        setOrganizationAndDivisionToRequestScope(context);
        // プロジェクト情報をDBから取得してリクエストスコープに設定する
        context.setRequestScopedVar("form", projectWithOrganizationDto);
        // プロジェクト情報をDBから取得してセッションに設定する
        SessionUtil.put(context, "project", BeanUtil.createAndCopy(Project.class, projectWithOrganizationDto));
        // プロジェクトIDをセッションに設定する
        SessionUtil.put(context, "updateInitialForm", updateInitialForm);

        return new HttpResponse("/WEB-INF/view/project/update.jsp");
    }

    /**
     * 更新情報確認画面を表示。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    @InjectForm(form = ProjectUpdateForm.class, prefix = "form")
    @OnError(type = ApplicationException.class, path = "/WEB-INF/view/project/update.jsp")
    public HttpResponse confirmUpdate(HttpRequest request, ExecutionContext context) {

        ProjectUpdateForm form = context.getRequestScopedVar("form");
        Project project = SessionUtil.get(context, "project");

        // TODO:顧客選択が実装されたら消す
        project.setClientId(0);
        BeanUtil.copy(form, project);

        // 事業部/部門のプルダウンをDBから取得してリクエストスコープに設定する
        setOrganizationAndDivisionToRequestScope(context);
        SessionUtil.put(context, "project", project);

        // 更新情報確認画面を表示
        return new HttpResponse("/WEB-INF/view/project/confirmationOfUpdate.jsp");
    }

    /**
     * 更新情報入力画面へ戻る。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    public HttpResponse backToEnterUpdate(HttpRequest request, ExecutionContext context) {

        Project project = SessionUtil.get(context, "project");
        ProjectUpdateInitialForm projectUpdateInitialForm = SessionUtil.get(context, "updateInitialForm");
        ProjectUpdateForm projectUpdateForm = BeanUtil.createAndCopy(ProjectUpdateForm.class, project);

        String projectStartDate = DateUtil.formatDate(projectUpdateForm.getProjectStartDate(), "yyyy/MM/dd");
        String projectEndDate = DateUtil.formatDate(projectUpdateForm.getProjectEndDate(), "yyyy/MM/dd");
        projectUpdateForm.setProjectStartDate(projectStartDate);
        projectUpdateForm.setProjectEndDate(projectEndDate);

        // 設定した事業部/部門のIDを登録確認画面から取得してリクエストスコープに設定する
        ProjectService service = new ProjectService();
        Organization organization = service.findOrganizationById(project.getOrganizationId());
        Organization division = service.findOrganizationById(organization.getUpperOrganization());
        projectUpdateForm.setDivisionId(String.valueOf(division.getOrganizationId()));

        context.setRequestScopedVar("form", projectUpdateForm);
        context.setRequestScopedVar("targetForm", projectUpdateInitialForm);

        return new HttpResponse("/WEB-INF/view/project/update.jsp");

    }

    /**
     * 更新処理。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    @OnDoubleSubmission
    public HttpResponse update(HttpRequest request, ExecutionContext context) {
        Project project = SessionUtil.get(context, "project");
        ProjectService service = new ProjectService();
        service.updateProject(project);
        return new HttpResponse(303, "redirect://completionUpdate");
    }

    /**
     * 更新完了画面を表示。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    public HttpResponse completionUpdate(HttpRequest request, ExecutionContext context) {
        final Project project = SessionUtil.delete(context, "project");
        context.setRequestScopedVar("project", project);

        return new HttpResponse("/WEB-INF/view/project/completionOfUpdate.jsp");
    }

    /**
     * 事業部と部門をリクエストスコープに設定する。
     *
     * @param context 実行コンテキスト
     */
    private void setOrganizationAndDivisionToRequestScope(ExecutionContext context) {

        // 事業部/部門の情報を取得
        ProjectService service = new ProjectService();
        List<Organization> topOrganizationList = service.findAllDivision();
        List<Organization> subOrganizationList = service.findAllDepartment();

        SessionUtil.put(context, "project", "");
        // 事業部と部門をリクエストスコープに設定する
        SessionUtil.put(context, "topOrganization", topOrganizationList);
        SessionUtil.put(context, "subOrganization", subOrganizationList);
    }

}
