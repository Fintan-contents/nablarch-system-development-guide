<%--------------------------------------------------------------
tag that displays a list of search results.
--------------------------------------------------------------%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="lsr" tagdir="/WEB-INF/tags/listSearchResult" %>

<%--------------------------------------------------------------
attribute
--------------------------------------------------------------%>
<%@ attribute name="submitFormName" required="false" rtexprvalue="true" %>
<%-- paging--%>
<%@ attribute name="pagingPosition" required="false" rtexprvalue="true" %>
<%@ attribute name="searchUri" required="false" rtexprvalue="true" %>
<%-- paging(first) --%>
<%@ attribute name="firstSubmitLabel" required="false" rtexprvalue="true" %>
<%-- paging(previous ) --%>
<%@ attribute name="prevSubmitLabel" required="false" rtexprvalue="true" %>
<%-- paging(next) --%>
<%@ attribute name="nextSubmitLabel" required="false" rtexprvalue="true" %>
<%-- paging(last) --%>
<%@ attribute name="lastSubmitLabel" required="false" rtexprvalue="true" %>
<%-- search results --%>
<%@ attribute name="resultSetName" required="false" rtexprvalue="true" %>
<%@ attribute name="headerRowFragment" required="false" fragment="true" %>
<%@ attribute name="bodyRowFragment" required="false" fragment="true" %>
<%--------------------------------------------------------------
default
--------------------------------------------------------------%>
<%-- paging --%>
<c:if test="${empty pagingPosition}"><n:set var="pagingPosition" value="top" scope="page"/></c:if>
<c:if test="${empty submitFormName}"><n:set var="submitFormName" value="form" scope="page"/></c:if>
<c:if test="${empty resultSetName}"><n:set var="resultSetName" value="searchResult" scope="page"/></c:if>

<%--------------------------------------------------------------
body processing
--------------------------------------------------------------%>
<%-- Whole wrapper.--%>
<n:set var="resultSet" name="${resultSetName}" scope="page" bySingleValue="false"/>
<%-- Because resultSet is a class that inherits from List, it cannot be accessed in EL expression except for the index number. --%>
<%-- Therefore, we store and use a separate variable, pagination, for this purpose. --%>
<n:set var="pagination" name="${resultSetName}.pagination" scope="page"/>
<c:if test="${resultSet != null}">
    <div class="sixteen wide column">
            <%-- spacer --%>
        <div><span>&nbsp;</span></div>
    </div>
    <%-- paging --%>
    <c:if test="${(pagination.pageCount != 1) && ((pagingPosition == 'top') || (pagingPosition == 'both'))}">
        <lsr:listSearchPaging resultSetName="${resultSetName}"
                              submitFormName="${submitFormName}"
                              searchUri="${searchUri}"
                              firstSubmitLabel="${firstSubmitLabel}"
                              prevSubmitLabel="${prevSubmitLabel}"
                              nextSubmitLabel="${nextSubmitLabel}"
                              lastSubmitLabel="${lastSubmitLabel}">
        </lsr:listSearchPaging>
        <div class="sixteen wide column">
            <!-- spacer -->
            <div><span>&nbsp;</span></div>
        </div>
    </c:if>
    <%-- search results --%>
    <c:if test="${not empty pagination}">
        <n:set var="startPosition" value="${pagination.startPosition}" scope="page"/>
    </c:if>
    <c:if test="${empty pagination}">
        <n:set var="startPosition" value="1" scope="page"/>
    </c:if>
    <lsr:table resultSetName="${resultSetName}"
               startPosition="${startPosition}">
        <jsp:attribute name="headerRowFragment"><jsp:invoke fragment="headerRowFragment"/></jsp:attribute>
        <jsp:attribute name="bodyRowFragment"><jsp:invoke fragment="bodyRowFragment"/></jsp:attribute>
    </lsr:table>
    <%-- paging--%>
    <c:if test="${(pagination.pageCount != 1) && ((pagingPosition == 'bottom') || (pagingPosition == 'both'))}">
        <div class="sixteen wide column">
            <!-- spacer -->
            <div><span>&nbsp;</span></div>
        </div>
        <lsr:listSearchPaging resultSetName="${resultSetName}"
                              submitFormName="${submitFormName}"
                              searchUri="${searchUri}"
                              firstSubmitLabel="${firstSubmitLabel}"
                              prevSubmitLabel="${prevSubmitLabel}"
                              nextSubmitLabel="${nextSubmitLabel}"
                              lastSubmitLabel="${lastSubmitLabel}">
        </lsr:listSearchPaging>
    </c:if>
</c:if>
