/**
 * 文件说明：网站导航数据加载
 * 
 * 历史记录：
 * 2014-07-09	wangyi	创建文件。
 */


/**
 * 方法说明：生成二级以上导航
 * @param obj 二级以上导航菜单的子菜单对象
 * @return genHtml 二级以上导航html字符串
 * @author wangyi
 */
/*
var genNav = function (obj) {
	var genHtml = "<ul class='dropdown-menu'>";
	$.each(obj, function (i) {
		if (obj[i].sub) {
			genHtml += "<li class='dropdown-submenu'><a href='javascript:void(0);'>" + obj[i].name + "</a>" + genNav(obj[i].sub);
		} else {
			genHtml += "<li><a href=" + obj[i].href + ">" + obj[i].name + "</a>";
		}
		genHtml += "</li>";
	});
	genHtml += "</ul>";
	return genHtml;
};*/

/**
 * 方法说明：生成导航一级菜单
 * @param obj 导航一级菜单对象
 * @return genFHtml 导航一级菜单html字符串
 * @author wangyi
 */
/*var genFnav = function (obj) {
	var genFHtml = "";
	$.each(obj, function (i) {
		obj[i].href = obj[i].href || "javascript:void(0);";
		genFHtml += "<li><a class='fnav' href=" + obj[i].href + ">" + obj[i].name + "</a>";
		if (obj[i].sub) {
			genFHtml += genNav(obj[i].sub);
		}
		genFHtml += "</li>";
	});
	return genFHtml;
};
*/
/**
 * 方法说明：登录表单提交前的校验方法
 * @author wangyi
 */
function validate() {
	$.ajax({
		type: "post",
		url: "preLogin",
		data: $form_login.serializeArray(),
		dataType: "json",
		success: function(jsonData) {
			if (jsonData.success === true) {
				$form_login.submit();
			} else {
				$form_login.find(".alert").remove();
				$form_login.prepend("<div role='alert' class='alert alert-danger'>" + jsonData.msg + "</div>");
				$btn_submit.button('reset');
				return false;
			}
		}
	});
}

var $form_login = $(".form-signin");
var $btn_submit = $(".btn-submit");

$(document).ready(function () {
	$btn_submit.on("click", function() {
		$btn_submit.button('loading');
		validate();
	});
	$form_login.keydown(function(event) {
		if (event.keyCode == "13") {//keyCode=13是回车键
			$btn_submit.click();
		}
	});
	/*$(".nav").append(genFnav(json_nav));*/
	//在title属性中配置：可引用html语句
	$("#weixin-icon a").tooltip({html:true});
});

/**
 * 方法说明：全局搜索前判断输入是否为空
 * @author liujia
 */
$('#searchButton').on('click',function(){
	if($('#searchButton').parent().prev().val()){
		$('#searchButton').closest('form').submit();
	} else {
		$('#searchModal').modal('show');
		$('#searchModal').on('hidden.bs.modal', function (e) {});
	}
});