<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require src="jquery,noty,common,bootstrap,font,table,{maintenance/DBMaintenance}"></m:require>
    <title>${currentMenu.menuName}</title>
    <script type="text/javascript">
        $(function () {
            $(".init-db").click(function () {
                moon.confirm("确认要初始化数据库?").done(function (confirm) {
                    if (confirm) {
                        $(".loading").removeClass("hide");
                        $.getJsonData(contextPath + "/dbMaintenance/initDb", {}, {type: "Post"}).done(function (data) {
                            if (data) {
                                moon.info("数据库初始化完成");
                            } else {
                                moon.info("数据库初始化失败，详情请查看系统日志");
                            }
                            $(".loading").addClass("hide");
                        });
                    }
                });
            });
        });
    </script>
    <style type="text/css">
    </style>
</head>
<body>
<div class="wrapper">
    <%@ include file="../common/nav.jsp" %>
    <section class="content-section">
        <h3 class="header-title">${currentMenu.menuName}</h3>

        <div class="DBMaintence">
            <div class="db-info">
                <div class="item">
                    数据库类型：
                    <span class="db-type">${dbInfo.name}</span>
                </div>
                <div class="item">
                    版本：
                    <span class="db-version">${dbInfo.version}</span>
                </div>
                <div class="item db-init">
                    <input type="button" class="btn btn-danger init-db" value="初始化数据库" title="注意:此操作会清空已有数据">
                    <i class="loading hide"></i>
                </div>
            </div>
            <div class="table-content">
                <div class="table-name-list">
                    <span class="table-name-title">数据表</span>
                    <ul class="nav nav-tabs nav-stacked">
                        <li class="loading"><i class="fa fa-spinner fa-spin"></i></li>
                    </ul>
                </div>
                <div class="columns">

                </div>
            </div>


        </div>
    </section>
</div>
</body>
</html>