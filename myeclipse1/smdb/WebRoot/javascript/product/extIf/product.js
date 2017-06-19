/**
 * 弹出层成果
 */
define(function(require, exports, module) {
	require('uploadify');
	require('uploadify-ext');
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/productService');
	var datepick = require("datepick-init");
	var validate = require('javascript/product/extIf/validate');
	require('validate');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('form');
	
	var projectId;//项目id
	var inspectionId;//年检、中检或结项id
	var projectType;//项目类型(1.一般项目；2.重大攻关项目；3.基地项目；4.后期资助项目；5.委托应急课题);
	var viewType;//查看类型(1.年检成果； 2.中检成果；3.结项成果；4.相关成果；5.添加奖励成果)
	var productHtml = [];//加载的成果页面
	var productType = 1;//论文类型(1.论文 2.著作 3.研究咨询报告4.电子出版物5.专利6.其他成果)(默认选中论文)
	var productTypeMap = {1 : "paper", 2 : "book", 3 : "consultation", 4 : "electronic", 5: "patent", 6: "otherProduct"};
	
	thisPopLayer = top.PopLayer.instances[top.PopLayer.id];	
	/**
	 * 添加成果初始化
	 */
	var initProd = function(){
		
		projectId = $("#projectId").val();
		inspectionId = $("#inspectionId").val();
		projectType = $("#projectType").val();
		viewType = $("#viewTypeFlag").val();
		
		productHtml.push($("#paper").html());
		productHtml.push($("#book").html());
		productHtml.push($("#consultation").html());
		productHtml.push($("#electronic").html());
		productHtml.push($("#patent").html());
		productHtml.push($("#otherProduct").html());
		$("#product").children().each(function(){
			$(this).remove();
		});
		//默认添加新的成果
		$("#product").append('<div id="content"></div>');
		$("input[name='SecOrAdd'][value=0][type='radio']").attr("checked", true);
		$("input[name='pro_form'][value=" + parseInt(productType) + "][type='radio']").attr("checked", true);
		if($("#viewType").length != 0){
			$("#viewType").attr("value", viewType);
		}//列表类型
		loadHtml(productType);//加载添加成果页面html
		$("#product").show();
		$("#submit_btn").live("click", function(){
			selectSubmit();
		});
	};
	
	/**
	 * 加载项目相关成果-->成果下拉框-->项目相关成果
	 * viewType：1.年检成果 2.中检成果 3.结项成果 4.相关成果
	 */
	function loadProductList(viewType, proType){
		productService.getProduct(projectId, projectType, viewType, proType, inspectionId, function(result){
			if($("#list_product").length > 0)
				DWRUtil.addOptions('list_product', result);
			}
		);
	};
	
	/**
	 * 添加已有成果提交
	 */
	function selectSubmit(){
		var entityId = $("#list_product").val();
		if (entityId == null || entityId == '-1') {
			alert("请选择成果！");
			return false;
		} else {
			var proType = parseInt($("input[name='pro_form'][type='radio']:checked").val());
			$("#select_form").attr("action", "product/" + productTypeMap[proType] + "/exAdd.action").ajaxSubmit({
				dataType : "json",
				success : function(result){
					if(result.errorInfo == null || result.errorInfo == "") {
						thisPopLayer.callBack(thisPopLayer);
						thisPopLayer.destroy();
					} else {
						alert(result.errorInfo);
					};
				}
			});
		}
	};
	
	/**
	 * 加载html页面（不同成果添加页面）
	 * type：1.论文 2.著作 3.研究咨询报告4.电子出版物5.专利6.其他成果
	 */
	function loadHtml(type){
		var projType = {1:"general", 2:"key", 3:"instp", 4:"post", 5:"entrust"};//其它项目待定
		$("#content").html(productHtml[parseInt(type)-1]);
		$("#proj_type_id").val(projType[parseInt(projectType)]);
		$("#proj_name").val(projectId);
	};
	/**
	 * 切换成果
	 * type：1.论文 2.著作 3.研究咨询报告4.电子出版物5.专利6.其他成果
	 */
	function switchItem(type){
		$("#product").hide();
		$("#select_product").show();
		$("#product_title").children().each(function(){$(this).hide();});
		$("#list_product").attr("length", 1);
		loadProductList(viewType, type);//加载成果下拉项
		$("#product_title").children().eq(type-1).show();
	};
	
	
	//绑定弹出层
	var bindSelect = function(){
		$(".authorType").live("change", function(){
			if(this.value == 4){//第一作者是无id项目成员
				$("#otherPerson").show();
				$("#author_warn").html("&nbsp;(这里的项目其他成员是指无法添加自己成果的项目成员！)");
				$("#projectMemberId").rules("add", {selected: true});
			}else{
				$("#otherPerson").hide();
				$("#author_warn").html("");
				$("#projectMemberId").rules("remove");
			} 
		});
		$("#select_disciplineType_btn").live("click", function(){
			$("#disptr").parent("td").next("td").html("");
			popSelect({
				type : 11,
				inData : $("#dispName").val(),
				callBack : function(result){
					doWithXX(result, "dispName", "disptr");
				}
			});
		});
		$("#select_disciplineCode_btn").live("click", function(){
			$("#rdsp").parent("td").next("td").html("");
			popSelect({
				type : 12,
				inData : $("#relyDisciplineName").val(),
				callBack : function(result){
					doWithXX(result, "relyDisciplineName", "rdsp");
				}
			});
		});
		$("#select_indexType_btn").live("click", function(){//索引类型
			popSelect({
				type : 14,
				inData : $("[name = paper.index]").val(),
				callBack : function(result){
					$("[name=paper.index]").val(result.data.indexType);
					$("#indexType").html(result.data.indexType);
				}
			});
		});
		$("#selectOrNot").live("change",function(){//发表刊物是选择还是输入
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
		$("#select_publication_btn").live("click",function(){//选择发表刊物
			if($("#publication_level").val() == "-1"){
				alert("请先选择刊物级别！");
				return;
			}
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
	};
	
	var select = function(){
		//添加、修改成果
		$("#submit").live("click", function() {
			if($("#form_product").valid()) {
				$("#form_product").ajaxSubmit({
					dataType: "json",
					success: function(result) {
						if(result.errorInfo == null || result.errorInfo == '') {
							thisPopLayer.callBack(result);
							thisPopLayer.destroy();
						} else {
							alert(result.errorInfo);
						}
					}
				});
				return false;
			}
		});
		//下载成果
		$(".downlaod_product").live("click", function() {
			var $this = this;
			$.ajax({
				url: "product/" + $this.type + "/validateFile.action?entityId=" + this.id + "&fileFileName=" + $this.name,
				type: "post",
				dataType: "json",
				success: function(result){
					if(result.errorInfo != null && result.errorInfo != ""){
						alert(result.errorInfo);
					}else{
						window.location.href = basePath + "product/" + $this.type + "/download.action?entityId=" + this.id + "&fileFileName=" + this.name;
					}
				}
			});
			return false;
		});
		//切换著作类型; 译著、译需要选择原著语言
		$("select[name='book.type.id']").live("change", function() {
			if($(this).val() == 'translationWork' || $(this).val() == 'translation') {
				$("select", $(this).next()).rules("add", {selected: true});
				$(this).next().show();
			} else {
				$("select", $(this).next()).val("-1").rules("remove");
				$(this).next().hide();
			}
		});
		
		//添加成果 0：添加新的成果 1：从已有成果中选择
		$(".SecOrAdd").live("click", function(){
			var proType=$("input[name='pro_form'][type='radio']:checked").val();
			if(this.value == 1){
				switchItem(productTypeMap[proType]);//加载已有成果下拉项
				$("#field_error").html("");//清空错误区
			} else{
				$("#select_product").hide();
				loadHtml(proType);//加载添加成果页面html
				$("#product").show();
//					initSwf();//异步上传文件初始化
			}
		});
		// 切换成果形式
		$(".pro_form").live("change",function(){
			if ($("input[name='SecOrAdd'][type='radio']:checked").length > 0) {
				var SecOrAddFlag = $("input[name='SecOrAdd'][type='radio']:checked").val();//添加或选择成果(0.添加；1.选择)
				if (SecOrAddFlag == 0 || typeof SecOrAddFlag == "undefined") {
					loadHtml(this.value);//加载添加成果html
//						initSwf();
				} else {
					switchItem(productTypeMap[this.value]);//加载已有成果下拉项
				}
			} else {//奖励模块
				loadHtml(this.value);//加载添加成果页面html
//					initSwf();
			}
			if (productType != this.value) {
				$("#field_error").html("");//清空错误区
			}
			productType = this.value;//更新成果形式
		});
	};	
	
	exports.init = function(){
		datepick.init();
		validate.valid();//绑定校验
		initProd();
		select();
		bindSelect();
		$(function() {
			var productId = $("#productId").val();
			$("#file_" +productId).uploadifyExt({
//			uploadLimitExt : 1,
				fileSizeLimit : '300MB',
				fileTypeExts:'*.doc; *.docx; *.pdf',
				fileTypeDesc : '附件'
			});
			
			var bookId = $("#bookId").val();
			$("#file_" +bookId).uploadifyExt({
				fileSizeLimit : '300MB',
				fileTypeExts:'*.doc; *.docx; *.pdf',
				fileTypeDesc : '附件'
			});
			var consultationId = $("#consultationId").val();
			$("#file_" +consultationId).uploadifyExt({
				fileSizeLimit : '300MB',
				fileTypeExts:'*.doc; *.docx; *.pdf',
				fileTypeDesc : '附件'
			});
			var electronicId = $("#electronicId").val();
			$("#file_" +electronicId).uploadifyExt({
				fileSizeLimit : '300MB',
				fileTypeExts:'*.doc; *.docx; *.pdf',
				fileTypeDesc : '附件'
			});
			var otherProductId = $("#otherProductId").val();
			$("#file_" +otherProductId).uploadifyExt({
				fileSizeLimit : '300MB',
				fileTypeExts:'*.doc; *.docx; *.pdf',
				fileTypeDesc : '附件'
			});
			var paperId = $("#paperId").val();
			$("#file_" +paperId).uploadifyExt({
				fileSizeLimit : '300MB',
				fileTypeExts:'*.doc; *.docx; *.pdf',
				fileTypeDesc : '附件'
			});
			var patentId = $("#patentId").val();
			$("#file_" +patentId).uploadifyExt({
				fileSizeLimit : '300MB',
				fileTypeExts:'*.doc; *.docx; *.pdf',
				fileTypeDesc : '附件'
			});
		});
	};
});