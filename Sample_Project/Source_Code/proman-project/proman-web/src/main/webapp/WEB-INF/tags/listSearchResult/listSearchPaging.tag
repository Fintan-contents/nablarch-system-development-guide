<%--------------------------------------------------------------
検索結果のリスト表示のページングを出力するタグ。
--------------------------------------------------------------%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="lsr" tagdir="/WEB-INF/tags/listSearchResult" %>

<%--------------------------------------------------------------
属性
--------------------------------------------------------------%>
<%@ attribute name="resultSetName" required="true" rtexprvalue="true" %>
<%@ attribute name="submitFormName" required="true" rtexprvalue="true" %>
<%@ attribute name="searchUri" required="true" rtexprvalue="true" %>
<%-- 最初 --%>
<%@ attribute name="firstSubmitLabel" required="false" rtexprvalue="true" %>
<%-- 前へ --%>
<%@ attribute name="prevSubmitLabel" required="false" rtexprvalue="true" %>
<%-- 次へ --%>
<%@ attribute name="nextSubmitLabel" required="false" rtexprvalue="true" %>
<%-- 最後 --%>
<%@ attribute name="lastSubmitLabel" required="false" rtexprvalue="true" %>

<%--------------------------------------------------------------
デフォルト
--------------------------------------------------------------%>
<%-- 最初 --%>
<c:if test="${empty firstSubmitLabel}"><n:set var="firstSubmitLabel" value="<<" scope="page"/></c:if>
<%-- 前へ --%>
<c:if test="${empty prevSubmitLabel}"><n:set var="prevSubmitLabel" value="＜" scope="page"/></c:if>
<%-- 次へ --%>
<c:if test="${empty nextSubmitLabel}"><n:set var="nextSubmitLabel" value="＞" scope="page"/></c:if>
<%-- 最後 --%>
<c:if test="${empty lastSubmitLabel}"><n:set var="lastSubmitLabel" value=">>" scope="page"/></c:if>

<%--------------------------------------------------------------
本体処理
--------------------------------------------------------------%>
<n:set var="resultSet" name="${resultSetName}" scope="page" bySingleValue="false"/>
<%-- resultSetはListを継承したクラスであるため、EL式ではindex番号以外でのアクセスができない。 --%>
<%-- そのため、paginationを一旦別変数に保存して使用する。 --%>
<n:set var="pagination" name="${resultSetName}.pagination" scope="page"/>
<div class="sixteen wide column">
    <div class="ui right floated pagination menu">
        <%-- 最初 --%>
        <lsr:listSearchSubmit
                label="${firstSubmitLabel}"
                enable="${pagination.hasPrevPage}"
                uri="${searchUri}"
                pageNumber="${pagination.firstPageNumber}"
                submitFormName="${submitFormName}"/>
        <%-- 前へ --%>
        <lsr:listSearchSubmit
                label="${prevSubmitLabel}"
                enable="${pagination.hasPrevPage}"
                uri="${searchUri}"
                pageNumber="${pagination.prevPageNumber}"
                submitFormName="${submitFormName}"/>
        <%--  ページ番号(1 2 3 ...n) --%>
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
        <%-- 次へ --%>
        <lsr:listSearchSubmit
                label="${nextSubmitLabel}"
                enable="${pagination.hasNextPage}"
                uri="${searchUri}"
                pageNumber="${pagination.nextPageNumber}"
                submitFormName="${submitFormName}"/>
        <%-- 最後 --%>
        <lsr:listSearchSubmit
                label="${lastSubmitLabel} "
                enable="${pagination.hasNextPage}"
                uri="${searchUri}"
                pageNumber="${pagination.lastPageNumber}"
                submitFormName="${submitFormName}"/>
    </div>
</div>
