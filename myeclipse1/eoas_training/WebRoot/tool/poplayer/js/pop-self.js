// =================================================================================================
// ================================弹出层统一注册====================================================
// =================================================================================================

/************************************公共部分********************************************/





/**
 * 弹出层选择
 * 参数赋值{"type":?, "label":?, "proType":?, "title":*, ...}
 * @param {Object} args 参数对象
 * @param {number} type 弹层选择的类别
 * @param {number} label 是否账号模块调用
 * @param {number} proType 项目类型
 * @param {string} title 层标题
 */
function popSelect(args) {
	var title = "选择学科代码";//标题
	new top.PopLayer({"title" : title, "src" : "expert/toSelectDiscipline.action?entityId=" + args.inData.id,
		"document" : top.document, "inData" : args.inData, "callBack" : args.callBack});
};

/**
 * 编辑弹出层
 * 参数赋值{"type":?, "label":?, "proType":?, "title":*, ...}
 * @param {Object} args 参数对象
 * @param {number} type 弹层编辑的类别
 * @param {number} label 是否账号模块调用
 * @param {number} proType 项目类型
 * @param {string} title 层标题
 */
function popEdit(args) {
	var titleArray = ["编辑结项成果数量"];//默认标题
	var type = args.type || 0;//选择类别
	//var label = args.label || 0;//是否检测账号
	//var proType = args.proType || 0;//项目类别
	var title = args.title ? args.title : titleArray[type - 1];//标题
	var isApplyNoevaluation = args.isApplyNoevaluation || 0;//是否申请免鉴定
	if(type == 1) {
		new top.PopLayer({"title" : title, "src" : "editProductType/toEdit.action?isApplyNoevaluation=" + isApplyNoevaluation + "&entityId=" + args.inData.id, 
			"document" : top.document, "inData" : args.inData, "callBack" : args.callBack});
	}
};

/**
 * 弹出层选择多个
 * 参数赋值{"type":?, "label":?, "proType":?, "title":*, ...}
 * @param {Object} args 参数对象
 * @param {number} type 弹层选择的类别
 * @param {number} label 是否账号模块调用
 * @param {number} proType 项目类型
 * @param {string} title 层标题
 */
function popSelectGroup(args) {
	var titleArray = [
		"选择部级机构", "选择省级机构", "选择校级机构"];//默认标题
	var type = args.type || 0;//选择类别
	var label = args.label || 0;//是否检测账号
	var title = args.title ? args.title : titleArray[type - 1];//标题
	if(type == 1) {
		new top.PopLayer({"title" : title, "src" : "selectMinistryGroup/toList.action?label=" + label, 
			"document" : top.document, "inData" : args.inData, "outData" : args.outData, "callBack" : args.callBack});
	} else if (type == 2) {
		new top.PopLayer({"title" : title, "src" : "selectProvinceGroup/toList.action?label=" + label, 
			"document" : top.document, "inData" : args.inData, "outData" : args.outData,  "callBack" : args.callBack});
	} else if (type == 3) {
		new top.PopLayer({"title" : title, "src" : "selectUniversityGroup/toList.action?label=" + label, 
			"document" : top.document, "inData" : args.inData, "outData" : args.outData,  "callBack" : args.callBack});
	}
};

/************************************系统管理********************************************/
/**
 * 弹出层切换服务器
 */
function popSwitchDB(args){
	new top.PopLayer({"title" : "切换服务器", "src" : args.src, 
		"document" : top.document, "inData" : args.inData, "callBack" : args.callBack});
};

/**
 * 弹出层切换账号
 */
function popSwitchAccount(args){
	new top.PopLayer({"title" : "切换账号", "src" : args.src, 
		"document" : top.document, "inData" : args.inData, "callBack" : args.callBack});
};

/**
 * 弹出层启用账号
 */
function popAccountEnable(args){
	new top.PopLayer({"title" : "启用账号", "src" : args.src, 
		"document" : top.document, "callBack" : args.callBack});
};

/**
 * 弹出层账号分配角色
 */
function popAccountDistrict(args){
	new top.PopLayer({"title" : "分配角色", "src" : args.src, 
		"document" : top.document, "callBack" : args.callBack});
};

/**
 * 弹出层账号修改密码
 */
function popPasswordModify(args){
	new top.PopLayer({"title" : "修改密码", "src" : args.src, 
		"document" : top.document, "callBack" : args.callBack});
};

/**
 * 弹出层添加账号
 */
function popAccountAdd(args){
	new top.PopLayer({"title" : args.title || "添加账号", "src" : args.src, 
		"document" : top.document, "inData" : args.inData, "callBack" : args.callBack});
};

