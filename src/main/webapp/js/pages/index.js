var moon = moon||{};
(function(w){
    //通知栏目模板
    var noticeTemplate = "<li><a class='no-tab' href='javascript:void(0)'><span class='msg-sender'>{0}:</span>{1}</a></li>";

    $(document).ready(function () {
        $('.collapse').collapse({
            toggle: false
        });
        moon.tabs = $(".tab").KandyTabs({
            trigger:"click",
            scroll:true
        });

        $(document).on("click",".tabbtn i",function(e){
            var $menu = $(e.target).parent();
            moon.tabs.del($menu);
            moon.deleteTab($menu.find(".title").html())
        });
        /**
         * 给iframe绑定load事件,load触发后给iframe的内容绑定点击事件,关闭bootstrap的dropdown菜单
         */
        $("iframe#main").bind("load",function(){
            $(this.contentDocument).click(function(){
                $("[id^='menu_']").removeClass('open');
            });
        });


        $(document).on("click",".dropdown-menu a:not(.no-tab)",function(e){
            var $menu = $(e.target);
            moon.addTab($menu.html(),$menu.attr("href"));
            e.preventDefault();
            window.event.returnValue = false;
        });

//
    });



})(window);
