<%--------------------------------------------------------------
Tags for outputting list display submission elements for search results.
--------------------------------------------------------------%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags/listSearchResult" %>
<%--------------------------------------------------------------
Attributes
--------------------------------------------------------------%>
<%@ attribute name="css" required="false" rtexprvalue="true" %>
<%@ attribute name="label" required="true" rtexprvalue="true" %>
<%@ attribute name="enable" required="true" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="uri" required="true" rtexprvalue="true" %>
<%@ attribute name="name" required="true" rtexprvalue="true" %>
<%@ attribute name="pageNumber" required="true" rtexprvalue="true" %>
<%@ attribute name="sortId" required="false" rtexprvalue="true" %>
<%@ attribute name="listSearchInfoName" required="true" rtexprvalue="true" %>

<%--------------------------------------------------------------
Process of main unit
--------------------------------------------------------------%>
<c:if test="${enable}">
    <c:url value="${uri}" var="uri" context="/">
        <c:if test="${not empty pageNumber}">
            <c:param name="${listSearchInfoName}.pageNumber" value="${pageNumber}" />
        </c:if>
        <c:if test="${not empty sortId}">
            <c:param name="${listSearchInfoName}.sortId" value="${sortId}" />
        </c:if>
    </c:url>
    <n:a href="${uri}" name="${name}" cssClass="item ${css}"><n:write name="label" /></n:a>
</c:if>
<c:if test="${not enable}">
    <c:if test="${label == pageNumber}">
        <a class="active item" href="javascript:void(0)"><n:write name="label" /></a>
    </c:if>
    <c:if test="${label != pageNumber}">
        <a class="item" href="javascript:void(0)"><n:write name="label" /></a>
    </c:if>
</c:if>
