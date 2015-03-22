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
    });
})();