function ajaxLoadingBigScreen(id){
    $('<div id="ajaxloader3"> <div class="outer"></div> <div class="inner"></div> </div>').appendTo("#"+id).css({
        "display":"block",
        "z-index":99999,
        "left":($("#"+id).outerWidth(true)-25) / 2,
        "top":($("#"+id).height()+2) / 2
    });
}
/*
 * 函数功能：结束loading效果
 */
function ajaxLoadEndBigScreen(){
    $("#ajaxloader3").remove();
}
