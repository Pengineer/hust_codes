/*
 *@author fengzq 
 *此js文件用来存放公共的控制部分
 */

$(document).ready(function(){
	/*添加下拉框可选项*/
	var genderCounts=0;
	var ethnicCounts=0;
	var membershipCounts=0;
	var educationCounts=0;
	arr1 = new Array('男','女');
	arr2 = new Array('汉族','蒙古族','彝族','侗族','哈萨克族',
			'畲族','纳西族','仫佬族','仡佬族','怒族','保安族',
			'鄂伦春族','回族','壮族','瑶族','傣族','高山族',
			'景颇族','羌族','锡伯族','乌孜别克族','裕固族','赫哲族',
			'藏族','布依族','白族','黎族','拉祜族','柯尔克孜族','布朗族',
			'阿昌族','俄罗斯族','京族','门巴族','维吾尔族','朝鲜族',
			'土家族','傈僳族','水族','土族','撒拉族','普米族','鄂温克族',
			'塔塔尔族','珞巴族','苗族','满族','哈尼族','佤族','东乡族',
			'达斡尔族','毛南族','塔吉克族','德昂族','独龙族','基诺族');
	arr3 = new Array('中共党员','中共预备党员','共青团员','民革党员',
			'民盟盟员','民建会员','民进会员','农工党党员','致公党党员',
			'九三学社社员','台盟盟员','无党派民主人士','群众');
	arr4 = new Array('本科','硕士','博士');
	genderCounts=arr1.length;
	ethnicCounts=arr2.length;
	membershipCounts=arr3.length;
	educationCounts=arr4.length;
	$("#gender").focus(function() {
		for(var i=0; i<genderCounts; i++) { 
			$("#gender")[0].options[i] = new Option(arr1[i],arr1[i]);
		}
	});
	$("#ethnic").focus(function() {
		for(var i=0; i<ethnicCounts; i++) { 
			$("#ethnic")[0].options[i] = new Option(arr2[i],arr2[i]);
		}
	});
	$("#membership").focus(function() {
		for(var i=0; i<membershipCounts; i++) { 
			$("#membership")[0].options[i] = new Option(arr3[i],arr3[i]);
		}
	});
	$("#education").focus(function() {
		for(var i=0; i<educationCounts; i++) { 
			$("#education")[0].options[i] = new Option(arr4[i],arr4[i]);
		}
	});
})