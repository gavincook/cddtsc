;

(function(){
    $(function() {

        //点击某个商品跳转到展示全部正在销售此商品的商品列表页面
        $(".container-ul").on("click", ".good-img", function (e) {
            var currentGoods = $(e.currentTarget).closest('li');
            console.log(currentGoods.attr('data-goods-id'));
            goodsId = currentGoods.attr('data-goods-id');
            window.location.href = contextPath + "/" + goodsId + "_item.html";
        });

    });

})();