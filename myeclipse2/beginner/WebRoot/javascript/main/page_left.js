// ========================================================================
// 文件名：page_left.js
//
// 文件说明：
//     本文件主要实现left帧菜单的展开与隐藏
// 
// 历史记录：
// 2010-01-28 龚凡  创建文件。
// 2010-04-09 陈根鹏   添加是否要定制个人主页的效果
// ========================================================================

function showmenu(menu){
	var ob;
	/* 先检查是否展开三级菜单 */
	if (menu === "usermanage") {
		ob = document.getElementById("user");
		ob.style.display = "block";
		ob = document.getElementById("roleright");
		ob.style.display = "none";
	} else if (menu === "rolerightmanage") {
		ob = document.getElementById("roleright");
		ob.style.display = "block";
		ob = document.getElementById("user");
		ob.style.display = "none";
	} else {
		/* 先关闭所有菜单 */
		ob = document.getElementById("user");
		if (ob != null) {
			ob.style.display = "none";
		}
		ob = document.getElementById("roleright");
		if (ob != null) {
			ob.style.display = "none";
		}
		ob = document.getElementById("usermanage");
		if (ob != null) {
			ob.style.display = "none";
		}
		ob = document.getElementById("rolerightmanage");
		if (ob != null) {
			ob.style.display = "none";
		}
		ob = document.getElementById("self");
		if (ob != null) {
			ob.style.display = "none";
		}
		ob = document.getElementById("static");
		if (ob != null) {
			ob.style.display = "none";
		}
		ob = document.getElementById("business");
		if (ob != null) {
			ob.style.display = "none";
		}
		/* 再展开指定的菜单 */
		if (menu === "systemmanage") {
			ob = document.getElementById("usermanage");
			if (ob != null) {
				ob.style.display = "block";
			}
			ob = document.getElementById("rolerightmanage");
			if (ob != null) {
				ob.style.display = "block";
			}
		} else {
			ob = document.getElementById(menu);
			if (ob != null) {
				ob.style.display = "block";
			}
		}
		return true;
	}
}