;
(function(){
    $(function(){
       $("#orderTable").table({
           url:contextPath+"/order/list"
       });

    });
})();