<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
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
        <n:script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.4.1/dist/jquery.min.js"/>
        <n:script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"/>
        <n:script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js"/>
        <n:script type="text/javascript" src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"/>
        <n:script type="text/javascript"
                  src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.js"/>
        <title>
            プロジェクト詳細 | Proman
        </title>
    </head>

    <body>
        <div class="mainContents">
            <n:include path="/WEB-INF/view/common/header.jsp"/>
        </div>
        <div class="ui main container">
            <div class="ui grid">
                <n:include path="/WEB-INF/view/common/menu.jsp"/>
                <div class="twelve wide column">
                    <div class="ui two column grid">
                        <div class="row">
                            <div class="sixteen wide column">
                                <div class="ui huge header">プロジェクト詳細</div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="column">
                                <h5 class="ui header">事業部/部門</h5>
                                <n:write name="project.divisionName"/>
                            </div>
                            <div class="column">
                                <h5 class="ui header">&nbsp;</h5>
                                <n:write name="project.organizationName"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="sixteen wide column">
                                <h5 class="ui header">PJ名</h5>
                                <n:write name="project.projectName"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="column">
                                <h5 class="ui header">PJ種別</h5>
                                <n:code codeId="C0300001" name="project.projectType"/>
                            </div>
                            <div class="column">
                                <h5 class="ui header">PJ分類</h5>
                                <n:code codeId="C0200001" name="project.projectClass"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="column">
                                <h5 class="ui header">売上高</h5>
                                <n:write value="${n:format('number', project.sales, '###,###,### 千円')}"/>
                            </div>
                            <div class="column">
                                <h5 class="ui header">顧客</h5>
                                <n:write name="clientName"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="column">
                                <h5 class="ui header">PM</h5>
                                <n:write name="project.projectManager"/>
                            </div>
                            <div class="column">
                                <h5 class="ui header">PL</h5>
                                <n:write name="project.projectLeader"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="column">
                                <h5 class="ui header">開始日</h5>
                                <n:write value="${n:formatByDefault('dateTime', project.projectStartDate)}"/>
                            </div>
                            <div class="column">
                                <h5 class="ui header">終了日</h5>
                                <n:write value="${n:formatByDefault('dateTime', project.projectEndDate)}"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="sixteen wide column">
                                <h5 class="ui header">備考</h5>
                                <n:write name="project.note"/>
                            </div>
                        </div>
                        <div class="row">
                            <n:form method="GET" action="/app/project/backToList">
                                <button type="submit" class="ui left floated primary button">
                                    戻る
                                </button>
                            </n:form>
                        </div>
                    </div>
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
