<%--------------------------------------------------------------
検索結果のリスト表示を行うタグ。
--------------------------------------------------------------%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lsr" tagdir="/WEB-INF/tags/listSearchResult" %>

<%--------------------------------------------------------------
属性
--------------------------------------------------------------%>
<%@ attribute name="submitFormName" required="false" rtexprvalue="true" %>
<%-- ページング --%>
<%@ attribute name="pagingPosition" required="false" rtexprvalue="true" %>
<%@ attribute name="searchUri" required="false" rtexprvalue="true" %>
<%-- ページング(最初) --%>
<%@ attribute name="firstSubmitLabel" required="false" rtexprvalue="true" %>
<%-- ページング(前へ) --%>
<%@ attribute name="prevSubmitLabel" required="false" rtexprvalue="true" %>
<%-- ページング(次へ) --%>
<%@ attribute name="nextSubmitLabel" required="false" rtexprvalue="true" %>
<%-- ページング(最後) --%>
<%@ attribute name="lastSubmitLabel" required="false" rtexprvalue="true" %>
<%-- 検索結果 --%>
<%@ attribute name="resultSetName" required="false" rtexprvalue="true" %>
<%@ attribute name="headerRowFragment" required="false" fragment="true" %>
<%@ attribute name="bodyRowFragment" required="false" fragment="true" %>
<%--------------------------------------------------------------
デフォルト
--------------------------------------------------------------%>
<%-- ページング --%>
<c:if test="${empty pagingPosition}"><n:set var="pagingPosition" value="top" scope="page"/></c:if>
<c:if test="${empty submitFormName}"><n:set var="submitFormName" value="form" scope="page"/></c:if>
<c:if test="${empty resultSetName}"><n:set var="resultSetName" value="searchResult" scope="page"/></c:if>

<%--------------------------------------------------------------
本体処理
--------------------------------------------------------------%>
<%-- 全体ラッパ --%>
<n:set var="resultSet" name="${resultSetName}" scope="page" bySingleValue="false"/>
<%-- resultSetはListを継承したクラスであるため、EL式ではindex番号以外でのアクセスができない。 --%>
<%-- そのため、paginationを一旦別変数に保存して使用する。 --%>
<n:set var="pagination" name="${resultSetName}.pagination" scope="page"/>
<c:if test="${resultSet != null}">
    <div class="sixteen wide column">
            <%-- spacer --%>
        <div><span>&nbsp;</span></div>
    </div>
    <%-- ページング --%>
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
    <%-- 検索結果 --%>
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
    <%-- ページング --%>
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
