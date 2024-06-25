<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ page session="false" %>

<div class="four wide column row">
    <div class="ui vertical menu">
        <div class="item">
            <div class="header">Project management</div>
            <div class="menu">
                <n:a id="projectSearchkLink" href="/app/project/search" cssClass="item">Project search</n:a>
                <n:a id="projectCreatekLink" href="/app/project/register" cssClass="item">Project registration</n:a>
            </div>
        </div>
        <div class="item">
            <div class="header">Client management</div>
            <div class="menu">
                <n:a id="clientSearchkLink" href="#" cssClass="item">Client search</n:a>
                <n:a id="clientCreatekLink" href="#" cssClass="item">Register client</n:a>
            </div>
        </div>
        <div class="item">
        </div>
    </div>
</div>
