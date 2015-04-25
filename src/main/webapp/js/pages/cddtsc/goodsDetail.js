;

(function(){
    $(function(){
        var $storeForm = $("#storeForm");

        $(document).on("click",".spec",function(e){
            var $spec = $(e.target);
            window.location.href = contextPath+"/"+$spec.attr("data-url")+"_item.html";
        });
        $.getJsonData(contextPath+"/listGoods").done(function(data){
            $("#goodsTemplate").renderTemplate(data.result.items,{container:".container-ul"});
        });

        //添加购物车
        $(".goods-container").on("click",".add-shopcar",function(e){
            if(user == ""){
                if(confirm("您还未登录,请先登录.")){
                    window.location.href = contextPath+"/user/login.html?from="+window.location.href;
                }
                return false;
            }
            var goodsContainer = $(".goods-container");
            var data = {}, param = {};
            data.userGoodsId = goodsContainer.attr("data-goods-id");
            data.number = goodsContainer.find(".goods-buy input").val();
            if(data.number > inventory){
                moon.error("您所填写的商品数量超过库存");
                return;
            }
            if(!data.number){
                alert("请输入您要购买的数量！");
                return false;
            }
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
            preloadImages: false,
            alwaysOn:false,
            zoomWidth: 400,
            zoomHeight: 400,
            xOffset:20,
            yOffset:0,
            position:'left'
        });

        $(".left-opt").click(function(){
            $(".small-images").animate({
                scrollLeft:$(".small-images").scrollLeft()-100
            });
        });

        $(".right-opt").click(function(){
            $(".small-images").animate({
                scrollLeft:$(".small-images").scrollLeft()+100
            });
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
        if(number == inventory){
            $numberDiv.find(".plus").addClass("disabled");
        }else{
            $numberDiv.find(".plus").removeClass("disabled");
        }
    }
})();