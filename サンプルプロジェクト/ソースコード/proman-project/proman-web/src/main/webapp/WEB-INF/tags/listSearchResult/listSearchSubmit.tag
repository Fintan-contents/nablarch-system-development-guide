<%--------------------------------------------------------------
検索結果のリスト表示のサブミット要素を出力するタグ。
--------------------------------------------------------------%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags/listSearchResult" %>
<%--------------------------------------------------------------
属性
--------------------------------------------------------------%>
<%@ attribute name="label" required="true" rtexprvalue="true" %>
<%@ attribute name="enable" required="true" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="uri" required="true" rtexprvalue="true" %>
<%@ attribute name="pageNumber" required="true" rtexprvalue="true" %>
<%@ attribute name="submitFormName" required="true" rtexprvalue="true" %>

<%--------------------------------------------------------------
本体処理
--------------------------------------------------------------%>
<c:if test="${enable}">
    <c:url value="${uri}" var="uri" context="/">
        <c:if test="${not empty pageNumber}">
            <c:param name="${submitFormName}.pageNumber" value="${pageNumber}"/>
        </c:if>
    </c:url>
    <n:a href="${uri}" cssClass="item"><n:write name="label"/></n:a>
</c:if>
<c:if test="${not enable}">
    <c:if test="${label == pageNumber}">
        <a class="active item" href="javascript:void(0)"><n:write name="label"/></a>
    </c:if>
    <c:if test="${label != pageNumber}">
        <a class="item" href="javascript:void(0)"><n:write name="label"/></a>
    </c:if>
</c:if>