/**
 * 弹出层查看日志信息
 */
function popLog(id){
	new top.PopLayer({"title" : "查看日志", "src" : "view/viewLog.action?entityId=" + id, 
		"document" : top.document});
};

/**
 * 弹出层系统日志统计
 */
function popLogStat(){
	new top.PopLayer({"title" : "日志统计信息", "src" : basePath + "log/toStatistic.action", 
		"document" : top.document});
};

/**
 * 弹出层审核留言
 */
function popMessageAudit(args) {
	new top.PopLayer({"title" : "审核留言", "src" : "message/inner/toAudit.action", 
		"document" : top.document, "callBack" : args.callBack});
};

/**
 * 弹出层添加外网留言
 */
function popAddMessage(args) {
	new top.PopLayer({"title" : "添加留言", "src" : args.src, 
		"document" : top.document, "callBack" : args.callBack});
};

/**
 * 弹出层备忘提醒
 */
function popMemo(args) {
	new top.PopLayer({"title" : "备忘提醒", "src" : "view/viewMemo.action?entityId=2", 
		"isModal" : false,"document" : top.document});
};

/**
 * 弹出层备忘倒计时提醒
 */
function popMemoTimeCountDown(args) {
	new top.PopLayer({"title" : "倒计时", "src" : arg.src, 
		"isModal" : true,"document" : top.document});
};
/**
 * 弹出层下载标准xml数据
 */
function popDownloadXML(){
	new top.PopLayer({"title" : "下载标准xml数据", "src" : basePath + "system/config/toDownloadXML.action", 
		"document" : top.document});
};
/************************************项目管理********************************************/
/**
 * 弹出层操作项目
 * @param {Object} args
 */
function popProjectOperation(args) {
	new top.PopLayer({"title" : args.title, "src" : args.src, "inData" : args.inData,
		"document" : top.document, "callBack" : args.callBack});
};
/**
 * 弹出层查看需中检数据
 * @param {Object} args
 */
function popMidRequired(title, src) {
	new top.PopLayer({"title" : title, "src" : src, "document" : top.document});
};
/************************************经费管理********************************************/
/**
 * 弹出层添加申请经费概算明细
 * @param {Object} args
 */
function popAddApplyFee(args) {
	new top.PopLayer({"title" : args.title, "src" : args.src, "inData" : args.inData,
		"document" : top.document, "callBack" : args.callBack});
};
/**
 * 弹出层添加立项经费预算明细
 * @param {Object} args
 */
function popAddGrantedFee(args) {
	new top.PopLayer({"title" : args.title, "src" : args.src, "inData" : args.inData,
		"document" : top.document, "callBack" : args.callBack});
};
/**
 * 弹出层添加年检经费结算明细
 * @param {Object} args
 */
function popAddAnnFee(args) {
	new top.PopLayer({"title" : args.title, "src" : args.src, "inData" : args.inData,
		"document" : top.document, "callBack" : args.callBack});
};
/**
 * 弹出层添加中检经费结算明细
 * @param {Object} args
 */
function popAddMidFee(args) {
	new top.PopLayer({"title" : args.title, "src" : args.src, "inData" : args.inData,
		"document" : top.document, "callBack" : args.callBack});
};
/**
 * 弹出层添加结项经费结算明细
 * @param {Object} args
 */
function popAddEndFee(args) {
	new top.PopLayer({"title" : args.title, "src" : args.src, "inData" : args.inData,
		"document" : top.document, "callBack" : args.callBack});
};
/**
 * 弹出层添加结项经费结算明细
 * @param {Object} args
 */
function popAddVarFee(args) {
	new top.PopLayer({"title" : args.title, "src" : args.src, "inData" : args.inData,
		"document" : top.document, "callBack" : args.callBack});
};
/**
 * 弹出层修改经费
 * @param {Object} args
 */
function popModifyFee(args) {
	new top.PopLayer({"title" : args.title, "src" : args.src, 
		"document" : top.document, "callBack" : args.callBack});
};
/**
 * 弹出层添加经费拨款清单
 * @param {Object} args
 */
function popAddFundList(args) {
	new top.PopLayer({"title" : args.title, "src" : args.src, "inData" : args.inData,
		"document" : top.document, "callBack" : args.callBack});
};

/**
 * 弹出层修改经费拨款清单
 * @param {Object} args
 */
function popModifyFundList(args) {
	new top.PopLayer({"title" : args.title, "src" : args.src, "inData" : args.inData,
		"document" : top.document, "callBack" : args.callBack});
};

