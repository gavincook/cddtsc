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

        </div>

        <script type="text/html" id="goodsTemplate">
            {{#each this}}
            <div class="goods-item" data-id="{{id}}">
                <div class="cover-container">
                    <img src="${pageContext.request.contextPath}/file/get/{{cover}}">
                </div>
                <div class="description">
                    <span>{{name}}</span>
                </div>
            </div>
            {{/each}}
        </script>

        <div class="form-horizontal hide" id="storeForm">
            <div class="modal-backdrop in"></div>
            <div class="store-form">
                <h3 class="title">请完善库存</h3>
                <div class="goods-item-container"></div>
                <div class="store-div">
                    <input type="text" placeholder = "库存数量" class="form-control" id="inventory">
                </div>
                <div>
                    <button type="button" class="btn btn-warning col-md-5 confirm-store">确认</button>
                    <span class="col-md-2"></span>
                    <button type="button" class="btn btn-default col-md-5">取消</button>
                    <span class="clearfix"></span>
                </div>
            </div>
        </div>

    </body>
</html>