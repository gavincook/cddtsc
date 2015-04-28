/**
 * 判断是否为空
 */
Handlebars.registerHelper('nonEmpty', function(variable, options) {
    if(variable && variable.length > 0) {
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});

/**
 * 判断是否相等
 */
Handlebars.registerHelper('eq', function(var1,var2,options) {
    if(var1 == var2) {
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});

/**
 * 判断是否全等
 */
Handlebars.registerHelper('congruent', function(var1,var2,options) {
    if(var1 === var2) {
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});


/**
 * 列出userType与角色对应列表
 *//**
 * 判断和系统变量是否相等
 */
Handlebars.registerHelper('eqConstant', function(var1,var2, options) {
    if(var1 == moon.constants[var2]) {
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});
Handlebars.registerHelper('listUserType', function(data,options) {
    var fn = options.fn, inverse = options.inverse;
    var ret = "";
    var roles = data.roles;
    for(var key in moon.constants){
        if(key.indexOf("userType") != -1){
            ret += "<div class='form-group'>"+
                "<label class='control-label col-md-2'>" +
                key.substr(9)+
                "</label>"+
                "<div class='col-md-8'>"+
                "<select name='userType" +moon.constants[key]+"'"+
                " class='form-control'>"+
                "<option value='0'>请选择角色</option>";
            for(var roleIndex in roles){
                var role = roles[roleIndex];
                ret += "<option value='"+role.id+"' ";
                if(role.id == data["role.userType"+moon.constants[key]]){
                    ret += " selected ";
                }
                ret += ">" + role.roleName +"</option>"
            }
            ret += "</select> </div> </div>";
        }
    }
    return ret;
});

/**
 * 算出总价
 */
Handlebars.registerHelper('plus', function(var1,var2, options) {
    return ((+var1)*(+var2)).toFixed(2);
});

Handlebars.registerHelper("renderPagination",function(data,opts){
    var ret = "";
    var prevAble = false,nextAble = false;
    if(data.previousPageIndex < data.currentPageIndex){
        prevAble = true;
    }
    ret += "<button class='btn btn-default page-prev pointer' "+(prevAble?"":"disabled")+" >上一页</button>";
    pageIndex = data.currentPageIndex;
    if(data.nextPageIndex > data.currentPageIndex){
        nextAble = true;
    }
    ret += "<button class='btn btn-default page-next pointer' "+(nextAble?"":"disabled")+">下一页</button>";
    return ret;
});