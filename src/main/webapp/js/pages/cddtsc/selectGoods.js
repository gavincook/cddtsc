;

(function(){
    $(function(){
        var $storeForm = $("#storeForm");
        var $selectGoodsContainer = $(".select-goods-container");

        $.getJsonData(contextPath+"/goods/listWithCover").done(function(data){
            console.log(data);
            $("#goodsTemplate").renderTemplate(data.result.items,{container:".goods-container"});
        });

        $.getJsonData(contextPath+"/goods/listWithCoverForSupplier").done(function(data){
            console.log(data);
            $("#goodsTemplate").renderTemplate(data.result.items,{container:".select-goods-container"});
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
                "margin-top":"-70px"
            });
            currentGoods.attr("status","confirming");
        });

        //添加商品
        $(".confirm-store").click(function(e){
            var $lastSelectGoods = $(".select-goods-container .goods-item");
            var $currentGoods = $(".goods-item[status='confirming']");
            $.getJsonData(contextPath+"/goods/select",{
                goodsId: $currentGoods.attr("data-id"),
                inventory:$("#inventory").val()
            },{type:"Post"}).done(function(data){
                if(data.success){
                    var rows = Math.ceil(($lastSelectGoods.length+1)/Math.floor($selectGoodsContainer.width()/132));//新数据应该在的行数
                    var cols = $lastSelectGoods.length%Math.floor($selectGoodsContainer.width()/132)+1;//新数据应该在的列数

                    var top = (rows-1)*132,left = (cols-1)*152;

                    $storeForm.animate({
                        top:top,
                        left:left
                    },function(){
                        $selectGoodsContainer.append($currentGoods.removeAttr("status"));
                        $storeForm.addClass("hide");
                    });
                }else{
                    moon.error("添加商品失败");
                }
            });

        });
    });
})();