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
 * Project registration
 *
 * @author Masaya Seko
 */
public class ProjectCreateAction {

    private static final String PROJECT_KEY = "projectCreateActionProject";
    /**
     * Displays initial screen for project registration.
     *
     * @param request HTTP request
     * @param context Context for execution
     * @return HTTP response
     */
    public HttpResponse index(HttpRequest request, ExecutionContext context) {

        // Acquires division/department pull-down menu from database and sets within request scope.
        setOrganizationAndDivisionToRequestScope(context);

        return new HttpResponse("/WEB-INF/view/project/create.jsp");
    }

    /**
     * Displays screen for confirming registration information.
     *
     * @param request HTTP request
     * @param context Context for execution
     * @return HTTP response
     */
    @InjectForm(form = ProjectCreateForm.class, prefix = "form")
    @OnError(type = ApplicationException.class, path = "forward:///app/project/errorRegister")
    public HttpResponse confirmRegistration(HttpRequest request, ExecutionContext context) {
        ProjectCreateForm form = context.getRequestScopedVar("form");
        Project project = BeanUtil.createAndCopy(Project.class, form);

        // TODO: Erase when customer selection is implemented
        project.setClientId(0);

        // Acquires division/department pull-down menu from database and sets within request scope.
        setOrganizationAndDivisionToRequestScope(context);
        SessionUtil.put(context, PROJECT_KEY, project);

        // Displays screen for confirming registration information
        return new HttpResponse("/WEB-INF/view/project/confirmationOfCreation.jsp");
    }

    /**
     * Registration process.
     *
     * @param request HTTP request
     * @param context Context for execution
     * @return HTTP response
     */
    @OnDoubleSubmission
    public HttpResponse register(HttpRequest request, ExecutionContext context) {
        final Project project = SessionUtil.delete(context, PROJECT_KEY);
        ProjectService service = new ProjectService();
        service.insertProject(project);
        return new HttpResponse(303, "redirect:///app/project/completeRegistration");
    }

    /**
     * Displays registration completion screen.
     *
     * @param request HTTP request
     * @param context Context for execution
     * @return HTTP response
     */
    public HttpResponse completeRegistration(HttpRequest request, ExecutionContext context) {
        return new HttpResponse("/WEB-INF/view/project/completionOfCreation.jsp");
    }

    /**
     * Returns to registration information input screen.
     *
     * @param request HTTP request
     * @param context Context for execution
     * @return HTTP response
     */
    public HttpResponse backToEnterRegistration(HttpRequest request, ExecutionContext context) {

        Project project = SessionUtil.get(context, PROJECT_KEY);
        ProjectCreateForm projectCreateForm = BeanUtil.createAndCopy(ProjectCreateForm.class, project);

        String projectStartDate = DateUtil.formatDate(projectCreateForm.getProjectStartDate(), "yyyy/MM/dd");
        String projectEndDate = DateUtil.formatDate(projectCreateForm.getProjectEndDate(), "yyyy/MM/dd");
        projectCreateForm.setProjectStartDate(projectStartDate);
        projectCreateForm.setProjectEndDate(projectEndDate);

        // Acquires set division/department ID from registration confirmation screen and sets within request scope.
        ProjectService service = new ProjectService();
        Organization organization = service.findOrganizationById(project.getOrganizationId());
        Organization division = service.findOrganizationById(organization.getUpperOrganization());
        projectCreateForm.setDivisionId(String.valueOf(division.getOrganizationId()));

        context.setRequestScopedVar("form", projectCreateForm);

        return new HttpResponse("forward:///app/project/returnRegister");

    }

    /**
     * Sets division and department within request scope.
     *
     * @param context Context for execution
     */
    private void setOrganizationAndDivisionToRequestScope(ExecutionContext context) {

        // Acquires division and department information
        ProjectService service = new ProjectService();
        List<Organization> topOrganizationList = service.findAllDivision();
        List<Organization> subOrganizationList = service.findAllDepartment();

        SessionUtil.put(context, PROJECT_KEY, "");
        // Sets division and department within request scope
        context.setRequestScopedVar("topOrganization", topOrganizationList);
        context.setRequestScopedVar("subOrganization", subOrganizationList);
    }

}
