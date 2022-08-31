<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <n:link rel="stylesheet" type="text/css" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css"/>
        <n:link rel="stylesheet" type="text/css"
                href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css"/>
        <n:link rel="stylesheet" type="text/css"
                href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.css"/>
        <n:script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.min.js"/>
        <n:script type="text/javascript" src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"/>
        <n:script type="text/javascript"
                  src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui-calendar/0.0.8/calendar.min.js"/>
        <title>
            エラー画面 | Proman
        </title>
    </head>

    <body>
        <n:include path="/WEB-INF/view/common/noscript.jsp"/>

        <div class="mainContents">
            <n:include path="/WEB-INF/view/common/header.jsp"/>
        </div>
        <div class="ui main container">
            <div class="ui grid">
                <n:include path="/WEB-INF/view/common/menu.jsp"/>
                <div class="twelve wide column">
                    <h1 class="page-title">エラー画面</h1>
                    <div class="message-area">
                        アップロードファイルのサイズがシステム上限値を超過しました。
                    </div>
                </div>
            </div>
        </div>

        <n:include path="/WEB-INF/view/common/footer.jsp"/>

    </body>
</html>
