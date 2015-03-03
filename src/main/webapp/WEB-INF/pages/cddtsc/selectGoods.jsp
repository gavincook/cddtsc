<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
      <%@ include file="../common/header.jsp" %>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <m:require src="jquery,handlebars,common,noty,font,ev,webuploader,dialog,bootstrap,{cddtsc/selectGoods}"></m:require>
        <title>选择商品</title>
    </head>

    <body>
        <div class="select-goods-container col-md-6">

        </div>

        <div class="goods-container  col-md-6">
            <div class="search-form">
                <div class="col-md-1"></div>
                <div class="col-md-8">
                    <input type="text" class="form-control" placeholder="搜索商品" id="goodsName">
                </div>
                <div class="col-md-3">
                    <button class="btn btn-warning btn-block search-goods" type="button">搜索</button>
                </div>
            </div>
            <div class="goods-items">

            </div>
            <div class="load-more load-goods" data-page="1">点击加载更多</div>
        </div>

        <script type="text/html" id="goodsTemplate">
            {{#each this}}
            <div class="goods-item" data-id="{{id}}">
                <i class="fa fa-times-circle delete"></i>
                <div class="cover-container">
                    <img src="${pageContext.request.contextPath}/file/get/{{cover}}">
                </div>
                <div class="description">
                    <div class="goods-name">{{name}}</div>
                    <div class="price">
                        <span>价格：</span>
                        <i class="fa fa-rmb"></i> {{price}}
                    </div>
                    <div class="level">
                        <span>等级：</span>
                        {{level}}
                    </div>
                    <div class="unit">
                        <span>单位：</span>
                        {{unit}}
                    </div>
                    <div class="inventory">
                        <span>库存：</span>
                        <span class="inventory-num">{{inventory}}</span>
                    </div>
                </div>
            </div>
            {{/each}}
        </script>

        <div class="form-horizontal hide store-dialog" id="storeForm">
            <div class="modal-backdrop in"></div>
            <div class="store-form">
                <h3 class="title">请完善库存</h3>
                <div class="goods-item-container"></div>
                <div class="store-div">
                    <input type="text" placeholder = "库存数量" class="form-control" id="inventory">
                </div>
                <div class="btn-container">
                    <button type="button" class="btn btn-warning col-md-5 confirm-store">确认</button>
                    <span class="col-md-2"></span>
                    <button type="button" class="btn btn-default col-md-5 cancel">取消</button>
                    <span class="clearfix"></span>
                </div>
            </div>
        </div>

    </body>
</html>