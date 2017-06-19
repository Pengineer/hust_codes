// ztree相关的全局变量
var zTreeObj;	//专家树对象
// zTree 的参数配置，（setting 配置详解）
var setting = {
	view: {
		selectedMulti: false
	},
	async : {//异步请求，获取专家树数据
		enable : true,
		url : "award/moeSocial/createExpertTree.action",
	},
	check: {
		enable: true,
		chkStyle: "checkbox"
	},
	callback: {
		beforeAsync: zTreeBeforeAsync,
		onCheck: zTreeOnCheck,
		onClick: zTreeOnClick,
		onAsyncSuccess: showTree
	}
};
				
$(document).ready(function(){
	//加载专家树
	zTreeObj = $.fn.zTree.init($("#expert_tree"), setting);
});

/**
 * 发送请求前的预处理<br>
 * treeNode = null表示根节点，只允许发一次请求
 */
function zTreeBeforeAsync(treeId, treeNode) {
	if(treeNode != null) return false;
};

/**
 * 节点单击事件（弹出层查看专家）
 */
function zTreeOnClick(event, treeId, treeNode) {
	if(!treeNode.isParent){//只有叶子节点才执行
		showExp(treeNode.id);
	}
};

/**
 * checkbox的勾选事件（选择专家）
 */
function zTreeOnCheck(event, treeId, treeNode) {
	checkLegal(treeNode);
};



//业务相关的全局变量
var projectExpertNumber;	//每个项目应分配的专家数(如：5)
var expertProjectMin;		//每个专家最少评审项目数(如：20)
var expertProjectMax;		//每个专家最多评审项目数(如：60)
var projectId;				//项目id
var selectExpertIds;		//选择的专家ids
var disciplineLevelOnes;	//一级学科代码

/**
 * 显示专家树
 */
function showTree() {
	$("#loading").hide(500);
	$("#expall").show(500);
}

/**
 * 专家树页面初始函数
 */
function init() {
	if(projectExpertNumber == null || expertProjectMax == null) {
		//DWR访问server，获取初始化参数，initParamCallback为回调函数
		awardService.initParam("Tree", initParamCallback);
	}
	initHeight();
}

/**
 * init的回调函数
 * @param data
 */
function initParamCallback(data) {
	var param = data.split(",");	//对返回的数据data，按照“,”分隔开
	projectExpertNumber = param[0];	//一般项目_每个项目_所需的专家数
	expertProjectMin = param[1];	//一般项目_每个专家_评审的最小项目数
	expertProjectMax = param[2];	//一般项目_每个专家_评审的最大项目数
	projectId = param[3];			//选中的项目ids
	if(param[4] == 'null') {
		param[4] = '';
	}
	if(param[5] == 'null') {
		param[5] = '';
	}
	selectExpertIds = param[4];		//选中的专家ids
	disciplineLevelOnes = param[5];	//选中的一级学科代码
	// 给页面元素赋值
	$("input[name='selectExp']").val(param[4]);
	$("input[name='discipline']").val(param[5]);
	initHeight();
}

/**
 * 初始化高度
 */
function initHeight() {
	if(projectExpertNumber == 9999) {
		$("#treeId").css("height",'253px');
		$("#expList").css("height",'253px');
		$("#loading").css("height",'154px');
	} else {
		var treeIdH = 253 + (projectExpertNumber - 5) * 31;//计算高度
		$("#treeId").css("height",treeIdH + 'px');
		$("#expList").css("height",treeIdH + 'px');
		var loadingH = 154 + (projectExpertNumber - 5) * 31;
		$("#loading").css("height",loadingH + 'px');
	}
	//根据initParamCallback时从后台返回的一级学科代码，勾选页面上的checkbox
	if(disciplineLevelOnes != null && disciplineLevelOnes.length > 0) {
		$("input[name='disids'][type='checkbox']").each(function(){
			if(disciplineLevelOnes.indexOf($(this).attr("id"))>=0){
				$(this).attr("checked", true);
			}
		});
	}
	//根据initParamCallback时从后台返回的选择的专家，获取专家信息
	if(selectExpertIds != null || selectExpertIds != 'null') {
		//DWR访问server，获取专家信息，getDetail为回调函数
		awardService.getExpInfo("award", selectExpertIds, getDetail);
		return false;
	}
	getBodyHeight();
}

/**
 * 专家树右侧的详情
 * @param data server端返回的结果<br>
 * data是以 "%" 隔开的多个专家的信息，其中每个专家的信息又以 "," 分隔开
 * 例如：id,专家信息（学科代码^专家姓名^参评信息）,
 */
