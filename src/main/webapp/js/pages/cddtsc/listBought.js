;
(function(){
    $(function(){
        //加载订单数据
        $.getJsonData(contextPath+"/order/list_bought_items").done(function(data){
            $("#listBoughtTemplate").renderTemplate(data.result.items,{container:"#listBoughtTable",emptyParent:false});

        });

        //删除订单
        $(document).on("click",".remove",function(e){
            if(confirm("是否要删除该订单?")){
                var $tbody = d = $(e.target).closest("tbody");
                var id = $tbody.attr("data-id");
                $.getJsonData(contextPath+"/order/delete",{id:id},{type:"Post"}).done(function(data){
                    if(data.success){
                        $tbody.hide("slow",function(){
                            $tbody.remove();
                        });
                    }
                });
            }
        });

        //订单操作
        $(document).on("click",".opt",function(e){
            var $opt = $(e.target);
            var action = $opt.attr("data-action");
            var orderId = $opt.closest("tbody").attr("data-id");

            switch(action){
                case "pay"         : window.location.href = contextPath+"/order/pay.html?orderId="+orderId; break;
                case "confirmOrder" :
                    if(confirm("是否确认收货?")){
                        $.getJsonData(contextPath+"/order/confirmOrder",{id:orderId},{type:"Post"}).done(function(data){
                            if(data.success){
                                moon.success("操作成功");
                                $opt[0].outerHTML = data.result.currentStatus;
                            }else{
                                moon.error("操作失败");
                            }
                        });
                    }
                    break;
                default :
            }
        });
    });
})();