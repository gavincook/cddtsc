$(function() {
	$("form").validate({
		theme : "darkblue",
		errorMsg : {
			'required' : '该项为必填项,请填写...<br/>'
		}
	});
	$("#confirm").click(function() {
		$("form").validate("validate");
		$("form").validate("validate").done(function(result){
			if(result){
				$.getJsonData(contextPath + "/user/~/doChangePassword", {
					password : $("#newPassword").val()
				},{type:"Post"}).done(function(data) {
					if(data.success) {
						moon.success("密码修改成功.");
						$("form").reset();
					}else{
						moon.error("密码修改失败.");
						$("form").reset();
					}
				});
			}
		});

	});
});

function checkOldPassword() {
	var dfd = $.Deferred();
	$.getJsonData(contextPath + "/user/~/matchOldPassword", {
		password : $("#oldPassword").val()
	},{type:"Get"}).done(function(data) {
		if(!data.success){
			dfd.resolve("原密码不正确，请核对.<br/>");
		}else{
			dfd.resolve("");
		}
	});
	return dfd.promise();
}