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
 * プロジェクト登録
 *
 * @author TIS
 */
public class ProjectCreateAction {

    /**
     * プロジェクト登録初期画面を表示。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    public HttpResponse index(HttpRequest request, ExecutionContext context) {

        // 事業部/部門のプルダウンをDBから取得してリクエストスコープに設定する
        setOrganizationAndDivisionToRequestScope(context);

        return new HttpResponse("/WEB-INF/view/project/create.jsp");
    }

    /**
     * 登録情報確認画面を表示。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    @InjectForm(form = ProjectCreateForm.class, prefix = "form")
    @OnError(type = ApplicationException.class, path = "forward:///app/project/errorRegister")
    public HttpResponse confirmRegistration(HttpRequest request, ExecutionContext context) {
        ProjectCreateForm form = context.getRequestScopedVar("form");
        Project project = BeanUtil.createAndCopy(Project.class, form);

        // TODO:顧客選択が実装されたら消す
        project.setClientId(0);

        // 事業部/部門のプルダウンをDBから取得してリクエストスコープに設定する
        setOrganizationAndDivisionToRequestScope(context);
        SessionUtil.put(context, "project", project);

        // 登録情報確認画面を表示
        return new HttpResponse("/WEB-INF/view/project/confirmationOfCreation.jsp");
    }

    /**
     * 登録処理。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    @OnDoubleSubmission
    public HttpResponse register(HttpRequest request, ExecutionContext context) {
        final Project project = SessionUtil.delete(context, "project");
        ProjectService service = new ProjectService();
        service.insertProject(project);
        return new HttpResponse(303, "redirect:///app/project/completionRegistration");
    }

    /**
     * 登録完了画面を表示。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    public HttpResponse completionRegistration(HttpRequest request, ExecutionContext context) {
        return new HttpResponse("/WEB-INF/view/project/completionOfCreation.jsp");
    }

    /**
     * 登録情報入力画面へ戻る。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    public HttpResponse backToEnterRegistration(HttpRequest request, ExecutionContext context) {

        Project project = SessionUtil.get(context, "project");
        ProjectCreateForm projectCreateForm = BeanUtil.createAndCopy(ProjectCreateForm.class, project);

        String projectStartDate = DateUtil.formatDate(projectCreateForm.getProjectStartDate(), "yyyy/MM/dd");
        String projectEndDate = DateUtil.formatDate(projectCreateForm.getProjectEndDate(), "yyyy/MM/dd");
        projectCreateForm.setProjectStartDate(projectStartDate);
        projectCreateForm.setProjectEndDate(projectEndDate);

        // 設定した事業部/部門のIDを登録確認画面から取得してリクエストスコープに設定する
        ProjectService service = new ProjectService();
        Organization organization = service.findOrganizationById(project.getOrganizationId());
        Organization division = service.findOrganizationById(organization.getUpperOrganization());
        projectCreateForm.setDivisionId(String.valueOf(division.getOrganizationId()));

        context.setRequestScopedVar("form", projectCreateForm);

        return new HttpResponse("forward:///app/project/returnRegister");

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
        context.setRequestScopedVar("topOrganization", topOrganizationList);
        context.setRequestScopedVar("subOrganization", subOrganizationList);
    }

}
