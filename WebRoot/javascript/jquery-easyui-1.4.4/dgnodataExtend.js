/**
 * datagrid扩展查询结果无数据
 */ 
var myview = $.extend({},$.fn.datagrid.defaults.view,{
    onAfterRender:function(target){
        $.fn.datagrid.defaults.view.onAfterRender.call(this,target);
        var opts = $(target).datagrid('options');
        var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
        vc.children('div.datagrid-empty').remove();
        if (!$(target).datagrid('getRows').length){
            var d = $('<div class="datagrid-empty"></div>').html(opts.emptyMsg || '没有查询到相应的数据！').appendTo(vc);
            d.css({
                position:'absolute',
                left:0,
                top:50,
                width:'100%',
                textAlign:'center'
            });
        }
    }
    });