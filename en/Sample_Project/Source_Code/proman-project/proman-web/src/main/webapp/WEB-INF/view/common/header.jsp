<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ page session="false" %>

<n:link rel="stylesheet" type="text/css" href="/stylesheets/common.css" />

<n:script type="text/javascript" charset="UTF-8" src="/javascripts/common.js"></n:script>

<style type="text/css">
    body {
        background-color: #FFFFFF;
        height: auto;
    }

    .ui.menu .item i.logo {
        /*　Margin of logo in header　*/
        margin-right: 0.5em;
    }

    .main.container {
        /* Margin of content section*/
        margin-top: 7em;
    }

    .ui.footer.segment {
        /* Margin of footer section*/
        margin: 1em 0em 0em;
        padding: 0em 0em;
    }
</style>
<div class="ui fixed inverted menu">
    <div class="ui container">
        <n:a id="topLink" href="/" cssClass="header item">
            <i class="logo fas fa-database"></i>Proman
        </n:a>
        <c:if test="${userContext != null}">
            <div class="right menu">
                <n:a id="logoutLink" href="/app/logout" cssClass="item">
                    Logout
                </n:a>
            </div>
        </c:if>
    </div>
</div>
