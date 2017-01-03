/**
 * Created by Administrator on 2017/1/3.
 */
// 超过250字的问题的“显示全部”和“收起”
// 两种场景：1、“发现”中的问题  2、问题具体页的问题描述
$(function() {
    // “显示全部”与“收起”
    $(document).on("click", ".feed-content .feed-summary .expand, .question-detail .feed-summary .expand", function(){
        var $parent = $(this).parent();
        var questionLink = $parent.parent().siblings(".feed-title").find("a") || $parent.parent().siblings(".question-title").find("a");
        var questionId = questionLink.attr("data-questionId");  // 取得问题的id

        // 取得全文的内容
        $.ajax({
            url: "/questions/q/" + questionId + "/detail",
            type: "GET",
            data: questionId,
            dataType: "json",
            success: function(data){
                // 设置全文的 html 为 data.content
                console.log(data);
                console.log(JSON.stringify(data));

                var str = data.content;
                console.log("str: " + str);
                str = str.replace(/\r\n/g, "<br />");
                console.log("--str: " + str);
                $parent.next().html(str);
            }
        });

        $parent.addClass("hide");            // 隐藏简文
        $parent.next().removeClass("hide");  // 显示全文
    });
    // $(document).on("click", ".pack-up", function(){
    //     var $parent = $(this).parent();
    //     $parent.addClass("hide");
    //     $parent.prev().removeClass("hide");
    // });
    // $(document).on("click", ".feed-summary", function(event){
    //     if($(event.target).hasClass("expand")){
    //         return true;
    //     }
    //     $(this).find(".expand").click();
    // });
});