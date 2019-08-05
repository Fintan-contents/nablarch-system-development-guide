<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <n:link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" />
    <n:link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css" />
    <n:link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.css" />
    <n:script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.4.1/dist/jquery.min.js"></n:script>
    <n:script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></n:script>
    <n:script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js"></n:script>
    <n:script type="text/javascript" src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"></n:script>
    <n:script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.js"></n:script>
    <title>
        プロジェクト削除 | Proman
    </title>
</head>

<body>
    <n:include path="/WEB-INF/view/common/header.jsp" />
    <div class="ui main container">
        <div class="ui  grid container">
            <n:include path="/WEB-INF/view/common/menu.jsp" />
            <div class="twelve wide column row">
                <div class="sixteen wide column row">
                    <div class="ui huge header">プロジェクト削除</div>
                </div>
                <div class="sixteen wide column row">
                    <n:form useToken="true" cssClass="ui form">
                        <n:plainHidden name="form.projectId" id="projectId"/>
                        <div class="two fields">
                            <div class="field">
                                <label>事業部/部門</label>
                                <div class="field">
                                    <n:write name="form.divisionName"/>
                                </div>
                            </div>
                            <div class="field">
                                <label>&nbsp;</label>
                                <div class="field">
                                    <n:write name="form.organizationName"/>
                                </div>
                            </div>
                        </div>
                        <div class="field">
                            <label>PJ名</label>
                            <n:write name="form.projectName"/>
                        </div>
                        <div class="two fields">
                            <div class="field">
                                <label>PJ種別</label>
                                <div class="field">
                                    <n:code name="form.projectType" codeId="C0300001" />
                                </div>
                            </div>
                            <div class="field">
                                <label>PJ分類</label>
                                <div class="field">
                                    <n:code name="form.projectClass" codeId="C0200001" />
                                </div>
                            </div>
                        </div>
                        <div class="two fields">
                            <div class="field">
                                <label>売上高</label>
                                <n:write value="${n:format('number', form.sales, '###,###,### 千円')}"/>
                            </div>
                            <div class="field">
                                <label>顧客</label>
                                <div class="ui action input">
                                <n:write name="form.clientId"/>
                                </div>
                            </div>
                        </div>
                        <div class="two fields">
                            <div class="field">
                                <label>PM</label>
                                <n:write name="form.projectManager"/>
                            </div>
                            <div class="field">
                                <label>PL</label>
                                <n:write name="form.projectLeader"/>
                            </div>
                        </div>
                        <div class="two fields">
                            <div class="field">
                                <label>開始日</label>
                                <n:write value="${n:formatByDefault('dateTime', form.projectStartDate)}"/>
                            </div>
                            <div class="field">
                                <label>終了日</label>
                                <n:write value="${n:formatByDefault('dateTime', form.projectEndDate)}"/>
                            </div>
                        </div>
                        <div class="field">
                            <label>備考</label>
                            <n:write name="form.note"/>
                        </div>
                        <br>
                        <div class="four fields">
                            <div class="field centered aligned">

                                <n:a href="/action/projectSearch/find/${form.projectId}" cssClass="fluid ui primary button">戻る</n:a>
                            </div>
                            <div class="field centered aligned">&nbsp;</div>
                            <div class="field centered aligned">
                                <n:button uri="/action/ProjectDelete/delete" cssClass="fluid ui youtube button" allowDoubleSubmission="false">確定</n:button>
                            </div>
                        </div>
                    </n:form>
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
