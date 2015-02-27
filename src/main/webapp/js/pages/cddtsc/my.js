;
(function(){
    var uploader;

    //新头像地址
    var newAvatarPath;

    $(function(){

        $.getJsonData(contextPath+"/user/addresses").done(function(data){
            $("#addressTemplate").renderTemplate(data.result,{emptyParent:true,container:".address-list"});
        });
        //编辑头像
        $(".update-avatar").click(function(e){
            $(".avatar-edit-dialog").dialog({
               title:"编辑头像",
                afterShown:function(){
                    initUploaderIfNecessary();
                },
                buttons:[
                    {
                        text:"保存",
                        css:"btn btn-primary",
                        click:function(){
                            var dialog = this;
                            if(newAvatarPath){
                                $.getJsonData(contextPath+"/user/avatar/update",{
                                    avatar:newAvatarPath
                                },{
                                    type:"Post"
                                }).done(function(data){
                                    $("#avatar").attr("src",moon.filePrefix+newAvatarPath);
                                    moon.success("头像更改成功");
                                    dialog.close();
                                })
                            }
                        }
                    },
                    {
                        text:"取消",
                        css:"btn btn-default",
                        click:function(){
                            this.close();
                        }
                    }
                ]
            });
        });

        //添加收货地址
        $(document).on("click",".add-address",function(){
            $("#addressFormTemplate").dialog({
               title:"添加收货地址",
                data:{},
                style:"right",
                buttons:[
                    {
                        text:"保存",
                        css:"btn btn-warning",
                        click:function(){
                            var dialog = this;
                            console.log(dialog.$e.find("form"));
                            dialog.$e.find("form").ajaxSubmitForm(contextPath+"/user/address/add").done(function(data){
                                $(".address-list").append($("#addressTemplate").renderTemplate([data.result]));
                                moon.success("添加收货地址成功");
                                dialog.close();
                            });
                        }
                    },{
                        text:"取消",
                        css:"btn btn-default",
                        click:function(){

                        }
                    }
                ]
            });
        });

        //删除地址
        $(document).on("click",".remove",function(e){
            moon.confirm("是否要删除该收货地址?").done(function(result){
                if(result){
                    var $tr = $(e.target).closest("tr");
                    var id = $tr.attr("data-id");
                    $.getJsonData(contextPath+"/user/address/delete",{id:id},{type:"Post"}).done(function(){
                        moon.success("收货地址删除成功");
                        $tr.hide(1000,function(){
                            $(this).remove();
                        });
                    });
                }
            });
        });

        //设置默认地址
        $(document).on("click",".set-default",function(e){
            var $tr = $(e.target).closest("tr");
            var id = $tr.attr("data-id");
            $.getJsonData(contextPath+"/user/address/default/set",{id:id},{type:"Post"}).done(function(){
                moon.success("默认地址设置成功");
                $(".address-status").not($tr).empty();
                $tr.find(".address-status").html("默认");
            });
        });

        //更新地址
        $(document).on("click",".edit",function(e){
            var $tr = $(e.target).closest("tr");
            var id = $tr.attr("data-id");
            $.getJsonData(contextPath+"/user/address/get",{id:id}).done(function(data){
                $("#addressFormTemplate").dialog({
                    title:"添加收货地址",
                    data:data.result,
                    style:"right",
                    buttons:[
                        {
                            text:"保存",
                            css:"btn btn-warning",
                            click:function(){
                                var dialog = this;
                                dialog.$e.find("form").ajaxSubmitForm(contextPath+"/user/address/update",{id:id}).done(function(data){
                                    $tr.replaceWith($("#addressTemplate").renderTemplate([data.result]));
                                    moon.success("更新收货地址成功");
                                    dialog.close();
                                });
                            }
                        },{
                            text:"取消",
                            css:"btn btn-default",
                            click:function(){
                                this.close();
                            }
                        }
                    ]
                });
            });
        });
    });

    /**
     * 当Uploader没有初始化时,进行初始化.
     * 如果初始化了,则进行重置
     */
    function initUploaderIfNecessary(){
        if(!uploader) {
            uploader = moon.webuploader({
                fileQueued: function (f) {
                    this.makeThumb(f, function (error, src) {
                        if (error) {
                            console.log('不能预览');
                            return;
                        }
                    }, 200, 200);
                    this.upload(f);
                },
                pick: {
                    id:"#upload",
                    innerHTML:"选择头像"
                },
                accept:{
                    title: 'Images',
                    extensions:"png,jpg,jpeg,gif,bmp",
                    mimeTypes: 'image/*'
                },
                uploadAccept: function (o, ret) {
                    if (ret.failure.length == 1) {//上传失败
                        moon.error("头像上传失败,请稍后再试.")
                    }else{
                        var successFile = ret.success[0];
                        if(successFile){
                            newAvatarPath = successFile.filePath;
                            $("#avatar-eidt").attr("src",moon.filePrefix+successFile.filePath);
                        }
                    }
                }
            });
        }else{
            $.each(uploader.getFiles("complete"),function(index,f){
                uploader.removeFile(f);
            });
        }
    }
})();
