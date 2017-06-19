/**
 * 弹层初始化
 */
$(function(){
	//该层对象
	thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
	
	//初始化数据(区分单选和多选)
	if($("label[name='checkboxPop']").size() > 0){thisPopLayer.outData = thisPopLayer.inData;}
	else{thisPopLayer.outData = {data : thisPopLayer.inData};}
	
	//查看确定关闭
	$("#okclosebutton").click(function(){
		thisPopLayer.destroy();
	});
	
	//取消关闭
	$("#cancel").live("click", function(){
		thisPopLayer.destroy();
	});
	
	//确定回调
	$("#confirm").live("click", function(){
		thisPopLayer.callBack(thisPopLayer.outData);
		thisPopLayer.destroy();
	});
	
	// 点击取消radio的选中
	$("#cancelSelectedRadio").live('click', function(){
		cancelSelected(0);
	});
	
	// 点击取消checkbox的选中
	$("#cancelSelectedCheckbox").live('click', function(){
		cancelSelected(1);
	});
	
	//单选列表默认回传值、可重写
	$("input[type='radio']", $("#list_container")).live("click", function(){
		writeIdAndName(this);
	});
	
	// 绑定复选框选中事件
	$(".checkbox_select", $("#list_container")).live("click", function() {
		writeIdsAndNames(this);
	});
});

/**
 * 设置默认选中
 * 在进入页面或者翻页时调用
 */
function defaultSelected(){
	var interval = setInterval(function(){
		if($("#list_container").css("display") == "block"){
			if($("input[type='radio']", $("#list_container")).length > 0){//单选
				defaultSelect(thisPopLayer.inData);
				clearInterval(interval);
			} else if($("input[type='checkbox']", $("#list_container")).length > 0){//复选
				defaultSelectGroup(thisPopLayer.inData);
				clearInterval(interval);
			}
		}
	}, 5);
};

/**
 * 	弹出层列表显示时，设置底部显示已选项的名称
 * 	将所选的机构或人员名称截取合适的长度显示在"已选择"后面
 * 	@param name已选项的名称
 */
function showTrimedName(name){
	var trimName = (name.length > 71) ? name.substring(0, 70) + "……" : name;
	if($("#trimedName").attr('name') == "radioPop"){
		$("#trimedName").empty().append(trimName).append('<a id="cancelSelectedRadio" href="javascript:void(0)" title="点击删除" ><img id="image" src="image/del.gif" /></a>');
	} else if ($("#trimedName").attr('name') == "checkboxPop"){
		$("#trimedName").empty().append(trimName).append('<a id="cancelSelectedCheckbox" href="javascript:void(0)" title="点击删除" ><img id="image" src="image/del.gif" /></a>');
	}
};

/**
 *	弹出层单选列表显示时，设置默认选中
 * 	data.id	默认选中id
 * 	data.name 默认选中name
 */
function defaultSelect(data) {
	if(data && data.name && data.id){
		showTrimedName(data.name);// 将默认的名字显示在选择页面上
		$("input[type='radio'][value=" + data.id + "]").attr("checked", true);
	}
};

/**
 * 弹出层多选列表显示时，设置默认选中
 */
function defaultSelectGroup(entity) {
	var id = entity.data.id;// 读取默认选中的ID
	var name = entity.data.name;// 读取默认选中的NAME
	if(name != null && name != "") {
		name = name.join("; ");
		showTrimedName(name);// 将默认的名字显示在选择页面上
	}
	if (id != null && id != "") {
		var id_length = id.length;
		$("input[type='checkbox']").each(function() {
			for (var i = 0; i < id_length; i++) {
				if (this.value == id[i]) {
					this.checked = true;
					break;
				}
			}
		});
	}
};

/**
 * 叉按钮点击事件，点击移除
 * 移除所有radio或者chackbox的checked属性，清空trimedName区域信息
 * @param type 0: radio; 1: checkbox.
 */
function cancelSelected(type){
	if(type == 0){
		var that = {id: "", alt: "", title: ""};
		that.value = that.alt = that.title = "";
		writeIdAndName(that);//模拟点击了一个空的radio
		$(":input[type='radio']:checked").removeAttr('checked');
		$("#trimedName").empty();
	}
	else if(type == 1){
		thisPopLayer.outData.data.id = thisPopLayer.outData.data.name = null;//将存储的ids和names清空
		$(".checkbox_select:checked", $("#list_container")).each(function(){
			$(this).removeAttr('checked');
		});
		$("#trimedName").empty();
	}
};

/**
 * 弹出层单选列表中，选择时将所选entity的id和name更新
 * 到默认值中，并刷新已选项的显示
 * @param that调用者对象
 */
function writeIdAndName(elem) {
	thisPopLayer.outData = {
		data : {"id" : elem.value, "name" : elem.alt, "sname" : elem.title}
	};
	showTrimedName(elem.alt);
};

/**
 * 弹出层多选列表中，选择时将所选entity的id和name更新
 * 到默认值中，并刷新已选项的显示
 * @param that调用者对象
 */
function writeIdsAndNames(that) {
	var entity = thisPopLayer.outData;// 读取父页面的传值
	var id = entity.data.id;// 读取默认选中的ID
	var name = entity.data.name;// 读取默认选中的NAME
	if (id == undefined || id == null) {
		id = [];
	}
	if (name == undefined || name == null) {
		name = [];
	}
	var tmp_id = that.value;
	var tmp_name = that.alt;
	if (that.checked == true) {// 选中，则将新增项添加的onloadDate中
		id.push(tmp_id);
		name.push(tmp_name);
	} else {// 取消，则从onloadDate中剔除此项
		var id_length = id.length;
		// 遍历数组，查找此项
		for (var i = 0; i < id_length; i++) {
			if (id[i] == tmp_id) {
				id.splice(i, 1);
				name.splice(i, 1);
				break;
			}
		}
	}
	thisPopLayer.outData.data.id = id;
	thisPopLayer.outData.data.name = name;
	name = name.join("; ");
	showTrimedName(name);
};