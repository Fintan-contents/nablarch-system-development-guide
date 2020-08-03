<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%-- javascript --%>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.4.1/dist/jquery.min.js"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.js"></script>
        <%-- stylesheet --%>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.css" />
        <title>Login</title>
    </head>

    <body>
        <div class="mainContents">
            <n:include path="/WEB-INF/view/common/header.jsp" />
        </div>
        </div>
        <div class="ui main container">
            <div class="ui grid container">
                <div class="twelve wide column row">
                    <div class="sixteen wide column row">
                        <div class="ui huge header">Login</div>
                        </br>
                    </div>
                    <div class="sixteen wide column row">
                        <n:form cssClass="ui form">
                            <%-- Error display area --%>
                            <div class="message-area margin-top">
                                <n:errors filter="global" cssClass="message-error"/>
                            </div>
                            <div class="two fields">
                                <div class="required field">
                                    <label>Login ID</label>
                                    <n:text name="loginId" cssClass="form-control" errorCss="input-error" placeholder="Login ID"/>
                                    <%-- Error display area --%><n:error errorCss="message-error" name="loginId" /><span>&nbsp;</span>
                                </div>
                            </div>
                            <div class="two fields">
                                <div class="required field">
                                    <label>Password</label>
                                    <n:password name="userPassword" cssClass="form-control" errorCss="input-error" placeholder="Password" autocomplete="off"/>
                                    <%-- Error display area --%><n:error errorCss="message-error" name="userPassword" /><span>&nbsp;</span>
                                </div>
                            </div>
                            <div class="six fields">
                                <div class="field centered aligned">&nbsp;</div>
                                <div class="field centered aligned">
                                    <n:button uri="/app/login" cssClass="fluid ui primary button" allowDoubleSubmission="false">Login</n:button>
                                </div>
                            </div>
                        </n:form>
                    </div>
                </div>
            </div>
        </div>
        <n:include path="/WEB-INF/view/common/footer.jsp" />
    </body>
</html>
