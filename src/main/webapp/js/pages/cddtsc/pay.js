;
(function(){
    $(function(){
        //支付按钮事件
        $(".pay-btn").click(function(){
           if($("[name='password']").val().length<6){//密码小于6位
               moon.error("请输入正确的支付密码");
               return false;
           }
           $("#payForm").ajaxSubmitForm(contextPath+"/order/pay").done(function(data){
               console.log(data);
           })
       });
    });
})();