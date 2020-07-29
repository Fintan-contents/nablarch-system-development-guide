<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags/listSearchResult" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <n:script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.4.1/dist/jquery.min.js"></n:script>
    <n:script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></n:script>
    <n:script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js"></n:script>
    <n:script type="text/javascript" src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"></n:script>
    <n:script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.js"></n:script>
    <%-- stylesheets --%>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.css" />
    <n:script type="text/javascript">
        var dateFormatter = function (date) {
            var day = ('0' + date.getDate()).slice(-2);
            var month = ('0' + (date.getMonth() + 1)).slice(-2);
            var year = date.getFullYear();
            return year + '/' + month + '/' + day;
        }
        $(function () {
            $("#example1").calendar({
                    type: 'date',
                    formatter: {
                        date: dateFormatter
                    }
            });
            $("#example2").calendar({
                    type: 'date',
                    formatter: {
                        date: dateFormatter
                    }
            });
            $("#example3").calendar({
                    type: 'date',
                    formatter: {
                        date: dateFormatter
                    }
            });
            $("#example4").calendar({
                    type: 'date',
                    formatter: {
                        date: dateFormatter
                    }
            });
            $("#searchButton").click(function(){
                    $("#pageNumber").val("1");
            });
            saveListUrl();
        });
    </n:script>
    <title>
        Project search | Proman
    </title>
</head>

