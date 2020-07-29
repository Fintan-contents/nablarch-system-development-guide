<%--------------------------------------------------------------
Tags for outputting list display pagination for search results.
--------------------------------------------------------------%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags/listSearchResult" %>

<%--------------------------------------------------------------
Attributes
--------------------------------------------------------------%>
<%@ attribute name="resultSetName" required="true" rtexprvalue="true" %>
<%@ attribute name="listSearchInfoName" required="true" rtexprvalue="true" %>
<%@ attribute name="pagingCss" required="false" rtexprvalue="true" %>
<%@ attribute name="searchUri" required="true" rtexprvalue="true" %>
<%@ attribute name="submitNameSuffix" required="false" rtexprvalue="true" %>
<%-- Current page number --%>
<%@ attribute name="useCurrentPageNumber" required="false" rtexprvalue="true" %>
<%@ attribute name="currentPageNumberCss" required="false" rtexprvalue="true" %>
<%@ attribute name="currentPageNumberFragment" required="false" fragment="true" %>
<%-- First --%>
<%@ attribute name="useFirstSubmit" required="false" rtexprvalue="true" %>
<%@ attribute name="firstSubmitCss" required="false" rtexprvalue="true" %>
<%@ attribute name="firstSubmitLabel" required="false" rtexprvalue="true" %>
<%@ attribute name="firstSubmitName" required="false" rtexprvalue="true" %>
<%-- Back --%>
<%@ attribute name="usePrevSubmit" required="false" rtexprvalue="true" %>
<%@ attribute name="prevSubmitCss" required="false" rtexprvalue="true" %>
<%@ attribute name="prevSubmitLabel" required="false" rtexprvalue="true" %>
<%@ attribute name="prevSubmitName" required="false" rtexprvalue="true" %>
<%-- Page number (1 2 3 ...n) --%>
<%@ attribute name="usePageNumberSubmit" required="false" rtexprvalue="true" %>
<%@ attribute name="pageNumberSubmitWrapperCss" required="false" rtexprvalue="true" %>
<%@ attribute name="pageNumberSubmitCss" required="false" rtexprvalue="true" %>
<%@ attribute name="pageNumberSubmitName" required="false" rtexprvalue="true" %>
<%-- Next --%>
<%@ attribute name="useNextSubmit" required="false" rtexprvalue="true" %>
<%@ attribute name="nextSubmitCss" required="false" rtexprvalue="true" %>
<%@ attribute name="nextSubmitLabel" required="false" rtexprvalue="true" %>
<%@ attribute name="nextSubmitName" required="false" rtexprvalue="true" %>
<%-- Last --%>
<%@ attribute name="useLastSubmit" required="false" rtexprvalue="true" %>
<%@ attribute name="lastSubmitCss" required="false" rtexprvalue="true" %>
<%@ attribute name="lastSubmitLabel" required="false" rtexprvalue="true" %>
<%@ attribute name="lastSubmitName" required="false" rtexprvalue="true" %>

<%--------------------------------------------------------------
Default
--------------------------------------------------------------%>
<c:if test="${empty pagingCss}"><n:set var="pagingCss" value="nablarch_paging" scope="page" /></c:if>
<%-- Current page number --%>
<c:if test="${empty useCurrentPageNumber}"><n:set var="useCurrentPageNumber" value="true" scope="page" /></c:if>
<c:if test="${empty currentPageNumberCss}"><n:set var="currentPageNumberCss" value="nablarch_currentPageNumber" scope="page" /></c:if>
<%-- First --%>
<c:if test="${empty useFirstSubmit}"><n:set var="useFirstSubmit" value="false" scope="page" /></c:if>
<c:if test="${empty firstSubmitCss}"><n:set var="firstSubmitCss" value="nablarch_firstSubmit" scope="page" /></c:if>
<c:if test="${empty firstSubmitLabel}"><n:set var="firstSubmitLabel" value="first" scope="page" /></c:if>
<c:if test="${empty firstSubmitName}"><n:set var="firstSubmitName" value="firstSubmit" scope="page" /></c:if>
<%-- Back --%>
<c:if test="${empty usePrevSubmit}"><n:set var="usePrevSubmit" value="true" scope="page" /></c:if>
<c:if test="${empty prevSubmitCss}"><n:set var="prevSubmitCss" value="nablarch_prevSubmit" scope="page" /></c:if>
<c:if test="${empty prevSubmitLabel}"><n:set var="prevSubmitLabel" value="back" scope="page" /></c:if>
<c:if test="${empty prevSubmitName}"><n:set var="prevSubmitName" value="prevSubmit" scope="page" /></c:if>
<%-- Page number (1 2 3 ...n) --%>
<c:if test="${empty usePageNumberSubmit}"><n:set var="usePageNumberSubmit" value="false" scope="page" /></c:if>
<c:if test="${empty pageNumberSubmitWrapperCss}"><n:set var="pageNumberSubmitWrapperCss" value="nablarch_pageNumberSubmitWrapper" scope="page" /></c:if>
<c:if test="${empty pageNumberSubmitCss}"><n:set var="pageNumberSubmitCss" value="nablarch_pageNumberSubmit" scope="page" /></c:if>
<c:if test="${empty pageNumberSubmitName}"><n:set var="pageNumberSubmitName" value="pageNumberSubmit" scope="page" /></c:if>
<%-- Next --%>
<c:if test="${empty useNextSubmit}"><n:set var="useNextSubmit" value="true" scope="page" /></c:if>
<c:if test="${empty nextSubmitCss}"><n:set var="nextSubmitCss" value="nablarch_nextSubmit" scope="page" /></c:if>
<c:if test="${empty nextSubmitLabel}"><n:set var="nextSubmitLabel" value="next" scope="page" /></c:if>
<c:if test="${empty nextSubmitName}"><n:set var="nextSubmitName" value="nextSubmit" scope="page" /></c:if>
<%-- Last --%>
<c:if test="${empty useLastSubmit}"><n:set var="useLastSubmit" value="false" scope="page" /></c:if>
<c:if test="${empty lastSubmitCss}"><n:set var="lastSubmitCss" value="nablarch_lastSubmit" scope="page" /></c:if>
<c:if test="${empty lastSubmitLabel}"><n:set var="lastSubmitLabel" value="last" scope="page" /></c:if>
<c:if test="${empty lastSubmitName}"><n:set var="lastSubmitName" value="lastSubmit" scope="page" /></c:if>