function getDetail(data) {
	var hasData = true;//有返回数据 true：有，false：没有
	if(data == null || data.length == 0) {
		hasData = false;
		data = '';
	}
	//生成右侧详情html代码	
	var htmlStr = "<table class='table_eview' cellspacing='0' cellpadding='0'>";
	var dataArray = data.split('%');//获取不同专家信息的数组
	var trNum = 5;//待添加的行数，专家数量（临时变量）
	if(dataArray.length > 5) {//如果返回的专家个数大于5，则更新左右两侧div的高度
		var expheight = 253 + (dataArray.length - 5) * 31;//31表示每一行的高度
		$("#expList").css("height", expheight + 'px');	//右侧专家信息div的高度
		$("#treeId").css("height", expheight + 'px');	//左侧专家树div的高度
		trNum = dataArray.length;
	} else {//否则，采用默认高度
		$("#expList").css("height", '253px');	
		$("#treeId").css("height", '253px');	
	}
	htmlStr +=
		"<tr style='background-color: rgb(230, 230, 230);'>" +
		"	<td style='border: solid #999;border-width: 0 1px 1px 0;' width='30'>序号</td>" +
		"	<td style='border: solid #999;border-width: 0 1px 1px 0;' width='45'>姓名</td>" +
		"	<td width='205' style='border: solid #999;border-width: 0 1px 1px 0;'>学科代码</td>" +
		"	<td width='30' style='border: solid #999;border-width: 0 0 1px 0;'>删除</td>" +
		"</tr>";
	if(hasData) {//有返回数据
		for(var i = 0; i < trNum; i++) {
			if(i < dataArray.length) {//dataArray的个数可能小于tmpnum
				var datas = dataArray[i].split(',');
				//radio列，datas[0]表示专家的id
//				var radioStr = '<input name="rank" type="radio" id="' + datas[0] +  '"/>';
				//序列号列
				var serialNumber = ' ' + (i+1);//序号，从1开始
				//专家信息（学科代码：expertInfo[0]+专家姓名：expertInfo[1]+参评信息：expertInfo[2]）
				var expertInfo = datas[1].split('^');
				var st1 = expertInfo[2].indexOf('参评');
				var st2 = expertInfo[2].indexOf('项]');
				var cpNum = expertInfo[2].substr(st1 + 2, st2 - st1 - 2);//获取参评数量
				//姓名列
				var nameStr = '<a onclick="showExp(\'' + datas[0] + '\')"; style="cursor: pointer;" title="' + expertInfo[2] + '" target="external">' + expertInfo[1] + '</a>';
				//删除列
				var deleteStr = '<a onclick="deleteExp(\'' + datas[0] + '\');" style="cursor: pointer;"><img style="cursor: pointer;" title="删除专家" src="image/project/del_reviewer.gif" /></a>';
				if(parseInt(cpNum) >= expertProjectMin) {//专家参评数大于等于20，设置为红色
					nameStr = '<span style="color:#FF0000">' + nameStr + '</span>';//姓名
					serialNumber = '<span style="color:#FF0000">' + serialNumber + '</span>';//序号
					expertInfo[0] = '<span style="color:#FF0000">' + expertInfo[0] + '</span>';//学科代码
				}
				if(i == trNum - 1) {//如果是最后一行，下面的线条宽度为0
					htmlStr += '<tr><td style="border: solid #999;border-width: 1px 1px 0 0;">' + serialNumber +'</td>' + '<td style="border: solid #999;border-width: 1px 1px 0 0;">' + nameStr + '</td>' + '<td style="border: solid #999;border-width: 1px 1px 0 0;">' + expertInfo[0] + '</td><td style="border: solid #999;border-width: 1px 0 0 0;">' +  deleteStr + '</td></tr>';
				}else{//否则，下面的线条宽度为1px;
					htmlStr += '<tr><td style="border: solid #999;border-width: 1px 1px 1px 0;">' + serialNumber +'</td>' + '<td style="border: solid #999;border-width: 1px 1px 1px 0;">' + nameStr + '</td>' + '<td style="border: solid #999;border-width: 1px 1px 1px 0;">' + expertInfo[0] + '</td><td style="border: solid #999;border-width: 1px 0 1px 0;">' +  deleteStr + '</td></tr>';
				}
			}
			else {//当 i 超过dataArray的长度，即没有数据，则合并5个td,添加空白行
				htmlStr += '<tr><td colspan="5" style="border: solid #999;border-width: 0 0 0 0;"></td></tr>';
			}
		}
	}else if(!hasData) {//无返回数据，在div正中间显示"尚未分配专家！"
		for(var i = 1; i <= trNum / 2; i++){
			htmlStr += '<tr><td colspan="5" style="border: solid #999;border-width: 0 0 0 0;"></td></tr>';
		}
		htmlStr += '<tr><td  colspan="5" style="border: solid #999;border-width: 0 0 0 0;">尚未分配专家！</td></tr>';
		for(var i = trNum / 2 + 1; i <= trNum; i++){
			htmlStr += '<tr><td colspan="5" style="border: solid #999;border-width: 0 0 0 0;"></td></tr>';
		}
	}
	
	//底部说明文字
	htmlStr += '</table><table width="100%" cellspacing="0" cellpadding="0" height="57px">';
	var typeStr = '每个项目分配专家应 ≤ ' + projectExpertNumber + ' 个';
	if(projectExpertNumber == 9999) {
		typeStr = '请勾选左侧专家树中的专家进行选择';
	}
	htmlStr += '<tr><td style="border: solid #999;border-width: 1px 0 0 0;">&nbsp;提示：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. ' + typeStr + '；<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. 每个专家参评项目应 ≤ ' + expertProjectMax + ' 项；<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3. 显示红色的专家参评数目 ≥ ' + expertProjectMin + '个。</td></tr></table>';
	//将htmlStr填充进id="expList"的div中
	$("#expList").html(htmlStr);
	
	var hididVal = $("input[name='hidid']").val();
	if(hididVal.length > 1) {
		$("input[name='rank'][type='radio'][id='"+ hididVal + "']").attr("checked",true);//设置id=hididVal的radio选中
		$("input[name='hidid']").val('');//清空hidid的值
	}
	getBodyHeight();
}

