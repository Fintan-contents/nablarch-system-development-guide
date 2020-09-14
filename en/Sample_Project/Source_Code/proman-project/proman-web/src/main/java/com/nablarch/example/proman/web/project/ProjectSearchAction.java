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
 * Project Search
 */
public class ProjectSearchAction {
    /** A key to store the search criteria in the session when returning to the search screen from the detail screen. */
    private static final String CONDITION_DTO_SESSION_KEY = "searchCondition";
    /** Search screen */
    private static final String SEARCH_JSP_PATH = "/WEB-INF/view/project/search.jsp";
    /** Detail screen */
    private static final String DETAIL_JSP_PATH = "/WEB-INF/view/project/detail.jsp";

    /**
     * Initial display of the project search screen.
     *
     * @param request HTTP request
     * @param context execution context
     * @return HTTP Response
     */
    public HttpResponse search(HttpRequest request, ExecutionContext context) {
        SessionUtil.delete(context, CONDITION_DTO_SESSION_KEY);
        // Set business divisions and departments as request scopes.
        setOrganizationAndDivisionToRequestScope(context);
        return new HttpResponse(SEARCH_JSP_PATH);
    }

    /**
     * List Search
     *
     * @param request HTTP request
     * @param context execution context
     * @return HTTP Response
     */
    @InjectForm(form = ProjectSearchForm.class, prefix = "form")
    @OnError(type = ApplicationException.class, path = "forward://search")
    public HttpResponse list(HttpRequest request, ExecutionContext context) {
        // Repackaging user input values to DTO
        ProjectSearchForm form = context.getRequestScopedVar("form");
        if (StringUtil.isNullOrEmpty(form.getPageNumber())) {
            // In case of paging, a page number is sent, but when the search button is pressed, it is empty, so it is set here.
            form.setPageNumber("1");
        }
        ProjectSearchConditionDto condition = BeanUtil.createAndCopy(ProjectSearchConditionDto.class, form);

        // Run a search
        searchProjectAndSetToRequestScope(context, condition);
        // Set business divisions and departments as request scopes.
        setOrganizationAndDivisionToRequestScope(context);

        // Search criteria are stored in the session for use when returning from the detail screen.
        SessionUtil.put(context, CONDITION_DTO_SESSION_KEY, condition);

        return new HttpResponse(SEARCH_JSP_PATH);
    }

    /**
     * Return to search screen
     *
     * @param request HTTP request
     * @param context execution context
     * @return HTTP Response
     */
    @OnError(type = ApplicationException.class, path = "forward://search")
    public HttpResponse backToList(HttpRequest request, ExecutionContext context) {
        // Perform search with the search conditions displayed before the screen transition.
        ProjectSearchConditionDto condition = SessionUtil.get(context, "searchCondition");
        searchProjectAndSetToRequestScope(context, condition);

        // Set the form in request scope to restore the value to input item.
        ProjectSearchForm form = BeanUtil.createAndCopy(ProjectSearchForm.class, condition);
        context.setRequestScopedVar("form", form);

        // Set the division and department to request scope.
        setOrganizationAndDivisionToRequestScope(context);
        return new HttpResponse(SEARCH_JSP_PATH);
    }


    /**
     * Detailed screen display
     *
     * @param request HTTP request
     * @param context execution context
     * @return HTTP Response
     */
    @InjectForm(form = ProjectDetailInitialForm.class)
    public HttpResponse detail(HttpRequest request, ExecutionContext context) {
        ProjectDetailInitialForm form = context.getRequestScopedVar("form");
        ProjectService service = new ProjectService();
        context.setRequestScopedVar("project", service.findProjectByIdWithOrganization(Integer.parseInt(form.getProjectId())));
        // TODO Corrected when you can integrate with the client management system.
        context.setRequestScopedVar("clientName", "Dummy Co.");
        return new HttpResponse(DETAIL_JSP_PATH);
    }

    /**
     * Set project list to request scope.
     *
     * @param context execution context
     * @param condition search criteria
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
     * Set business divisions and departments as request scopes.
     *
     * @param context execution context
     */
    private void setOrganizationAndDivisionToRequestScope(ExecutionContext context) {
        ProjectService service = new ProjectService();
        context.setRequestScopedVar("topOrganization", service.findAllDivision());
        context.setRequestScopedVar("subOrganization", service.findAllDepartment());
    }

}
