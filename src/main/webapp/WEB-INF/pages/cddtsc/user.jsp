<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <script type="text/javascript">
        var userType = "${userType}", /*当前显示的用户类型*/
                typeDescription = "${typeDescription}";
    </script>
    <m:require
            src="jquery,ev,common,handlebars,bootstrap,table,dialog,noty,font,{cddtsc/user}"></m:require>
    <title>会员管理</title>

</head>
<body style="margin:0;">
<div class="wrapper">
    <%@ include file="../common/nav.jsp" %>
    <div class="content-section">
        <h3 class="header-title">${currentMenu.menuName}</h3>
        <!-- 会员列表 -->
        <div id="userTable"></div>

        <!-- Modal -->
        <script id="userFormTemplate" type="text/x-handlebars-template">
            <form id="userForm" class="form-horizontal">
                <div class="form-group">
                    <label class="col-sm-2 control-label">手机号:</label>

                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="userName" value="{{userName}}"
                               validate="validate[required]"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">密码:</label>

                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="password" value="{{password}}"
                               validate="validate[required]"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">真实姓名:</label>

                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="realName" value="{{realName}}"
                               validate="validate[required]"/>
                    </div>
                </div>
            </form>
        </script>

        <div class="pick-container hide">
            <span id="picker"></span>
        </div>
    </div>
</div>
</body>
</html>