define(function(require, exports, module) {
	var teacherBusinessUrl = {
		"011":"project/general/application/apply",
		"021":"project/instp/application/apply",
		"031":"project/post/application/apply",
		"041":"project/key/application/apply",
		"051":"project/entrust/application/apply",
		"012":"project/general/midinspection/apply",
		"022":"project/instp/midinspection/apply",
		"042":"project/key/midinspection/apply",
		"013":"project/general/endinspection/apply",
		"023":"project/instp/endinspection/apply",
		"033":"project/post/endinspection/apply",
		"043":"project/key/endinspection/apply",
		"053":"project/entrust/endinspection/apply",
		"014":"project/general/variation/apply",
		"024":"project/instp/variation/apply",
		"034":"project/post/variation/apply",
		"044":"project/key/variation/apply",
		"054":"project/entrust/variation/apply",
		"015":"project/general/anninspection/apply",
		"025":"project/instp/anninspection/apply",
		"035":"project/post/anninspection/apply",
		"045":"project/key/anninspection/apply"
	};
	var teacherReviewProjectUrl = {
		"011":"project/general/application/review",
		"021":"project/instp/application/review",
		"031":"project/post/application/review",
		"041":"project/key/application/review",
		"051":"project/entrust/application/review",
		"013":"project/general/endinspection/review",
		"023":"project/instp/endinspection/review",
		"033":"project/post/endinspection/review",
		"043":"project/key/endinspection/review",
		"053":"project/entrust/endinspection/review"
	};
	var managerBusinessUrl = {
		"011":"project/general/application/apply",
		"012":"project/general/midinspection/apply",
		"013":"project/general/endinspection/apply",
		"014":"project/general/variation/apply",
		"015":"project/general/anninspection/apply",
		"021":"project/instp/application/apply",
		"022":"project/instp/midinspection/apply",
		"023":"project/instp/endinspection/apply",
		"024":"project/instp/variation/apply",
		"025":"project/instp/anninspection/apply",
		"031":"project/post/application/apply",
		"033":"project/post/endinspection/apply",
		"034":"project/post/variation/apply",
		"035":"project/post/anninspection/apply",
		"041":"project/key/application/apply",
		"042":"project/key/midinspection/apply",
		"043":"project/key/endinspection/apply",
		"044":"project/key/variation/apply",
		"045":"project/key/anninspection/apply",
		"051":"project/entrust/application/apply",
		"053":"project/entrust/endinspection/apply",
		"054":"project/entrust/variation/apply"
	};
	var selectedTab = {
		"011":"application",
		"012":"midinspection",
		"013":"endinspection",
		"014":"variation",
		"015":"anninspection",
		"021":"application",
		"022":"midinspection",
		"023":"endinspection",
		"024":"variation",
		"025":"anninspection",
		"031":"application",
		"033":"endinspection",
		"034":"variation",
		"035":"anninspection",
		"041":"application",
		"042":"midinspection",
		"043":"endinspection",
		"044":"variation",
		"045":"anninspection",
		"051":"application",
		"053":"endinspection",
		"054":"variation"
	};
	var mainFlag = {
		"011":{
			"deal":"0115",
			"receive":"0111",
			"audit":"0112",
			"submit":"0113",
			"review":"0114"},
		"012":{
				"deal":"0125",
				"receive":"0121",
			    "audit":"0122",
			    "submit":"0123"},
		"013":{
				"deal":"0135",
				"receive":"0131",
				"audit":"0132",
				"submit":"0133",
				"review":"0134"},
		"014":{
				"deal":"0145",
				"receive":"0141",
				"audit":"0142",
				"submit":"0143"},
		"015":{
				"deal":"0155",
				"receive":"0151",
				"audit":"0152",
				"submit":"0153"},
		"021":{
				"deal":"0215",
				"receive":"0211",
				"audit":"0212",
				"submit":"0213",
				"review":"0214"},
		"022":{
				"deal":"0225",
				"receive":"0221",
				"audit":"0222",
				"submit":"0223"},
		"023":{
				"deal":"0235",
				"receive":"0231",
				"audit":"0232",
				"submit":"0233",
				"review":"0234"},
		"024":{
				"deal":"0245",
				"receive":"0241",
				"audit":"0242",
				"submit":"0243"},
		"025":{
				"deal":"0255",
				"receive":"0251",
				"audit":"0252",
				"submit":"0253"},
		"031":{
				"deal":"0315",
				"receive":"0311",
				"audit":"0312",
				"submit":"0313",
				"review":"0314"},
		"033":{
				"deal":"0335",
				"receive":"0331",
				"audit":"0332",
				"submit":"0333",
				"review":"0334"},
		"034":{
				"deal":"0345",
				"receive":"0341",
				"audit":"0342",
				"submit":"0343"},
		"035":{
				"deal":"0355",
				"receive":"0351",
				"audit":"0352",
				"submit":"0353"},
		"041":{
				"deal":"0415",
				"receive":"0411",
				"audit":"0412",
				"submit":"0413",
				"review":"0414"},
		"042":{
				"deal":"0425",
				"receive":"0421",
				"audit":"0422",
				"submit":"0423"},
		"043":{
				"deal":"0435",
				"receive":"0431",
				"audit":"0432",
				"submit":"0433",
				"review":"0434"},
		"044":{
				"deal":"0445",
				"receive":"0441",
				"audit":"0442",
				"submit":"0443"},
		"045":{
				"deal":"0455",
				"receive":"0451",
				"audit":"0452",
				"submit":"0453"},
		"051":{
				"deal":"0515",
				"receive":"0511",
				"audit":"0512",
				"submit":"0513",
				"review":"0514"},
		"053":{
				"deal":"0535",
				"receive":"0531",
				"audit":"0532",
				"submit":"0533",
				"review":"0534"},
		"054":{
				"deal":"0545",
				"receive":"0541",
				"audit":"0542",
				"submit":"0543"}
	};
	
	var init = function(){
		$(".b_btn").live("click", function() {
			window.location.href = basePath + managerBusinessUrl[$(this).attr("alt")] + "/toList.action?update=1&mainFlag=" + mainFlag[$(this).attr("alt")][$(this).attr("type")];
			return false;
		});
		$(".c_btn").live("click", function() {
			window.location.href = basePath + managerGrantedUrl[$(this).attr("alt")] + "/toList.action?update=1&mainFlag=" + mainFlag[$(this).attr("alt")][$(this).attr("type")];
			return false;
		});
		$(".d_btn").live("click", function() {
			window.location.href = basePath + teacherReviewProjectUrl[$(this).attr("alt")] + "/toList.action?update=1&mainFlag=" + mainFlag[$(this).attr("alt")][$(this).attr("type")];
			return false;
		});
		$(".e_btn").live("click", function() {
			window.location.href = basePath + teacherBusinessUrl[$(this).attr("alt")] + "/toList.action?update=1&mainFlag=" + mainFlag[$(this).attr("alt")][$(this).attr("type")];
			return false;
		});
	};
	
	exports.init = function() {
		init();
	};

});