/**
 * 选择学科(checkbox区域)
 */
function selectDis(){
	var allDisciplines = [];
	$("input[name='disids']:checked").each(function(){
		allDisciplines.push($(this).attr("id"));
	});
	$("input[name='discipline']").val(allDisciplines.join(';'));//选择的学科
}

/**
 * 检索并生成专家树
 * @return {TypeName} 
 * 
 */
function create() {
	var ename = $("input[name='ename']").val();//专家名称
	var uname = $("input[name='uname']").val();//高校名称
	selectDis();//获取勾选中的学科
	var discipline = $("input[name='discipline']").val();
	if((uname == null || uname==0) && (ename == null||ename==0) && (discipline == null||discipline=='') ) {
		alert('请至少选择或填写一个检索条件！');
		return;
	}
//	selectDis();//更新选择后的discipline
	//提交表单，向server发请求，检索专家
	$("#expertTree").attr("action", "award/moeSocial/searchExpert.action");
	$("#expertTree").submit();
}



/**
 * 目的：勾选属于同一个人的所有节点<br>
 * 描述：同一个专家因为有多个所属学科代码，所以在专家树中可能出现多次，<br>
 * 为了区分他在tree中的所属的学科位置，在他的id后面加上0,1,2,3...等用于区分<br>
 * 例如：某个专家id为4028d88a29920f0901299215f71111ab；则在不同的学科类别中表示成：<br>
 * 4028d88a29920f0901299215f71111ab0，4028d88a29920f0901299215f71111ab1，4028d88a29920f0901299215f71111ab2等等
 */
function checkAllSameOne() {
	var str = $("input[name='selectExp']").val();
	var selectExpertIds = str.split(';');
	//同一个专家的多个不同节点，都勾选中，表示是同一个人
	for(var i = 0; i < selectExpertIds.length; i++) {
		for(var j = 0; j < 100; j++) {
			var selectExpertId = selectExpertIds[i] + j;
			var node = zTreeObj.getNodesByParam("id", selectExpertId, null);
   		  	if(node == null || node.length == 0) break;
   		  	node[0].checked = true;//设为选中状态
		  	zTreeObj.updateNode(node[0], true);//更新
		}
	}
}

/**
 * 判断选专家的时候是否合法
 * @param {Object} expertId
 * @return {TypeName} 
 */
