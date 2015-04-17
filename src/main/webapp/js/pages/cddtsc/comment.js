/**
 * Created by table on 2015/4/15.
 */
;
(function(){
    $(function(){
        var str=window.location.href;
        var es=/orderId=/;
        es.exec(str);
        var orderId=RegExp.rightContext;

        $.getJsonData(contextPath+"/order/get",{id:orderId}).done(function(data){
            console.log(data);
            $(".shop .shop-name").text(data.result.name);
            $("#commentTemplate").renderTemplate(data.result,{container:"#commentContainer",emptyParent:false});

        });

        //评论订单
        $(document).on("click",".submit",function(e){
            var that = $(this).parent();
            var content = that.find(".comment-content").val();
            console.log(content);
            if(content.length<5){
                moon.warn("请至少输入五个字评论");
                return;
            }
            $.getJsonData(contextPath+"/comment/add",{orderDetailId:that.attr("data-id"),content:content},{type:"Post"}).done(function(data){
                if(data.success){
                    console.log("评论成功");
                    that.find(".comment-content,.submit").remove();
                    that.append($("<span class='comment'>评论："+content+"</span>"));
                }
            });
        });
    });
})();