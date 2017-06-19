// ========================================================================
// 文件名：common.js
//
// 文件说明：
//     本文件主要实现诸如全选、删除提示等功能
// 
// 历史记录：
// 2010-01-28 龚凡  创建文件。
// 2010-03-04 周中坚  添加函数。
// ========================================================================

var msg = new Array(99);
msg[0] = "请选择要删除的项！"
msg[1] = "您确定要删除选中项么？"
msg[2] = "无可删除项！"
msg[3] = "关键词请勿超过20个字符！"
msg[4] = "此处不得为空 ！"
msg[5] = "请勿超过100000000个字符 ！"
msg[6] = "请选择要发送的邮件！"
msg[7] = "您确定要重新发送选中的邮件么？"
msg[8] = "请选择要暂停发送的邮件！"
msg[9] = "您确定要暂停发送选中的邮件么？"
msg[10] = "请选择要备份的记录！"
msg[11] = "您确定要备份选中项么？"
	
// ==============================================================
// 函数名：addLoadEvent
// 函数描述：定义某些操作完成js事件的重新加载
// 返回值：无
// by Simon Willison 
// ==============================================================
function addLoadEvent(func) {
var oldonload = window.onload;
if (typeof window.onload != 'function') {
	window.onload = func;
} else {
	window.onload = function() {
	if (oldonload) {
		oldonload();
	}
	func();
	}
}
}

// ==============================================================
// 方法名: checkAll
// 方法描述: 本方法主要实现列表复选框的全选与不选
// 返回值：无
// ==============================================================
function checkAll(e, // 复选框选中状态
		ob) // 复选框名称
{
	// 获取复选框对象数组
	var sel_box = document.getElementsByName(ob);
	// 设置复选框状态
	for ( var i = 0; i < sel_box.length; i += 1) {
//		if(!sel_box[i].checked){
		sel_box[i].checked = e;
//		writeIdsAndNames(sel_box[i]);
//	}
		}
}

// ==============================================================
// 方法名: count
// 方法描述: 本方法主要实现列表复选框选中个数的统计
// 返回值：
// cnt: 复选框被选中的个数
// ==============================================================
function count(ob) // 复选框名称
{
	// 获取复选框对象数组
	var sel_box = document.getElementsByName(ob);
	// 选中个数
	var cnt = 0;
	// 统计
	for ( var i = 0; i < sel_box.length; i += 1) {
		if (sel_box[i].checked) {
			cnt += 1;
		}
	}
	return cnt;
}

// ==============================================================
// 方法名: delSelected
// 方法描述: 本方法主要实现列表的群删功能
// 返回值：
// false: 不提交请求
// ==============================================================
function delSelected(ob, // 复选框名称
		ff) // 表单form的名称
{
	// 先判断是否有复选框被选中
	var cnt = count(ob);
	if (cnt == 0) {
		alert(msg[0]);
		return false;
	} else {
		if (confirm(msg[1])) {
			document.forms[ff].submit();
		}
	}
}

// ==============================================================
// 方法名: backupSelected
// 方法描述: 本方法主要实现列表的备份功能
// 返回值：
// false: 不提交请求
// ==============================================================
function backupSelected(ob,ff)
{
	var cnt = count(ob);
	if (cnt == 0){
		alert(msg[10]);
		return false;
	}else {
		if (confirm(msg[11])) {
			document.forms[ff].action = "report/backupReport.action";
			document.forms[ff].submit();
//			return false;
		}
	}
	
}
// ==============================================================
// 方法名: delRow
// 方法描述: 本方法主要实现删除列表最后一行
// 返回值：无
// ==============================================================
function delRow(ob) //列表的ID
{
	// 获取table dom
	var idTB = document.getElementById(ob);
	// 获取当前表格行数
	var i = idTB.rows.length;
	if (i <= 1) {
		alert(msg[2]);
	}
	else {
		idTB.deleteRow(i - 1);
	}
}

// ==============================================================
// 方法名: addNum
// 方法描述: 本方法主要实现列表序号的重写
// 返回值：无
// ==============================================================
function addNum(ob) // 列表的ID
{
	// 获取列表对象
	var idTB = document.getElementById(ob);
	// 开始编号
	for ( var i = 1; i < idTB.rows.length; i+=1) {
		idTB.rows[i].cells[0].innerHTML = i.toString();
	}
}

// ==============================================================
// 方法名: valid
// 方法描述: 本方法主要实现列表页面检索的输入框
// 返回值：
// false: 不提交请求
// ==============================================================
function valid(ob) {
	var cont = document.getElementById(ob).value;
	if (cont.length > 20) {
		alert(msg[3]);
		return false;
	}
}

