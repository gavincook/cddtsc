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


    });

})();