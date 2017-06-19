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
	
	var valid_product_type = function(){
		$(function() {
			$(".itl").each(function(index){
				var productTypes = document.getElementsByName('entityNames');
				if (productTypes[index].checked == true) {
					$("input[name*='totalNumber']",$(this)).rules("add",{required: true, number: true, digits:true, min: 1});
					$("input[name*='realNumber']",$(this)).rules("add",{required: true, number: true, digits:true, range: function(){
							var total = $("input[name*='totalNumber']");
							var max = total.eq(index).val();
							if(isNaN(max) || max == undefined || max==""){
								max = 0;
							}
							return [0,max];
						}
					});
				}else{
					$("input[name*='totalNumber']",$(this)).rules("remove");
					$("input[name*='realNumber']",$(this)).rules("remove");
				}
			});
			var $other = $("input[name='entityNames'][type='checkbox'][alt='其他']");
			if($other.attr("checked") == true){
				$("input[name='productTypeOther']").rules("add", {required:true, maxlength:100, keyWords:true });
			}else{
				$("input[name='productTypeOther']").rules("remove");
			}
		});
	};
	var initfunc = function(){
		var productType = thisPopLayer.inData, disp = "";
		var tmp = productType.split(/; */g);
		for (var i = 0; i < tmp.length; i++) {
			if(tmp[i].indexOf('其他') != -1){
				var a = tmp[i].split(/\//g);
				$("input[name='entityNames'][alt='其他'][type='checkbox']").attr("checked", true);
				var $tr = $("input[name='entityNames'][alt='其他'][type='checkbox']").parent().parent();
				$("td:eq(4)", $tr).children().eq(0).attr("value", a[1]);
				$("td:eq(6)", $tr).children().eq(0).attr("value", a[2]);
				var b = a[0].split("(");
				var c = b[1].split(")");
				$("input[name*='productTypeOther']:eq(0)").attr("value", c[0]);
				$("#productTypeOtherSpan").show();
			}else{
				var a = tmp[i].split(/\//g);
				$("input[name='entityNames'][alt=" + a[0] + "][type='checkbox']").attr("checked", true);
				var $tr = $("input[name='entityNames'][alt=" + a[0] + "][type='checkbox']").parent().parent();
				$("td:eq(4)", $tr).children().eq(0).attr("value", a[1]);
				$("td:eq(6)", $tr).children().eq(0).attr("value", a[2]);
			}
		}
		initCheckAll();//判断是否全部选中
		$("#check").live("click", function() {
			checkAll(this.checked, "entityNames");
			valid_product_type();
		});
		$("input[name='entityNames']").live("click", function() {
			var checkbox_length = $("input[name='entityNames']").length;
			var cnt = count("entityNames");// 当前已选中的个数
			if ($(this).attr("checked")) {// 当有项被选中时，判断当前是否已全选了
				if (cnt == checkbox_length) {
					$("#check").eq(0).attr("checked", true);
				}
			} else {// 当有项撤销选中时，判断头是否处于非全选状态
				$("#check").eq(0).attr("checked", false);
			}
			if($(this).attr("alt") == "其他"){
				if($(this).attr("checked")){
					$("#productTypeOtherSpan").show();
				}else{
					$("#productTypeOtherSpan").hide();
		//			$("#profuctTypeOtherTd").html("");
				}
			}
			valid_product_type();
		});
		
		$("#confirm").unbind("click").click(function(){
			if(!$("#product_type_form").valid()){
				return false;
			}
			var productTypeEnd = "";
			var productTypes = document.getElementsByName('entityNames');
			var totalNumbers = $("input[name*='totalNumber']");
			var realNumbers = $("input[name*='realNumber']");
			for (var i = 0; i < productTypes.length; i++) {
				if (productTypes[i].checked == true) {
					if(productTypes[i].alt == "其他"){
						productTypeEnd += (productTypeEnd.length ? "; " : "") + productTypes[i].alt + "("  + $("input[name*='productTypeOther']:eq(0)").val()+ ")" + "/" + totalNumbers.eq(i).val() + "/" + realNumbers[i].value;
					}else{
						productTypeEnd += (productTypeEnd.length ? "; " : "") + productTypes[i].alt + "/" + totalNumbers.eq(i).val() + "/" + realNumbers[i].value;
					}
				}
			}
			thisPopLayer.outData = {data : {"productType" : productTypeEnd}};
			thisPopLayer.callBack(thisPopLayer.outData);
			thisPopLayer.destroy();
		});
		//前台校验
		$("#product_type_form").validate({
			errorElement: "span",
			event: "blur",
			errorClass: "error1",
			rules:{
				
				},
			errorPlacement: function(error, element){
				element.parent("td").parent("tr").children("td").last().html("");
				error.appendTo( element.parent("td").parent("tr").children("td").last() );
			}
		});
		valid_product_type();
	};
	
	exports.init = function(){
		initfunc();
	};
});


