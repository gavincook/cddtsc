<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../common/header.jsp" %>
    <m:require
            src="jquery,common,noty,font,ev,webuploader,dialog,bootstrap,ueditor,table,{cddtsc/article},{cddtsc/articleView}"></m:require>
    <title>文章阅览</title>
</head>
<body style="width: 1000px;margin: 0 auto;">
<div class="wrapper">
    <div id=""></div>
    <div class="articleHeader">
        <h1>${article.title}</h1>

        <p>
            <span>${article.time}</span>
            <span>作者:${article.realName}</span>
            <span>类型:${article.articleTypeName}</span>
        </p>
    </div>

    <div class="articleBody">
        <div class="articleContent">
            <span>${article.content}</span>
        </div>
    </div>

    <div class="articleFooter">
        <p>

        <div class="articleShare">
            <div class="bdsharebuttonbox">
                <a href="#" class="bds_more" data-cmd="more"></a>
                <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
                <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
                <a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a>
                <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
            </div>
        </div>
        </p>
    </div>
</div>
</body>
</html>
