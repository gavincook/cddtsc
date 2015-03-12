;
(function(){
    $(function(){
        //加载购物车数据
        $.getJsonData(contextPath+"/shopcart/list").done(function(data){
            $("#shopcartTemplate").renderTemplate(data.result.items,{container:".shopcart-items"});
            var totalNum = 0;
            $.each(data.result.items,function(i,e){
                totalNum += (+e.number)*(+e.price);
            });
            $("#totalNum").html(totalNum.toFixed(2));
        });

        //购物车减少数量
        $(document).on("click",".minus",function(e){
            changeShopCartNumber(event,false);
        });

        //购物车增加
        $(document).on("click",".plus",function(e){
            changeShopCartNumber(event,true);
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
        $tr.find(".subtotal").html(((+$tr.attr("data-price"))*number).toFixed(2));
        reCalcTotalNum();
        $.getJsonData(contextPath+"/shopcart/update",{number:number,id:$tr.attr("data-id")},{type:"Post"}).done(function(){

        });
    }

    //重新计算总价
    function reCalcTotalNum(){
        var totalNum = 0;
        $(".subtotal").each(function(i,e){
            totalNum += (+e.innerHTML);
        });
        $("#totalNum").html(totalNum.toFixed(2));
    }
})();