<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require src="jquery,common,noty,font,ev,webuploader,dialog,ueditor,bootstrap,table,{cddtsc/category}"></m:require>
    <title>商品类别</title>
</head>

<body>
<div class="wrapper">
    <%@ include file="../common/nav.jsp" %>
    <section class="content-section">
        <h3 class="header-title">${currentMenu.menuName}</h3>

        <div>
            <div id="categoryTable" class="category-table"></div>
            <div id="goodsTable" class="goods-table">
                <div class="empty">
                    <span>选择类别后，这里就能显示该类别的商品了哦.</span>
                </div>
            </div>
            <form id="categoryForm" class="hide form-horizontal">
                <div class="form-group">
                    <label class="col-sm-2 control-label">类别名字:</label>

                    <div class="col-sm-10">
                        <input type="text" placeholder="类别名字" class="form-control" name="name"
                               validate="validate[required]"/>
                    </div>
                </div>
            </form>

            <form id="goodsForm" class="hide form-horizontal">
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品名字:</label>

                    <div class="col-sm-4">
                        <input type="text" placeholder="商品名字" class="form-control" name="name"
                               validate="validate[required]"/>
                    </div>
                    <label class="col-sm-2 control-label">价格:</label>

                    <div class="col-sm-4">
                        <input type="text" placeholder="商品价格" class="form-control" name="price"
                               validate="validate[required]"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">商品规格:</label>

                    <div class="col-sm-10">
                        <input type="text" placeholder="商品规格" class="form-control" name="specification"
                               validate="validate[required]"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品等级:</label>

                    <div class="col-sm-10">
                        <m:dicSelect code="goodsLevel" name="level" css="form-control"></m:dicSelect>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">计量单位:</label>

                    <div class="col-sm-10">
                        <m:dicSelect code="goodsUnit" name="unit" css="form-control"></m:dicSelect>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">商品图片:</label>
                    <input type="hidden" name="url" class="url"/>

                    <div class="col-sm-10">
                        <div id="fileContainer" class="uploader-list">
                        </div>
                        <div id="btns">
                            <div id="picker">点击选择文件</div>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">计量单位:</label>

                    <div class="col-sm-10">
                        <div class="consultation-reply-content"></div>
                    </div>
                </div>

            </form>
        </div>
    </section>
    <%--富文本框--%>
    <div id="contentContainer" class="content-container hide">
        <script id="content" type="text/plain" name="description" style="height:150px;"></script>
    </div>
</div>
</body>
</html>