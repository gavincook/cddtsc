;
(function(){
    $(function(){
        //默认选择第一个收货地址
        $(".address-item:eq(0)").addClass("selected");

        //选择收货地址
        $(document).on("click",".address-item",function(e){
            $(".address-item.selected").removeClass("selected");
            $(e.currentTarget).addClass("selected");
        });


        $.getJsonData(contextPath+"/shopcart/list").done(function(data){
            $("#shopcartTemplate").renderTemplate(data.result.items,{container:".shopcart-items"});
            var totalNum = 0;
            $.each(data.result.items,function(i,e){
                totalNum += (+e.number)*(+e.price);
            });
            $("#totalNum").html(totalNum.toFixed(2));
        });
    });
})();