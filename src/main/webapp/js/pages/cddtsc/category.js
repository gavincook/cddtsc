;
(function(){

    var categoryTable;
    var goodsTable;
    var currentCategoryId;//当前类别Id,用于商品表格的操作
    var uploader;
    var template =  '<div class="imgContainer" data-id="{0}"><div class="content">'
        +'<i class="fa fa-times"></i><img src="{1}" class="img">'
        +'<div class="progress">'
        +'<div class="progress-bar progress-bar-info"></div>'
        +'</div></div>'
        +'</div>' ;
    var updateTemplate =  '<div class="imgContainer" data-attachment-id="{1}"><div class="content">'
        +'<i class="fa fa-times"></i><img src="{0}" class="img">'
        +'</div>'
        +'</div>' ;

    var fileIds;
    var removeFileIds,deletedGoodsIds;

    //图片预览删除事件
    $(document).on("click","#fileContainer i",function(e){
        var $div = $(e.target).closest(".imgContainer");
        if($div.attr("data-id")){
            uploader.removeFile($div.attr("data-id"));//新添加的图片
        }else{//编辑以前的图片
            var currentId = $div.attr("data-attachment-id");
            $.each(fileIds,function(index,id){
                if( id == currentId){
                    removeFileIds.push(id);
                    return;
                }
            });
        }
        $div.remove();
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
                        $(".uploader-list").append(moon.format(template, f.id, src));
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
                paste:document.body,
                uploadAccept: function (o, ret) {
                    var id = o.file.id;
                    if (ret.failure.length == 1) {//上传失败
                        $("[data-id='" + id + "'] .progress-bar").removeClass("progress-bar-success")
                            .addClass("progress-bar-danger")
                            .html(ret.failure[0].errorMsg);
                    }else{
                        var successFile = ret.success[0];
                        if(successFile){
                            o.file.filePath = successFile.filePath;
                        }
                    }
                }
            });
        }else{
            $(".uploader-list").empty();
            $.each(uploader.getFiles("complete"),function(index,f){
                uploader.removeFile(f);
            });
        }
    }
    var editor;
    $(function(){
        editor = UM.getEditor("content");

        //修改后变红色
        $(document).on("change",".price-box",function(e){
            var $input = $(e.target);
            $input.css("color","#F00");
        });

        $(".add-row").click(function(){
           $(".rows-container").append($("#goodsRow").html());
        });

        $(document).on("click",".remove-row",function(e){
            var goodsRow  = $(e.target).closest(".goods-row");
            goodsRow.remove();
            var goodsId = goodsRow.attr("data-id");
            if(goodsId) {
                deletedGoodsIds.push(goodsId);
            }
        });

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
            callback:function(){
                $("[data-number='0']",this.$container).trigger("click");
            },
            buttons:[
                {
                    text:"增加类别",
                    click:categoryBtnHandler,
                    name:'addBtn'
                },
                {
                    text:"增加子类别",
                    click:categoryBtnHandler,
                    name:'addChildBtn'
                },
                {
                    text:"编辑",
                    click:categoryBtnHandler,
                    name:'editBtn'
                },
                {
                    text:"删除",
                    click:categoryBtnHandler,
                    name:'deleteBtn'
                }
            ]
        });

        //选择类别显示商品
        $(document).on("click","#categoryTable tbody tr",function(e){
            var rowData = categoryTable.getRowData(e.currentTarget);
            currentCategoryId = rowData.id;
            if(goodsTable) {
                goodsTable.refresh({categoryId: currentCategoryId});
            }else{
                goodsTable = $("#goodsTable").table({
                    url:contextPath+"/goods/list",
                    columns:[{name:"name",display:"商品",width:"300px",render:function(columnData){
                        return columnData.name;
                    }},
                        {name:"specification",display:"规格",width:"300px",render:function(columnData){
                            var ret = "";
                            var specifications = columnData.specification.split("@#$");
                            for(var index in specifications){
                                ret += "<div class='item'>" +specifications[index]+ "</div>";
                            }
                            return ret;
                        }},
                        {name:"price",display:"价格",width:300,render:function(columnData){
                            var ret = "";
                            var prices = columnData.price.split("@#$");
                            var ids = columnData.id.split("@#$");
                            for(var index in prices){
                                ret += "<div class='item'><div class='input-group'><div class='input-group-addon'><i class='fa fa-rmb'></i> </div>" +
                                "<input type='text' class='form-control price-box' data-id='"+ids[index]+"' value='"+prices[index]+"'>"+
                                "</div></div>";
                            }
                            return ret;
                        }}
                    ],
                    params:{categoryId: currentCategoryId},
                    formatData:function(data){
                        return data.result;
                    },
                    title:"商品列表",
                    rowId:"id",
                    treeTable:false,
                    multiSelect:false,
                    pageSize:100,
                    buttons:[
                        {
                            text: "保存价格",
                            click: goodsBtnHandler,
                            name: 'updateBtn'
                        },
                        {
                            text:"增加商品",
                            click:goodsBtnHandler,
                            name:'addBtn'
                        },
                        {
                            text:"编辑",
                            click:goodsBtnHandler,
                            name:'editBtn'
                        },
                        {
                            text:"删除",
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
                    editor.setContent("");
                    $("#goodsForm").validate({align:'right',theme:"darkblue"});
                    initUploaderIfNecessary();
                    $(".consultation-reply-content",this.$e).html($("#contentContainer").removeClass("hide"));
                },
                beforeClose:function(){
                    $("#goodsForm").validate("hide");
                    $(document.body).append($("#contentContainer").addClass("hide"));
                },
                size:"lg",
                buttons:[
                    {
                        text : "保存",
                        css  : "btn btn-primary",
                        click:function(){
                            $("#goodsForm").validate("validate").done(function(result){
                                console.log(result);
                                if(result){
                                    var attachments = new Array();
                                    $.each(uploader.getFiles("complete"),function(index,f){
                                        console.log(f.filePath);
                                        attachments.push(encodeURIComponent(encodeURIComponent(f.filePath,"UTF-8"),"UTF-8"));
                                    });
                                    console.log(attachments);
                                    var param = {};
                                    param.categoryId = currentCategoryId;
                                    param.attachments = attachments;
                                    param.description =  encodeURIComponent(encodeURIComponent(editor.getContent(),"UTF-8"),"UTF-8");
                                    $("#goodsForm").ajaxSubmitForm(contextPath+"/goods/add", param).done(function(data){
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
            var id = selectRows[0].goodsId;
            fileIds = new Array();
            removeFileIds = new Array();
            deletedGoodsIds = [];
            $('#goodsForm').dialog({
                title:"编辑商品",
                size:"lg",
                afterShown:function(){
                    $("#goodsForm").validate({align:'right',theme:"darkblue",model:"update"});
                    $.getJsonData(contextPath+"/goods/get",{id:id}).done(function(data){
                        var result = data.result;
                        editor.setContent(result.description||"");
                        var $imgList = $(".uploader-list").empty();
                        $("#goodsForm").find("[name='name']").val(result.name);

                        var prices = result.price.split("@#$");
                        var levels = result.level.split("@#$");
                        var units = result.unit.split("@#$");
                        var ids = result.id.split("@#$");
                        var specifications = result.specification.split("@#$");
                        var levelDescriptions = result.levelDescription?result.levelDescription.split("@#$"):[];
                        $(".rows-container").empty();
                        for(var i in prices){
                            var goodsItem = $("#goodsRow>div").clone(true);
                            goodsItem.attr("data-id",ids[i]);
                            goodsItem.find("[name='level']").val(levels[i]);
                            goodsItem.find("[name='levelDescription']").val(levelDescriptions[i] || "");
                            goodsItem.find("[name='specification']").val(specifications[i]);
                            goodsItem.find("[name='price']").val(prices[i]);
                            goodsItem.find("[name='unit']").val(units[i]);
                            goodsItem.find("[name='id']").val(ids[i]);
                            $(".rows-container").append(goodsItem);
                        }

                        $.each(result.images,function(index,image){
                            var filepath = contextPath+"/file/get/"+image.url;
                            $imgList.append(moon.format(updateTemplate,filepath,image.id));
                            fileIds.push(image.id);//添加到文件队列
                        });
                    });
                    //$("#goodsForm").autoCompleteForm(contextPath+"/goods/get",{id:id},function(data){
                    //    editor.setContent(data.description||"");
                    //    var $imgList = $(".uploader-list").empty();
                    //    $.each(data.images,function(index,image){
                    //        var filepath = contextPath+"/file/get/"+image.url;
                    //        $imgList.append(moon.format(updateTemplate,filepath,image.id));
                    //        fileIds.push(image.id);//添加到文件队列
                    //    });
                    //});
                    $(".consultation-reply-content",this.$e).html($("#contentContainer").removeClass("hide"));
                    initUploaderIfNecessary();
                },
                beforeClose:function(){
                    $("#goodsForm").validate("hide");
                    $(document.body).append($("#contentContainer").addClass("hide"));
                },
                buttons:[
                    {
                        text : "保存",
                        css  : "btn btn-primary",
                        click:function(){
                            $("#goodsForm").validate("validate").done(function(result){
                                if(result){
                                    var attachments = new Array();
                                    $.each(uploader.getFiles("complete"),function(index,f){
                                        attachments.push(encodeURIComponent(encodeURIComponent(f.filePath,"UTF-8"),"UTF-8"));
                                    });
                                    console.log(attachments);
                                    console.log(removeFileIds);
                                    var param = {};
                                    param.goodsId = id;
                                    param.attachments = attachments;
                                    param.attachmentIds = removeFileIds;
                                    param.description =  encodeURIComponent(encodeURIComponent(editor.getContent(),"UTF-8"),"UTF-8");
                                    param.deletedIds = deletedGoodsIds;
                                    $("#goodsForm").ajaxSubmitForm(contextPath+"/goods/update", param).done(function(data){
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
        }else if(btnTest=='deleteBtn'){//删除商品信息
            var selectRows =  goodsTable.getSelect();
            if(selectRows.length<1){
                moon.warn("请选择要删除的数据");
                return false;
            }
            if(confirm("确认删除这"+selectRows.length+"条数据?")){
                var ids="";
                $.each(selectRows,function(index,e){

                    $.each(e.id.split("@#$"),function(i,id){
                        ids+="&ids="+id;
                    });
                });
                ids = ids.substring(1);
                $.post(contextPath+"/goods/delete",ids,function(result){
                    goodsTable.refresh();
                });
            }
        }else if(btnTest == 'updateBtn'){
            var ids = new Array(),prices = new Array();
            $(".price-box").each(function(index,e){
                ids.push($(e).attr("data-id"));
                prices.push(e.value);
            });
            $.getJsonData(contextPath+"/goods/updatePrice",{
                goodsId:ids,
                prices:prices
            },{type:"Post",traditional:true}).done(function(data){
                if(data.success){
                    moon.success("价格更新成功");
                }else{
                    moon.error("价格更新失败");
                }
            });
        }
    };

})();