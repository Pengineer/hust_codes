$(document).ready(function(){
	
	$("#selectDiscipline").click(function(){
		popOperation({
			title : "选择学科代码",
			src : "pop/select/disciplineTree/toDisciplineTree.action?selectedDisciplines=" + $("#discipline").val(),
			inData : $("#discipline").val(),
			callBack : function(result){
				$("#choosed").html(result.codeNames);
				$("#discipline").val(result.codes);
			}
		});
	});
	
	//举报信息提交更新
	$("#submit").click(function(){
		//姓名
		var name = $("input[name='expert.name']").val();
		if(!$.trim(name)){
			alert("请填入姓名！"); flag = false;
			return false;
		}
		//学科代码
		var name = $("input[name='expert.discipline']").val();
		if(!$.trim(name)){
			alert("请填入学科代码！"); flag = false;
			return false;
		}
		
		$("#form_expert").submit();
	});
	
	$("#form_expert").submit(function(){
		$(this).ajaxSubmit({
			dataType : "json",
			success : function(result){
//				alert(result.entityId);
				thisPopLayer.callBack(result);
				thisPopLayer.destroy();
			}
		});
	});
	return false;
});