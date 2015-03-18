;
(function(){
    $(function(){
       $("#orderTable").table({
           url:contextPath+"/order/list",
           columns:[{name:"userName",display:"手机号"},
               {name:"realName",display:"真实姓名"},
               {display:"操作",render:function(rowData){
                   var opts = "<div class='opts'>";
                   if(rowData.active === false){
                       opts += "<a href='#' class='active-user'><i class='fa  fa-check-square-o'></i>激活 </a>";
                   }else{
                       opts += "<a href='#' class='disable-user'><i class='fa  fa-ban'></i>禁用 </a>";
                   }

                   opts += "<a href='#' class='reset-password'><i class='fa fa-key'></i>重置密码 </a>";
                   opts += "</div>";
                   return opts;
               }}
           ],
           formatData:function(data){return data.result;}
       });

    });
})();