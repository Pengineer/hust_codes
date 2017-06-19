$(document).ready(function() {
	//var flag = 9999;
	validateProject();
	
	/*
	 * 增加教育部部属项目
	*/
	$(".addMoeProject").live("click",function(){
		//flag++;
		//选中要增加节点的兄弟节点（在其后增加行元素）
		var onthis = $(this).parent().parent();
		
		//alert(onthis);
		var addMoeProjectTrString = '<tr class="newMoeProjectTr"><td class="wd24">'
							   + '<a class="delete_row" href="javascript:void(0);"><img src="image/del_entry.gif" title="删除"/></a></td>'
							   + '<td><table><tr><td class="wd16" style="border:0;"><input type="text" class="projectInput" name="newMoeProjectInfos[0].projectNum"/></td><td class="td_input" style="border:0;"></td></tr></table></td>'
							   + '<td><table><tr><td class="wd16" style="border:0;"><input type="text" class="projectInput" name="newMoeProjectInfos[0].projectName"/></td><td class="td_input" style="border:0;"></td></tr></table></td>'
							   + '<td><table><tr><td class="wd16" style="border:0;"><input type="text" class="projectInput" name="newMoeProjectInfos[0].projectType"/></td><td class="td_input" style="border:0;"></td></tr></table></td></tr>';
		$(addMoeProjectTrString).insertAfter(onthis);
		//将所有名字进行排序，并修改名字序号
		sortNameNum('newMoeProjectTr','newMoeProjectInfos');
		
	});
	
	/*
	 * 增加国社科项目
	*/
	$(".addNationalProject").live("click",function(){
		//flag++;
		//选中要增加节点的兄弟节点（在其后增加行元素）
		var onthis = $(this).parent().parent();
		
		var addNationalProjectTrString = '<tr class="newNationProjectTr"><td class="wd24">'
							   + '<a class="delete_row" href="javascript:void(0);"><img src="image/del_entry.gif" title="删除"/></a></td>'
							   + '<td><table><tr><td class="wd16" style="border:0;"><input type="text" class="projectInput" name="newNationalProjectInfos[0].projectNum"/></td><td class="td_input" style="border:0;"></td></tr></table></td>'
							   + '<td><table><tr><td class="wd16" style="border:0;"><input type="text" class="projectInput" name="newNationalProjectInfos[0].projectName"/></td><td class="td_input" style="border:0;"></td></tr></table></td>'
							   + '<td><table><tr><td class="wd16" style="border:0;"><input type="text" class="projectInput" name="newNationalProjectInfos[0].projectType"/></td><td class="td_input" style="border:0;"></td></tr></table></td></tr>';
		$(addNationalProjectTrString).insertAfter(onthis);
		//将所有名字进行排序，并修改名字序号
		sortNameNum('newNationProjectTr','newNationalProjectInfos');
		
	});
	
	/*
	 * 删除行，并对所有的项目按自己的分类排序，并修改名字序号
	*/
	$(".delete_row").live("click",function(){
		$(this).parent().parent().remove();
		sortNameNum('newMoeProjectTr','newMoeProjectInfos');
		sortNameNum('newNationProjectTr','newNationalProjectInfos');
	});
	
	
});

/*
 * 将每一类所有的项目名字重新编号
*/
var sortNameNum = function(className,projectType){
	$('.'+ className).each(function(key1,value){
		$(":input[name^=" + projectType + "]",value).each(function(key2, value){
			value.name = value.name.replace(/\[.*\]/, "[" + key1 + "]");

			/*
			 * 校验用。默认jquery validate会在指定位置（errorPlacement）增加label(errorElement),如果不手动更改它的name，则会出现错误消息重叠现象。
			*/
			if(0 != $(this).parent("td").next("td").find("label:first-child").length){
				var thisLabel = $(this).parent("td").next("td").find("label:first-child");
				thisLabel.attr("for",thisLabel.attr("for").replace(/\[.*\]/, "[" + key1 + "]"));
			}
		});
	});
};