// ==============================================================
// 方法名: validFCK
// 方法描述: 本方法主要实现FCk输入框的非空和字数验证
// 返回值：
// false: 不提交请求
// ==============================================================
function ValidFCK(EditorName) {
	var cont = FCKeditorAPI.GetInstance(EditorName);
	var str = cont.GetXHTML();
	var trimcont = str.replace(/<[^>]*>|<\/[^>]*>/gm, "");
	//将FCK编辑器中的html元素trim再计算长度
	if (trimcont === "") {
		alert(msg[4]);
		return false;
	} else if (trimcont.length > 100000000) {
		alert(msg[5]);
		return false;
	}
}

// ==============================================================
// 方法名: toggle
// 方法描述: 本方法主要实现点一下出现，再点一下隐藏的效果
// 返回值：无
// ==============================================================
function toggle(Id) {
	var Div = document.getElementById(Id);
	if (Div) {
		var display = Div.style.display;
		if (display == 'none') {
			Div.style.display = 'block';
		} else if (display == 'block') {
			Div.style.display = 'none';
		}
	}
} 

//==============================================================
//方法名: restrictTrHeight
//方法描述: 自动限制行高
//返回值：
//false: 无
//aName:  a标签name
//trHeight: 需要限制的高度
//==============================================================
function restrictTrHeight(aName, trHeight ) {
	var aTags = document.getElementsByName(aName); // a标签元素个数
	var lineNumber = -1;// 判断显示行数
	for(i = 0; i * 18 < trHeight; i++){
		lineNumber++;
	}
	var width = aTags[0].parentNode.offsetWidth;//显示宽度
	numEn = 2 * lineNumber * width / 12 - 3; //可以显示的最多英文字符个数（font-size设为12）
	for ( var i = 0; i < aTags.length; i++) {
		aTags[i].innerHTML = aTags[i].id;// 初始化显示内容
		var len = numEn;
		var textHeight = aTags[i].parentNode.offsetHeight; // 文本显示的实际高度
		var cnt = 0;
		while (textHeight > trHeight && cnt < 200) { // 检测文本显示的实际高度是否大于
			len = len - 1;
			cnt++;
			aTags[i].innerHTML = aTags[i].id.substr(0, len) + '...';
			textHeight = aTags[i].parentNode.offsetHeight;
		}
	}
}

/**
 * 用于转换时间格式
 * 例如：
 * alert(new Date().format("yyyy-MM-dd"));
 * alert(new Date("january 12 2008 11:12:30").format("yyyy-MM-dd hh:mm:ss"));
 * @param {Object} format
 */
Date.prototype.format = function(format){
	var o = {
		"M+": this.getMonth() + 1, //month
		"d+": this.getDate(), //day
		"h+": this.getHours(), //hour
		"m+": this.getMinutes(), //minute
		"s+": this.getSeconds(), //second
		"q+": Math.floor((this.getMonth() + 3) / 3), //quarter
		"S": this.getMilliseconds() //millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

/**
 * 为字符串增加trim函数
 */
if(typeof String.prototype.trim !== 'function') {
	String.prototype.trim = function() {
		return this.replace(/^\s+|\s+$/g, '');
	}
}

/**
 * 列表复选框绑定内容对表头的全选控制
 */
$(document).ready(function() {
	var list_table = $("#list_table").find("input[type='checkbox']");// 当前页列表内容
	var list_table_length = list_table.length;// 当前页记录条数
	if (list_table_length != 0) {// 当前页记录不为空
		var list_table_entityName = list_table.eq(0).attr("name");// 当前页复选框名称
		list_table.bind("click", function() {
			var cnt = count(list_table_entityName);;// 当前已选中的个数
			if (this.checked) {// 当有项被选中时，判断当前是否已全选了
				if (cnt == list_table_length) {
					$("input[name='check']").attr("checked", true);
				}
			} else {// 当有项撤销选中时，判断头是否处于非全选状态
				$("input[name='check']").attr("checked", false);
			}
		});
	}
});

function getData(){
	var url = "refreshFuncArea/refreshRemarkNum.action";
	var params = null;
	jQuery.post(url, params, callbackFun, 'json');
};

function callbackFun(data){
	var remarkNum = data.remarkNum;
	var messageNum = data.messageNum;
	var undoAppNum = data.undoAppNum;
	var onlineUserNum = data.totalOnline;
	var usernameArray = data.onlineUsers;

	$("#onlineUserNum").html(onlineUserNum);
	$("#remarkNum").html(remarkNum);
	$("#undoAppNum").html(undoAppNum);
	$("#messageNum").html(messageNum);
	
	
	id = document.getElementById("onlineuserlist");
	var len = id.rows.length;
	// 重新生成tabel
	// 先删除原有所有行
	for (var i = len - 1; i >= 0; i--) {
		id.deleteRow(i);
	}
	// 再根据返回值添加行
	for (var i = 0; i < usernameArray.length; i++) {
		var oTR = id.insertRow(i);
		var oTD = oTR.insertCell(0);
		oTD.width = "20";
		oTD.align = "right";
		oTD.innerHTML = "<img src='image/onlineuser.gif' />";
		var oTD = oTR.insertCell(1);
		oTD.innerHTML = usernameArray[i];
	}
}