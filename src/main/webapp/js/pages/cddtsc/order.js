;
(function(){
    $(function(){
        //加载订单数据
        $.getJsonData(contextPath+"/order/list").done(function(data){
            $("#orderTemplate").renderTemplate(data.result.items,{container:"#orderTable",emptyParent:false});

        });

    });
})();