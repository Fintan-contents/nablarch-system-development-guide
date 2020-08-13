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
        Home page | Proman
    </title>
</head>
<body>
    <n:include path="/WEB-INF/view/common/header.jsp" />
    <div class="ui main container">
        <h1 class="ui header">
            <div class="content">Collecting and using company-wide projects</div>
        </h1>
        </br>
        <h4 class="ui dividing header">Project management</h4>
        <label class="field">　+　<n:a href="/app/project/search">Project search</n:a></label></br>
        <c:choose>
            <c:when test="${userContext != null && userContext.pmFlag}">
                <label class="field">　+　<n:a href="/app/project/register">Project registration</n:a></label></br>
            </c:when>
        </c:choose>
        </br>
        <h4 class="ui dividing header">Client management</h4>
        <label class="field">　+　<n:a href="#">Client search</n:a></label></br>
        <c:choose>
            <c:when test="${userContext != null && userContext.pmFlag}">
                <label class="field">　+　<n:a href="#">Client registration</n:a></label></br>
            </c:when>
        </c:choose>
        </br>
        <c:choose>
            <c:when test="${userContext != null && userContext.pmFlag}">
                <h4 class="ui dividing header">Extraction of projects that each user is involved in</h4>
                <label class="field">　+　<n:a href="#">Instructions for extraction of projects that each user is involved in</n:a></label></br>
                </br>
                <h4 class="ui dividing header">Project information batches</h4>
                <label class="field">　+　<n:a href="#">Batch registration of projects</n:a></label></br>
            </c:when>
        </c:choose>
    </div>
</body>
<n:include path="/WEB-INF/view/common/footer.jsp" />
</html>
