;

(function(){
    $(function(){
        var $storeForm = $("#storeForm");

        $.getJsonData(contextPath+"/listGoods").done(function(data){
            $("#goodsTemplate").renderTemplate(data.result.items,{container:".container-ul"});
        });

        //点击某个商品跳转到展示全部正在销售此商品的商品列表页面
        $(".goods-container").on("click",".add-shopcar",function(e){
            var goodsContainer = $(".goods-container");
            var data = {}, param = {};
            data.userGoodsId = goodsContainer.attr("data-goods-id");
            data.number = goodsContainer.find(".goods-buy input").val();
            if(!data.number){
                alert("请输入您要购买的数量！");
                return false;
            }
            console.log(param);
            param.type = "POST";
            param.dataType = "Json";
            $.getJsonData("/shopcart/add",data,param).done(function(){
                alert("添加物品成功！");
            });
        });

        //减少数量
        $(document).on("click",".minus",function(e){
            changeShopCartNumber(event,false);
        });

        //增加
        $(document).on("click",".plus",function(e){
            changeShopCartNumber(event,true);
        });

        $('.jqzoom').jqzoom({
            zoomType: 'standard',
            lens:true,
            preloadImages: false,
            alwaysOn:false,
            zoomWidth: 0,
            zoomHeight: 0,
            xOffset:90,
            yOffset:30,
            position:'left'
        });


    });

    function changeShopCartNumber(event,plus){
        var $numberDiv = $(event.target).closest(".number");
        var $numberBox = $numberDiv.find(".number-box");
        var $tr = $numberDiv.closest("tr");

        var number = $numberBox.val();
        if(plus) {
            number = (+number)+1;
        }else{
            number = (+number)-1;
        }
        $numberBox.val(number);
        if(number == 1){
            $numberDiv.find(".minus").addClass("disabled");
        }else{
            $numberDiv.find(".minus").removeClass("disabled");
        }
    }
})();