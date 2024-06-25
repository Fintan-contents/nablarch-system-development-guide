<%--------------------------------------------------------------
行データ、ヘッダ行とボディ行のフラグメントを使用してテーブルを出力するタグ。
--------------------------------------------------------------%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags/listSearchResult" %>
<%--------------------------------------------------------------
属性
--------------------------------------------------------------%>
<%@ attribute name="resultSetName" required="true" rtexprvalue="true" %>
<%@ attribute name="headerRowFragment" required="true" fragment="true" %>
<%@ attribute name="bodyRowFragment" required="true" fragment="true" %>
<%@ attribute name="startPosition" required="true" rtexprvalue="true" type="java.lang.Integer" %>

<%--------------------------------------------------------------
本体処理
--------------------------------------------------------------%>
<div class="sixteen wide column">
    <table class="ui celled table">
        <%-- ヘッダ行 --%>
        <thead>
            <jsp:invoke fragment="headerRowFragment"/>
        </thead>
        <%-- ボディ行 --%>
        <tbody>
            <n:set var="resultSet" name="${resultSetName}" scope="page" bySingleValue="false"/>
            <c:forEach var="nablarch_row" items="${resultSet}" varStatus="nablarch_status">
                <n:set var="row" value="${nablarch_row}"/>
                <n:set var="status" value="${nablarch_status}"/>
                <n:set var="count" value="${nablarch_status.count}"/>
                <n:set var="rowCount" value="${startPosition + nablarch_status.index}"/>
                <jsp:invoke fragment="bodyRowFragment"/>
            </c:forEach>
        </tbody>
    </table>
</div>