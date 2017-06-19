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

$(document).ready(function(){
	
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
	
	$("#selectDiscipline").click(function(){
		dialog({
			id: 'test-dialog',
			title: '选择学科代码',
			data: $("#disciplineId").val(),
			url: 'expert/toSelectDiscipline.action',
			//quickClose: true,
			onshow: function () {
				console.log('onshow');
			},
			oniframeload: function () {
				console.log('oniframeload');
			},
			onclose: function () {
				if(this.returnValue.result){
					doWithXX(this.returnValue.result.ids, "disciplineId", "discipline");
				}
			},
			onremove: function () {
				console.log('onremove');
			}
		})
		.showModal();
		return false;
	});
	
	$("#birthday").datepicker({
		startDate: '1920-1-1',
		startView: 'month',
		minView: 'month',
		maxView: 'decade',
		autoclose: true,
		todayHighlight: true,
		format: 'yyyy-mm-dd'
	});
	
	/********************************************************************************************************/
	/**
	 * 填写表格处理
	 * @param {Object} xx 弹出窗口返回值
	 * @param {Object} hiddenId 属性对应隐藏域的code/name
	 * @param {Object} wholeDiv 整个显示区的id
	 */
	var basic_setting = new Setting({
		id: "basic",
		buttons: ['next', 'retry', 'cancel'],
		out_check: function() {
			var validationResult = $.makeArray($("#basic input, #basic select, #basic textarea").map(function(previousValue, currentValue, index, array) {
				return ($(currentValue).validationEngine('validate'));
			}));
			
			return ! validationResult.reduce(function(previousValue, currentValue, index, array) {
				  return previousValue || currentValue;
			});
		}
	});
	var jobInfo_setting = new Setting({
		id: "jobInfo",
		buttons: ['prev', 'next', 'retry', 'cancel'],
		out_check: function() {
			var validationResult = $.makeArray($("#jobInfo input, #jobInfo select, #jobInfo textarea").map(function(previousValue, currentValue, index, array) {
				return ($(currentValue).validationEngine('validate'));
			}));
			
			return ! validationResult.reduce(function(previousValue, currentValue, index, array) {
				  return previousValue || currentValue;
			});
		}
	});
	var contact_setting = new Setting({
		id: "contact",
		buttons: ['prev', 'next', 'retry', 'cancel'],
		out_check: function() {
			var validationResult = $.makeArray($("#contact input, #contact select, #contact textarea").map(function(previousValue, currentValue, index, array) {
				return ($(currentValue).validationEngine('validate'));
			}));
			
			return ! validationResult.reduce(function(previousValue, currentValue, index, array) {
				  return previousValue || currentValue;
			});
		}
	});
	var bankInfo_setting = new Setting({
		id: "bankInfo",
		buttons: ['prev', 'finish', 'retry', 'cancel'],
		out_check: function() {
			var validationResult = $.makeArray($("#bankInfo input, #bankInfo select, #bankInfo textarea").map(function(previousValue, currentValue, index, array) {
				return ($(currentValue).validationEngine('validate'));
			}));
			
			return ! validationResult.reduce(function(previousValue, currentValue, index, array) {
				  return previousValue || currentValue;
			});
		}
	});
	
	
	step_controller = new Step_controller();
	
	step_controller.after_move_opt = function(){
		var flag = false;
		for (step in step_controller.steps){
			var $curLi = $("li[name=" + step_controller.steps[step].id + "]");
			if (step_controller.steps[step].id == step_controller.state){
				flag = true;
				$curLi.attr("class", "proc step_d");
			} else if (!flag){
				$curLi.attr("class", "proc step_e");
			} else {
				$curLi.attr("class", "proc step_f");
			}
		}
	};
	
	step_controller.add_step(basic_setting);
	step_controller.add_step(jobInfo_setting);
	step_controller.add_step(contact_setting);
	step_controller.add_step(bankInfo_setting);
	step_controller.init();
	
////////////////////分割线//////////////////////
	
	
	$("#prev").click(function(){
		step_controller.prev();
	});

	$("#next").click(function(){
		step_controller.next();
	});

	$("#confirm").click(function(){
		step_controller.next();
	});

	$("#retry").click(function(){
		location.reload();
	});

	$("#cancel").click(function(){
		history.go(-1);
	});

	$("#finish").click(function(){
		step_controller.submit();
	});

	$("#info").show();
})
