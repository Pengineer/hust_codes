define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/productService');
	var datepick = require("datepick-init");
	var validate = require('javascript/product/validate');
	require('validate');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/step_tools');
	
	
	//初始化事件绑定
	var initPro = function(){
		//切换作者类型
		$("#authorType").live("change", function(){
			$("#per_name_div").html("");
			$("#per_university_unit").html("");
			$("#per_id").val("");
			$("#authorTypeId").val("");
		});
		
		//是否项目成果
		$(".isProRel").live("click", function(){//是否项目成果
			if(this.value == 1){$("#pro_info").show(); return;};
			$("#project_type").attr("value", -1);
			$("#proj_name_div").html("");
			$("#proj_name").attr("value", "");
			$("#pro_info").hide();
		});
		
		//切换项目类型
		$("#project_type").live("change", function(){//清空选择
			$("#proj_name").attr("value", "");
			$("#proj_name_div").html("");
		});
		
		//项目获取方式
		$(".isSelPro").live("click", function(){
			if(this.value == 1){
				$("#gainBySelect").show();
				$("#gainByNumber").hide();
			} else if(this.value == 0){
				$("#gainBySelect").hide();
				$("#gainByNumber").show();
			}
		});
		
		//根据项目编号获取项目
		$("#get_project_btn").live("click", function(){
			var projectType = $("#project_type").val();
			if(projectType == -1) {
				alert("请先选择项目类型！"); return false;
			}
			if(!$("#proNum").val()) {
				alert("请输入项目编号！"); return false;
			}
			var personId = (!!$("#person_per_id")) ? $("#person_per_id").val() : $("#team_per_id").val();
			productService.getProject($("#project_type").val(), $("#proNum").val(), personId,
				function(data){
					if(!data){
						alert("您没有参加此项目批准号的项目！"); return false;
					} else {
						$("#proj_name").val(data[0]);
						$("#proj_name_span").html(data[1]);
					}
				}
			);
		});
		
		//发表刊物获取方式
		$("#selectOrNot").live("change", function(){
			if($(this).val() == 1){
				$("#publication_name").html($("#publication").val());
				$("#select_yes").show();
				$("#select_no").hide();
			}else if($(this).val() == 0){
				$("#select_yes").hide();
				$("#select_no").show();
			}else{
				$("#select_yes").hide();
				$("#select_no").hide();
			}
		});
		
		//申报形式
		$(".applyType").live("click", function(){
			if($(".applyType:checked").val() == 0){
				$("#personal_info").show();
				$("#team_info").hide();
				$.cookie("applyType", 0);
			} else {
				$("#personal_info").hide();
				$("#team_info").show();
				$.cookie("applyType", 1);
			}
		});
	};
	
	//绑定弹出层选择
	var initPopLayer = function(){
		//选择学科门类
		$(".select_disciplineType_btn").click(function(){
			var idPrefix = this.alt;
			$("#" + idPrefix + "_disptr").parent("td").next("td").html("");
			popSelect({
				type : 11,
				inData : $("#" + idPrefix + "_dispName").val(),
				callBack : function(result){
					doWithXX(result, idPrefix + "_dispName", idPrefix + "_disptr");
				}
			});
		});
		
		//选择学科代码
		$("#select_disciplineCode_btn").click(function(){
			$("#rdsp").parent("td").next("td").html("");
			popSelect({
				type : 12,
				inData : $("#relyDisciplineName").val(),
				callBack : function(result){
					doWithXX(result, "relyDisciplineName", "rdsp");
				}
			});
		});
		
		//选择成果作者
		$(".select_author_btn").live("click", function(){
			$(this).parent("td").next("td").html("");
			var idPrefix = this.alt;//个人、团队
			var authorType = $("#" + idPrefix  + "_authorType").val();
			if(authorType == -1) {
				if(idPrefix == "person") {
					alert("请先选择作者类别！"); return false;
				} else {
					alert("请先选择负责人类别！"); return false;
				}
			}
			var authorTypeMap = [8, 7, 15];//教师、专家、学生
			popSelect({
				type : authorTypeMap[authorType - 1],
				inData : {
					"id" : $("#" + idPrefix  + "_authorTypeId").val(), 
					"name" : $("#" + idPrefix  + "_per_name_div").html().replace(/(&nbsp;)+/g, ";").split(";")[0],
					"sname" : $("#" + idPrefix  + "_per_university_unit").html(),
					"personId" : $("#" + idPrefix  + "_per_id").val()
				},
				callBack : function(result){
					$("#" + idPrefix  + "_authorTypeId").val(result.data.id);
					$("#" + idPrefix  + "_per_id").val(result.data.personId);
					$("#" + idPrefix  + "_per_name_div").html(result.data.name + "&nbsp;&nbsp;&nbsp;" + result.data.sname);
				}
			});
		});
		
		//选择成果所属项目
		$("#select_project_btn").click(function(){
			var projectType = $("#project_type").val();
			if(projectType == -1) {
				alert("请先选择项目类型！"); return false;
			}
			var projectTypeMap = {"general": 1, "key":2, "instp": 3, "post": 4, "entrust":5};
			var personId = (!!$("#person_per_id")) ? $("#person_per_id").val() : $("#team_per_id").val();
			popSelect({
				type : 13,
				proType : projectTypeMap[projectType],
				personId : personId,
				inData : {"id" : $("#proj_name").val(), "name" : $("#proj_name_div").html(), "update" : 1, "universityId" : ""},
				callBack : function(result){
					$("#proj_name").val(result.data.id);
					$("#proj_name_div").html(result.data.name);
				}
			});
		});
		
		//选择索引类型
		$("#select_indexType_btn").click(function(){
			popSelect({
				type : 14,
				inData : $("[name=paper.index]").val(),
				callBack : function(result){
					$("[name=paper.index]").val(result.data.indexType);
					$("#indexType").html(result.data.indexType);
				}
			});
		});
		
		//选择发表刊物
		$("#select_publication_btn").live("click", function(){
			if($("#publication_level").val() == "-1"){alert("请先选择刊物级别！"); return;}
			popSelect({
				type : 16,
				publicationLevelId : $("#publication_level").val(),
				inData : {"name" : $("#publication").val()},
				callBack : function(result){
					$("#publication").val(result.data.name);
					$("#publication_name").html(result.data.name);
				}
			});
		});
		
		//选择院系
		$("#select_dep_btn").live("click", function() {
			popSelect({
				type : 4,
				label : 3,
				inData : {"id":$("#departmentId").val(), "name":$("#unit").html()},
				callBack : function(result){
					$("#departmentId").val(result.data.id);
					$("#instituteId").val("");
					$("#unit").html(result.data.name);
					$("#unitName").val(result.data.name);
				}
			});
		});
		
		//选择基地
		$("#select_ins_btn").live("click", function() {
			popSelect({
				type : 5,
				label : 3,
				inData : {"id":$("#instituteId").val(), "name":$("#unit").html()},
				callBack : function(result){
					$("#instituteId").val(result.data.id);
					$("#departmentId").val("");
					$("#unit").html(result.data.name);
					$("#unitName").val(result.data.name);
				}
			});
		});
	};
	
	exports.init = function(nameSpace) {
		
		initPro();//初始化事件绑定
		initPopLayer();//绑定弹出层
		datepick.init();//初始化日期
		validate.valid();//编辑信息的校验
		
		//新建分布步编辑标签
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
		
		//基本信息
		if($("#addOrModify").val() == 1) {//添加成果
			var basic_setting = new Setting({
				id: "base_info",
				buttons: ['next', 'cancel'],
				out_check: function(){
					validate.valid_person_team();
					return $("#form_product").valid();
				},
				on_in_opt: function(){
					validate.valid_basic();
				}
			});
			
			//项目信息
			var project_setting = new Setting({//修改成果
				id: "project_info",
				buttons: ['prev', 'next', 'cancel'],
				out_check: function(){
					validate.valid_project();
					return $("#form_product").valid();
				}
			});
		} else {//修改成果
			var basic_setting = new Setting({
				id: "base_info",
				buttons: ['next', 'save_product', 'submit_product', 'finish', 'cancel'],
				out_check: function(){
					return $("#form_product").valid();
				},
				on_in_opt: function(){
					validate.valid_basic();
				}
			});
			
			//项目信息
			var project_setting = new Setting({
				id: "project_info",
				buttons: ['prev', 'next', 'save_product', 'submit_product', 'finish', 'cancel'],
				out_check: function(){
					validate.valid_project();
					return $("#form_product").valid();
				}
			});
		}
		
		//出版信息
		var publication_setting = new Setting({
			id: "publication_info",
			buttons: ['prev', 'save_product','submit_product', 'finish', 'cancel'],
			out_check: function(){
				return $("#form_product").valid();
			},
			on_in_opt: function(){
				validate.valid_publication();
			}
		});
		$("li.proc").live("click",function(){
			if($("#addOrModify").val() != 1)
			step_controller.move_to($(this).attr('name'));
		});
		step_controller.add_step(basic_setting);
		step_controller.add_step(project_setting);
		step_controller.add_step(publication_setting);
		step_controller.init();

		////////////////////分割线//////////////////////
		
		//添加成果
		if($("#addOrModify").val() == 1) {
			//申报形式
			if($.cookie("applyType")) {
				var applyType = $.cookie("applyType");
				$("input[name='applyType'][value=" + applyType + "]").attr("checked", true);
				$(".applyType:checked").trigger("click");
			}
		}
		//上下步
		$("#prev").click(function(){
			step_controller.prev();
		});

		$("#next").click(function(){
			step_controller.next();
		});
		//保存成果
		$("#save_product").click(function(){
			$("#submitStatus").attr("value",2);
			step_controller.submit();
		});
		//提交成果
		$("#submit_product").click(function(){
			$("#submitStatus").attr("value", 3);
			if (confirm("确认提交该成果吗？")) {
				step_controller.submit();
			}
		});
		//完成
		$("#finish").click(function(){
			$("#submitStatus").attr("value", 3);
			step_controller.submit();
		});
		//取消
		$("#cancel").click(function(){
			history.go(-1);
		});
	};
});
