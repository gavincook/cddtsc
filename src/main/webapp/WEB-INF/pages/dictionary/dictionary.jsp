<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <title>${currentMenu.menuName}</title>
    <m:require src="jquery,common,noty,font,bootstrap,table,ev,dialog,{dictionary/dictionary}"></m:require>
</head>

<body>
<div class="wrapper">
    <%@ include file="../common/nav.jsp" %>
    <section class="content-section">
        <h3 class="header-title">${currentMenu.menuName}</h3>

        <div id="dictionaryTable"></div>

        <!-- 字典项表单 -->
        <form id="dictionaryForm" class="form-horizontal hide">
            <div class="form-group">
                <label class="col-sm-2 control-label">字典代码:</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" name="dictionary.code" validate="validate[required]"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">字典名称:</label>

                <div class="col-sm-10">
                    <input type="text" name="dictionary.name" class="form-control" validate="validate[required]"/>
                </div>
            </div>
        </form>

        <!-- 字典参数表单 -->
        <form id="dictionaryParamForm" class="form-horizontal hide">
            <div class="form-group">
                <label class="col-sm-3 control-label">字典参数名称:</label>

                <div class="col-sm-9">
                    <input type="text" class="form-control" name="dictionary.code" validate="validate[required]"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">字典参数值:</label>

                <div class="col-sm-9">
                    <input type="text" class="form-control" name="dictionary.name" validate="validate[required]"/>
                </div>
            </div>
        </form>
    </section>
</div>
</body>
</html>