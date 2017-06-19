/**
 * @author 余潜玉
 */

/**
 * @author 王燕
 */
define(function(require, exports, module) {
	require('form');
	require('validate');
	
	//初始化时判断是否全部选中
	var initCheckAll = function(){
		var checkbox_length = $("input[name='entityNames'][type='checkbox']").length;
		var checked_length = $("input[name='entityNames'][type='checkbox']:checked").length;
		if(checkbox_length == checked_length){//判断是否全部选中
			$("#check").eq(0).attr("checked", true);
		}
	};

	var valid_index_type = function(){
		$(".itl").each(function(index){
			var indexTypes = document.getElementsByName('entityNames');
			if (indexTypes[index].checked == true) {
				$("input[name*='indexNumber']",$(this)).rules("add",{required: true, number: true, digits:true, min: 1});
			}else{
				$("input[name*='indexNumber']",$(this)).rules("remove");
			}
		})
	};
	
	var readyfunc = function(){
		var indexType = thisPopLayer.inData, disp = "";
		if (indexType.match(/(\w+\/\w+; )*\w+\/\w+/g)){
			var tmp = indexType.split(/; */g);
			for (var i=0;i<tmp.length;i++){
				var a = tmp[i].split(/\//g);
				$("input[name='entityNames'][alt="+a[0]+"][type='checkbox']").attr("checked",true);
				var $tr = $("input[name='entityNames'][alt="+a[0]+"][type='checkbox']").parent().parent();
				var $td = $("td:eq(2)", $tr);
				$td.children().eq(0).attr("value",a[1]);
			}
			initCheckAll();
		}
		$("#check").live("click", function() {
			checkAll(this.checked, "entityNames");
		});
		$("input[name='entityNames']").live("click", function() {
			var checkbox_length = $("input[name='entityNames']").length;
			var cnt = count("entityNames");// 当前已选中的个数
			if (this.checked) {// 当有项被选中时，判断当前是否已全选了
				if (cnt == checkbox_length) {
					$("#check").eq(0).attr("checked", true);
				}
			} else {// 当有项撤销选中时，判断头是否处于非全选状态
				$("#check").eq(0).attr("checked", false);
			}
			valid_index_type();
		});
		$("#confirm").unbind("click").click(function(){
			if(!$("#index_type_form").valid()){
				return;
			}
			var indexType="";
			var indexTypes = document.getElementsByName('entityNames');
			var numbers = $("input[name*='indexNumber']");
			for(var i=0;i<indexTypes.length;i++){
				if(indexTypes[i].checked == true){
					indexType += (indexType.length ? "; " : "") + indexTypes[i].alt+"/"+ numbers[i].value;
				}
			}
			thisPopLayer.callBack({
				data : {"indexType" : indexType}
			});
			thisPopLayer.destroy();
		});
		//前台校验
		$("#index_type_form").validate({
			errorElement: "span",
			event: "blur",
			rules:{
			
				},
			errorPlacement: function(error, element){
				error.appendTo( element.parent("td").next("td") );
			}
		});
		valid_index_type();
	};
	
	exports.init = function(){
		readyfunc();
	}
});