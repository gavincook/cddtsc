;

(function(){
    $(function(){
        var $storeForm = $("#storeForm");
        var $selectGoodsContainer = $(".select-goods-container");

        $.getJsonData(contextPath+"/goods/listWithCover").done(function(data){
            $("#goodsTemplate").renderTemplate(data.result.items,{container:".goods-container .goods-items"});
        });

        $.getJsonData(contextPath+"/goods/listWithCoverForSupplier").done(function(data){
            $("#goodsTemplate").renderTemplate(data.result.items,{container:".select-goods-container .goods-items"});
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

        //关闭输入库存对话框
        $(".cancel").click(function(){
           $storeForm.addClass("hide");
        });

        //关闭输入库存对话框
        $("#storeForm .modal-backdrop").click(function(){
            $storeForm.addClass("hide");
        });

        //添加商品
        $(".confirm-store").click(function(e){
            var $lastSelectGoods = $(".select-goods-container .goods-item");
            var $currentGoods = $(".goods-item[status='confirming']");
            var inventoryNum = $("#inventory").val();
            $.getJsonData(contextPath+"/goods/select",{
                goodsId: $currentGoods.attr("data-id"),
                inventory:inventoryNum
            },{type:"Post"}).done(function(data){
                if(data.success){
                    var rows = Math.ceil(($lastSelectGoods.length+1)/Math.floor($selectGoodsContainer.width()/132));//新数据应该在的行数
                    var cols = $lastSelectGoods.length%Math.floor($selectGoodsContainer.width()/132)+1;//新数据应该在的列数

                    var top = (rows-1)*244,left = (cols-1)*132;

                    $storeForm.animate({
                        top:top,
                        left:left
                    },function(){
                        $currentGoods.removeAttr("status");
                        $currentGoods.attr("data-id",data.result.id);
                        $currentGoods.find(".inventory-num").html(inventoryNum);
                        $selectGoodsContainer.append($currentGoods);
                        $storeForm.addClass("hide");
                    });
                }else{
                    moon.error("添加商品失败");
                }
            });

        });

        //删除已选择商品
        $(".select-goods-container").on("click",".delete",function(e){
            var $goodsItem = $(e.target).closest(".goods-item");
            $.getJsonData(contextPath+"/goods/select/delete",{selectGoodsId:$goodsItem.attr("data-id")},
                {type:"Post"}).done(function(data){
                    if(data.success){
                        moon.success("商品删除成功");
                        $goodsItem.hide(1000,function(){
                            $goodsItem.remove();
                        });
                    }
                });
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


        $(document).on("click",".search-select-goods",function(){
            doSearchSelectedGoods();
        });

        //回车搜索
        $("#selectGoodsName").keyup(function(e){
            if(e.keyCode == 13){
                doSearchSelectedGoods();
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
                $("#goodsTemplate").renderTemplate(data.result.items,{container:".goods-container .goods-items"});
            });
        });

        $(".load-select-goods").click(function(){
            var that = this;
            if($(that).hasClass("no-more")){
                return;
            }
            var inputValue = $("#selectGoodsName").val();
            var currentPage = $(that).attr("data-page");
            var nextPage = parseInt(currentPage)+1;
            var data = {pageIndex:nextPage};
            if(inputValue){
                data.keyword = inputValue;
            }
            $.getJsonData(contextPath+"/goods/listWithCoverForSupplier",data).done(function(data){
                $(that).attr("data-page",nextPage);
                if(data.result.items.length < data.result.pageSize){
                    $(that).addClass("no-more").html("没有更多了");
                }
                $("#goodsTemplate").renderTemplate(data.result.items,{container:".select-goods-container .goods-items"});
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
            $("#goodsTemplate").renderTemplate(data.result.items,{container:".goods-container .goods-items",emptyParent:true});
        });
    }

    function doSearchSelectedGoods(){
        var inputValue = $("#selectGoodsName").val();
        var data = {};
        if(inputValue){
            data = {keyword:inputValue};
        }
        $.getJsonData(contextPath+"/goods/listWithCoverForSupplier",data).done(function(data){
            $("#goodsTemplate").renderTemplate(data.result.items,{container:".select-goods-container .goods-items",emptyParent:true});
        });
    }

})();