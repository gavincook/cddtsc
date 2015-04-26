<%--
  Created by IntelliJ IDEA.
  User: table
  Date: 2015/4/19
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="m" uri="/moon" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
  <%@ include file="../common/header.jsp" %>
  <m:require src="jquery,handlebars,common,noty,font,ev,webuploader,dialog,bootstrap,{cddtsc/articleList}"></m:require>
  <title>成都通道蔬菜</title>
</head>
<body>
<%@ include file="../common/frontHeader.jsp" %>
<div class="content">

  <div class="article-type-list">
    <div class="article-type">文章分类</div>
    <ul>
      <a href="${pageContext.request.contextPath}/article/news/list.html"><li class="news">新闻中心</li></a>
      <a href="${pageContext.request.contextPath}/article/joinUs/list.html"><li class="joinUs">招聘信息</li></a>
      <a href="${pageContext.request.contextPath}/article/article/list.html"><li class="article">帮助中心</li></a>
    </ul>
  </div>

  <div class="listContainer">
    <div class="type-name">帮助中心</div>
    <ul>
      <c:forEach items="${articleList}" var="article" varStatus="status">
        <a class="comment-content" href="${pageContext.request.contextPath}/article/${article.id}.html">
          <li class="article  <c:if test="${status.index == articleList.size()-1}">last</c:if>">
            <div class="title"><span>${article.title}</span></div>
            <div class="time"><span>${article.time}</span></div>
          </li>
        </a>
      </c:forEach>
    </ul>
  </div>
</div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>