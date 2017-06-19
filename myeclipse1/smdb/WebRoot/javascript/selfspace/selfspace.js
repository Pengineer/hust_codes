function display(oj){
	var div1 = document.getElementById("bottom1");
	var div2 = document.getElementById("bottom2");
	if(oj.indexOf("1")>0){
		div1.style.display="block";
		div2.style.display="none";
	}else if (oj.indexOf("2")>0){
		div1.style.display="none";
		div2.style.display="block";
	}
}