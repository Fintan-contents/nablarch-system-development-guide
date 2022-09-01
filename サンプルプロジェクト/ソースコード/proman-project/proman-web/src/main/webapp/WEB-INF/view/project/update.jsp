<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.js"></script>
    <%-- stylesheets --%>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.css" />
    <script type="text/javascript">
            $(function () {
                $("#projectStartDate").calendar({
                    type: 'date',
                    formatter: {
                        date: function (date) {
                            var day = ('0' + date.getDate()).slice(-2);
                            var month = ('0' + (date.getMonth() + 1)).slice(-2);
                            var year = date.getFullYear();
                            return year + '/' + month + '/' + day;
                        }
                    }
                });
                $("#projectEndDate").calendar({
                    type: 'date',
                    formatter: {
                        date: function (date) {
                            var day = ('0' + date.getDate()).slice(-2);
                            var month = ('0' + (date.getMonth() + 1)).slice(-2);
                            var year = date.getFullYear();
                            return year + '/' + month + '/' + day;
                        }
                    }
                });
            });
        </script>
    <title>
        プロジェクト更新 | Proman
    </title>
</head>
<body>
    <div class="mainContents">
        <n:include path="/WEB-INF/view/common/header.jsp" />
    </div>
    <div class="ui main container">
        <div class="ui grid container">
            <n:include path="/WEB-INF/view/common/menu.jsp" />
            <div class="twelve wide column row">
                <div class="sixteen wide column row">
                    <div class="ui huge header">プロジェクト更新</div>
                </div>
                <div class="sixteen wide column row">
                    <n:form useToken="true" cssClass="ui form">
                        <div class="two fields">
                            <div class="required field">
                                <label>事業部/部門</label>
                                <div class="field">
                                    <n:set var="topOrganizationList" value="${topOrganization}"/>
                                    <n:select name="form.divisionId" listName="topOrganizationList" elementValueProperty="organizationId"
                                        elementLabelProperty="organizationName" elementLabelPattern="$LABEL$" withNoneOption="true"
                                        cssClass="ui dropdown" />
                                    <n:error errorCss="message-error" name="form.divisionId" /><span>&nbsp;</span>
                                </div>
                            </div>
                            <div class="field">
                                <label>&nbsp;</label>
                                <div class="required field">
                                    <n:set var="subOrganizationList" value="${subOrganization}" />
                                    <n:select name="form.organizationId" listName="subOrganizationList" elementValueProperty="organizationId"
                                        elementLabelProperty="organizationName" elementLabelPattern="$LABEL$" withNoneOption="true"
                                        cssClass="ui dropdown"/>
                                    <n:error errorCss="message-error" name="form.organizationId" /><span>&nbsp;</span>
                                </div>
                            </div>
                        </div>
                        <div class="required field">
                            <label>PJ名</label>
                            <n:text name="form.projectName" maxlength="128" errorCss="input-error" />
                            <n:error errorCss="message-error" name="form.projectName" /><span>&nbsp;</span>
                        </div>
                        <div class="two fields">
                            <div class="required field">
                                <label>PJ種別</label>
                                <div class="field">
                                    <n:codeSelect name="form.projectType" codeId="C0300001" pattern="PATTERN01" />
                                    <n:error errorCss="message-error" name="form.projectType" /><span>&nbsp;</span>
                                </div>
                            </div>
                            <div class="required field">
                                <label>PJ分類</label>
                                <div class="item">
                                    <div class = "fields code-radio-fields">
                                        <n:codeRadioButtons cssClass="code-radio" name="form.projectClass" codeId="C0200001" pattern="PATTERN01" listFormat ="div"/>
                                    </div>
                                    <n:error errorCss="message-error" name="form.projectClass" /><span>&nbsp;</span>
                                </div>
                            </div>
                        </div>
                        <div class="two fields">
                            <div class="field">
                                <label>売上高</label>
                                <n:forInputPage>
                                    <n:text name="form.salesAmount" maxlength="9" cssClass="form-control width-200" errorCss="input-error" style="float:left;"/>
                                    <n:error errorCss="message-error" name="form.salesAmount" /><span>&nbsp;</span>
                                </n:forInputPage>
                                <n:forConfirmationPage>
                                    <n:write value="${n:format('number', form.salesAmount, '###,###,###')}" />
                                </n:forConfirmationPage>
                            </div>
                            <div class="required field">
                                <label>顧客</label>
                                <n:forInputPage>
                                    <div class="ui action input">
                                        <n:plainHidden name="form.clientId" />
                                        <n:text name="form.clientName" maxlength="64" readonly="true" tabindex="-1" id="client-name" placeholder="顧客" />
                                        <n:button uri="/action" allowDoubleSubmission="false"
                                            cssClass="ui teal right labeled icon button" disabled="true">
                                            <i class="search icon"></i>
                                            選択
                                        </n:button>
                                    </div>
                                </n:forInputPage>
                                <n:forConfirmationPage>
                                    <n:write name="form.clientId" />
                                </n:forConfirmationPage>
                            </div>
                        </div>
                        <div class="two fields">
                            <div class="required field">
                                <label>PM</label>
                                <n:text name="form.pmKanjiName" maxlength="64" errorCss="input-error"/>
                                <n:error errorCss="message-error" name="form.pmKanjiName" /><span>&nbsp;</span>
                            </div>
                            <div class="required field">
                                <label>PL</label>
                                <n:text name="form.plKanjiName" maxlength="64" errorCss="input-error"/>
                                <n:error errorCss="message-error" name="form.plKanjiName" /><span>&nbsp;</span>
                            </div>
                        </div>
                        <div class="two fields row">
                            <div class="required field">
                                <label>開始日</label>
                                <n:forInputPage>
                                    <div class="ui calendar">
                                        <div class="ui input left icon" id="projectStartDate">
                                            <i class="calendar icon"></i>
                                            <n:text name="form.projectStartDate" nameAlias="form.date" errorCss="input-error" placeholder="開始日" />
                                        </div>
                                        <n:error errorCss="message-error" name="form.projectStartDate" /><span>&nbsp;</span>
                                    </div>
                                </n:forInputPage>
                                <n:forConfirmationPage>
                                    <n:set var="projectStartDate" name="form.projectStartDate" scope="page" />
                                    <n:write value="${n:formatByDefault('dateTime', projectStartDate)}" />
                                </n:forConfirmationPage>
                            </div>
                            <div class="required field">
                                <label>終了日</label>
                                <n:forInputPage>
                                    <div class="ui calendar">
                                        <div class="ui input left icon" id="projectEndDate">
                                            <i class="calendar icon"></i>
                                            <n:text name="form.projectEndDate" nameAlias="form.date" errorCss="input-error" placeholder="終了日" />
                                        </div>
                                        <n:error errorCss="message-error" name="form.projectEndDate" />
                                        <n:error errorCss="message-error" name="form.validProjectPeriod" /><span>&nbsp;</span>
                                    </div>
                                </n:forInputPage>
                                <n:forConfirmationPage>
                                    <n:set var="projectEndDate" name="form.projectEndDate" scope="page" />
                                    <n:write value="${n:formatByDefault('dateTime', projectEndDate)}" />
                                </n:forConfirmationPage>
                            </div>
                        </div>
                        <div class="field text">
                            <label>備考</label>
                            <n:textarea rows="3" cols="" name="form.note" errorCss="input-error"/>
                            <n:error errorCss="message-error" name="form.note" /><span>&nbsp;</span>
                        </div>
                        <br>
                        <div class="four fields">
                            <div class="field centered aligned">&nbsp;</div>
                            <n:forInputPage>
                                <div class="field centered aligned">
                                    <n:a href="/app/project/detail/${projectId}" cssClass="fluid ui primary button">戻る</n:a>
                                </div>
                                <div class="field centered aligned">
                                    <n:button uri="/app/project/confirmUpdate" cssClass="fluid ui primary button">更新
                                    </n:button>
                                </div>
                            </n:forInputPage>
                            <n:forConfirmationPage>
                                <div class="field centered aligned">
                                    <n:button uri="/app/project/backUpdate" cssClass="fluid ui primary button">戻る
                                    </n:button>
                                </div>
                                <div class="field centered aligned">
                                    <n:button uri="/app/project/update" cssClass="fluid ui primary button"
                                        allowDoubleSubmission="false">確定</n:button>
                                </div>
                            </n:forConfirmationPage>
                            <div class="field centered aligned">&nbsp;</div>
                        </div>
                    </n:form>
                </div>
            </div>
        </div>
    <n:include path="/WEB-INF/view/common/footer.jsp" />

    <%-- 顧客検索 --%>
</body>

</html>
