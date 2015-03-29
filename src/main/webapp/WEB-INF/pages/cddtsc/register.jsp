<%@ taglib prefix="m" uri="/moon" %>
<%@ page language="java" contentType="text/html;charset=utf-8" import="java.util.*"
         pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require src="jquery,bootstrap,common,ev,{cddtsc/register}"></m:require>
    <m:require src="css/pages/cddtsc/common.css" type="css"></m:require>
    <title>注册用户</title>
</head>

<body>
    <div class="content">
        <%@ include file="../common/frontHeader.jsp"%>
        <div class="user-regist">
            <div class="tittle">
                <h2>***欢迎加入成都通道蔬菜***</h2>
            </div>
            <div class="user-regist-body">
               <form id="regist-form" action="">
                   <div class="control-group">
                       <div class="content left">注册类型</div>
                       <div class="right">
                           <m:dicSelect code="userType" css="form-control" defaultVal="0" name="type"></m:dicSelect>
                       </div>
                   </div>
                   <div class="control-group">
                       <div class="content left">会员名称</div>
                       <div class="right">
                           <input type="text"  name="userName" validate="validate[required,number,call(checkPhoneNumber),call(isUserNameExists)]" placeholder="请输入您的手机号" errMsg="请输入有效的电话号码!"/>
                       </div>
                       <div class="errer-messae"></div>
                   </div>
                   <div class="control-group">
                       <div class="content left">密   码</div>
                       <div class="right">
                           <input type="password" class="password" name="password" validate="validate[required,minsize(6)]" placeholder="请输入不少于6个字符" errMsg="请输入不少于6个字符的密码!"/>
                       </div>
                       <div class="errer-messae"></div>
                   </div>
                   <div class="control-group">
                       <div class="content left">重复密码</div>
                       <div class="right">
                           <input type="password"  name="repass" validate="validate[required,minsize(6),eq(.password)]" placeholder="请再次输入密码!" errMsg="两次输入的密码不一致!"/>
                       </div>
                       <div class="errer-messae"></div>
                   </div>
                   <div class="control-group">
                       <div class="content left">性别</div>
                       <div class="right">
                           <m:dicSelect code="sex" css="form-control" defaultVal="0" name="sex"></m:dicSelect>
                       </div>
                   </div>
                   <div class="control-group hide">
                       <div class="content left">手机号</div>
                       <div class="right">
                           <input type="text"  name="phonenum"  placeholder="请输入您的手机号" />
                       </div>
                       <div class="errer-messae"></div>
                   </div>
                   <div class="control-group">
                       <div class="content left">会员姓名</div>
                       <div class="right">
                           <input type="text"  name="realName"  validate="validate[required,minsize(2)]"  placeholder="请输入您的真实姓名" errMsg="请输入您的真实姓名!"/>
                       </div>
                       <div class="errer-messae"></div>
                   </div>
                   <div class="control-group">
                       <div class="content left">身份证号</div>
                       <div class="right">
                           <input type="text"  name="IDNumber" validate="validate[required,number,minsize(18),call(checkIDNumber)]" placeholder="请输入您的身份证号码" errMsg="请输入您的身份证号码!"/>
                       </div>
                       <div class="errer-messae"></div>
                   </div>
                   <div class="control-group">
                       <div class="content left">收货地址</div>
                       <div class="right">
                           <span class="address">四川省.成都市.</span><input class="address" name="address" type="text" placeholder="请输入具体地址" />
                       </div>
                   </div>
                   <div class="user-regist-bottom">
                       <div class="agree">
                           <input type="checkbox" name="agree" checked="">
                           <span>我已阅读并同意<a href="#">《注册协议》</a></span>
                       </div>
                       <button class="btn btn-primary btn-save-user" type="button">立即注册</button>
                   </div>
               </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jsp"%>
</body>
<script type="text/javascript" defer="defer">
    (function($) {
        $("#regist-form").validate({align: 'right', theme: 'darkblue', model:"create"});
    })(jQuery);
</script>
</html>

