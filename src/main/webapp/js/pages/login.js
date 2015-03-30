$(function(){
	//登录页面欢迎信息
//	moon.info("欢迎使用携心医疗BS版!","top");
    $(".mobile i").popover({trigger:"hover",placement:"top",html:true});

	//登录动作
	$("#submit").click(function(){
		$("#loginForm").validate("validate").done(function(result){
			if(result){
				$("#loginForm").ajaxSubmitForm(contextPath+"/user/login/validate").done(function(data){
					if(data.success){
						if(from){
							$.href(from);
						}else{
							$.href(contextPath+"/index");
						}
					}else{
						moon.error("用户名或密码错误","top");
					}
				}).fail(function(){
					moon.error("服务出错，请稍后再试","top");
				});
			}
		});
	});

    //回车登录事件
    $("#loginForm :input").on("keyup",function(e){
        if(e.keyCode == 13){
            $("#submit").trigger("click");
        }
    });
	//页面动画
//	$(".form-container").animate({
//		"margin-top":"-120px"
//	},2000,"linear",function(){
//		$(".system-info-container").animate({
//			"margin-left":0
//		});
//	});

	//添加表单验证
	$("#loginForm").validate({align:'bottom',theme:"darkgrey"});
});