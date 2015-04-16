<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require
            src="jquery,common,ev,ac,handlebars,webuploader,bootstrap,table,dialog,noty,font,{cddtsc/my}"></m:require>
    <title>个人信息</title>

</head>
<body class="my">
<div class="wrapper">
    <%@ include file="../common/nav.jsp" %>
    <div class="content-section">
        <h3 class="header-title">${currentMenu.menuName}</h3>
        <%--个人信息--%>
        <div class="my-info-container">
            <div class="my-avatar">
                <div class="image-container">
                    <img id="avatar" src="/file/get/${user.avatar}" alt="个人头像" onerror="javascript:this.src='${pageContext.request.contextPath}/css/images/avatar.png'">
                </div>
                <i class="fa fa-camera update-avatar"></i>
            </div>
            <div class="my-info">
                <div class="my-name">
                    ${user.realName}
                </div>
                <div class="balance">
                    <span class="balance-label">余额</span>
                    <span class="balance-num"><i class="fa fa-rmb"></i> 999.5</span>
                    <%--<button type="button" class="btn btn-mini btn-danger charge-btn">充值</button>--%>
                </div>
            </div>
            <div class="clearfix"></div>
        </div>

        <div class="shop-container">
            <div class="title">
                <i class="fa fa-home"></i>
                <span>我的店铺</span>
            </div>
            <div class="shop-form">
                <span>名称：</span>
                <input type="text" value="${shop.name}" id="shopName">
                <button type="button" class="btn btn-primary save-shop">保存</button>
            </div>
        </div>
        <%--地址容器--%>
        <div class="address-container">
            <div class="title">
                <div class="address-title"><i class="fa fa-th-list"></i><span>收货地址</span></div>
                <div class="add-address"><i class="fa fa-plus-circle"></i>新增收货地址</div>
                <div class="clearfix"></div>
            </div>
            <div class="address-table">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>收货人</th>
                        <th>详细地址</th>
                        <th>电话/手机</th>
                        <th>操作</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody class="address-list">
                    <tr>
                        <td colspan="5" class="loading-status">
                            <i class="fa fa-spin fa-spinner"></i>加载中...
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>


        <%--头像上传对话框--%>
        <div class="avatar-edit-dialog hide">
            <div class="image-container">
                <img id="avatar-eidt" src="/file/get/${user.avatar}" alt="头像">
            </div>
            <span id="upload"></span>
        </div>

        <%--地址模板--%>
        <script type="text/html" id="addressTemplate">
            {{#each this}}
            <tr data-id="{{id}}">
                <td>{{consignee}}</td>
                <td>{{address}}</td>
                <td>{{phoneNumber}}</td>
                <td><a href="#" class="edit">修改</a>
                    <a href="#" class="remove">删除</a>
                    {{#if isDefault}}{{else}}<a href="#" class="set-default">设为默认</a>{{/if}}
                </td>
                <td class="address-status">{{#if isDefault}}默认{{/if}}</td>
            </tr>
            {{/each}}
        </script>

        <%--地址表单模板--%>
        <script type="text/x-handlebars-template" id="addressFormTemplate">
            <form class="form-horizontal address-form">
                <div class="form-group">
                    <span class="control-label col-md-2">收货人</span>

                    <div class="col-md-4">
                        <input type="text" name="consignee" class="form-control" value="{{consignee}}">
                    </div>
                    <span class="control-label col-md-2">电话</span>

                    <div class="col-md-4">
                        <input type="text" name="phoneNumber" class="form-control" value="{{phoneNumber}}">
                    </div>
                </div>
                <div class="form-group">
                    <span class="control-label col-md-2">详细地址</span>

                    <div class="col-md-10">
                        <input type="text" name="address" class="form-control" value="{{address}}">
                    </div>
                </div>
                <div class="form-group">
                    <span class="control-label margin-top-md">设为默认收货地址</span>
                    <input type="checkbox" name="isDefault" {{#if isDefault}}checked{{/if}}>
                </div>
            </form>
        </script>
    </div>
</div>
</body>
</html>