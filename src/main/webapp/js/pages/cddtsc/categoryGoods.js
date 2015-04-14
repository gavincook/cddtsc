;
(function(){
    $(function(){

        //显示类别下的产品列表
        $.getJsonData(contextPath+"/goods/listGoodsForCategory",{categoryId:categoryId}).done(function(data){
            $("#goodsTemplate").renderTemplate(data.result.items,{container:".container-ul"});
        });

        //点击某个商品跳转到展示全部正在销售此商品的商品列表页面
        $(".container-ul").on("click",".good-img",function(e){
            var currentGoods = $(e.currentTarget).closest('.container-li');
            console.log(currentGoods.attr('data-goods-id'));
            goodsId = currentGoods.attr('data-goods-id');
//            window.open(contextPath+"/goods.html",'_self');
            window.location.href = contextPath+"/"+goodsId+"_goods.html";
        });
    });
})();