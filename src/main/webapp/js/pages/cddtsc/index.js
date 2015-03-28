;

(function(){
    $(function(){
        var $storeForm = $("#storeForm");

        //显示主页的产品列表
        $.getJsonData(contextPath+"/goods/listGoodsOnSell").done(function(data){
            $("#goodsTemplate").renderTemplate(data.result.items,{container:".container-ul"});
        });

        //点击某个商品跳转到展示全部正在销售此商品的商品列表页面
        $(".container-ul").on("click",".good-img",function(e){
            var currentGoods = $(e.currentTarget).closest('li');
            console.log(currentGoods.attr('data-goods-id'));
            goodsId = currentGoods.attr('data-goods-id');
//            window.open(contextPath+"/goods.html",'_self');
            window.location.href = contextPath+"/"+goodsId+"_goods.html/";
        });

        $(".container-ul").on("click",".search-goods",function(){
            doSearchGoods();
        });

        //输入库存
        $(".goods-container").on("click",".goods-item",function(e){
            var currentGoods = $(e.currentTarget);

            var currentGoodsClone = currentGoods.clone(true);
            $storeForm.css({
                top:currentGoods.offset().top,
                left:currentGoods.offset().left
            });
            $storeForm.removeClass("hide").find(".goods-item-container").empty().append(currentGoodsClone);

            $storeForm.animate({
                left:"50%",
                top:"50%",
                "margin-left":"-70px",
                "margin-top":"-192px"
            });
            currentGoods.attr("status","confirming");
        });

        /**
         * （右侧）搜索商品
         */
        $(document).on("click",".search-goods",function(){
            doSearchGoods();
        });

        //回车搜索
        $("#goodsName").keyup(function(e){
           if(e.keyCode == 13){
               doSearchGoods();
           }
        });

        $(".load-goods").click(function(){
            var that = this;
            if($(that).hasClass("no-more")){
                return;
            }
            var inputValue = $("#goodsName").val();
            var currentPage = $(that).attr("data-page");
            var nextPage = parseInt(currentPage)+1;
            var data = {pageIndex:nextPage};
            if(inputValue){
                data.keyword = inputValue;
            }
            $.getJsonData(contextPath+"/goods/listWithCover",data).done(function(data){
                $(that).attr("data-page",nextPage);
                if(data.result.items.length < data.result.pageSize){
                    $(that).addClass("no-more").html("没有更多了");
                }
                $("#goodsTemplate").renderTemplate(data.result.items,{container:".goods-items"});
            });
        });
    });

    function doSearchGoods(){
        var inputValue = $("#goodsName").val();
        var data = {};
        if(inputValue){
            data = {keyword:inputValue};
        }
        $.getJsonData(contextPath+"/goods/listWithCover",data).done(function(data){
            $("#goodsTemplate").renderTemplate(data.result.items,{container:".goods-items",emptyParent:true});
        });
    }


})();