<body>
    <n:include path="/WEB-INF/view/common/header.jsp" />
    <div class="ui main container">
        <div class="ui  grid container">
            <n:include path="/WEB-INF/view/common/menu.jsp" />
            <div class="twelve wide column row">
                <div class="sixteen wide column row">
                    <div class="ui huge header">Project search</div>
                </div>
                <div class="sixteen wide column row">
                    <n:form method="GET" action="/action/projectSearch/search" cssClass="ui form">
                        <div class="message-area margin-top">
                            <n:errors filter="global" cssClass="message-error"/>
                        </div>
                        <n:plainHidden name="searchForm.pageNumber" id="pageNumber"/>
                        <div class="two fields">
                            <div class="field">
                                <label>Division</label>
                                <div class="field">
                                    <n:set var="topOrganizationList" value="${topOrganization}" />
                                    <n:select name="searchForm.divisionId" listName="topOrganizationList" elementValueProperty="organizationId"
                                        elementLabelProperty="organizationName" elementLabelPattern="$LABEL$" withNoneOption="true"
                                        cssClass="ui dropdown" />
                                    <n:error errorCss="message-error" name="searchForm.divisionId" /><span>&nbsp;</span>
                                </div>
                            </div>
                            <div class="field">
                                <label>Department</label>
                                <div class="field">
                                    <n:set var="subOrganizationList" value="${subOrganization}" />
                                    <n:select name="searchForm.organizationId" listName="subOrganizationList" elementValueProperty="organizationId"
                                        elementLabelProperty="organizationName" elementLabelPattern="$LABEL$" withNoneOption="true"
                                        cssClass="ui dropdown" />
                                    <n:error errorCss="message-error" name="searchForm.organizationId" /><span>&nbsp;</span>
                                </div>
                            </div>
                        </div>
                        <div class="field">
                            <label>Project classification</label>
                            <div class="item">
                                <div class = "fields code-checkbox-fields">
                                    <n:codeCheckboxes cssClass="code-checkbox" name="searchForm.projectClass" codeId="C0200001" pattern="PATTERN01" listFormat ="span"/>
                                </div>
                                <n:error errorCss="message-error" name="searchForm.projectClass" /><span>&nbsp;</span>
                            </div>
                        </div>
                        <div class="field">
                            <label>Project type</label>
                            <div class="item">
                                <div class="fields code-checkbox-fields">
                                    <n:codeCheckboxes cssClass="code-checkbox" name="searchForm.projectType" codeId="C0300001" pattern="PATTERN01" listFormat ="span"/>
                                </div>
                                <n:error errorCss="message-error" name="searchForm.projectType" /><span>&nbsp;</span>
                            </div>
                        </div>
                        <div class="two fields">
                            <div class="field">
                                <label>Sales_results_FROM</label>
                                <n:text placeholder="Sales_results_FROM" name="searchForm.salesFrom" />
                                <n:error errorCss="message-error" name="searchForm.salesFrom" /><span>&nbsp;</span>
                            </div>
                            <div class="field">
                                <label>Sales_results_TO</label>
                                <n:text placeholder="Sales_results_TO" name="searchForm.salesTo" />
                                <n:error errorCss="message-error" name="searchForm.salesTo" /><span>&nbsp;</span>
                            </div>
                        </div>
                        <div class="two fields">
                            <div class="field">
                                <label>Start date_FROM</label>
                                <div class="ui calendar" id="example1">
                                    <div class="ui input left icon">
                                        <i class="calendar icon"></i>
                                        <n:text placeholder="Start date_FROM" name="searchForm.projectStartDateBegin" />
                                        <n:error errorCss="message-error" name="searchForm.projectStartDateBegin" /><span>&nbsp;</span>
                                    </div>
                                </div>
                            </div>
                            <div class="field">
                                <label>Start date_TO</label>
                                <div class="ui calendar" id="example2">
                                    <div class="ui input left icon">
                                        <i class="calendar icon"></i>
                                        <n:text placeholder="Start date_TO" name="searchForm.projectStartDateEnd" />
                                        <n:error errorCss="message-error" name="searchForm.projectStartDateEnd" /><span>&nbsp;</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="two fields">
                            <div class="field">
                                <label>End date_FROM</label>
                                <div class="ui calendar" id="example3">
                                    <div class="ui input left icon">
                                        <i class="calendar icon"></i>
                                        <n:text placeholder="End date_TO" name="searchForm.projectEndDateEnd" />
                                        <n:error errorCss="message-error" name="searchForm.projectEndDateBegin" /><span>&nbsp;</span>
                                    </div>
                                </div>
                            </div>
                            <div class="field">
                                <label>End date_TO</label>
                                <div class="ui calendar" id="example4">
                                    <div class="ui input left icon">
                                        <i class="calendar icon"></i>
                                        <n:text placeholder="End date_FROM" name="searchForm.projectEndDateBegin" />
                                        <n:error errorCss="message-error" name="searchForm.projectEndDateEnd" /><span>&nbsp;</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="field">
                            <label>Search by project name</label>
                            <n:text placeholder="Multiple items must be separated by single-byte spaces" name="searchForm.projectName" />
                            <n:error errorCss="message-error" name="searchForm.projectName" /><span>&nbsp;</span>
                        </div>
                        <div class="four fields">
                            <div class="field centered aligned">&nbsp;</div>
                            <div class="field centered aligned">&nbsp;</div>
                            <div class="field centered aligned">&nbsp;</div>
                            <div class="field centered aligned">
                                <n:button cssClass="fluid ui primary button" uri="/action" id="searchButton">Search</n:button>
                            </div>
                        </div>
                    </n:form>
                </div>
                <div class="sixteen wide column row">
                    <%-- spacer --%>
                    <div><span>&nbsp;</span></div>
                </div>
                <div class="sixteen wide column row">
                    <%-- spacer --%>
                    <div><span>&nbsp;</span></div>
                </div>
                <!-- Search results are only displayed if no validation errors occur in the search form. -->
                <c:if test="${searchResult != null}">

                                        <n:form method="GET" action="/action/projectSearch/search">

                                            <%-- URIs with parameters consisting of search conditions used to display the current search results
                                               are registered as variables within the page scope.
                                               These variables are used as pagination URIs for the <app:listSearchResult> tag. --%>
                                            <c:url value="/action/projectSearch/search" var="uri" context="/">
                                                <c:param name="searchForm.divisionId" value="${searchForm.divisionId}"/>
                                                <c:param name="searchForm.organizationId" value="${searchForm.organizationId}"/>
                                                <c:param name="searchForm.projectName" value="${searchForm.projectName}"/>
                                                <c:forEach items="${searchForm.projectType}" var="projectType">
                                                    <c:param name="searchForm.projectType" value="${projectType}" />
                                                </c:forEach>
                                                <c:forEach items="${searchForm.projectClass}" var="projectClass">
                                                    <c:param name="searchForm.projectClass" value="${projectClass}" />
                                                </c:forEach>
                                                <c:param name="searchForm.salesFrom"
                                                         value="${searchForm.salesFrom}"/>
                                                <c:param name="searchForm.salesTo"
                                                         value="${searchForm.salesTo}"/>
                                                <c:param name="searchForm.projectStartDateBegin"
                                                         value="${searchForm.projectStartDateBegin}"/>
                                                <c:param name="searchForm.projectStartDateEnd"
                                                         value="${searchForm.projectStartDateEnd}"/>
                                                <c:param name="searchForm.projectEndDateBegin"
                                                         value="${searchForm.projectEndDateBegin}"/>
                                                <c:param name="searchForm.projectEndDateEnd"
                                                         value="${searchForm.projectEndDateEnd}"/>
                                            </c:url>
                                        </n:form>
                    <!-- Search results -->
                    <app:listSearchResult
                        currentPageNumberCss="form-control"
                        pagingCss="paging"
                        usePageNumberSubmit="true"
                        prevSubmitLabel="＜"
                        nextSubmitLabel="＞"
                        prevSubmitCss="prev-page-link"
                        nextSubmitCss="next-page-link"
                        firstSubmitLabel="≪"
                        lastSubmitLabel="≫"
                        resultSetCss="table table-striped table-hover"
                        listSearchInfoName="searchForm"
                        searchUri="${uri}"
                        resultSetName="searchResult"
                        useResultCount="false">
                        <jsp:attribute name="headerRowFragment">
                            <tr>
                                <th>Project ID</th>
                                <th>Project name</th>
                                <th>Division</th>
                                <th>Department</th>
                                <th>Project classification</th>
                                <th>Project type</th>
                                <th>PM</th>
                                <th>Sales_results</th>
                                <th>Start date</th>
                                <th>End date</th>
                            </tr>
                        </jsp:attribute>
                        <jsp:attribute name="bodyRowFragment">
                            <tr class="info">
                                <td>
                                    <!-- Display link with project ID as a parameter -->
                                    <n:a href="/action/projectSearch/show/${row.projectId}">
                                        <n:write name="row.projectId"/>
                                    </n:a>
                                </td>
                                <td>
                                    <n:write name="row.projectName" />
                                </td>
                                <td>
                                    <n:write name="row.divisionName" />
                                </td>
                                <td>
                                    <n:write name="row.organizationName" />
                                </td>
                                <td>
                                    <n:write name="row.projectClass" />
                                </td>
                                <td>
                                    <n:write name="row.projectType" />
                                </td>
                                <td>
                                    <n:write name="row.projectManager" />
                                </td>
                                <td>
                                    <n:write value="${n:format('number', row.sales, '###,###,###,000 yen')}"/>
                                </td>
                                <td>
                                    <n:write value="${n:formatByDefault('dateTime', row.projectStartDate)}" />
                                </td>
                                <td>
                                    <n:write value="${n:formatByDefault('dateTime', row.projectEndDate)}" />
                                </td>
                            </tr>
                        </jsp:attribute>
                    </app:listSearchResult>
                </c:if>
            </div>
        </div>
    </div>

    <footer>
        <div class="ui inverted vertical footer segment">
            <div class="ui container">
                TIS Inc
            </div>
        </div>
    </footer>
</body>

</html>