/************************************成果管理********************************************/
/**
 * 弹出层操作成果
 * @param {Object} args
 */
function popProductOperation(args){
	new top.PopLayer({"title" : args.title, "src" : args.src, 
		"document" : top.document, "callBack" : args.callBack, "inData" : args.inData});
};

/**
 * 弹出层查看团队信息
 * @param {Object} id 团队ID
 */
function popOrganization(id){
	new top.PopLayer({"title" : "查看团队信息", "src" : "view/viewOrganization.action?entityId=" + id, 
		"document" : top.document});
};

/*********************************************奖励管理***************************************************/
/**
 * 弹出层操作奖励
 * @param {Object} args
 */
function popAwardOperation(args){
	new top.PopLayer({"title" : args.title, "src" : args.src, 
		"document" : top.document, "callBack" : args.callBack});
};

/********************************************************************************************************/
/**
 * 学科门类及依托学科处理
 * @param {Object} xx 弹出窗口返回值
 * @param {Object} hiddenId 属性对应隐藏域的code/name
 * @param {Object} wholeDiv 整个显示区的id
 */
function doWithXX(xx,hiddenId,wholeDiv){
	if(xx != null && xx != "" && xx !='null') {
		document.getElementById(hiddenId).value = xx; //(隐藏域id)编号组成的字符串，传往后台
		var names = xx.split("; ");
		var xs2=""; //包含删除图片
		if(names.length > 1){
			var i=0;
			for (i=0;i<names.length-1;i++){
				xs2 += names[i]+"<a onclick='testA(this,\""+hiddenId+"\",\""+wholeDiv+"\")' id='"+names[i]+"' title='点击删除' href='javascript:void(0)'><IMG src='image/del.gif' name='image'/></a>"+"; ";
			}
			if(i=names.length-1){
				xs2 += names[i]+"<a onclick='testA(this,\""+hiddenId+"\",\""+wholeDiv+"\")' id='"+names[i]+"' title='点击删除' href='javascript:void(0)'><IMG src='image/del.gif' name='image'/></a>";
			}
		}
		if(names.length == 1){
			xs2 += names[0]+"<a onclick='testA(this,\""+hiddenId+"\",\""+wholeDiv+"\")' id='"+names[0]+"' title='点击删除' href='javascript:void(0)'><IMG src='image/del.gif' name='image'/></a>";
		}
		document.getElementById(wholeDiv).innerHTML=xs2;
		document.getElementById(wholeDiv).style.display="block";
	}else{
		document.getElementById(hiddenId).value = "";
		document.getElementById(wholeDiv).innerHTML="";
		document.getElementById(wholeDiv).style.display="block";
	}
};

/**
 * 点击叉形图片删除响应事件
 * @param {Object} a a链接对象
 * @param {Object} hiddenId 属性对应隐藏域的code/name
 * @param {Object} wholeDiv 整个显示区的id
 * @return {TypeName} 
 */
function testA(a,hiddenId,wholeDiv){
	var discipline = document.getElementById(hiddenId).value;
	var c = document.getElementById(wholeDiv).innerHTML; //带图片的名称显示
	var arr1 = discipline.split("; "); //code/name数组
	var arr3 = c.split("; "); //带图片的innerHTML
	if(arr1.length>0){
		for(var i = 0; i < arr1.length; i++){
			if(arr1[i] == a.id){
				arr1.splice(i,1);
				arr3.splice(i,1);
				break;
			}
		}
	}
	if(arr1.length>0) {
		document.getElementById(hiddenId).value = arr1.join("; ");
		document.getElementById(wholeDiv).innerHTML = arr3.join("; ");
	} else {
		document.getElementById(hiddenId).value = "";
		document.getElementById(wholeDiv).innerHTML = "";
	}
	return false;
};

/**
 * 弹出层合并高校
 * type: 1-高校 2-院系 3-基地
 */
function popMerge(args){
	var type = args.type || 0;//合并类别
	if(type == 1){
		new top.PopLayer({"title" : "合并高校", "src" : args.src, 
			"document" : top.document, "callBack" : args.callBack});
	} else if(type == 2){
		new top.PopLayer({"title" : "合并院系", "src" : args.src, 
			"document" : top.document, "callBack" : args.callBack});
	} else if(type == 3){
		new top.PopLayer({"title" : "合并基地", "src" : args.src, 
			"document" : top.document, "callBack" : args.callBack});
	}
};

