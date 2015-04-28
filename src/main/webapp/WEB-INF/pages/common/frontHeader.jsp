<%@ page contentType="text/html;charset=utf-8" language="java" %>
<div class="page-navigation">
  <div class="navigationbar">
    <div class="header-container">
      <span class="floatl">品质食材，只要成都道通蔬菜！<span class="boldhead">免费配送</span>。</span>
            <span class="floatr">
                <c:if test="${sessionScope.CURRENT_USER_ID != null }">
                    <a href="${pageContext.request.contextPath}/my_daotong.html"> ${sessionScope.userName}</a>
                </c:if>
                <c:if test="${sessionScope.CURRENT_USER_ID == null }">
                    <a href="${pageContext.request.contextPath}/user/regist.html"><span class="boldhead">注册</span></a>
                    <a href="${pageContext.request.contextPath}/user/login.html"><span class="boldhead">登录</span></a>
                </c:if>
                <a href="${pageContext.request.contextPath}/shopcart">
                  <span class="shop-cart">
                    <i class="fa fa-shopping-cart"></i>购物车
                  </span>
                </a>

                <a href="${pageContext.request.contextPath}/order/list_bought_items.html">
                  <span class="">
                    我的订单
                  </span>
                </a>

                <a href="${pageContext.request.contextPath}/article/article/list.html">帮助中心</a>
                <a href="${pageContext.request.contextPath}/index.html">关于我们</a>

                <c:if test="${sessionScope.CURRENT_USER_ID != null }">
                    <a href="${pageContext.request.contextPath}/user/signout"> <i class="fa fa-sign-out"></i> 退出</a>
                </c:if>
            </span>
    </div>
  </div>
  <div class="header clearfix container">
    <div class="thead">

        <h1 class="logo">
            <a href="/index.html"><img src="${pageContext.request.contextPath}/css/images/cddtsc/logo.png" width="260" height="38" title="道通蔬菜"></a>
        </h1>

      <div class="telephone">
                <span class="topimg">
                  <img src="${pageContext.request.contextPath}/css/images/cddtsc/tel.png" width="280" height="55">
                </span>
      </div>
        <form class="search-container form-inline" action="${pageContext.request.contextPath}/search.html" >
                <input type="text" class="search-box form-control" name="keyword" value="${keyword}" style="display:inline-block;vertical-align: middle;">
                <button class="search btn btn-info" type="submit" style="display:inline-block;vertical-align: middle;">搜&nbsp;索</button>
                <span style="height: 100%;display: inline-block;width: 0px;vertical-align: middle;"></span>
        </form>
    </div>
  </div>

    <div class="menu-nav">
        <div class="container">
            <c:forEach items="${categories}" var="category">
                <a class="menu-item" href="${pageContext.request.contextPath}/category_${category.id}.html">${category.name}</a>
            </c:forEach>
        </div>
    </div>
</div>

    <script type="text/javascript">
        $(function(){
           $("#addToDesktop").click(function(){
               try{
                   var WshShell = new ActiveXObject("WScript.Shell");
                   var oUrlLink = WshShell.CreateShortcut(WshShell.SpecialFolders("Desktop") + "\\道通.url");
                   oUrlLink.TargetPath = "http://www.baidu.com";
                   oUrlLink.Save();
               }catch(e){
                   alert("当前IE安全级别不允许操作！");
               }
           }) ;
            $(document).on("click",".page-prev",function(){
                turn(pageIndex-1);
            });

            $(document).on("click",".page-next",function(){
                turn(pageIndex+1);

            });

            function turn(page){
                if(window.location.href.indexOf("pageIndex=")!=-1){
                    window.location.href= window.location.href.replace(/pageIndex=(\d+)/,'pageIndex='+page);
                }else{
                    window.location.href= (window.location.href+"&pageIndex="+page).replace(/[&?]{1,2}/,"?");
                }
            }
        });
    </script>