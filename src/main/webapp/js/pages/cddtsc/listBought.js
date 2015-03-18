;
(function(){
    $(function(){
        //加载购物车数据
        $.getJsonData(contextPath+"/order/list_bought_items").done(function(data){
            $("#listBoughtTemplate").renderTemplate(data.result.items,{container:"#listBoughtTable",emptyParent:false});

        });

    });
})();