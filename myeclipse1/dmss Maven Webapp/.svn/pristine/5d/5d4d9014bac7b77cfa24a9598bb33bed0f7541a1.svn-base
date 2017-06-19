/**
 * IP地址校验
 */
Ext.apply(Ext.form.field.VTypes, {
    IPAddress:  function(v) {        //校验函数             
        return /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/.test(v);
    },
    IPAddressText: '必须是一个IP地址'
})

/**
 * 身份证校验
 */
Ext.apply(Ext.form.field.VTypes, {
    IDCard:  function(pId, field) {        //校验函数             
         var arrVerifyCode = [1,0,"x",9,8,7,6,5,4,3,2];  
            var Wi = [7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2];  
            var Checker = [1,9,8,7,6,5,4,3,2,1,1];  
            if(pId.length != 15 && pId.length != 18){  
                return false;  
            }  
            var Ai=pId.length==18 ?  pId.substring(0,17)   :   pId.slice(0,6)+"19"+pId.slice(6,16);  
            if (!/^\d+$/.test(Ai)){  
                return false;  
            }  
            var yyyy=Ai.slice(6,10) ,  mm=Ai.slice(10,12)-1  ,  dd=Ai.slice(12,14);  
            var d=new Date(yyyy,mm,dd) ,  now=new Date();  
             var year=d.getFullYear() ,  mon=d.getMonth() , day=d.getDate();  
            if (year!=yyyy || mon!=mm || day!=dd || d>now || year<1940){  
                return false;  
            }  
            for(var i=0,ret=0;i<17;i++)  ret+=Ai.charAt(i)*Wi[i];      
            Ai+=arrVerifyCode[ret %=11];       
            return pId.length ==18 && pId != Ai?false:true;    
    },
    IDCardText: '无效身份证号'
})

/**
*密码格式校验
*/
Ext.apply(Ext.form.field.VTypes, {
    password:  function(v) {        //校验函数             
    	if(v.length<6)
    		return false;
        return /^([\+\-a-zA-Z0-9!]{6,})$/i.test(v);
    },
    passwordText: '密码长度不能小于6位，且为字母和数字'
})

/**
 * 验证两次密码是否相同(用的时候，confirm的内容指向对象id)
 */
Ext.apply(Ext.form.field.VTypes, {
    passwordConfirm:  function(v,field) {        //校验函数             
	    if(field.confirmTo){ 
	        var pwd=Ext.getCmp(field.confirmTo);                   
	        if(v.trim()== pwd.getValue().trim()){ 
	            return true; 
	        } 
	        else { 
	            return false; 
	        } 
	        return false; 
	    }
    },
    passwordConfirmText: '两次输入密码不同'
})

/**
 * 评分
 */
Ext.apply(Ext.form.field.VTypes, {
    rating:  function(v) {        //校验函数             
        return /^[1-5]{1,1}$/g.test(v);
    },
    ratingText: '只能为1-5之间的整数'
})

Ext.apply(Ext.form.field.VTypes, {
    phone:  function(v) {        //校验函数          
        return /^1[3|4|5|8][0-9]\d{4,8}$/.test(v);
    },
    phoneText: '手机号码不合法'
})

Ext.apply(Ext.form.field.VTypes, {
    number:  function(v) {        //校验函数             
        return /[0-9]/.test(v);
    },
    numberText: '不是合法数字'
})

Ext.apply(Ext.form.field.VTypes, {
    username:  function(v) {        //校验函数             
        return /[0-9]/.test(v);
    },
    usernameText: '账号名已存在'
})