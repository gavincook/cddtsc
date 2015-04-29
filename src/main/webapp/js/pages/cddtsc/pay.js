;
(function(){
    $(function(){
        //支付按钮事件
        $(".pay-btn").click(function(){
            console.log(",,,,");
             if($("[name='password']").val().length<6){//密码小于6位
                 moon.error("请输入正确的支付密码");
                return false;
            }
            var $btn = $(".pay-btn");
            $btn.addClass("disabled").html("付款确认中...");
            $("#payForm").ajaxSubmitForm(contextPath+"/order/pay").done(function(data){
                if(data.success){
                    window.location.href = contextPath+"/order/list_bought_items.html";
                }else{
                    if(data.result) {
                        moon.error(data.result.errMsg || data.result.replace("org.moon.exception.ApplicationRunTimeException:",""));
                    }else{
                        moon.error(data.throwable.localizedMessage);
                    }
                    $btn.removeClass("disabled").html("付款");
                }
            });
       });
    });
})();