function checkLegal(treeNode) {
	var expertId = treeNode.id;//专家Id
	var isChecked = treeNode.checked;//是否勾选上 true:是，false:否
	if (expertId.length > 32) {
		var expText = treeNode.name;
		var cntStr = expText.substr(expText.indexOf('参评') + 2, expText.indexOf('项]') - expText.indexOf('参评') - 2);
		var cnt = parseInt(cntStr);
//		//专家评审项目数60的限制
//		if (isChecked && cnt >= expertProjectMax) {
//			alert('每个专家参评项目应≤' + expertProjectMax + '项');
//			zTreeObj.checkNode(treeNode, false, true);//第二个参数表示取消勾选，第三个参数表示关联父节点
//			return;
//		}
		for ( var i = 0; i < 100; i++) {
			var expertIdi = expertId.substr(0, 32) + i;//构造专家ID的格式为 “原32位id + 编号（0,1,2,3...）”
			var node = zTreeObj.getNodesByParam("id", expertIdi, null);//选择专家树中当前expertIdi对应的专家节点
   		  	if(node == null || node.length == 0) break;
   		  	node[0].checked = isChecked;
   		  	zTreeObj.updateNode(node[0], true);
		}
	} else {
		var expText = treeNode.name;
		var cntStr = expText.substr(expText.indexOf('[') + 1, expText.indexOf(']') - expText.indexOf('[') - 2);
		var cnt = parseInt(cntStr);
		if (cnt > projectExpertNumber && isChecked) {
			alert('每个项目分配专家应≤' + projectExpertNumber + '个！');
			treeNode.checked = false;//取消勾选
			zTreeObj.updateNode(treeNode, true);
			checkAllSameOne();
			return;
		} else {
			var nodes = treeNode.children;//获取treeNode节点的所有子节点
			for (var i=0, len = nodes.length; i < len; i++) {
				var expSubItem = nodes[i].id.substr(0, 32);
				for ( var j = 0; j < 100; j++) {
					var expSubItemj = expSubItem + j;
					var node = zTreeObj.getNodesByParam("id", expSubItemj, null);
		   		  	if(node == null || node.length == 0) break;
		   		  	node[0].checked = isChecked;
		   		  	zTreeObj.updateNode(node[0], true);
				}
			}
		}
	}
	
	// 更新选中的专家信息，更新后重新存入"input[name='selectExp']"中
	var str = $("input[name='selectExp']").val();
	if (isChecked) {
		var nodes = zTreeObj.getCheckedNodes(true);
		for (var i=0, len = nodes.length; i < len; i++) {
			if (nodes[i].id.length >= 32 && str.indexOf(nodes[i].id.substr(0, 32)) < 0) {
				if (str.length == 0) {
					str = nodes[i].id.substr(0, 32);
				} else {
					str = str + ';' + nodes[i].id.substr(0, 32);
				}
			}
		}
	} else {//取消勾选
		var selectExpIds = str.split(';');
		var expSubItem = (expertId.length < 32) ? getChildNodesIds(treeNode) : expertId.substr(0, 32);
		var expSubItems = expSubItem.split(',');
		str = [];
		for ( var i = 0; i < selectExpIds.length; i++) {
			var flag = 0;
			for ( var j = 0; j < expSubItems.length; j++) {
				if (expSubItems[j].length >= 32 && selectExpIds[i].indexOf(expSubItems[j].substr(0, 32)) >= 0) {
					flag = 1;
					break;
				}
			}
			if (flag == 0){
				str.push(selectExpIds[i]);
			}
		}
		str = str.join(";");
	}
	//str表示最终选择的专家的id的集合，如果集合中id的数量小于等于每个项目所需的专家数
	if (str.split(';').length <= projectExpertNumber) {
		$("input[name='selectExp']").val(str);
		awardService.getExpInfo("award", str, getDetail);
	} else {
		alert('每个项目分配专家应≤' + projectExpertNumber + '个！');
		treeNode.checked = false;//取消勾选
		zTreeObj.updateNode(treeNode, true);
		if (expertId.length < 32) {
			var nodes = zTreeObj.getCheckedNodes(true);//获取所有选中的节点
			for (var i=0, len = nodes.length; i < len; i++) {
				var expSubItem = nodes[i].id.substr(0, 32);
				for ( var j = 0; j < 100; j++) {
					var expSubItemj = expSubItem + j;
					var node = zTreeObj.getNodesByParam("id", expSubItemj, null);
					if(node == null || node.length == 0) break;
					node[0].checked = false;
					zTreeObj.updateNode(node[0], true);
				}
			}
		} else {
			var expSubItem = expertId.substr(0, 32);
			for ( var j = 0; j < 100; j++) {
				var expSubItemj = expSubItem + j;
				var node = zTreeObj.getNodesByParam("id", expSubItemj, null);
	   		  	if(node == null || node.length == 0) break;
	   		  	node[0].checked = false;
	   		  	zTreeObj.updateNode(node[0], true);
			}
		}
		checkAllSameOne();
	}
}

