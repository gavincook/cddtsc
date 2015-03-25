;

(function(){
    $(function(){
        var $storeForm = $("#storeForm");

        $.getJsonData(contextPath+"/listGoods").done(function(data){
            $("#goodsTemplate").renderTemplate(data.result.items,{container:".container-ul"});
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