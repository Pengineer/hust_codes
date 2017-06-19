function Step_controller(){

	this.state = null;
	this.init_state = null;
	this.form_id = null;
	this.steps = null;

	//有步骤转换时(包括初始进入第一步)，则自动执行
	this.after_move_opt = null;

};

/**
 * 跳转到指定步(div)
 * @param {Object} next_state 跳转到指定步
 * @memberOf {TypeName}
 * @return {TypeName}
 */
Step_controller.prototype.move_to = function(next_state, callback){
	var cur_step = this.steps[this.state];

	if (cur_step && typeof cur_step.out_check == 'function' && !cur_step.out_check()){
		if (typeof callback == 'function') {
			callback(false);
		}
		return;
	}

	if (cur_step && typeof cur_step.out_check_custom == 'function' && !cur_step.out_check_custom()){
		if (typeof callback == 'function') {
			callback(false);
		}
		return;
	}

	var next_step = this.steps[next_state];
	if (!next_step){
		if (typeof callback == 'function') {
			callback(false);
		}
		return;
	}

	if (typeof next_step.in_check == 'function' && !next_step.in_check()){
		if (typeof callback == 'function') {
			callback(false);
		}
		return;
	}
	if (typeof next_step.in_check_custom == 'function' && !next_step.in_check_custom()){
		if (typeof callback == 'function') {
			callback(false);
		}
		return;
	}

	if (cur_step && typeof cur_step.hide == 'function'){
		cur_step.hide();
	}

	if (typeof next_step.show == 'function'){
		next_step.show();
		next_step.button_show();
	}

	if (cur_step && typeof cur_step.on_out_opt == 'function'){
		cur_step.on_out_opt();
	}
//	if (cur_step){
//		$("li[name=" + cur_step.id + "]").removeClass("step_d");
//	}

	if (cur_step && typeof cur_step.on_out_opt_custom == 'function'){
		cur_step.on_out_opt_custom();
	}

	if (typeof next_step.on_in_opt == 'function'){
		next_step.on_in_opt();
	}
//	$("li[name=" + next_step.id + "]").addClass("step_d");

	if (typeof next_step.on_in_opt_custom == 'function'){
		next_step.on_in_opt_custom();
	}

	this.state = next_state;

	if (typeof this.after_move_opt == 'function'){
		this.after_move_opt();
	}
	
	if (typeof callback == 'function') {
		callback(true);
	}
}

/**
 * 添加一步(div)
 * @param {Object} setting
 * @memberOf {TypeName}
 * @return {TypeName}
 */
Step_controller.prototype.add_step = function(setting){
	if (setting == undefined || setting.id == undefined){
		return;
	}
	if (!this.steps){
		this.steps = {};
	} else {
		var last_step = this.steps[this.last_state];
		last_step.dest.next = setting.id;
		setting.dest.prev = last_step.id;
	}
	this.last_state = setting.id;
	this.steps[setting.id] = setting;

	if (!this.init_state){
		this.init_state = setting.id;
	}
}

Step_controller.prototype.next = function(callback){
	var cur_step = this.steps[this.state];
	if (typeof cur_step.next_opt == 'function'){
		this.move_to(cur_step.dest[cur_step.next_opt()], callback);
	} else if (typeof callback == 'function'){
		callback(false);
	}
}

Step_controller.prototype.prev = function(callback){
	var cur_step = this.steps[this.state];
	if (typeof cur_step.prev_opt == 'function'){
		this.move_to(cur_step.dest[cur_step.prev_opt()], callback);
	} else if (typeof callback == 'function'){
		callback(false);
	}
}

Step_controller.prototype.init = function(){
	for (step in this.steps){
		$("div#" + this.steps[step].id).hide();
	}
	this.move_to(this.init_state);
}

Step_controller.prototype.submit = function(){
	var $form = !!this.form_id ? $("form#" + this.form_id) : $("form").eq(0);
	for (step in step_controller.steps){
		if (typeof step_controller.steps[step].out_check == 'function'){
			if (!step_controller.steps[step].out_check()){
				return false;
			}
		}
	}
	for (step in step_controller.steps){
		if (typeof step_controller.steps[step].on_submit_opt == 'function'){
			step_controller.steps[step].on_submit_opt();
		}
		if (typeof step_controller.steps[step].on_submit_opt_custom == 'function'){
			step_controller.steps[step].on_submit_opt_custom();
		}
	}
	$form.submit();
}

////////////////////////////////////////////////////////////////////////////////////////////////

//div配置类，分步编辑页面的每个div都会绑定一个该类的实例，其中包含自定义的事件，由Step_controller统一调度
function Setting(parameter){

	//绑定的div的id名
	this.id = parameter.id;

	//跳转标识键值对
	this.dest = {
		prev : null,
		next : null
	};

	//上一步step标识
	this.prev_opt = function(){
		return "prev";
	}

	//下一步step标识
	this.next_opt = function(){
		return "next";
	}

	///////////////下面几条为标签统一的设置///////////////////////

	//进入该div前的执行的方法,若该方法返回false,则不进入
	this.in_check = null,

	//跳出该div前的执行的方法,若该方法返回false,则不跳出
	this.out_check = null,

	//刚进入该div时执行的方法，用于初始化该div及相关操作
	this.on_in_opt = null,

	//刚离开该div时执行的方法，用于善后、收尾
	this.on_out_opt = null,

	//表单提交前执行的方法
	this.on_submit_opt = null,

	///////////////////下面几条为具体页面相关的设置,用法和上五条类似///////////////
	this.in_check_custom = null,
	this.out_check_custom = null,
	this.on_in_opt_custom = null,
	this.on_out_opt_custom = null,
	this.on_submit_opt_custom = null,


	//显示该步对应的div
	this.show = function(){
		$("#" + this.id).show();
	}

	//隐藏该步对应的div
	this.hide = function(){
		$("#" + this.id).hide();
	}

	//指定该步有哪些按钮可点，其中元素为按钮的id
	this.buttons = [],

	this.button_show = function(){
		$("#optr input").css("display","none");

		for (i = 0; i < this.buttons.length; i++){
			$("#" + this.buttons[i]).css("display", "");
		}
		if (!this.dest.next){
			$("#next").css("display","none");
		}
		if (!this.dest.prev){
			$("#prev").css("display","none");
		}
	}

	//以上为step通用属性，下面是step各自特定属性
	for (data in parameter){
		this[data] = parameter[data];
	}

};

var flag = 99999;
function addRow(tableId, trId)
{
	// 获取table dom
	var idTB = document.getElementById(tableId);
	// 获取当前表格行数
	var len = idTB.rows.length;
	// 在当前表格末尾插入一行
	var oTR = idTB.insertRow(len - 1);
	var $oTR = $(oTR);

	$oTR.addClass("tr_dynamic");
	$oTR.addClass("tr_valid");
	// 获取行号rowIndex
	var tmpNum = oTR.rowIndex - 1;
	$oTR.html($("#" + trId).html());
	$(":input", $oTR).each(function(key, value){
		value.name = value.name.replace(/\[.*\]/, "[" + flag + "]");
		value.id = value.name;
	});
	// 动态绑定日期选择器
	$(".add_date", $oTR).datepick({alignment: "left", onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, autoSize: true});

	flag++;
	// 添加校验
}