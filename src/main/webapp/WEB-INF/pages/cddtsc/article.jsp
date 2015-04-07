<%--
  Created by IntelliJ IDEA.
  User: lywb
  Date: 2015/4/7
  Time: 0:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <%@ include file="../common/header.jsp" %>
  <m:require src="jquery,common,noty,font,ev,webuploader,dialog,ueditor,bootstrap,table,{cddtsc/article}"></m:require>
  <title>${currentMenu.menuName}</title>
  <script type="text/javascript">
    var all = "${all}";
  </script>
</head>

<body>
<div class="wrapper">
  <%@ include file="../common/nav.jsp" %>
  <section class="content-section">
    <h3 class="header-title">${currentMenu.menuName}</h3>
    <%--搜索表单--%>
    <div class="search-form form-horizontal">
      <div class="form-group">
        <label class="col-sm-2 control-label">关键字：</label>

        <div class="col-sm-2">
          <input type="text" name="articleTitle" class="form-control article-title" placeholder="请输入文章标题...">
        </div>
        <label class="col-sm-1 control-label">文章类型：</label>

        <div class="col-sm-2">
          <m:dicSelect code="articleType" css="form-control domain-name" defaultVal="1"
                       name="articleType"></m:dicSelect>
        </div>

        <div class="col-sm-1">
          <button class="btn btn-primary search">搜索</button>
        </div>
      </div>
    </div>

    <%--article form--%>
    <div id="articleTable"></div>
    <form id="articleForm" class="hide form-horizontal">
      <div class="form-group">
        <label class="col-sm-2 control-label">文章标题:</label>

        <div class="col-sm-10">
          <input type="text" placeholder="文章标题" class="form-control" name="title"
                 validate="validate[required]"/>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">文章类型:</label>

        <div class="col-sm-4">
          <m:dicSelect code="articleType" css="form-control" defaultVal="1" name="articleType"></m:dicSelect>
        </div>

      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">内容:</label>

        <div class="col-sm-10">
          <script id="container" style="height:100px;" name="content" type="text/plain"
                  placeholder="文章内容"></script>
        </div>
      </div>

    </form>
  </section>
</div>
</body>
</html>
