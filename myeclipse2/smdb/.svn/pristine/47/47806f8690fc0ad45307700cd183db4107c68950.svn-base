define(function(require, exports, module) {
	var teacherBusinessUrl = {
		"012":"project/general/midinspection/apply",
		"013":"project/general/endinspection/apply",
		"014":"project/general/variation/apply",
		"022":"project/instp/midinspection/apply",
		"023":"project/instp/endinspection/apply",
		"024":"project/instp/variation/apply",
		"033":"project/post/endinspection/apply",
		"034":"project/post/variation/apply"
	};
	var teacherReviewProjectUrl = {
		"013":"project/general/endinspection/review",
		"023":"project/instp/endinspection/review",
		"033":"project/post/endinspection/review"
	};
	var managerBusinessUrl = {
		"012":"project/general/midinspection/apply",
		"013":"project/general/endinspection/apply",
		"014":"project/general/variation/apply",
		"022":"project/instp/midinspection/apply",
		"023":"project/instp/endinspection/apply",
		"024":"project/instp/variation/apply",
		"033":"project/post/endinspection/apply",
		"034":"project/post/variation/apply"
	};
	var selectedTab = {
		"012":"midinspection",
		"013":"endinspection",
		"014":"variation",
		"022":"midinspection",
		"023":"endinspection",
		"024":"variation",
		"033":"endinspection",
		"034":"variation"
	};
	var mainFlag = {
		"012":{"receive":"0121","audit":"0122"},
		"013":{"receive":"0131","audit":"0132","review":"0134"},
		"014":{"receive":"0141","audit":"0142"},
		"022":{"receive":"0221","audit":"0222"},
		"023":{"receive":"0231","audit":"0232","review":"0234"},
		"024":{"receive":"0241","audit":"0242"},
		"033":{"receive":"0331","audit":"0332","review":"0334"},
		"034":{"receive":"0341","audit":"0342"}
	};
	
	var init = function(){
		$(".a_btn").bind("click", function() {
			window.location.href = basePath + teacherBusinessUrl[$(this).attr("alt")] + "/toView.action?entityId=" + $(this).parent().next().val() + "&listType=" + 6 + "&selectedTab=" + selectedTab[$(this).attr("alt")];
			return false;
		});
		$(".b_btn").bind("click", function() {
			window.location.href = basePath + managerBusinessUrl[$(this).attr("alt")] + "/toList.action?update=1&mainFlag=" + mainFlag[$(this).attr("alt")][$(this).attr("type")];
			return false;
		});
		$(".c_btn").bind("click", function() {
			window.location.href = basePath + "project/general/application/granted/toList.action?update=1&mainFlag=" + mainFlag[$(this).attr("alt")][$(this).attr("type")];
			return false;
		});
		$(".d_btn").bind("click", function() {
			window.location.href = basePath + teacherReviewProjectUrl[$(this).attr("alt")] + "/toList.action?update=1&mainFlag=" + mainFlag[$(this).attr("alt")][$(this).attr("type")];
			return false;
		});
	};
	
	exports.init = function() {
		init();
	};

});
