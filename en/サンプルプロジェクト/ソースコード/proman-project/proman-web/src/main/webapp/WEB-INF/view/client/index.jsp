<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ page session="false" %>

<input type="hidden" value="${pageContext.request.contextPath}" id="context-path" />
<div id="client-search-dialog" class="modal fade in">
  <div class="modal-dialog">
    <div class="modal-content client">
      <div class="modal-body">
        <div class="navbar navbar-inverse">
          <div class="container-fluid">
            <div class="navbar-header">
              <span class="navbar-brand">Client search list screen</span>
            </div>
          </div>
        </div>
        <div class="alert alert-dismissible" style="display: none" id="message-area">
          <button type="button" class="close" data-dismiss="alert">×</button>
          <div>
            
          </div>
        </div>
        <div class="form-group">
          <label for="search-client-name" class="col-md-2 control-label">Client Name</label>
          <div class="col-md-10">
            <input type="text" class="form-control" id="search-client-name" name="form.clientName">
          </div>
        </div>
        <div class="form-group">
          <label for="search-industry-code" class="col-md-2 control-label">Industry</label>
          <div class="col-md-10">
            <select id="search-industry-code" class="form-control" name="form.industryCode">
            </select>
          </div>
        </div>
        <div>
          <a href="javascript:void(0)" class="btn btn-raised btn-primary pull-right" id="client-search">Search</a>
        </div>
        <div style="overflow-y: scroll; height: 250px;" class="col-md-12">
          <table class="table table-striped table-hover" id="search-result">
            <thead>
            <tr>
              <th>Client ID</th>
              <th>Client name</th>
              <th>Industry</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>
