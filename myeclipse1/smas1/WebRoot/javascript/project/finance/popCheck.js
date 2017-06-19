
$(document).ready(function() {
	
	$("input[type='radio']", $("#list_container")).live("click", function(){
		thisPopLayer.outData = {
			data : {
				"id" : this.value,
				"name" : this.alt,
				"personId" : this.lang,
				"sname" : this.title
			}
		};
	});
	
	$(".isGranting").live('click', function(){
		var flag = $(this).next().val();
		if(flag == 0){
			$(this).html('拟立项');
			$(this).next().attr('value', 1);
		} else if(flag == 1){
			$(this).html('确定立项');
			$(this).next().attr('value', 2);
		} else {
			$(this).html('不立项');
			$(this).next().attr('value', 0);
		}
	});
	
	$("#confirm").live('click', function(){
		//todo 确定时组装idsList，传参数idsList, disType, area, proType
		var idsList1 = new Array(), idsList2 = new Array();
		var $obj = $(".isGranting");
		for(var i = 0; i < $obj.size(); i++){
			if($obj.eq(i).next().val() == 1){
//				alert($obj.eq(i).html().trim());
//				alert($obj.eq(i).next().val());
//				alert($obj.eq(i).next().next().val());
				idsList1.push($obj.eq(i).next().next().val());
			} else if($obj.eq(i).next().val() == 2){
				idsList2.push($obj.eq(i).next().next().val());
			}
		}
		//alert(idsList);
		$.ajax({
			url: "finance/checkById.action",
			type: "post",
			//data: "disType=" + $("#disType").val() + "&area=" + $("#area").val() + 
				//"&proType=" + $("#proType").val() + "&idsList=" + idsList,
			data : (function(){
				var para = "";
				for(var i in idsList1){
					para += ((i == 0) ? "" : "&") + "idsList1[" + i + "]=" + idsList1[i];
				}
				for(var i in idsList2){
					para += ((para == "") ? "" : "&") + "idsList2[" + i + "]=" + idsList2[i];
				}
				return para;
			})(),
			dataType: "json",
			success: function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						thisPopLayer.callBack(result);
						thisPopLayer.destroy();
					}else{
						alert(result.errorInfo);
					}
				}
		});
	})
});
