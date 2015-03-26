;

(function(){
    $(function(){
        var $storeForm = $("#storeForm");


        //用户登陆
        $(document).on("click",".login-container .btn-user-login",function(event){
            var $form = $(event.target).closest("form");
            var userData = $form.formData();
            $("#login-form").validate("validate").done(function(result){
                if(result){
                    console.log(result);
                    var param = {};
                    param.type = "POST";
                    param.dataType = "Json";
                    if(result === true){
                        $.getJsonData("/user/login",userData,param).done(function(){

                        });
                    }
                }
            });
        });

    });
})();