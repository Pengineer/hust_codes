define(function(require, exports, module) {
	var list = require('javascript/list');
	var listAccount = require('javascript/security/account/list')
	
	var nameSpace = "account/ministry/main";
	
	var leaf = function() {
//		$('#dropdown').live('click', function(){
//			alert(1);
//			$(this).parent().find("ul").slideDown('fast').show();
//			$(this).parent().hover(function() {
//			}, function(){
//				$(this).parent().find("ul").slideUp('slow'); //When the mouse hovers out of the subnav, move it back up
//			});
//			//Following events are applied to the trigger (Hover events for the trigger)
//			}).hover(function() { 
//				$(this).addClass("subhover"); //On hover over, add class "subhover"
//			}, function(){	//On Hover Out
//				$(this).removeClass("subhover"); //On hover out, remove class "subhover"
//		});
		$(".linkA").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
	}
	exports.init = function() {
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5}
		});
		listAccount.initListButton(nameSpace);
		leaf();
	};
});