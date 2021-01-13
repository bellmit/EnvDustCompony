/**
 * @param  id：环形计时器的ID
 * 销毁环形的实时计时器
 */
function destroyTimeCircle(id){
    if ($("#"+id) != null && $("#"+id).TimeCircles() != null) {
        $("#"+id).TimeCircles().destroy();
    }
}

/**
 * 初始化项目类型
 */
function initProjectTypeCombox(id){
    $.ajax({
        url:"../DeviceProjectController/queryDeviceProject",
        type:"post",
        dataType:"json",
        async:false,
        success:function(json){
            var data = [{"projectId":"","projectName":"全部"}];
            var comboxData = data.concat(json.rows);
            $("#"+id).combobox({
                data:comboxData,
                valueField:"projectId",
                textField:"projectName",
                editable:false,
                onChange: function (n,o) {
                    var pojectId = $("#"+id).combobox("getValue");
                    if("projectTypeDCombox" == id){
                        initTreeNoe("alarmLineDeviceTree", '../TreeController/getAreaTree', {"parentId":"","projectId":pojectId},
                            true,"alarmLineDeviceTree");
                    }else if("projectTypeIntervalDCombox" == id){
                        initTreeNoe("getDataIntervalTree", '../TreeController/getAreaTree', {"parentId":"","projectId":pojectId},
                            true,"getDataIntervalTree");
                    }else if("projectTypeRlDCombox" == id){
                        initTreeNoe("getRldDataTree", '../TreeController/getAreaTree', {"parentId":"","projectId":pojectId},
                            true,"getRldDataTree");
                    }else if("projectTypeSpanDCombox" == id){
                        initTreeNoe("getSpanTimeDataTree", '../TreeController/getAreaTree',  {"parentId":"","projectId":pojectId},
                            true,"getSpanTimeDataTree");
                    }else if("projectTypeVolDCombox" == id){
                        initTreeNoe("getVrDataTree", '../TreeController/getAreaTree', {"parentId":"","projectId":pojectId},true,"getVrDataTree");
                    }else if("projectTypeTemptDCombox" == id){
                        initTreeNoe("getTcDataTree", '../TreeController/getAreaTree',  {"parentId":"","projectId":pojectId},true,"getTcDataTree");
                    }else if("projectTypeExtDCombox" == id){
                        initTreeNoe("getErDataTree", '../TreeController/getAreaTree', {"parentId":"","projectId":pojectId},
                            true,"getErDataTree");
                    }
                },
                onLoadSuccess : function(){
                    if(json.rows.length>0){
                        $("#"+id).combobox("setValue", json.rows["0"]["projectId"]);
                    }else{
                        $("#"+id).combobox("setValue", data["0"]["projectId"]);
                    }
                }
            });

        }
    })
}