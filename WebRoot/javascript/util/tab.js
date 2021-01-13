//创建tab页
$(function(){
addPanel("地图", '<div id="allmap"></div>');
});

/*添加tab页*/
function addPanel(tabtitle, htmlcontent) {
	var closeenable = true;
	if (tabtitle == "地图") {
		closeenable = false;
	} else {
		closeenable = true;
	}
	if(!$('#mytab').tabs('exists', tabtitle)){
		$('#mytab').tabs('add', {
			title : tabtitle,
			content : htmlcontent,
			closable : closeenable
		});
	}else{
		$('#mytab').tabs('select', tabtitle); 
	}
}