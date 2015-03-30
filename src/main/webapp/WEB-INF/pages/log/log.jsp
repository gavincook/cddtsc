<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp"%>
<m:require
	src="jquery,zt,js/ztree.extend.js,common,bootstrap,table,font,dialog,js/pages/log/log.js"></m:require>
<title>${currentMenu.menuName}</title>
</head>
<body>
<div class="wrapper">
    <%@ include file="../common/nav.jsp" %>
    <section class="content-section">
        <h3 class="header-title">${currentMenu.menuName}</h3>
	<div id="logTable"></div>
	<div id="logDetail" style="display: none;">
		<div id="content"></div>
	</div>
        </section>
    </div>
</body>
</html>