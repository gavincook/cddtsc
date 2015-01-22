;
(function(){

    var table;
    $(function(){
        table = $("#userTable").table({
            url:contextPath+(isMemberPage?"/user/member/list":"/user/list"),
            columns:[{name:"userName",display:"手机号"},
                     {name:"realName",display:"真实姓名"},
                     {display:"操作",render:function(rowData){
                         var opts = "<div class='opts'>";
                         if(rowData.active === false){
                             opts += "<a href='#' class='active-user'><i class='fa  fa-check-square-o'></i>激活 </a>";
                         }
                         opts += "</div>";
                         return opts;
                     }}
            ],
            formatData:function(data){return data.result;},
            title:isMemberPage?"会员列表" : "用户管理",
            rowId:"id"//,
/*            buttons:[
                {
                    text:"增加会员管理",
                    click:btnHandler,
                    name:'addBtn'
                },
                {
                    text:"编辑会员管理",
                    click:btnHandler,
                    name:'editBtn'
                },
                {
                    text:"删除会员管理",
                    click:btnHandler,
                    name:'deleteBtn'
                }
            ]*/
        });

        //激活用户
        $(document).on("click",".active-user",function(e){
            var $tr = $(e.target).closest("tr");
            var rowData = table.getRowData($tr);
            $.getJsonData(contextPath+"/user/active",{userId:rowData.id},{type:"Post"}).done(function(d){
                if(d.success){
                    moon.success("用户激活成功");
                    $tr.find(".active-user").remove();
                }else{
                    moon.error("用户激活失败");
                }
            });
        });
    });

    function btnHandler(btnTest){
        btnTest = btnTest.name;
        if(btnTest=='addBtn'){//添加会员管理
            $('#userForm').reset();
            $('#userFormTemplate').dialog({
                title:"添加会员",
                afterShown:function(){
                    $("#userForm").validate({align:'top',theme:"darkblue"});
                },
                beforeClose:function(){
                    $("#userForm").validate("hide");
                },
                style:"right",
                data:{},
                buttons:[
                    {
                        text : "保存",
                        css  : "btn btn-warning btn-block",
                        click:function(){
                            var dialog = this;
                            var $form = $("form",dialog.$e);
                            $("#userForm").validate("validate").done(function(result){
                                if(result){
                                    $form.ajaxSubmitForm(contextPath+"/user/add").done(function(data){
                                        if(data.success){
                                            dialog.close();
                                            table.refresh();
                                            moon.success("会员添加成功");
                                        }else{
                                            moon.error("会员添加失败");
                                        }
                                    });
                                }
                            });
                        }
                    }
                ]
            });
        }else if(btnTest=='editBtn'){//编辑会员管理
            var selectRows = table.getSelect();
            if(selectRows.length!=1){
                moon.warn("请选中一条数据进行编辑.");
                return false;
            }
            var id = selectRows[0].id;

            $('#userForm').dialog({
                title:"编辑会员",
                afterShown:function(){
                    $("#userForm").validate({align:'right',theme:"darkblue",model:"update"});
                    $("#userForm").autoCompleteForm(contextPath+"/company/get",{id:id});
                },
                beforeClose:function(){
                    $("#userForm").validate("hide");
                },
                buttons:[
                    {
                        text : "保存",
                        css  : "btn btn-primary",
                        click:function(){
                            $("#userForm").validate("validate").done(function(result){
                                if(result){
                                    $("#userForm").ajaxSubmitForm(contextPath+"/user/update",{"id":id}).done(function(data){
                                        if(data.success){
                                            $("#userForm").dialog("close");
                                            table.refresh();
                                            $("#userForm").reset();
                                            moon.success("会员修改成功");
                                        }else{
                                            moon.error("会员修改成功");
                                        }
                                    });
                                }
                            });
                        }
                    },
                    {
                        text  : "取消",
                        css   : "btn",
                        click : function(){
                            $("#userForm").dialog("close");
                        }
                    }
                ]
            });
        }else if(btnTest=='deleteBtn'){//删除会员管理
            var selectRows =  table.getSelect();
            if(selectRows.length<1){
                moon.warn("请选择要删除的数据");
                return false;
            }
            if(confirm("确认删除这"+selectRows.length+"条数据?")){
                var ids="";
                $.each(selectRows,function(index,e){
                    ids+="&ids="+e.id;
                });
                ids = ids.substring(1);
                $.post(contextPath+"/user/delete",ids,function(result){
                    table.refresh();
                });
            }
        }
    };


})();