<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript">
        var isMemberPage = "${memberPage}" == "true";/*是否是会员页面*/
    </script>
    <m:require src="jquery,common,ev,ac,handlebars,webuploader,bootstrap,table,dialog,noty,font,{cddtsc/user}"></m:require>
    <title>会员管理</title>

</head>
<body style="margin:0;">

<!-- 会员列表 -->
<div id="userTable"></div>

<!-- Modal -->
<script id="userFormTemplate" type="text/x-handlebars-template">
    <form id="userForm" class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">手机号:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="userName" value="{{userName}}" validate="validate[required]"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">真实姓名:</label>
            <div class="col-sm-10">
                <input type="text"  class="form-control"  name="realName" value="{{userName}}" validate="validate[required]"/>
            </div>
        </div>
    </form>
</script>

<div class="pick-container hide">
    <span id="picker"></span>
</div>

</body>
</html>