<%--------------------------------------------------------------
Process of main unit
--------------------------------------------------------------%>
<n:set var="listSearchInfo" name="${listSearchInfoName}" scope="page" bySingleValue="false" />
<n:set var="resultSet" name="${resultSetName}" scope="page" bySingleValue="false" />
<%-- For resultSet, only the index number can be accessed using an EL formula as resultSet is a class that inherits a list. --%>
<%-- For this reason, pagination is temporarily saved to a variable during use. --%>
<n:set var="pagination" name="${resultSetName}.pagination" scope="page" />
<c:if test="${pagination.resultCount != 0}">

    <div class="sixteen wide column row <n:write name="pagingCss" withHtmlFormat="false" />">
        <div class="ui right pagination menu right floated">
        <%-- First --%>
        <c:if test="${useFirstSubmit}">
            <app:listSearchSubmit
                                css="${firstSubmitCss}"
                                label="${firstSubmitLabel}"
                                enable="${pagination.hasPrevPage}"
                                uri="${searchUri}"
                                name="${firstSubmitName}${submitNameSuffix}"
                                pageNumber="${pagination.firstPageNumber}"
                                listSearchInfoName="${listSearchInfoName}" />
        </c:if>
        <%-- Back --%>
        <c:if test="${usePrevSubmit}">
            <app:listSearchSubmit
                                css="${prevSubmitCss}"
                                label="${prevSubmitLabel}"
                                enable="${pagination.hasPrevPage}"
                                uri="${searchUri}"
                                name="${prevSubmitName}${submitNameSuffix}"
                                pageNumber="${pagination.prevPageNumber}"
                                listSearchInfoName="${listSearchInfoName}" />
        </c:if>
        <%-- Page number (1 2 3 ...n) --%>
        <c:if test="${(usePageNumberSubmit == 'true') && (pagination.pageCount != 1)}">

                <c:forEach begin="1" end="${pagination.pageCount}" varStatus="status">
                    <n:set var="pageNumber" value="${status.index}" scope="page" />
                    <app:listSearchSubmit
                                        css="${pageNumberSubmitCss}"
                                        label="${pageNumber}"
                                        enable="${pagination.pageNumber != pageNumber}"
                                        uri="${searchUri}"
                                        name="${pageNumberSubmitName}${pageNumber}${submitNameSuffix}"
                                        pageNumber="${pageNumber}"
                                        listSearchInfoName="${listSearchInfoName}" />
                </c:forEach>
        </c:if>
        <%-- Next --%>
        <c:if test="${useNextSubmit}">
            <app:listSearchSubmit
                                css="${nextSubmitCss}"
                                label="${nextSubmitLabel}"
                                enable="${pagination.hasNextPage}"
                                uri="${searchUri}"
                                name="${nextSubmitName}${submitNameSuffix}"
                                pageNumber="${pagination.nextPageNumber}"
                                listSearchInfoName="${listSearchInfoName}" />
        </c:if>
        <%-- Last --%>
        <c:if test="${useLastSubmit}">
            <app:listSearchSubmit
                                css="${lastSubmitCss}"
                                label="${lastSubmitLabel}"
                                enable="${pagination.hasNextPage}"
                                uri="${searchUri}"
                                name="${lastSubmitName}${submitNameSuffix}"
                                pageNumber="${pagination.lastPageNumber}"
                                listSearchInfoName="${listSearchInfoName}" />
        </c:if>
        </div>
    </div>
</c:if>
