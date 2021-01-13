var cmenu;
function createColumnMenu(id){
    cmenu = $('<div/>').appendTo('body');
    cmenu.menu({
        onClick: function(item){
            if (item.iconCls == 'icon-ok'){
                $('#'+id).datagrid('hideColumn', item.name);
                cmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'icon-empty'
                });
            } else {
                $('#'+id).datagrid('showColumn', item.name);
                cmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'icon-ok'
                });
            }
        }
    });
    var fields = $('#'+id).datagrid('getColumnFields');
    for(var i=0; i<fields.length; i++){
        var field = fields[i];
        var col = $('#'+id).datagrid('getColumnOption', field);
        var myIcon = 'icon-ok';
        if(col.hidden){
            myIcon = "icon-empty"
        }else{
            myIcon = 'icon-ok';
        }
        cmenu.menu('appendItem', {
            text: col.title,
            name: field,
            iconCls:myIcon
        });
    }
}