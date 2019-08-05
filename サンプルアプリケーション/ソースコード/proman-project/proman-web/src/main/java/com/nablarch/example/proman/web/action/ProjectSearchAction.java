package com.nablarch.example.proman.web.action;

import com.nablarch.example.proman.entity.Organization;
import com.nablarch.example.proman.web.dto.ProjectWithOrganizationDto;
import com.nablarch.example.proman.web.dto.ProjectWithOrganizationSearchConditionDto;
import com.nablarch.example.proman.web.dto.ProjectWithOrganizationSearchResultDto;
import com.nablarch.example.proman.web.form.ProjectSearchForm;
import com.nablarch.example.proman.web.form.ProjectSearchInitialForm;
import com.nablarch.example.proman.web.service.ProjectService;
import nablarch.common.dao.EntityList;
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
 * プロジェクト検索アクション。
 *
 * @author TIS
 */
public class ProjectSearchAction {

    /**
     * プロジェクト検索画面初期表示。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    public HttpResponse index(HttpRequest request, ExecutionContext context) {

        // 事業部/部門のプルダウンをDBから取得して表示する
        ProjectService projectService = new ProjectService();
        List<Organization> topOrganizationList = projectService.findAllDivision();
        List<Organization> subOrganizationList = projectService.findAllDepartment();

        SessionUtil.put(context, "topOrganization", topOrganizationList);
        SessionUtil.put(context, "subOrganization", subOrganizationList);
        return new HttpResponse("/WEB-INF/view/project/search.jsp");
    }

    /**
     * プロジェクト検索。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    @InjectForm(form = ProjectSearchForm.class, prefix = "searchForm", name = "searchForm")
    @OnError(type = ApplicationException.class, path = "/WEB-INF/view/project/search.jsp")
    public HttpResponse search(HttpRequest request, ExecutionContext context) {

        ProjectSearchForm searchForm = context.getRequestScopedVar("searchForm");
        if (StringUtil.isNullOrEmpty(searchForm.getPageNumber())) {
            searchForm.setPageNumber("1");
        }
        context.setRequestScopedVar("searchForm", searchForm);
        ProjectWithOrganizationSearchConditionDto searchCondition = BeanUtil
                .createAndCopy(ProjectWithOrganizationSearchConditionDto.class, searchForm);

        ProjectService searchService = new ProjectService();
        EntityList<ProjectWithOrganizationSearchResultDto> searchList = searchService.findProjectWithOrganizationByCondition(searchCondition);
        if (searchList.size() > 0) {
            context.setRequestScopedVar("searchResult", searchList);
        } else {
            SessionUtil.delete(context, "searchResult");
            throw new ApplicationException(
                    MessageUtil.createMessage(MessageLevel.ERROR,
                            "errors.search.nothing"));
        }
        return new HttpResponse("/WEB-INF/view/project/search.jsp");
    }

    /**
     * プロジェクト詳細画面を表示。
     *
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    @InjectForm(form = ProjectSearchInitialForm.class)
    public HttpResponse show(HttpRequest request, ExecutionContext context) {
        ProjectSearchInitialForm projectSearchInitialForm = context.getRequestScopedVar("form");

        ProjectService searchService = new ProjectService();
        ProjectWithOrganizationDto projectWithOrganizationDto = searchService
                .findProjectWithOrganizationById(Integer.valueOf(projectSearchInitialForm.getProjectId()));
        context.setRequestScopedVar("project", projectWithOrganizationDto);

        return new HttpResponse("/WEB-INF/view/project/detail.jsp");
    }

}
