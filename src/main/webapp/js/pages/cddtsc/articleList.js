/**
 * Created by table on 2015/4/24.
 */
/**
 * Created by table on 2015/4/15.
 */
;
(function(){
    $(function(){
        var str=window.location.href;
        var es=/article\//;
        es.exec(str);
        var rightContext=RegExp.rightContext;
        var articleType = rightContext.substring(0,rightContext.indexOf("/"));
        $(".article-type-list ."+articleType).addClass("active");
        $(".type-name").html($(".article-type-list ."+articleType).text());
    });
})();