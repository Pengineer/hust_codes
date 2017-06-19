function selectPerson(data, check, label){
	if(check){
		var array1 = document.getElementsByName('entityid');
		var array2 = document.getElementsByName('name');
		var re = new Array(2);
		var t = array1.length;
		for(var i = 0; i < array1.length; i++) {
			if(array1[i].value != data) {
				array1[i].checked = false;
			} else {
				t = i;
				break;
			}
		}
		if (t < array1.length) {
			re[0] = array1[t].value;
			re[1] = array2[t].innerHTML;
			if (confirm("您确定选择此项吗？")) {
				window.returnValue = re;
				window.close();
			}
		}
	}
}

function selectOneDirector(data, check) {
	if(check) {
		var array1 = document.getElementsByName('selectDirectorId');
		var array2 = document.getElementsByName('name');
		var array3 = document.getElementsByName('specialityTitle');
		var array4 = document.getElementsByName('major');
		var re = new Array(4);
		var t = array1.length;
		for(var i = 0; i < array1.length; i++) {
			if(array1[i].value != data) {
				array1[i].checked = false;
			} else {
				t = i;
			}
		}
		if (t < array1.length) {
			re[0] = array1[t].value;
			re[1] = array2[t].innerHTML;
			re[2] = array3[t].value;
			re[3] = array4[t].value;
			if (confirm("您确定选择此项吗？")) {
				window.returnValue = re;
				window.close();
			}
		}
	}
}

function selectOneProduct(data,check){
	if(check) {
		var array1 = document.getElementsByName('selectProductId');
		var array2 = document.getElementsByName('name');
		var re = new Array(2);
		var t = array1.length;
		for(var i = 0; i < array1.length; i++) {
			if(array1[i].value != data) {
				array1[i].checked = false;
			} else {
				t = i;
			}
		}
		if (t < array1.length) {
			re[0] = array1[t].value;
			re[1] = array2[t].innerHTML;
			if (confirm("您确定选择此项吗？")) {
				window.returnValue = re;
				window.close();
			}
		}
	}
}

//弹出层选择学科类别
function selectDisciplines(id,name,check){
	var ids = document.getElementById("id").value;
	var names= document.getElementById("name").value;
	if(check){
		if(ids !=""){
			ids +=";"+id;
			names +=";"+name;
		}else{
			ids += id;
			names +=name;
		}
		document.getElementById("id").value=ids;
		document.getElementById("name").value=names;
	}else if(!check){
		var arr1=ids.split(";");
		var arr2=names.split(";");
		if(arr1.length > 0){
			for (var i=0;i<arr1.length;i++){
				if(arr1[i] == id){
					arr1.splice(i,1);
					break;
				}
			}
		}
		if(arr2.length > 0){
			for(var j=0;j<arr2.length;j++){
				if(arr2[j] == name){
					arr2.splice(j,1);
				}
			}
		}
		ids = arr1.join(";");
		names = arr2.join(";");
		document.getElementById("id").value=ids;
		document.getElementById("name").value=names;
	}
}

//弹出层选择一个并返回数据
function selectOne(label){
	var array1 = document.getElementsByName('entityid');
	var array2 = document.getElementsByName('name');
	var array3 = document.getElementsByName('entityId');
	var re = new Array(2);
	for(var i = 0; i < array1.length; i++) {
		if(array1[i].checked) {
			parent.document.getElementById("subjectionId").value;
			parent.document.getElementById("subjectionName").value=array2[i].innerHTML;
			break;
		}
	}
}