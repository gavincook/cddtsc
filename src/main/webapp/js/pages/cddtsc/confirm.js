;
(function(){
    $(function(){
        //默认选择第一个收货地址
        $(".address-item:eq(0)").addClass("selected");

        //选择收货地址
        $(document).on("click",".address-item",function(e){
            $(".address-item.selected").removeClass("selected");
            $("#addressId").val($(e.currentTarget).addClass("selected").attr("data-id"));
        });

        //读取购物车的数据
        $.getJsonData(contextPath+"/shopcart/list").done(function(data){
            $("#shopcartTemplate").renderTemplate(data.result.items,{container:".shopcart-items"});
            var totalNum = 0;
            $.each(data.result.items,function(i,e){
                totalNum += (+e.number)*(+e.price);
            });
            $("#totalNum").html(totalNum.toFixed(2));
        });

        ////提交订单
        //$(document).on("click",".submit-order",function(){
        //    var addressId = $(".address-item.selected").attr("data-id");
        //    if(typeof addressId == "undefined"){
        //        return false;
        //    }
        //    $.getJsonData(contextPath+"/order/add",{addressId:addressId},{type:"Post"}).done(function(data){
        //        console.log(data);
        //    });
        //});
    });
})();