<%--------------------------------------------------------------
Tags for displaying lists of search results.
--------------------------------------------------------------%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags/listSearchResult" %>

<%--------------------------------------------------------------
Attributes
--------------------------------------------------------------%>
<%-- Overall wrapper --%>
<%@ attribute name="listSearchResultWrapperCss" required="false" rtexprvalue="true" %>
<%@ attribute name="listSearchInfoName" required="false" rtexprvalue="true" %>
<%-- Number of search results --%>
<%@ attribute name="useResultCount" required="false" rtexprvalue="true" %>
<%@ attribute name="resultCountCss" required="false" rtexprvalue="true" %>
<%@ attribute name="resultCountFragment" required="false" fragment="true" %>
<%-- Pagination --%>
<%@ attribute name="usePaging" required="false" rtexprvalue="true" %>
<%@ attribute name="pagingPosition" required="false" rtexprvalue="true" %>
<%@ attribute name="pagingCss" required="false" rtexprvalue="true" %>
<%@ attribute name="searchUri" required="false" rtexprvalue="true" %>
<%-- Pagination (current page number) --%>
<%@ attribute name="useCurrentPageNumber" required="false" rtexprvalue="true" %>
<%@ attribute name="currentPageNumberCss" required="false" rtexprvalue="true" %>
<%@ attribute name="currentPageNumberFragment" required="false" fragment="true" %>
<%-- Pagination (first) --%>
<%@ attribute name="useFirstSubmit" required="false" rtexprvalue="true" %>
<%@ attribute name="firstSubmitCss" required="false" rtexprvalue="true" %>
<%@ attribute name="firstSubmitLabel" required="false" rtexprvalue="true" %>
<%@ attribute name="firstSubmitName" required="false" rtexprvalue="true" %>
<%-- Pagination (back) --%>
<%@ attribute name="usePrevSubmit" required="false" rtexprvalue="true" %>
<%@ attribute name="prevSubmitCss" required="false" rtexprvalue="true" %>
<%@ attribute name="prevSubmitLabel" required="false" rtexprvalue="true" %>
<%@ attribute name="prevSubmitName" required="false" rtexprvalue="true" %>
<%-- Pagination (page number (1 2 3 ...n)) --%>
<%@ attribute name="usePageNumberSubmit" required="false" rtexprvalue="true" %>
<%@ attribute name="pageNumberSubmitWrapperCss" required="false" rtexprvalue="true" %>
<%@ attribute name="pageNumberSubmitCss" required="false" rtexprvalue="true" %>
<%@ attribute name="pageNumberSubmitName" required="false" rtexprvalue="true" %>
<%-- Pagination (next) --%>
<%@ attribute name="useNextSubmit" required="false" rtexprvalue="true" %>
<%@ attribute name="nextSubmitCss" required="false" rtexprvalue="true" %>
<%@ attribute name="nextSubmitLabel" required="false" rtexprvalue="true" %>
<%@ attribute name="nextSubmitName" required="false" rtexprvalue="true" %>
<%-- Pagination (last) --%>
<%@ attribute name="useLastSubmit" required="false" rtexprvalue="true" %>
<%@ attribute name="lastSubmitCss" required="false" rtexprvalue="true" %>
<%@ attribute name="lastSubmitLabel" required="false" rtexprvalue="true" %>
<%@ attribute name="lastSubmitName" required="false" rtexprvalue="true" %>
<%-- Search results --%>
<%@ attribute name="showResult" required="false" rtexprvalue="true" %>
<%@ attribute name="resultSetName" required="false" rtexprvalue="true" %>
<%@ attribute name="resultSetCss" required="false" rtexprvalue="true" %>
<%@ attribute name="headerRowFragment" required="false" fragment="true" %>
<%@ attribute name="bodyRowFragment" required="false" fragment="true" %>
<%@ attribute name="varRowName" required="false" rtexprvalue="true" %>
<%@ attribute name="varStatusName" required="false" rtexprvalue="true" %>
<%@ attribute name="varCountName" required="false" rtexprvalue="true" %>
<%@ attribute name="varRowCountName" required="false" rtexprvalue="true" %>
<%@ attribute name="varOddEvenName" required="false" rtexprvalue="true" %>
<%@ attribute name="oddValue" required="false" rtexprvalue="true" %>
<%@ attribute name="evenValue" required="false" rtexprvalue="true" %>

<%--------------------------------------------------------------
Default
--------------------------------------------------------------%>
<%-- Overall wrapper --%>
<c:if test="${empty listSearchResultWrapperCss}"><n:set var="listSearchResultWrapperCss" value="nablarch_listSearchResultWrapper" scope="page" /></c:if>
<c:if test="${empty pagingPosition}"><n:set var="pagingPosition" value="top" scope="page" /></c:if>
<c:if test="${empty showResult}"><n:set var="showResult" value="true" scope="page" /></c:if>
<%-- Number of search results --%>
<c:if test="${empty useResultCount}"><n:set var="useResultCount" value="true" scope="page" /></c:if>
<c:if test="${empty resultCountCss}"><n:set var="resultCountCss" value="true" scope="page" /></c:if>
<%-- Pagination --%>
<c:if test="${empty usePaging}"><n:set var="usePaging" value="true" scope="page" /></c:if>

