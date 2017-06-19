define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/productService');
	var datepick = require("datepick-init");
//	var validate = require('javascript/product/validate');
	require('validate');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/step_tools');
	
	//选择成果类型
	var selectProductType = function(){
		parent._productHtml=[];
		parent._productHtml.push($("#paper").html());
		parent._productHtml.push($("#book").html());
		parent._productHtml.push($("#consultation").html());
		parent._productHtml.push($("#electronic").html());
		parent._productHtml.push($("#patent").html());
		parent._productHtml.push($("#otherProduct").html());
		$("#product").children().each(function(){$(this).remove();});
		$("#product").append('<div id="content"></div>');
		if($("#productflag").val()==1){
			$("input[name='ptype'][value=1][type='radio']").attr("checked",true);
			$("#content").html(parent._productHtml[0]);
		}else{
			if($("#ptypeid").val()=="paper"){
				$("#content").html(parent._productHtml[0]);
			}else if($("#ptypeid").val()=="book"){
				$("#content").html(parent._productHtml[1]);
			}else if($("#ptypeid").val()=="consultation"){
				$("#content").html(parent._productHtml[2]);
			}else if($("#ptypeid").val()=="electronic"){
				$("#content").html(parent._productHtml[3]);
			}else if($("#ptypeid").val()=="patent"){
				$("#content").html(parent._productHtml[4]);
			}else if($("#ptypeid").val()=="otherProduct"){
				$("#content").html(parent._productHtml[5]);
			}
		}
		initSwf();
		$('#publicationDate').datepick({yearRange: '-90:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});
	};
	
	var selectProduct = function(data){
		$("#productType").attr("value", data);
		$("#content").html(parent._productHtml[data-1]);	
		initSwf();
		$('#publicationDate').datepick({yearRange: '-90:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});
	};
	
	exports.initClick = function() {
		//成果类别变化时更换显示内容
		$("input[name='ptype'][type='radio']").click(function(){
			var data = $("input[name='ptype'][type='radio']:checked").val();
			
		});
//		window.selectProduct = function(data){selectProduct(data)};//成果类别变化时更换显示内容
	};
	
	exports.init = function() {
		datepick.init();//日期初始化
		selectProductType();//页面加载初始化加载成果类型
	};
});
