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
        Project details | Proman
    </title>
    <n:script type="text/javascript">
        $(function(){
            setListUrlTo("returnToList");
        });
    </n:script>
</head>

<body>
    <n:include path="/WEB-INF/view/common/header.jsp" />
    <div class="ui main container">
        <div class="ui  grid container">
            <n:include path="/WEB-INF/view/common/menu.jsp" />
            <div class="twelve wide column row">
                <div class="sixteen wide column row">
                    <div class="ui huge header">Project details</div>
                </div>
                <div class="sixteen wide column row">
                    <n:form cssClass="ui form">
                        <n:plainHidden name="project.projectId" id="projectId"/>
                        <div class="two fields">
                            <div class="field">
                                <label>Division/department</label>
                                <div class="field">
                                    <n:write name="project.divisionName"/>
                                </div>
                            </div>
                            <div class="field">
                                <label>&nbsp;</label>
                                <div class="field">
                                    <n:write name="project.organizationName"/>
                                </div>
                            </div>
                        </div>
                        <div class="field">
                            <label>Project name</label>
                            <n:write name="project.projectName"/>
                        </div>
                        <div class="two fields">
                            <div class="field">
                                <label>Project type</label>
                                <div class="field">
                                    <n:code name="project.projectType" codeId ="C0300001" pattern="PATTERN01" />
                                </div>
                            </div>
                            <div class="field">
                                <label>Project classification</label>
                                <div class="field">
                                    <n:code name="project.projectClass" codeId ="C0200001" pattern="PATTERN01" />
                                </div>
                            </div>
                        </div>
                        <div class="two fields">
                            <div class="field">
                                <label>Sales</label>
                                <n:write value="${n:format('number', project.sales, '###,###,###,000 yen')}"/>
                            </div>
                            <div class="field">
                                <label>Client</label>
                                <div class="ui action input">
                                <n:write name="project.clientId"/>
                                </div>
                            </div>
                        </div>
                        <div class="two fields">
                            <div class="field">
                                <label>PM</label>
                                <n:write name="project.projectManager"/>
                            </div>
                            <div class="field">
                                <label>PL</label>
                                <n:write name="project.projectLeader"/>
                            </div>
                        </div>
                        <div class="two fields">
                            <div class="field">
                                <label>Start date</label>
                                <n:write value="${n:formatByDefault('dateTime', project.projectStartDate)}"/>
                            </div>
                            <div class="field">
                                <label>End date</label>
                                <n:write value="${n:formatByDefault('dateTime', project.projectEndDate)}"/>
                            </div>
                        </div>
                        <div class="field text">
                            <label>Note</label>
                            <n:write name="project.note"/>
                        </div>
                        <br>
                        <div class="four fields">
                            <div class="field centered aligned">
                                <n:a id="returnToList" href="#" cssClass="fluid ui primary button">Back</n:a>
                            </div>
                            <div class="field centered aligned">&nbsp;</div>
                            <div class="field centered aligned">
                                <n:button uri="/action/ProjectUpdate/index/${project.projectId}" cssClass="fluid ui primary button">Update</n:button>
                            </div>
                            <div class="field centered aligned">
                                <n:button uri="/action/ProjectDelete/confirmDeletion/${project.projectId}" cssClass="fluid ui youtube button">Delete</n:button>
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