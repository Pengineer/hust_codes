define(function(require, exports, module) {
	require('pop-init');
	//弹出层选择学科类别
	function selectDispType(obj){
		var name = obj.alt;
		var names= document.getElementById("entityNames").value;
		if(obj.type == 'radio') {
			names = name;
			document.getElementById("entityNames").value=names;
		} else {
			if(obj.checked){
				if(names !=null && names !=""){
					names +="; "+name;
				}else{
					names +=name;
				}
				document.getElementById("entityNames").value=names;
			}else if(!obj.checked){
				var arr=names.split("; ");
				if(arr.length > 0){
					for (var i=0;i<arr.length;i++){
						if(arr[i] == name){
							arr.splice(i,1);
							break;
						}
					}
				}
				names = arr.join("; ");
				document.getElementById("entityNames").value=names;
			}
		}
		showTrimedNameV2(names);
//		$("#cancelSelectedCheckbox").remove();
	}

	$(".cancelSelectedItem").live("click", function(){
		var name = $(this).parent().text().replace(/; /g, "").trim();
		var names= $("#entityNames").val();
		var arr = names.split("; ");
		if(arr.length > 0){
			for (var i=0;i<arr.length;i++){
				if(arr[i] == name){
					arr.splice(i,1);
					break;
				}
			}
		}
		names = arr.join("; ");
		document.getElementById("entityNames").value=names;
		$("input:checked").each(function(){
			if($(this).attr("alt") == name) {
				$(this).removeAttr("checked");
				return;
			}
		});
		showTrimedNameV2(names);
	})
	// 勾选默认选中，显示默认选中
	function checkDisp(){
		var mainIfr = parent.document.getElementById("main");
		var names = "";
		if(null != thisPopLayer.inData && "" !=thisPopLayer.inData && "null" != thisPopLayer.inData ){ //ie中获取的为"null"字符串
			names =(thisPopLayer.inData)+"";
		}
		document.getElementById("entityNames").value = names;
		var dispIds = document.getElementsByName("dispTypeIds");
		for(var i=0;i<dispIds.length;i++){
			if(names.indexOf(dispIds[i].alt)>=0) {
				dispIds[i].checked = true;
			}
		}
		showTrimedNameV2(names);
//		$("#cancelSelectedCheckbox").remove();
	}
	
	exports.init = function() {
		checkDisp();
		$("#confirm").die().live("click", function(){
			var names = document.getElementById("entityNames").value;
			thisPopLayer.callBack(names);
			thisPopLayer.destroy();
		});
		window.selectDispType = function(obj){
			selectDispType(obj);
		}
	};
});