function popMergePerson(args){
	var type = args.type || 0;//合并类别
	if(type == 1){
		new top.PopLayer({"title" : "合并高校", "src" : args.src, 
			"document" : top.document, "callBack" : args.callBack});
	} else if(type == 2){
		new top.PopLayer({"title" : "合并院系", "src" : args.src, 
			"document" : top.document, "callBack" : args.callBack});
	} else if(type == 3){
		new top.PopLayer({"title" : "合并基地", "src" : args.src, 
			"document" : top.document, "callBack" : args.callBack});
	}
};

/**
 * 导入常规统计弹出层标题编辑（定制统计分析）
 */
function popTitleCreate(args){
	new top.PopLayer({"title" : "编辑标题", "src" : args.src, 
		"document" : top.document, "inData" : args.inData, "callBack" : args.callBack});
};

/************************************************内部管理**********************************************/
/**
 * 弹出层职位选择文件模板
 * @param {Object} args
 */
function popSelectTemplate(args){
	new top.PopLayer({"title": args.title, "src": args.src,
		"document": top.document, "inData": args.inData, "callBack":　args.callBack});
}

/**
 * 职位选择模板多选处理
 * @param {Object} xx 弹出窗口返回值
 * @param {Object} xxId 弹出窗口返回值
 * @param {Object} hiddenId 属性对应隐藏域的name
 * @param {Object} hiddenIdId 属性对应隐藏域的nameId
 * @param {Object} wholeDiv 整个显示区的id
 */
function doWithId(xx,xxId,hiddenId,hiddenIdId,wholeDiv){
	if(xx != null && xx != "" && xx !='null') {
		document.getElementById(hiddenId).value = xx; //(name隐藏域id)编号组成的字符串
		document.getElementById(hiddenIdId).value = xxId; //(name隐藏域id)编号组成的字符串
		var names = xx.split("; ");
		var ids = xxId.split("; ");
		var xs2=""; //包含删除图片
		if(names.length > 1){
			var i=0;
			for (i=0;i<names.length-1;i++){
				xs2 += names[i]+"<a onclick='testB(this,\""+hiddenId+"\",\""+hiddenIdId+"\",\""+wholeDiv+"\")' id='"+names[i]+"' title='点击删除' href='javascript:void(0)'><IMG src='image/del.gif' name='image'/></a>"+"; ";
			}
			if(i=names.length-1){
				xs2 += names[i]+"<a onclick='testB(this,\""+hiddenId+"\",\""+hiddenIdId+"\",\""+wholeDiv+"\")' id='"+names[i]+"' title='点击删除' href='javascript:void(0)'><IMG src='image/del.gif' name='image'/></a>";
			}
		}
		if(names.length == 1){
			xs2 += names[0]+"<a onclick='testB(this,\""+hiddenId+"\",\""+hiddenIdId+"\",\""+wholeDiv+"\")' id='"+names[0]+"' title='点击删除' href='javascript:void(0)'><IMG src='image/del.gif' name='image'/></a>";
		}
		document.getElementById(wholeDiv).innerHTML=xs2;
		document.getElementById(wholeDiv).style.display="inline-block";
	}else{
		document.getElementById(hiddenId).value = "";
		document.getElementById(hiddenIdId).value = "";
		document.getElementById(wholeDiv).innerHTML="";
		document.getElementById(wholeDiv).style.display="inline-block";
	}
};

/**
 * 点击叉形图片删除响应事件
 * @param {Object} a a链接对象
 * @param {Object} hiddenId 属性对应隐藏域的name
 * @param {Object} hiddenIdId 属性对应隐藏域的name的Id
 * @param {Object} wholeDiv 整个显示区的id
 * @return {TypeName} 
 */
function testB(a,hiddenId,hiddenIdId,wholeDiv){
	var name = document.getElementById(hiddenId).value;
	var nameId = document.getElementById(hiddenIdId).value;
	var c = document.getElementById(wholeDiv).innerHTML; //带图片的名称显示
	var arr1 = name.split("; "); //name数组
	var arr2 = nameId.split("; "); //name的Id数组
	var arr3 = c.split("; "); //带图片的innerHTML
	if(arr1.length>0){
		for(var i = 0; i < arr1.length; i++){
			if(arr1[i] == a.id){
				arr1.splice(i,1);
				arr2.splice(i,1)
				arr3.splice(i,1);
				break;
			}
		}
	}
	if(arr1.length>0) {
		document.getElementById(hiddenId).value = arr1.join("; ");
		document.getElementById(hiddenIdId).value = arr2.join("; ");
		document.getElementById(wholeDiv).innerHTML = arr3.join("; ");
	} else {
		document.getElementById(hiddenId).value = "";
		document.getElementById(hiddenIdId).value = "";
		document.getElementById(wholeDiv).innerHTML = "";
	}
	return false;
};