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

        //删除购物车
        $(document).on("click",".remove",function(e){
            var $tr = $(e.target).closest("tr");
            var id = $tr.attr("data-id");
            moon.confirm("确认从购物车中移除该商品?").done(function(result){
               if(result){
                   $.getJsonData(contextPath+"/shopcart/delete",{id:id},{type:"Post"}).done(function(data){
                      if(data.success){
                          $tr.hide("slow",function(){
                             $tr.remove();
                          });
                      }
                   });
               }
            });
        });

        //确认订单
        $(document).on("click",".confirm",function(){
            window.location.href = contextPath+"/shopcart/confirm";
        });
    });


    function changeShopCartNumber(event,plus){
        var target = event.target || window.event.srcElement;
        var $numberDiv = $(target).closest(".number");
        var $numberBox = $(".number-box",$numberDiv);
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