/**
 * 获取当前节点的子节点的id集合
 * @param treeNode	当前节点
 * @returns	用","隔开的字符串
 */
function getChildNodesIds(treeNode){
	var allChildNodesIds = [];
	var childNodes = zTreeObj.transformToArray(treeNode.children);
	for(var i = 0, len = childNodes.length; i < len; i++){
		if(childNodes[i].id.length > 7){//过滤掉非Id的数据
			allChildNodesIds.push(childNodes[i].id);
		}
	}
	return allChildNodesIds.join(",");
}

/**
 * 删除专家的选择状态，并更新专家树中多选框的选中
 * @param expertId	待去掉的专家ID
 */
function deleteExp(expertId) {
	var nodes = zTreeObj.getCheckedNodes(true);
	for (var i=0, len = nodes.length; i < len; i++) {
		if(nodes[i].id.indexOf(expertId) >= 0){
			zTreeObj.checkNode(nodes[i], false , true,false);
		}
	}
	updateShow(expertId);
}

/**
 * 在删除专家后，更新专家树中复选框checkbox的选中状态
 * @param expertId	//待排除的专家Id
 */
function updateShow(expertId) {
	var selectedExpertIdstr = $("input[name='selectExp']").val();
	var selectedExpertIds = selectedExpertIdstr.split(';');//得到选择的专家ids
	var toShowExpertIdstr = '';//要显示的专家ID
	for(var i = 0; i < selectedExpertIds.length; i++) {
		if(selectedExpertIds[i].indexOf(expertId) < 0) {//如果当前id不等于待排除的专家Id，则保留
			toShowExpertIdstr += selectedExpertIds[i] + ';';
		}
	}
	toShowExpertIdstr = toShowExpertIdstr.substr(0, toShowExpertIdstr.length-1);//去掉toShowExpertIds字符串中最后一个分号
	//根据所有保留下来的专家ids，进行节点复选框勾选状态的更新
	var toShowExpertIds = toShowExpertIdstr.split(';');
	for(var i = 0; i < toShowExpertIds.length; i++){
		for ( var j = 0; j < 100; j++) {
			var expSubItemj = toShowExpertIds[i] + j;
			var node = zTreeObj.getNodesByParam("id", expSubItemj, null);
			if(node == null || node.length == 0) break;
			node[0].checked = true;
			zTreeObj.updateNode(node[0], true);
		}
	}
	//将所有保留下来的专家ids存入页面的"input[name='selectExp']"中
	$("input[name='selectExp']").val(toShowExpertIdstr);
	awardService.getExpInfo("award", toShowExpertIdstr, getDetail);
}

/**
 * 右侧详情中专家排序
 * @param act 1：升序，-1：降序
 */
function reRank(act) {
	var expId;
	var rankRadio = $("input[name='rank'][type='radio']:checked");//获取选中的radio
	if(rankRadio.val() == null){
        alert("请选择一个专家！");
        return false;
    }else{
    	expId = rankRadio.attr("id");
    }
	$("input[name='hidid']").val(expId);
	var selectExpIds = $("input[name='selectExp']").val().split(';');
	for(var i = 0; i < selectExpIds.length; i++) {
		if(expId == selectExpIds[i]) {
			if(act == 1 && i > 0) {
				var tmp = selectExpIds[i - 1];
				selectExpIds[i - 1] = selectExpIds[i];
				selectExpIds[i] = tmp;
				break;
			}
			else if(act == -1 && i < selectExpIds.length - 1) {
				var tmp = selectExpIds[i + 1];
				selectExpIds[i + 1] = selectExpIds[i];
				selectExpIds[i] = tmp;
				break;
			}
		}
	}
	var selectExpIdStr = selectExpIds.join(';');//组装成字符串
	$("input[name='selectExp']").val(selectExpIdStr);
	awardService.getExpInfo("award", selectExpIdStr, getDetail);
}

/**
 * 常规页面，查看专家详情
 * @param {Object} expertId 专家id
 * @return {TypeName} 
 */
function normalShowExp(expertId) {
	if(expertId.length < 32) return; //如果expertId长度小于32位，为错误数据，直接返回
	expertId = expertId.substr(0, 32);//格式化专家ID
	//更新页面中id为expertTreeBk的表单提交的action参数
	var url = 'expert/toView.action?entityId=' + expertId + '&listType=0';
	$("#expertTreeBk").attr("action", url);
	$("#expertTreeBk").attr("target", '_blank');
	$("#expertTreeBk").submit();
}

