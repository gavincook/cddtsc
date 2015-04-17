<%--
  Created by IntelliJ IDEA.
  User: table
  Date: 2015/4/15
  Time: 23:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=utf-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <%@ include file="../common/header.jsp" %>
  <m:require src="jquery,common,handlebars,bootstrap,table,dialog,noty,font,{cddtsc/comment}"></m:require>
  <title>商品评论</title>

</head>
<body class="list-bought">
<div class="content">
  <%@ include file="../common/frontHeader.jsp" %>
    <div class="container" id="commentContainer">
      <div class="shop">
        <span class="shop-title">店铺信息:</span>
        <span class="shop-name"></span>
      </div>

      <script type="text/html" id="commentTemplate">
        {{#each orderDetail}}
          <div class="comment-li"  data-id="{{id}}" >
            <div class="goods-detail">
              <img src="${pageContext.request.contextPath}/file/get/{{url}}" class="goods-img">
              <span class="goods-name">{{name}}</span>
            </div>
            {{#if content}}
              <span class="comment">{{content}}</span>
              <span class="time">{{time}}</span>
            {{else}}
              <textarea class="comment-content" rows="7"></textarea>
              <input class="submit" type="button" value="提交评论" />
            {{/if}}
          </div>
        {{/each}}
      </script>


    </div>
</div>
<%@ include file="../common/footer.jsp"%>
</body>
</html>