;
(function(){

    window.checkPhoneNumber = function checkPhoneNumber(field,type,opts) {
        console.log(opts.model);
        if (opts.model == "update") {
            return "";
        } else {
            var validate = RegExp(/^(0|86|17951)?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$/).test(field.val());
            if (!validate) {
                alert("美国");
                return "请输入有效的手机号!";
            }
            else
                return "";
        }
    };

    window.checkIDNumber = function checkPhoneNumber(field,type,opts) {
        console.log(opts.model);
        if (opts.model == "update") {
            return "";
        } else {
            var validate = RegExp(/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i).test(field.val());
            if (!validate)
                return "请输入有效的身份证号码!";
            else
                return "";
        }
    };

    window.isUserNameExists = function isUserNameExists(field,type,opts) {
        console.log(opts.model);
        if (opts.model == "update") {
            return "";
        } else {
            var dfd = $.Deferred();
            $.getJsonData(contextPath + "/user/userName/check", {userName: field.val()}, {type: "Post"}).done(function (result) {
                if (result.result) {
                    dfd.resolve("用户名已经存在.<br/>");
                } else {
                    dfd.resolve("");
                }
            });
            return dfd.promise();
        }
    };

    function check($form){
        var $form = $form;
        $form.validate("validate");
        var userData = $form.formData();
        console.log(userData);
        return true;
    };

    //激活用户
    $(document).on("click",".user-regist .btn-save-user",function(event){
        var $form = $(event.target).closest("form");
        var userData = $form.formData();
        $("#regist-form").validate("validate").done(function(result){
            if(result){
                console.log(result);
                var param = {};
                param.type = "POST";
                param.dataType = "Json";
                if(result === true){
                    $.getJsonData("/user/register",userData,param).done(function(){

                    });
                }
            }
        });

    });
})();