<c:if test="${empty currentPageNumberCss}"><n:set var="currentPageNumberCss" value="form-control" scope="page" /></c:if>
<c:if test="${empty pagingCss}"><n:set var="pagingCss" value="paging" scope="page" /></c:if>
<c:if test="${empty usePageNumberSubmit}"><n:set var="usePageNumberSubmit" value="true" scope="page" /></c:if>
<c:if test="${empty useLastSubmit}"><n:set var="useLastSubmit" value="true" scope="page" /></c:if>
<c:if test="${empty useFirstSubmit}"><n:set var="useFirstSubmit" value="true" scope="page" /></c:if>
<c:if test="${empty firstSubmitLabel}"><n:set var="firstSubmitLabel" value="«" scope="page" /></c:if>
<c:if test="${empty lastSubmitLabel}"><n:set var="lastSubmitLabel" value="»" scope="page" /></c:if>
<c:if test="${empty prevSubmitLabel}"><n:set var="prevSubmitLabel" value="<" scope="page" /></c:if>
<c:if test="${empty nextSubmitLabel}"><n:set var="nextSubmitLabel" value=">" scope="page" /></c:if>
<c:if test="${empty prevSubmitCss}"><n:set var="prevSubmitCss" value="prev-page-link" scope="page" /></c:if>
<c:if test="${empty nextSubmitCss}"><n:set var="nextSubmitCss" value="next-page-link" scope="page" /></c:if>
<c:if test="${empty resultSetCss}"><n:set var="resultSetCss" value="table table-striped table-hover" scope="page" /></c:if>
<c:if test="${empty listSearchInfoName}"><n:set var="listSearchInfoName" value="searchForm" scope="page" /></c:if>
<c:if test="${empty useResultCount}"><n:set var="useResultCount" value="false" scope="page" /></c:if>
<c:if test="${empty resultSetName}"><n:set var="resultSetName" value="searchResult" scope="page" /></c:if>

<%--------------------------------------------------------------
Process of main unit
--------------------------------------------------------------%>
<%-- Overall wrapper --%>
<n:set var="resultSet" name="${resultSetName}" scope="page" bySingleValue="false" />
<%-- For resultSet, only the index number can be accessed using an EL formula as resultSet is a class that inherits a list. --%>
<%-- For this reason, pagination is temporarily saved to a variable during use. --%>
<n:set var="pagination" name="${resultSetName}.pagination" scope="page" />
<c:if test="${resultSet != null}">
<c:if test="${not empty listSearchInfoName}">
    <n:set var="listSearchInfo" name="${listSearchInfoName}" scope="page" bySingleValue="false" />
</c:if>
    <%-- Pagination (top) --%>
    <c:if test="${(not empty listSearchInfo && (usePaging == 'true')) && ((pagingPosition == 'top') || (pagingPosition == 'both'))}">
    <app:listSearchPaging resultSetName="${resultSetName}"
                        listSearchInfoName="${listSearchInfoName}"
                        pagingCss="${pagingCss}"
                        searchUri="${searchUri}"
                        submitNameSuffix="_top"
                        useCurrentPageNumber="${useCurrentPageNumber}"
                        currentPageNumberCss="${currentPageNumberCss}"
                        useFirstSubmit="${useFirstSubmit}"
                        firstSubmitCss="${firstSubmitCss}"
                        firstSubmitLabel="${firstSubmitLabel}"
                        firstSubmitName="${firstSubmitName}"
                        usePrevSubmit="${usePrevSubmit}"
                        prevSubmitCss="${prevSubmitCss}"
                        prevSubmitLabel="${prevSubmitLabel}"
                        prevSubmitName="${prevSubmitName}"
                        usePageNumberSubmit="${usePageNumberSubmit}"
                        pageNumberSubmitWrapperCss="${pageNumberSubmitWrapperCss}"
                        pageNumberSubmitCss="${pageNumberSubmitCss}"
                        pageNumberSubmitName="${pageNumberSubmitName}"
                        useNextSubmit="${useNextSubmit}"
                        nextSubmitCss="${nextSubmitCss}"
                        nextSubmitLabel="${nextSubmitLabel}"
                        nextSubmitName="${nextSubmitName}"
                        useLastSubmit="${useLastSubmit}"
                        lastSubmitCss="${lastSubmitCss}"
                        lastSubmitLabel="${lastSubmitLabel}"
                        lastSubmitName="${lastSubmitName}">
        <jsp:attribute name="currentPageNumberFragment"><jsp:invoke fragment="currentPageNumberFragment" /></jsp:attribute>
    </app:listSearchPaging>
        <div class="sixteen wide column row">
            <!-- spacer -->
            <div><span>&nbsp;</span></div>
        </div>
    </c:if>
    <%-- Search results --%>
    <c:if test="${showResult}">
        <c:if test="${not empty listSearchInfo}">
            <n:set var="startPosition" value="${pagination.startPosition}" scope="page" />
        </c:if>
        <c:if test="${empty listSearchInfo}">
            <n:set var="startPosition" value="1" scope="page" />
        </c:if>
        <app:table resultSetName="${resultSetName}"
                 resultSetCss="${resultSetCss}"
                 varRowName="${varRowName}"
                 varStatusName="${varStatusName}"
                 varCountName="${varCountName}"
                 varRowCountName="${varRowCountName}"
                 varOddEvenName="${varOddEvenName}"
                 oddValue="${oddValue}"
                 evenValue="${evenValue}"
                 startPosition="${startPosition}">
            <jsp:attribute name="headerRowFragment"><jsp:invoke fragment="headerRowFragment" /></jsp:attribute>
            <jsp:attribute name="bodyRowFragment"><jsp:invoke fragment="bodyRowFragment" /></jsp:attribute>
        </app:table>
    </c:if>
</c:if>
