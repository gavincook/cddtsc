<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require src="jquery,bootstrap,table,font"/>
    <title>Table</title>
    <script type="text/javascript">
        var topY, bottomY;
        $(function () {
            $("#userTable").table({url: contextPath + "/permission/getPermissionData",
                columns: [
                    {name: "id"},
                    {name: "name"},
                    {name: "code"}
                ],
                formatData: function (data) {
                    return {items: data.result.items, totalItemsCount: data.result.totalItemsCount};
                },
                title: "测试表格",
                showSelectBox: true
            });
        });
    </script>
</head>
<body>
<div class="wrapper">
    <%@ include file="../common/nav.jsp" %>
    <section class="content-section">
        <h3 class="header-title">${currentMenu.menuName}</h3>
        <div id="userTable"></div>
    </section>
</div>
</body>
</html>