<%@ page contentType="text/html;charset=utf-8" language="java" %>
<div class="page-navigation">
  <div class="navigationbar">
    <div class="header-container">
      <span class="floatl">品质食材，只要成都道通蔬菜！<span class="boldhead">免费配送</span>。</span>
            <span class="floatr">
                <a href="${pageContext.request.contextPath}/user/regist.html"><span class="boldhead">注册</span></a>
                <a href="${pageContext.request.contextPath}/user/login.html"><span class="boldhead">登录</span></a>
                <a href="${pageContext.request.contextPath}/shopcart">
                  <span class="boldhead">
                    <i class="fa fa-shopping-cart"></i>购物车
                  </span>
                </a>
                <a href="#" id="addToDesktop">添加到桌面</a>
                <a href="javascript:void(0)" onclick="SetHome(this,window.location)" style="color:red;font-weight:bold;">
                  设为首页</a>|<a href="javascript:void(0)" onclick="AddFavorite(window.location,document.title)" style="color:red;font-weight:bold;">
              收藏本站</a>
                <a href="${pageContext.request.contextPath}/index.html">帮助中心</a>
                <a href="${pageContext.request.contextPath}/index.html">关于我们</a>
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
        });
    </script>