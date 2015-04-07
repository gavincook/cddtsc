/**
 * Created by lywb on 2015/4/7.
 */
;
(function(){

    var table;
    $(function(){

        //初始化UEditor
        var um = UM.getEditor('container');
        console.log("=======");
        table = $("#articleTable").table({
            url:contextPath+"/article/list",
            columns:[{name:"title",display:"文章标题",width:130,align:"center"},
                {name:"content",
                    render:function(rowData){
                        return "<div class=\"articleContentList\">"+rowData.content+"</div>";
                        console.log(rowData);
                    },
                    display:"文章内容",width:300},
                {name:"time",display:"发表时间"},
                {display:"操作",render:function(){
                    var operation = $("<div class=\"operation\"></div>");
                    operation.append("<a href=\"javascript:void(0)\" target=\"main\" class=\"show-article\">文章预览</a>");
                    return operation;
                }}
            ],

            //定义表单标题，按钮
            formatData:function(data){return data.result;},
            rowId:"id",
            dragSelect:false,
            showSelectBox:true,
            buttons:[
                {
                    text:"增加文章",
                    click:btnHandler,
                    name:'addBtn'
                },
                {
                    text:"编辑文章",
                    click:btnHandler,
                    name:'editBtn'
                },
                {
                    text:"删除文章",
                    click:btnHandler,
                    name:'deleteBtn'
                }
            ]
        });

        //搜索事件
        $(".search").click(function(){
            var articleKeyword = $(".article-title").val();
            var domainCode = $(".domain-name").val();
            var params = {keyword:articleKeyword,domain:domainCode};
            table.refresh(params);
        });

        $(".search-form").on("keyup",":input",function(event){
            if(event.keyCode == 13){
                $(".search").trigger("click");
            }
        });

        //预览文章
        $(document).on("click",".show-article",function(e){
            var rowData = table.getRowData($(e.currentTarget).closest("tr"));
            var articleId = rowData.id;
            var $table = $(e.target);
            moon.addTab("文章预览",contextPath+"/article/"+articleId+".html",{refresh:true});
            return false;

        });

    });

    function btnHandler(btnTest){
        var editor = UM.getEditor('container');
        btnTest = btnTest.name;
        if(btnTest=='addBtn'){
            $('#articleForm').dialog({
                title:"添加文章",
                style:"right",
                size:"lg",
                afterShown:function(){
                    $('#articleForm').reset();
                    editor.setContent("");
                    var $imgList = $(".uploader-list").empty();
                    $("#articleForm").validate({align:'right',theme:"darkblue"});
                },
                beforeClose:function(){
                    $("#articleForm").validate("hide");
                },
                buttons:[
                    {
                        text : "保存",
                        css  : "btn btn-primary",
                        click:function(){
                            $("#articleForm").validate("validate").done(function(result){
                                if(result){
                                    var params = {articleType:moon.constants["article.type.user"]};
                                    $("#articleForm").ajaxSubmitForm(contextPath+"/article/add",params).done(function(data){
                                        if(data.success){
                                            $("#articleForm").dialog("close");
                                            $("#articleTable").table("refresh");
                                            $("#articleForm").reset();
                                            moon.success("操作成功");
                                        }else{
                                            moon.error("操作失败");
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
                            $("#articleForm").dialog("close");
                        }
                    }
                ]
            });
        }else if(btnTest=='editBtn'){
            var selectRows = table.getSelect();
            if(selectRows.length!=1){
                moon.warn("请选中一条数据进行编辑.");
                return false;
            }
            var id = selectRows[0].id;

            $('#articleForm').reset();
            $('#articleForm').dialog({
                title:"编辑文章",
                style:"right",
                size:"lg",
                afterShown:function(){
                    $("#articleForm").validate({align:'right',theme:"darkblue",model:"update"});
                    $("#articleForm").autoCompleteForm(contextPath+"/article/get",{id:id},function(data){
                        editor.setContent(data.content);
                        var $imgList = $(".uploader-list").empty();
                    });
                },
                beforeClose:function(){
                    $("#articleForm").validate("hide");
                },
                buttons:[
                    {
                        text : "保存",
                        css  : "btn btn-primary",
                        click:function(){
                            $("#articleForm").validate("validate").done(function(result){
                                if(result){
                                    $("#articleForm").ajaxSubmitForm(contextPath+"/article/update",{"id":id}).done(function(data){
                                        if(data.success){
                                            $("#articleForm").dialog("close");
                                            table.refresh();
                                            $("#articleForm").reset();
                                            moon.success("操作成功");
                                        }else{
                                            moon.error("操作失败");
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
                            $("#articleForm").dialog("close");
                        }
                    }
                ]
            });
        }else if(btnTest=='deleteBtn'){//删除信息
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
                $.post(contextPath+"/article/delete",ids,function(result){
                    table.refresh();
                });
            }
        }
    };
})();