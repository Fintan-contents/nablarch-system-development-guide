<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ page session="false" %>

<div class="four wide column row">
    <div class="ui vertical menu">
        <div class="item">
            <div class="header">プロジェクト管理</div>
            <div class="menu">
                <n:a id="projectSearchkLink" href="/app/project/search" cssClass="item">プロジェクト検索</n:a>
                <n:a id="projectCreatekLink" href="/app/project/register" cssClass="item">プロジェクト登録</n:a>
            </div>
        </div>
        <div class="item">
            <div class="header">顧客管理</div>
            <div class="menu">
                <n:a id="clientSearchkLink" href="#" cssClass="item">顧客検索</n:a>
                <n:a id="clientCreatekLink" href="#" cssClass="item">顧客登録</n:a>
            </div>
        </div>
        <div class="item">
        </div>
    </div>
</div>
