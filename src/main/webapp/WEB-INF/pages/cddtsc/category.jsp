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
    <div class="content-section">
        <h3 class="header-title">${currentMenu.menuName}</h3>

        <div>
            <div id="categoryTable" class="category-table"></div>

            <div class="splitor">
                <i class="fa fa-angle-double-right"></i>
            </div>

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

                    <div class="col-sm-10">
                        <input type="text" placeholder="商品名字" class="form-control" name="name"
                               validate="validate[required]"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label"></label>
                    <label class="col-sm-2 control-label">商品等级:</label>
                    <label class="col-sm-2 control-label">等级描述:</label>
                    <label class="col-sm-2 control-label">计量单位:</label>
                    <label class="col-sm-2 control-label">商品规格:</label>
                    <label class="col-sm-2 control-label">商品价格:</label>
                </div>
                <div class="rows-container">
                    <div class="form-group goods-row">
                        <label class="col-sm-2 control-label"></label>
                        <div class="col-sm-2">
                            <m:dicSelect code="goodsLevel" name="level" css="form-control"></m:dicSelect>
                        </div>
                        <div class="col-sm-2">
                            <input type="text" placeholder="等级描述" class="form-control" name="levelDescription"
                                   validate="validate[required]"/>
                        </div>
                        <div class="col-sm-2">
                            <m:dicSelect code="goodsUnit" name="unit" css="form-control"></m:dicSelect>
                        </div>
                        <div class="col-sm-2">
                            <input type="text" placeholder="商品规格" class="form-control" name="specification"
                                   validate="validate[required]"/>
                        </div>
                        <div class="col-sm-2">
                            <input type="text" placeholder="商品价格" class="form-control width94" name="price"
                                   validate="validate[required]"/>
                            <i class="fa fa-times-circle remove-row"></i>
                        </div>
                    </div>
                </div>
                <div class="add-level">
                    <a href="#" class="add-row">添加等级</a>
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
    </div>
    <%--富文本框--%>
    <div id="contentContainer" class="content-container hide">
        <script id="content" type="text/plain" name="description" style="height:150px;"></script>
    </div>

    <div id="goodsRow" class="hide">
        <div class="form-group goods-row">
            <label class="col-sm-2 control-label"></label>
            <div class="col-sm-2">
                <m:dicSelect code="goodsLevel" name="level" css="form-control"></m:dicSelect>
            </div>
            <div class="col-sm-2">
                <input type="text" placeholder="等级描述" class="form-control" name="levelDescription"
                       validate="validate[required]"/>
            </div>
            <div class="col-sm-2">
                <m:dicSelect code="goodsUnit" name="unit" css="form-control"></m:dicSelect>
            </div>
            <div class="col-sm-2">
                <input type="text" placeholder="商品规格" class="form-control" name="specification"
                       validate="validate[required]"/>
            </div>
            <div class="col-sm-2">
                <input type="text" placeholder="商品价格" class="form-control width94" name="price"
                       validate="validate[required]"/>
                <i class="fa fa-times-circle remove-row"></i>
            </div>

            <input type="hidden" name="id">
        </div>
    </div>
</div>
</body>
</html>