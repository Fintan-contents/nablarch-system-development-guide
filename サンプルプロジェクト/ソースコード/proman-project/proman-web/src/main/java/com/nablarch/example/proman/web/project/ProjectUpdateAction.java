package com.nablarch.example.proman.web.project;

import com.nablarch.example.proman.entity.Organization;
import com.nablarch.example.proman.entity.Project;
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

    private static final String PROJECT_KEY = "projectUpdateActionProject";

    /**
     * プロジェクト詳細画面からプロジェクト更新を表示。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    @InjectForm(form = ProjectUpdateInitialForm.class)
    public HttpResponse index(HttpRequest request, ExecutionContext context) {
        ProjectUpdateInitialForm form = context.getRequestScopedVar("form");
        ProjectService service = new ProjectService();
        Project project = service.findProjectById(Integer.parseInt(form.getProjectId()));
        ProjectUpdateForm projectUpdateForm = buildFormFromEntity(project, service);
        context.setRequestScopedVar("form", projectUpdateForm);
        SessionUtil.put(context, PROJECT_KEY, project);
        return new HttpResponse("forward:///app/project/moveUpdate");
    }

    /**
     * 更新情報確認画面を表示。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    @InjectForm(form = ProjectUpdateForm.class, prefix = "form")
    @OnError(type = ApplicationException.class, path = "forward:///app/project/moveUpdate")
    public HttpResponse confirmUpdate(HttpRequest request, ExecutionContext context) {
        ProjectUpdateForm form = context.getRequestScopedVar("form");
        Project project = SessionUtil.get(context, PROJECT_KEY);
        BeanUtil.copy(form, project);
        // 事業部/部門のプルダウンをDBから取得してリクエストスコープに設定する
        setOrganizationAndDivisionToRequestScope(context);
        // 更新情報確認画面を表示
        return new HttpResponse("/WEB-INF/view/project/confirmationOfUpdate.jsp");
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
        final Project project = SessionUtil.delete(context, PROJECT_KEY);
        ProjectService service = new ProjectService();
        service.updateProject(project);
        return new HttpResponse(303, "redirect:///app/project/completeUpdate");
    }

    /**
     * 更新完了画面を表示。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    public HttpResponse completeUpdate(HttpRequest request, ExecutionContext context) {
        return new HttpResponse("/WEB-INF/view/project/completionOfUpdate.jsp");
    }

    /**
     * 更新情報入力画面へ戻る。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    public HttpResponse backToEnterUpdate(HttpRequest request, ExecutionContext context) {
        Project project = SessionUtil.get(context, PROJECT_KEY);
        ProjectUpdateForm projectUpdateForm = buildFormFromEntity(project, new ProjectService());
        context.setRequestScopedVar("form", projectUpdateForm);
        return new HttpResponse("forward:///app/project/moveUpdate");
    }

    /**
     * プロジェクトエンティティをもとにプロジェクト更新フォームを作成する。
     *
     * @param project プロジェクトエンティティ
     * @param service プロジェクトサービス
     * @return プロジェクト更新フォーム
     */
    private ProjectUpdateForm buildFormFromEntity(Project project, ProjectService service) {
        ProjectUpdateForm projectUpdateForm = BeanUtil.createAndCopy(ProjectUpdateForm.class, project);

        String projectStartDate = DateUtil.formatDate(projectUpdateForm.getProjectStartDate(), "yyyy/MM/dd");
        String projectEndDate = DateUtil.formatDate(projectUpdateForm.getProjectEndDate(), "yyyy/MM/dd");
        projectUpdateForm.setProjectStartDate(projectStartDate);
        projectUpdateForm.setProjectEndDate(projectEndDate);

        // 設定した事業部/部門のIDを更新確認画面から取得してリクエストスコープに設定する
        Organization organization = service.findOrganizationById(project.getOrganizationId());
        Organization division = service.findOrganizationById(organization.getUpperOrganization());
        projectUpdateForm.setDivisionId(String.valueOf(division.getOrganizationId()));

        return projectUpdateForm;
    }

    /**
     * プルダウンを設定してプロジェク更新画面を表示。
     * 詳細、確認画面遷移からの際に必要なため設定。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    public HttpResponse indexSetPullDown(HttpRequest request, ExecutionContext context) {
        // 事業部/部門のプルダウンをDBから取得してリクエストスコープに設定する
        setOrganizationAndDivisionToRequestScope(context);
        Project project = SessionUtil.get(context, PROJECT_KEY);
        context.setRequestScopedVar("projectId", project.getProjectId());
        return new HttpResponse("/WEB-INF/view/project/update.jsp");
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

        // 事業部と部門をリクエストスコープに設定する
        context.setRequestScopedVar("topOrganization", topOrganizationList);
        context.setRequestScopedVar("subOrganization", subOrganizationList);
    }

}
