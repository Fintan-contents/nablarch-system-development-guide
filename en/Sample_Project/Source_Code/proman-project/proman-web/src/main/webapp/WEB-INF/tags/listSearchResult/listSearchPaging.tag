<%--------------------------------------------------------------
Tags for outputting list display pagination for search results.
--------------------------------------------------------------%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lsr" tagdir="/WEB-INF/tags/listSearchResult" %>

<%--------------------------------------------------------------
attribute
--------------------------------------------------------------%>
<%@ attribute name="resultSetName" required="true" rtexprvalue="true" %>
<%@ attribute name="submitFormName" required="true" rtexprvalue="true" %>
<%@ attribute name="searchUri" required="true" rtexprvalue="true" %>
<%-- first --%>
<%@ attribute name="firstSubmitLabel" required="false" rtexprvalue="true" %>
<%-- previous --%>
<%@ attribute name="prevSubmitLabel" required="false" rtexprvalue="true" %>
<%-- next --%>
<%@ attribute name="nextSubmitLabel" required="false" rtexprvalue="true" %>
<%-- last --%>
<%@ attribute name="lastSubmitLabel" required="false" rtexprvalue="true" %>

<%--------------------------------------------------------------
デフォルト
--------------------------------------------------------------%>
<%-- first --%>
<c:if test="${empty firstSubmitLabel}"><n:set var="firstSubmitLabel" value="<<" scope="page"/></c:if>
<%-- previous --%>
<c:if test="${empty prevSubmitLabel}"><n:set var="prevSubmitLabel" value="＜" scope="page"/></c:if>
<%-- next --%>
<c:if test="${empty nextSubmitLabel}"><n:set var="nextSubmitLabel" value="＞" scope="page"/></c:if>
<%-- last --%>
<c:if test="${empty lastSubmitLabel}"><n:set var="lastSubmitLabel" value=">>" scope="page"/></c:if>

<%--------------------------------------------------------------
body processing
--------------------------------------------------------------%>
<n:set var="resultSet" name="${resultSetName}" scope="page" bySingleValue="false"/>
<%-- Because resultSet is a class that inherits from List, it cannot be accessed in EL expression except for the index number. --%>
<%-- Therefore, we store and use a separate variable, pagination, for this purpose. --%>
<n:set var="pagination" name="${resultSetName}.pagination" scope="page"/>
<div class="sixteen wide column">
    <div class="ui right floated pagination menu">
        <%-- first --%>
        <lsr:listSearchSubmit
                label="${firstSubmitLabel}"
                enable="${pagination.hasPrevPage}"
                uri="${searchUri}"
                pageNumber="${pagination.firstPageNumber}"
                submitFormName="${submitFormName}"/>
        <%-- previous --%>
        <lsr:listSearchSubmit
                label="${prevSubmitLabel}"
                enable="${pagination.hasPrevPage}"
                uri="${searchUri}"
                pageNumber="${pagination.prevPageNumber}"
                submitFormName="${submitFormName}"/>
        <%--  page number(1 2 3 ...n) --%>
        <c:if test="${(pagination.pageCount != 1)}">
            <c:forEach begin="1" end="${pagination.pageCount}" varStatus="status">
                <n:set var="pageNumber" value="${status.index}" scope="page"/>
                <lsr:listSearchSubmit
                        label="${pageNumber}"
                        enable="${pagination.pageNumber != pageNumber}"
                        uri="${searchUri}"
                        pageNumber="${pageNumber}"
                        submitFormName="${submitFormName}"/>
            </c:forEach>
        </c:if>
        <%-- next--%>
        <lsr:listSearchSubmit
                label="${nextSubmitLabel}"
                enable="${pagination.hasNextPage}"
                uri="${searchUri}"
                pageNumber="${pagination.nextPageNumber}"
                submitFormName="${submitFormName}"/>
        <%-- last --%>
        <lsr:listSearchSubmit
                label="${lastSubmitLabel} "
                enable="${pagination.hasNextPage}"
                uri="${searchUri}"
                pageNumber="${pagination.lastPageNumber}"
                submitFormName="${submitFormName}"/>
    </div>
</div>
