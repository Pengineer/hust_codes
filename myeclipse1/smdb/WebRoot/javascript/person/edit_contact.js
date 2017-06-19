define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/person/validate');

	var setting = new Setting({

		id: "contact",

		out_check: function(){
			$(":input[name='person.homeAddress']").val(getAddrs($("#home-addr-table")));
			$(":input[name='person.homePostcode']").val(getPostcodes($("#home-addr-table")));
			$(":input[name='person.officeAddress']").val(getAddrs($("#office-addr-table")));
			$(":input[name='person.officePostcode']").val(getPostcodes($("#office-addr-table")));
			return $("#form_person").valid();
		},
		on_submit_opt: function(){
			/*$(":input[name^='comb_addr']").remove();
			$(":input[name^='comb_postcode']").remove();*/
			$("#tr_home_addr").remove();
			$("#tr_office_addr").remove();
			$("#home-addr-table tr").each(function(key, value){
				$("input, select", $(value)).each(function(key1, value1){
					value1.name = value1.name.replace(/\[.*\]/, "[" + (key-1) + "]");
				});
			});
			$("#office-addr-table tr").each(function(key, value){
				$("input, select", $(value)).each(function(key1, value1){
					value1.name = value1.name.replace(/\[.*\]/, "[" + (key-1) + "]");
				});
			});
			
			$(":checkbox[name*='isDefault']",$("#home-addr-table")).each(function(){
				var name = $(this).attr("name"),
					selector = ":hidden[name='" + name + "']"
				$(selector).val($(this).val());
				$(this).remove();
			});
			$(":checkbox[name*='isDefault']",$("#office-addr-table")).each(function(){
				var name = $(this).attr("name"),
					selector = ":hidden[name='" + name + "']"
				$(selector).val($(this).val());
				$(this).remove();
			});
		},
		on_in_opt: function(){
			validate.valid_contact();
		}
	});
	
	var init = function(){
		$(":input[name*='isDefault']").each(function(){
			var $checkBox = $(":checkbox",$(this).parent());
			$checkBox.attr({
				"name": $(this).attr("name"),
				"value": $(this).val()
			});
			if($(this).val() == "1") $checkBox.attr("checked","true");
		});
		//家庭地址组合
		$("#add-home-addr").live("click", function(){
			var flag = true;
			addRow("home-addr-table", "tr_home_addr");
			validate.valid_contact();
			$(":checkbox[name*='isDefault']",$("#home-addr-table")).each(function(){
				if($(this).val() == "1"){
					flag = false;
				}
			});
			if(flag) $(":checkbox[name*='isDefault']",$("#home-addr-table")).eq(0).attr("checked","true").val("1");
			prevAddrCheckbox[0] = $(":checked[name*='isDefault']",$("#home-addr-table")).val("1") || {};
		});
		
		$(".delete_row").live("click", function(){
			$(this).parent().parent().remove();
		});

		//办公地址组合
		$("#add-office-addr").live("click", function(){
			addRow("office-addr-table", "tr_office_addr");
			validate.valid_contact();
			$(":checkbox[name*='isDefault']",$("#office-addr-table")).each(function(){
				if($(this).val() == "1"){
					flag = false;
				}
			});
			if(flag) $(":checkbox[name*='isDefault']",$("#office-addr-table")).eq(0).attr("checked","true").val("1");
			prevAddrCheckbox[0] = $(":checked[name*='isDefault']",$("#home-addr-table")).val("1") || {};
		});
		
		//初始化组合地址
//		initHomeAddr();
//		initOfficeAddr();
// 初始化设置默认的地址的checkbox
		prevAddrCheckbox = {};
		prevAddrCheckbox[0] = $(":checked[name*='isDefault']",$("#home-addr-table")).val("1") || {};	
		$(":checkbox[name*='isDefault']",$("#home-addr-table")).live("change",function(){				
			if(prevAddrCheckbox[0].attr("name") !== $(this).attr("name")) {
				prevAddrCheckbox[0].removeAttr("checked").val("0");
				$(this).attr("checked","true").val("1");
				prevAddrCheckbox[0] = $(this);
			}				
		});
		$(":checkbox[name*='isDefault']",$("#home-addr-table")).live("click",function(){
			$(this).attr("checked","true").val("1");
		})
		prevAddrCheckbox[1] = $(":checked[name*='isDefault']",$("#office-addr-table")).val("1") || {};
		$(":checkbox[name*='isDefault']",$("#office-addr-table")).live("change",function(){				
			if(prevAddrCheckbox[1].attr("name") !== $(this).attr("name")) {
				prevAddrCheckbox[1].removeAttr("checked").val("0");
				$(this).attr("checked","true").val("1");
				prevAddrCheckbox[1] = $(this);
			}				
		});
		$(":checkbox[name*='isDefault']",$("#office-addr-table")).live("click",function(){
			$(this).attr("checked","true").val("1");
		})
	};
	
	var getPostcodes = function(selector){
		var codes="";
		selector.find(":input[name*='comb_postcode']").each(function(){
			if($(this).val().length > 0 || ($(this).val().length==0 && $(this).parent().parent().find(":input[name*='comb_addr']").val().length>0) ){
				codes += $(this).val()+"; ";
			} 
		});
		codes = codes.substr(0,codes.length-2);
		return codes;
	}
	
	var getAddrs = function(selector){
		var addrs="";
		selector.find(":input[name*='comb_addr']").each(function(){
			if($(this).val().length > 0 || ($(this).val().length==0 && $(this).parent().parent().find(":input[name*='comb_postcode']").val().length>0) ){
				addrs += $(this).val()+"; ";
			}
		});
		addrs = addrs.substr(0,addrs.length-2);
		return addrs;
	}
	
	/**
	 * 初始化家庭地址组合框
	 */
	var initHomeAddr = function(){
		if($(":input[name='person.homeAddress']").val().length>0){
			addrs = $(":input[name='person.homeAddress']").val().split(/;\s*/);
			postcodes = $(":input[name='person.homePostcode']").val().split(/;\s*/);
			for(var i=0;i<addrs.length;i++){
				addRow("home-addr-table", "tr_addr");
			}
			$("#home-addr-table .tr_valid").each(function(key){
				$(this).find(":input[name*='comb_addr']").val(addrs[key]);
				$(this).find(":input[name*='comb_postcode']").val( postcodes[key]);
			});	
		}
	}
	
	/**
	 * 初始化办公地址组合框
	 */
	var initOfficeAddr = function(){
		if($(":input[name='person.officeAddress']").val().length>0){
			addrs = $(":input[name='person.officeAddress']").val().split(/;\s*/);
			postcodes = $(":input[name='person.officePostcode']").val().split(/;\s*/);
			for(var i=0;i<addrs.length;i++){
				addRow("office-addr-table", "tr_addr");
			}
			$("#office-addr-table .tr_valid").each(function(key){
				$(this).find(":input[name*='comb_addr']").val(addrs[key]);
				$(this).find(":input[name*='comb_postcode']").val( postcodes[key]);
			});	
		}
	}
	
	function initHomeAddr(address,postcode){
		address = address==undefined ? "":address;
		postcode = postcode==undefined ? "":postcode;
		if(address.length>0){
			addrs = address.split(/;\s*/);
			postcodes = postcode.split(/;\s*/);
			for(var i=0;i<addrs.length;i++){
				addRow("home-addr-table", "tr_addr");
			}
			$("#home-addr-table .tr_valid").each(function(key){
				$(this).find(":input[name*='comb_addr']").val(addrs[key]);
				$(this).find(":input[name*='comb_postcode']").val( postcodes[key]);
			});		
		}
	}


	function initOfficeAddr(address,postcode){
		address = address==undefined ? "":address;
		postcode = postcode==undefined ? "":postcode;
		if(address.length>0){
			addrs = address.split(/;\s*/);
			postcodes = postcode.split(/;\s*/);
			for(var i=0;i<addrs.length;i++){
				addRow("office-addr-table", "tr_addr");
			}
			$("#office-addr-table .tr_valid").each(function(key){
				$(this).find(":input[name*='comb_addr']").val(addrs[key]);
				$(this).find(":input[name*='comb_postcode']").val( postcodes[key]);
			});				
		}
	}
	
	module.exports = {
		setting : setting,
		init : function(){init()},
		initHomeAddr : function(){initHomeAddr()},
		initOfficeAddr : function(){initOfficeAddr()}
	};
});
