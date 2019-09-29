<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.4.1/dist/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.js"></script>
    <%-- stylesheets --%>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.css" />

    <title>
        プロジェクト登録完了 | Proman
    </title>
</head>

<body>
    <n:include path="/WEB-INF/view/common/noscript.jsp" />
    <div class="mainContents">
        <n:include path="/WEB-INF/view/common/header.jsp" />
    </div>
    <div class="ui main container">
        <div class="ui  grid container">
            <n:include path="/WEB-INF/view/common/menu.jsp" />
            <div class="twelve wide column row">
                <div class="sixteen wide column row">
                    <div class="ui huge header">プロジェクト登録完了</div>
                </div>
                <div class="sixteen wide column row">
                    <form class="ui form">
                        <div class="field centered aligned">
                            登録が完了しました。
                        </div>
                        <div class="four fields">
                            <div class="field centered aligned">&nbsp;</div>
                            <div class="field centered aligned">&nbsp;</div>
                            <div class="field centered aligned">
                                <n:a href="/app/project/register" cssClass="fluid ui primary button">次へ</n:a>
                            </div>
                            <div class="field centered aligned">&nbsp;</div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <n:include path="/WEB-INF/view/common/footer.jsp" />
</body>

</html>
