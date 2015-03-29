;

(function(){
    $(function(){
        $("#loginForm").validate({align:'bottom',theme:"darkgrey"});

        //用户登陆
        $(document).on("click",".login-container .btn-user-login",function(event){
            login();
        });

        $(".login-form input").on("keyup",function(e){
            if(e.keyCode == 13){
                login();
            }
        });
    });

    function login(){
        $("#loginForm").validate("validate").done(function(result){
            if(result){
                $("#loginForm").ajaxSubmitForm(contextPath+"/user/login/validate").done(function(data){
                    if(data.success){
                        console.log(window.location.search.substr(6));
                        moon.success("登录成功");
                        if(window.location.search.indexOf("from=")!=-1) {
                            window.location.href = window.location.search.substr(6);
                        }else{
                            window.location.href = contextPath+"/index.html";
                        }
                    }else{
                        moon.error("用户名或密码错误");
                    }
                });
            }
        });
    }
})();