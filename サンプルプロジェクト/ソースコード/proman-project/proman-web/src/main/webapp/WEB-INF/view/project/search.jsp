<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ taglib prefix="lsr" tagdir="/WEB-INF/tags/listSearchResult" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <n:link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css"/>
        <n:link rel="stylesheet" type="text/css"
                href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css"/>
        <n:link rel="stylesheet" type="text/css"
                href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.css"/>
        <n:script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.min.js"/>
        <n:script type="text/javascript" src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"/>
        <n:script type="text/javascript"
                  src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.js"/>
        <script type="text/javascript">
            $(function () {
                function slashFormat(date) {
                    var day = ('0' + date.getDate()).slice(-2);
                    var month = ('0' + (date.getMonth() + 1)).slice(-2);
                    var year = date.getFullYear();
                    return year + '/' + month + '/' + day;
                }

                $('#projectStartDateFrom').calendar({
                    endCalendar: $('#projectStartDateTo'),
                    type: 'date',
                    formatter: {
                        date: slashFormat
                    }
                });
                $('#projectStartDateTo').calendar({
                    startCalendar: $('#projectStartDateFrom'),
                    type: 'date',
                    formatter: {
                        date: slashFormat
                    }
                });
                $('#projectEndDateFrom').calendar({
                    endCalendar: $('#projectEndDateTo'),
                    type: 'date',
                    formatter: {
                        date: slashFormat
                    }
                });
                $('#projectEndDateTo').calendar({
                    startCalendar: $('#projectEndDateFrom'),
                    type: 'date',
                    formatter: {
                        date: slashFormat
                    }
                });
            });
        </script>
        <title>
            プロジェクト検索 | Proman
        </title>
    </head>

    <body>
        <div class="mainContents">
            <n:include path="/WEB-INF/view/common/header.jsp"/>
        </div>
        <div class="ui main container">
            <div class="ui grid">
                <n:include path="/WEB-INF/view/common/menu.jsp"/>
                <div class="twelve wide column row">
                    <div class="sixteen wide column">
                        <div class="ui huge header">プロジェクト検索</div>
                        <n:errors filter="global" errorCss="message-error"/>
                        <n:form cssClass="ui form" method="GET" action="/app/project/list">
                            <div class="two fields">
                                <div class="field">
                                    <label>
                                        事業部
                                        <n:select cssClass="ui dropdown"
                                                  name="form.divisionId"
                                                  listName="topOrganization"
                                                  elementValueProperty="organizationId"
                                                  elementLabelProperty="organizationName"
                                                  elementLabelPattern="$LABEL$"
                                                  withNoneOption="true"
                                                  noneOptionLabel="事業部を選択"/>
                                    </label>
                                    <n:error name="form.divisionId" errorCss="message-error"/>
                                </div>
                                <div class="field">
                                    <label>
                                        部門
                                        <n:select cssClass="ui dropdown"
                                                  name="form.organizationId"
                                                  listName="subOrganization"
                                                  elementValueProperty="organizationId"
                                                  elementLabelProperty="organizationName"
                                                  elementLabelPattern="$LABEL$"
                                                  withNoneOption="true"
                                                  noneOptionLabel="部門を選択"/>
                                    </label>
                                    <n:error name="form.organizationId" errorCss="message-error"/>
                                </div>
                            </div>
                            <div class="field">
                                <label>PJ種別</label>
                                <div class="fields code-checkbox-fields">
                                    <n:codeCheckboxes cssClass="code-checkbox" name="form.projectType"
                                                      codeId="C0300001" listFormat="span"/>
                                </div>
                                <c:forEach items="${form.projectType}" varStatus="status">
                                    <n:error name="form.projectType[${status.index}].projectType"
                                             errorCss="message-error"/>
                                </c:forEach>
                            </div>
                            <div class="field">
                                <label>PJ分類</label>
                                <div class="fields code-checkbox-fields">
                                    <n:codeCheckboxes cssClass="code-checkbox" name="form.projectClass"
                                                      codeId="C0200001" listFormat="span"/>
                                </div>
                                <c:forEach items="${form.projectClass}" varStatus="status">
                                    <n:error name="form.projectClass[${status.index}].projectClass"
                                             errorCss="message-error"/>
                                </c:forEach>
                            </div>
                            <div class="two fields">
                                <div class="field">
                                    <label>売上高_実績_FROM</label>
                                    <n:text placeholder="売上高_実績_FROM" name="form.salesFrom" maxlength="9"/>
                                    <n:error name="form.salesFrom" errorCss="message-error"/>
                                    <n:error name="form.validProjectSalesRange" errorCss="message-error"/>
                                </div>
                                <div class="field">
                                    <label>売上高_実績_TO</label>
                                    <n:text placeholder="売上高_実績_TO" name="form.salesTo" maxlength="9"/>
                                    <n:error name="form.salesTo" errorCss="message-error"/>
                                </div>
                            </div>
                            <div class="two fields">
                                <div class="field ui calendar" id="projectStartDateFrom">
                                    <label>開始日_FROM</label>
                                    <div class="ui input left icon">
                                        <i class="calendar icon"></i>
                                        <n:text name="form.projectStartDateFrom" placeholder="開始日_FROM"
                                                value="${n:formatByDefault('dateTime', form.projectStartDateFrom)}"
                                                maxlength="10"/>
                                    </div>
                                    <n:error name="form.projectStartDateFrom" errorCss="message-error"/>
                                    <n:error name="form.validProjectStartDateRange" errorCss="message-error"/>
                                </div>
                                <div class="field ui calendar" id="projectStartDateTo">
                                    <label>開始日_TO</label>
                                    <div class="ui input left icon">
                                        <i class="calendar icon"></i>
                                        <n:text name="form.projectStartDateTo" placeholder="開始日_TO"
                                                value="${n:formatByDefault('dateTime', form.projectStartDateTo)}"
                                                maxlength="10"/>
                                    </div>
                                    <n:error name="form.projectStartDateTo" errorCss="message-error"/>
                                </div>
                            </div>
                            <div class="two fields">
                                <div class="field ui calendar" id="projectEndDateFrom">
                                    <label>終了日_FROM</label>
                                    <div class="ui input left icon">
                                        <i class="calendar icon"></i>
                                        <n:text name="form.projectEndDateFrom" placeholder="終了日_FROM"
                                                value="${n:formatByDefault('dateTime', form.projectEndDateFrom)}"
                                                maxlength="10"/>
                                    </div>
                                    <n:error name="form.projectEndDateFrom" errorCss="message-error"/>
                                    <n:error name="form.validProjectEndDateRange" errorCss="message-error"/>
                                </div>
                                <div class="field ui calendar" id="projectEndDateTo">
                                    <label>終了日_TO</label>
                                    <div class="ui input left icon">
                                        <i class="calendar icon"></i>
                                        <n:text name="form.projectEndDateTo" placeholder="終了日_TO"
                                                value="${n:formatByDefault('dateTime', form.projectEndDateTo)}"
                                                maxlength="10"/>
                                    </div>
                                    <n:error name="form.projectEndDateTo" errorCss="message-error"/>
                                </div>
                            </div>
                            <div class="field">
                                <label>プロジェクト名で検索</label>
                                <n:text placeholder="プロジェクト名" name="form.projectName" maxlength="128"/>
                                <n:error name="form.projectName" errorCss="message-error"/>
                            </div>
                            <button type="submit" class="ui right floated primary button">検索</button>
                        </n:form>
                    </div>
                    <c:if test="${searchResult != null}">
                        <%-- 現在の検索結果の表示に使用した検索条件をパラメータとして持つURIを、
                           変数としてpageスコープに登録する。
                           この変数は、<lsr:listSearchResult>タグのページング用のURIとして使用される。--%>
                        <c:url value="/app/project/list" var="uri" context="/">
                            <c:param name="form.divisionId" value="${form.divisionId}"/>
                            <c:param name="form.organizationId" value="${form.organizationId}"/>
                            <c:param name="form.projectName" value="${form.projectName}"/>
                            <c:forEach items="${form.projectType}" var="projectType">
                                <c:param name="form.projectType" value="${projectType}"/>
                            </c:forEach>
                            <c:forEach items="${form.projectClass}" var="projectClass">
                                <c:param name="form.projectClass" value="${projectClass}"/>
                            </c:forEach>
                            <c:param name="form.salesFrom" value="${form.salesFrom}"/>
                            <c:param name="form.salesTo" value="${form.salesTo}"/>
                            <c:param name="form.projectStartDateFrom"
                                     value="${n:formatByDefault('dateTime', form.projectStartDateFrom)}"/>
                            <c:param name="form.projectStartDateTo"
                                     value="${n:formatByDefault('dateTime', form.projectStartDateTo)}"/>
                            <c:param name="form.projectEndDateFrom"
                                     value="${n:formatByDefault('dateTime', form.projectEndDateFrom)}"/>
                            <c:param name="form.projectEndDateTo"
                                     value="${n:formatByDefault('dateTime', form.projectEndDateTo)}"/>
                        </c:url>
                        <!-- 検索結果 -->
                        <lsr:listSearchResult
                                searchUri="${uri}"
                                resultSetName="searchResult">
                            <jsp:attribute name="headerRowFragment">
                                <tr>
                                    <th>PJ名</th>
                                    <th>事業部</th>
                                    <th>部門</th>
                                    <th>PJ種別</th>
                                    <th>PJ分類</th>
                                    <th>PM</th>
                                    <th>売上高_実績</th>
                                    <th>開始日</th>
                                    <th>終了日</th>
                                </tr>
                            </jsp:attribute>
                            <jsp:attribute name="bodyRowFragment">
                                <tr class="info">
                                    <td>
                                        <n:a href="/app/project/detail/${row.projectId}">
                                            <n:write name="row.projectName"/>
                                        </n:a>
                                    </td>
                                    <td><n:write name="row.divisionName"/></td>
                                    <td><n:write name="row.organizationName"/></td>
                                    <td><n:code codeId="C0300001" name="row.projectType"/></td>
                                    <td><n:code codeId="C0200001" name="row.projectClass"/></td>
                                    <td><n:write name="row.projectManager"/></td>
                                    <td><n:write value="${n:format('number', row.sales, '###,###,### 千円')}"/></td>
                                    <td><n:write value="${n:formatByDefault('dateTime', row.projectStartDate)}"/></td>
                                    <td><n:write value="${n:formatByDefault('dateTime', row.projectEndDate)}"/></td>
                                </tr>
                            </jsp:attribute>
                        </lsr:listSearchResult>
                    </c:if>
                </div>
            </div>
        </div>
        <n:include path="/WEB-INF/view/common/footer.jsp"/>
    </body>

</html>
