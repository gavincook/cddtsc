;
(function(){
    $(function(){
        //加载订单数据
        $.getJsonData(contextPath+"/order/list").done(function(data){
            $("#orderTemplate").renderTemplate(data.result.items,{container:"#orderTable",emptyParent:false});

        });

        //订单操作
        $(document).on("click",".opt",function(e){
            var $btn = $(e.target);
            var id = $btn.closest("tbody").attr("data-id");
            var action = $btn.attr("data-action");
            $.getJsonData(contextPath+"/order/"+action,{
                id:id
            },{type:"Post"}).done(function(data){
               if(data.success){
                   moon.success("操作成功");
                   var result = data.result;
                   if(result.serverOpt){//后台操作
                       $btn.attr("data-action",result.action).html(result.orderBtnText);
                   }else{
                       $btn.parent().html(result.currentStatus);
                   }
               }
            });
        });
    });
})();