<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require
            src="jquery,font,common,zt,js/ztree.extend.js,bootstrap,table,dialog,js/pages/rbac/permission.js"></m:require>
    <title>${currentMenu.menuName}</title>
</head>
<body style="margin:0">
<div class="wrapper">
    <%@ include file="../common/nav.jsp" %>
    <div class="content-section">
        <h3 class="header-title">${currentMenu.menuName}</h3>
        <!-- 权限列表 -->
        <div id="permissionTable"></div>

        <!-- 角色分配 -->
        <div id="roleTree" class="ztree"></div>
    </div>
</div>
</body>
</html>