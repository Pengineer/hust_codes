
function setReview(ob, // 复选框ID
			ff, // form表单名称或id
			flag) // 评审状态
{
	var idsList1 = new Array();
	var $obj = $(":input[name='entityIds']");
	for(var i = 0; i < $obj.size(); i++){
		if($obj.eq(i).prop("checked")){
			idsList1.push($obj.eq(i).val());
		}
	}
	var entityIds = "";
	for(var i in idsList1){
		entityIds += ((i == 0) ? "" : "&") + "entityIds[" + i + "]=" + idsList1[i];
	}
	
	// 判断是否有专家被选中
	var cnt = count(ob);
	if (cnt == 0) {
		if (flag == 0) {
			alert("请选择要退出评审的项目！");
		} else if (flag == 1) {
			alert("请选择要参加评审的项目！");
		} else {
			alert("无效的项目参评状态！");
		}
		return false;
	} else {
		if (flag == 0){
			popSetReview({
				title : "填写退评原因",
				src : "project/instp/toDisableReview.action?" + entityIds,
				callBack : function(result){
					window.location.href = "project/instp/toList.action?update=1&listType=" + $("input[name='listType']").val() + "&isReviewable=1";
				}
			});
		} else if (flag == 1) {
			if (confirm("您确定要让选中的项目参加评审吗？")) {
				document.forms[ff].action = "project/instp/enableReview.action";
				document.forms[ff].submit();
				return true;
			}
		} else {
			alert("无效的项目参评状态！");
			return false;
		}
	}
}

/**
 * 手工匹配专家确定
 */
function doSelect() {
	var preYear=$(window.parent.document).find("#year[name=year]").val();
	var curYear=$(window.parent.document).find("#year>option[selected=selected]").val();
	if(preYear != curYear){
		alert("不允许对当年度项目做此操作");
	}else {
		var iframeObj = document.getElementById('expertTreeFrame').contentDocument;
		var exps = iframeObj.getElementById('selectedExpertIds').value;
		//取得基地项目评审专家数=基地项目部级评审专家数+基地项目地方评审专家数
		var InstpMinistryExpertNumber = parseInt($("#InstpMinistryExpertNumber").val());
		var InstpLocalExpertNumber = parseInt($("#InstpLocalExpertNumber").val());
		var projectExpertNumber = InstpMinistryExpertNumber + InstpLocalExpertNumber;
//		if(exps.length == 0 || exps == null) {
//			alert('请至少选择一个专家！');
//			return ;
//		}
		document.getElementById('selectedExpertIds').value = exps;
		var selectedExpertNum = exps.split(";").length;
		//判断项目配置专家数，必须不能大于系统设置的数量
		if(selectedExpertNum > projectExpertNumber){
			alert("项目分配专家数应≤" + projectExpertNumber);
			return false;
		}else{
			document.forms['selectReviewer'].action = 'project/instp/reMatchExpert.action';
			document.forms['selectReviewer'].submit();
		}
	}
}


function toggle_view(nodeId) {
	var currentNode = document.getElementById(nodeId);
	var Div = currentNode.nextElementSibling || currentNode.nextSibling ;
	var lastChild =  currentNode.firstElementChild || currentNode.firstChild;
	if (Div) {
		if (lastChild.className == 'img_dis') {
			Div.style.display = 'none';
			lastChild.className = 'img_undis';
			
		} else if (lastChild.className == 'img_undis') {
			Div.style.display = 'block';
			lastChild.className = 'img_dis';
		}
	}
}

