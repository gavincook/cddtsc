;
(function(){

    var categoryTable;
    var goodsTable;
    var currentCategoryId;//当前类别Id,用于商品表格的操作
    var uploader;
    var template =  '<div class="imgContainer" data-id="{0}"><div class="content">'
        +'<i class="fa fa-times"></i><img src="{1}">'
        +'<div class="progress">'
        +'<div class="progress-bar progress-bar-info"></div>'
        +'</div></div>'
        +'</div>' ;
    $(function(){
        categoryTable = $("#categoryTable").table({
            url:contextPath+"/category/list",
            columns:[{name:"name",display:"类别名字",width:"300px"}
            ],
            formatData:function(data){return data.result;},
            title:"商品类别列表",
            rowId:"id",
            treeColumn:"name",
            multiSelect:false,
            childrenData:function(row,rowData){
                var $dfd = $.Deferred();
                $.getJsonData(contextPath+"/category/list",{parentId:rowData.id}).done(function(data){
                    if(data && data.result){
                        $dfd.resolve(data.result.items);
                    }
                });
                return $dfd;
            },
            buttons:[
                {
                    text:"增加商品类别",
                    click:categoryBtnHandler,
                    name:'addBtn'
                },
                {
                    text:"增加字类别",
                    click:categoryBtnHandler,
                    name:'addChildBtn'
                },
                {
                    text:"编辑商品类别",
                    click:categoryBtnHandler,
                    name:'editBtn'
                },
                {
                    text:"删除商品类别",
                    click:categoryBtnHandler,
                    name:'deleteBtn'
                }
            ]
        });

        $(document).on("click","#fileContainer i",function(e){
            var $div = $(e.target).closest(".imgContainer");
            uploader.removeFile($div.attr("data-id"));
            $div.remove();
        });

        $(document).on("click","#categoryTable tbody tr",function(e){
            var rowData = categoryTable.getRowData(e.currentTarget);
            currentCategoryId = rowData.id;
            if(goodsTable) {
                goodsTable.refresh({categoryId: currentCategoryId});
            }else{
                goodsTable = $("#goodsTable").table({
                    url:contextPath+"/goods/list",
                    columns:[{name:"name",display:"商品",width:"300px",render:function(columnData){
                        return "<img class=\"img\" src="+contextPath+"/file/get/"+columnData.url+"/>"+columnData.name;
                    }},
                        {name:"specification",display:"规格",width:"300px"}
                    ],
                    params:{categoryId: currentCategoryId},
                    formatData:function(data){return data.result;},
                    title:"商品列表",
                    rowId:"id",
                    treeTable:false,
                    multiSelect:false,
                    buttons:[
                        {
                            text:"增加商品",
                            click:goodsBtnHandler,
                            name:'addBtn'
                        },
                        {
                            text:"编辑商品",
                            click:goodsBtnHandler,
                            name:'editBtn'
                        },
                        {
                            text:"删除商品",
                            click:goodsBtnHandler,
                            name:'deleteBtn'
                        }
                    ]
                });
            }
        });


    });

    function categoryBtnHandler(btnTest){
        btnTest = btnTest.name;
        if(btnTest=='addBtn'){//添加商品类别
            doAdd(null);
        }else if(btnTest=="addChildBtn"){
            var selectRows = categoryTable.getSelect();
            if(selectRows.length!=1){
                moon.warn("请选择要操作的商品类别.");
                return false;
            }
            doAdd(selectRows[0].id);
        }else if(btnTest=='editBtn'){//编辑商品类别
            var selectRows = categoryTable.getSelect();
            if(selectRows.length!=1){
                moon.warn("请选中一条数据进行编辑.");
                return false;
            }
            var id = selectRows[0].id;

            $('#categoryForm').dialog({
                title:"编辑商品类别",
                afterShown:function(){
                    $("#categoryForm").validate({align:'right',theme:"darkblue",model:"update"});
                    $("#categoryForm").autoCompleteForm(contextPath+"/category/get",{id:id});
                },
                beforeClose:function(){
                    $("#categoryForm").validate("hide");
                },
                buttons:[
                    {
                        text : "保存",
                        css  : "btn btn-primary",
                        click:function(){
                            $("#categoryForm").validate("validate").done(function(result){
                                if(result){
                                    $("#categoryForm").ajaxSubmitForm(contextPath+"/category/update",{"id":id}).done(function(data){
                                        if(data.success){
                                            $("#categoryForm").dialog("close");
                                            categoryTable.refresh();
                                            $("#categoryForm").reset();
                                            moon.success("商品类别修改成功");
                                        }else{
                                            moon.error("商品类别修改成功");
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
                            $("#categoryForm").dialog("close");
                        }
                    }
                ]
            });
        }else if(btnTest=='deleteBtn'){//删除商品类别
            var selectRows =  categoryTable.getSelect();
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
                $.post(contextPath+"/category/delete",ids,function(result){
                    categoryTable.refresh();
                });
            }
        }
    };

    function doAdd(parentId){
        var params = parentId?{parentId:parentId}:{};
        $('#categoryForm').reset();
        $('#categoryForm').dialog({
            title:"添加商品类别",
            afterShown:function(){
                $("#categoryForm").validate({align:'right',theme:"darkblue"});
            },
            beforeClose:function(){
                $("#categoryForm").validate("hide");
            },
            buttons:[
                {
                    text : "保存",
                    css  : "btn btn-primary",
                    click:function(){
                        $("#categoryForm").validate("validate").done(function(result){
                            if(result){
                                $("#categoryForm").ajaxSubmitForm(contextPath+"/category/add",params).done(function(data){
                                    if(data.success){
                                        $("#categoryForm").dialog("close");
                                        $("#categoryTable").table("refresh");
                                        $("#categoryForm").reset();
                                        moon.success("商品类别添加成功");
                                    }else{
                                        moon.error("商品类别添加失败");
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
                        $("#categoryForm").dialog("close");
                    }
                }
            ]
        });
    }

    function goodsBtnHandler(btnTest){
        btnTest = btnTest.name;
        if(btnTest=='addBtn'){//添加优惠信息
            $('#goodsForm').reset();
            $(".uploader-list").empty();
            $('#goodsForm').dialog({
                title:"添加商品",
                afterShown:function(){
                    $("#goodsForm").validate({align:'right',theme:"darkblue"});
                    initUploaderIfNecessary();
                },
                beforeClose:function(){
                    $("#goodsForm").validate("hide");
                },
                buttons:[
                    {
                        text : "保存",
                        css  : "btn btn-primary",
                        click:function(){
                            $("#goodsForm").validate("validate").done(function(result){
                                if(result){
                                    $("#goodsForm").ajaxSubmitForm(contextPath+"/goods/add",{categoryId:currentCategoryId}).done(function(data){
                                        if(data.success){
                                            $("#goodsForm").dialog("close");
                                            $("#goodsTable").table("refresh");
                                            $("#goodsForm").reset();
                                            moon.success("商品添加成功");
                                        }else{
                                            moon.error("商品添加失败");
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
                            $("#saleForm").dialog("close");
                        }
                    }
                ]
            });
        }else if(btnTest=='editBtn'){//编辑优惠信息
            var selectRows = goodsTable.getSelect();
            if(selectRows.length!=1){
                moon.warn("请选中一条数据进行编辑.");
                return false;
            }
            var id = selectRows[0].id;

            $('#goodsForm').dialog({
                title:"编辑商品",
                afterShown:function(){
                    $("#goodsForm").validate({align:'right',theme:"darkblue",model:"update"});
                    $("#goodsForm").autoCompleteForm(contextPath+"/goods/get",{id:id},function(data){
                        $(".uploader-list").html("<img src=\""+contextPath+"/file/get/"+data.url+"\"/ class=\"img\" />");
                    });
                    initUploaderIfNecessary();
                },
                beforeClose:function(){
                    $("#goodsForm").validate("hide");
                },
                buttons:[
                    {
                        text : "保存",
                        css  : "btn btn-primary",
                        click:function(){
                            $("#goodsForm").validate("validate").done(function(result){
                                if(result){
                                    $("#goodsForm").ajaxSubmitForm(contextPath+"/goods/update",{"id":id}).done(function(data){
                                        if(data.success){
                                            $("#goodsForm").dialog("close");
                                            goodsTable.refresh();
                                            $("#goodsForm").reset();
                                            moon.success("商品修改成功");
                                        }else{
                                            moon.error("商品修改成功");
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
                            $("#goodsForm").dialog("close");
                        }
                    }
                ]
            });
        }else if(btnTest=='deleteBtn'){//删除优惠信息
            var selectRows =  goodsTable.getSelect();
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
                $.post(contextPath+"/goods/delete",ids,function(result){
                    goodsTable.refresh();
                });
            }
        }
    };

    function initUploaderIfNecessary(){
        if(!uploader) {
            uploader = moon.webuploader({
                fileQueued: function (f) {
                    this.makeThumb(f, function (error, src) {
                        if (error) {
                            console.log('不能预览');
                            return;
                        }
                        $(".uploader-list").html(moon.format(template, f.id, src));
                    }, 200, 200);
                    this.upload(f);
                },
                pick: "#picker",
                uploadProgress: function (file, percentage) {
                    var $bar = $("[data-id='" + file.id + "'] .progress-bar").css({
                        width: percentage * 100 + "%"
                    });
                    if (percentage == 1) {
                        $bar.removeClass("progress-bar-info").addClass("progress-bar-success");
                    }
                },
                accept:{
                    title: 'Images',
                    extensions:"png,jpg,jpeg,gif,bmp",
                    mimeTypes: 'image/*'
                },
                fileNumLimit:1,
                paste:document.body,
                uploadAccept: function (o, ret) {
                    var id = o.file.id;
                    uploader.removeFile(id);
                    if (ret.failure.length == 1) {//上传失败
                        $("[data-id='" + id + "'] .progress-bar").removeClass("progress-bar-success")
                            .addClass("progress-bar-danger")
                            .html(ret.failure[0].errorMsg);
                    }else{
                        var successFile = ret.success[0];
                        if(successFile){
                            $(".url").val(successFile.filePath);
                        }
                    }
                }
            });
        }else{
            uploader.reset();
        }
    };
})();