/**
 * 弹出层查看专家详情，提供的功能有：<br>
 * 1、专家详情查看；
 * 2、查看页面提供专家的项目退评功能：执行的是GeneralAction中的deleteExpertProjects()方法。
 * @param {Object} expertId 专家id
 * @return {TypeName} 
 */
function showExp(expertId) {
	if(expertId.length < 32) return; //如果expertId长度小于32位，为错误数据，直接返回
	expertId = expertId.substr(0, 32);//格式化专家ID
	//弹出层调用
	popExpertDetail({
		title : "查看专家评审项目",
		src : "expert/toView.action?listType=3&viewType=1&entityId=" + expertId,
		callBack : function(result){//弹出层回调函数，这里的回调函数提供的是退评的功能，并且完成退评后需要进行数据更新
			var id = result.expertId;
			var detailStr = '';
			var node = zTreeObj.getNodesByParam("id", id, null);//根据id获取专家树中的专家节点
   		  	if(node == null || node.length == 0) {//如果节点未找到，则专家id补零，再进行寻找
   		  		node = zTreeObj.getNodesByParam("id", id + '0', null);
   		  	}
   		  	detailStr = node[0].name;//获取找到的专家节点的信息
//			if(tree2.getIndexById(id) == null) {
//				detailStr = tree2.getItemText(id + '0');
//			} else {
//				detailStr = tree2.getItemText(id);
//			}
			//解析专家节点信息detailStr字符串，更新参评项目总数
			var totalCnt = parseInt(detailStr.substr(detailStr.indexOf('参评') + 2, detailStr.indexOf('项]') - detailStr.indexOf('参评') - 2));
			if(totalCnt - result.deletedProjectCnt >= 0)//该专家原来参评总数 减去 这次退评的项目数量
				totalCnt = totalCnt - result.deletedProjectCnt;
			//更新该专家其他节点的name信息：主要是指改变后的参评项目总数
			var newNode = zTreeObj.getNodesByParam("id", id, null);
			if(newNode == null || newNode.length == 0) {//如果通过原始专家id未找到节点，则专家id补零，再进行寻找
				for ( var i = 0; i < 100; i++) {
					var newid = id + i;
					newNode = zTreeObj.getNodesByParam("id", newid, null);
					if(newNode == null || newNode.length == 0) {//没有其他节点，进入下一个
						break;
					}else{//节点存在，更新节点的name信息
						newNode[0].name = detailStr.substr(0,detailStr.indexOf('参评') + 2) + totalCnt + '项]';
						zTreeObj.updateNode(newNode[0], true);
					}
				}
			} else {//如果通过原始专家id找到了节点，直接更新节点的name信息
				newNode[0].name = detailStr.substr(0,detailStr.indexOf('参评') + 2) + totalCnt + '项]';
				zTreeObj.updateNode(newNode[0], true);
			}
			//如果退评的项目id中包含当前父页面的项目ID，则删除父页面专家树中该专家的选中状态
			var pojid = $("#entityId").val();//从弹出层父页面，获取项目id
			if(pojid != null && pojid != '' && result.projectId.indexOf(pojid) >= 0) {
				deleteExp(result.expertId);
			}
		}
	});
}

/**
 * div收起或展开
 * @param nodeId
 */
function toggle_view(nodeId) {
	var currentNode = document.getElementById(nodeId);
	var div = currentNode.nextElementSibling || currentNode.nextSibling ;
	var lastChild =  currentNode.firstElementChild || currentNode.firstChild;
	if (div) {
		if (lastChild.className == 'img_dis') {//如果当前已展开
			div.style.display = 'none';
			lastChild.className = 'img_undis';
		} else if (lastChild.className == 'img_undis') {//如果当前未展开
			div.style.display = 'block';
			lastChild.className = 'img_dis';
		}
	}
	getBodyHeight();
}

/**
 * 计算iframe高度
 */
function getBodyHeight() {
	var iframe = $('#expertTreeFrame', window.parent.document);
	//chrome浏览器
	if($.browser.webkit == true) {
		var height = document.body.scrollHeight;
		var i = height;
		iframe.css("height",  height + 50 + 'px');
		iframe.height(i);
	} 
	//firefox、ie浏览器
	else {
		iframe.css("height",  document.body.scrollHeight + 20 + 'px');
	}
}