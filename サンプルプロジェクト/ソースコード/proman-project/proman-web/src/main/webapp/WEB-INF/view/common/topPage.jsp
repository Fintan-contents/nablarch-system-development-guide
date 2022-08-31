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
    <n:script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.min.js"></n:script>
    <n:script type="text/javascript" src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"></n:script>
    <n:script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.js"></n:script>
    <title>
        トップページ | Proman
    </title>
</head>
<body>
    <n:include path="/WEB-INF/view/common/header.jsp" />
    <div class="ui main container">
        <h1 class="ui header">
            <div class="content">全社のプロジェクトを収集、活用</div>
        </h1>
        </br>
        <h4 class="ui dividing header">プロジェクト管理</h4>
        <label class="field">　┗　<n:a href="/app/project/search">プロジェクト検索</n:a></label></br>
        <c:choose>
            <c:when test="${userContext != null && userContext.pmFlag}">
                <label class="field">　┗　<n:a href="/app/project/register">プロジェクト登録</n:a></label></br>
            </c:when>
        </c:choose>
        </br>
        <h4 class="ui dividing header">顧客管理</h4>
        <label class="field">　┗　<n:a href="#">顧客検索</n:a></label></br>
        <c:choose>
            <c:when test="${userContext != null && userContext.pmFlag}">
                <label class="field">　┗　<n:a href="#">顧客登録</n:a></label></br>
            </c:when>
        </c:choose>
        </br>
        <c:choose>
            <c:when test="${userContext != null && userContext.pmFlag}">
                <h4 class="ui dividing header">ユーザ別従事プロジェクト抽出</h4>
                <label class="field">　┗　<n:a href="#">ユーザ別従事プロジェクト抽出指示</n:a></label></br>
                </br>
                <h4 class="ui dividing header">プロジェクト情報一括</h4>
                <label class="field">　┗　<n:a href="#">プロジェクト一括登録</n:a></label></br>
            </c:when>
        </c:choose>
    </div>
</body>
<n:include path="/WEB-INF/view/common/footer.jsp" />
</html>
