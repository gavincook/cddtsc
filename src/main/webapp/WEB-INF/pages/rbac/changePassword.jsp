<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require src="jquery,noty,font,bootstrap,common,ev,{rbac/changePassword}"></m:require>
    <title>修改密码</title>
</head>
<body>
<div class="wrapper">
    <%@ include file="../common/nav.jsp" %>
    <section class="content-section">
        <h3 class="header-title">${currentMenu.menuName}</h3>
        <c:if test="${info!=null}">
            ${info }

        </c:if>
        <c:if test="${info==null}">
            <form class="form">
                <div class="form-group">
                    <span class="control-label col-sm-4">原密码：</span>
                    <div class="col-sm-8">
                        <input name="oldPassword" id="oldPassword" type="password" class="form-control"
                               validate="validate[call(checkOldPassword)]"
                               msgalign="right"/>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="form-group">
                    <span class="control-label col-sm-4">新密码：</span>
                    <div class="col-sm-8">
                        <input name="newPassword" id="newPassword" type="password" class="form-control"
                               validate="validate[required]"
                               msgalign="right"/>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="form-group">
                    <span class="control-label col-sm-4">确认密码：</span>
                    <div class="col-sm-8">
                        <input name="rePassword" type="password"
                               validate="validate[required,eq(#newPassword)]" class="form-control"
                               msgalign="right"/>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <input type="button" value="确认" id="confirm" class="btn btn-primary"/>
            </form>
        </c:if>
    </section>
</div>
</body>
</html>