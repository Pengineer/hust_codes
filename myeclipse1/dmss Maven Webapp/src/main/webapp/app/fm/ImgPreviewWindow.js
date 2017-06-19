/**
 * 图片预览窗口
 */
Ext.define('app.fm.ImgPreviewWindow',{
	extend:'Ext.window.Window',
	documentId: '',//保存文件Id
	title:'文件预览',
	width:500,
	height: 500,
	thisUrl:'',
	thisString: '',
	pack: 'center',
	layout:'fit',//设置窗口内部布局
	style:{
	    'display': 'block',
	    'margin': 'auto'
	},
	listeners: {
        'afterlayout': function () {
        	//var myimg = Ext.get('myimg');
          //  myimg.alignTo('ImgPreviewWindow', 'c-c');
            //myimg.setSrc("http://www.sencha.com/img/20110215-feat-html5.png")
            
        }
    },
    

	plain:true,//true则主体背景透明，false则和主体背景有些差别
	collapsible:true,//是否可收缩
	modal:true,//是否为模式窗体,
	//html: '<img id="myimg" src="' + '' + '"/>',
	
	constructor: function(config){
		Ext.apply(this,config);		
		this.initConfig(config);
		this.callParent([config]);
		return this;
	},
	
	initComponent: function(){
		//根据传来的参数是图片或者string来创建不同的内容
		if('' == this.thisUrl && '' != this.thisString){
			this.html = this.thisString;
		}else{
			var changingImage = Ext.create('Ext.Img', {
				    src: this.thisUrl
				   //renderTo: Ext.getBody()
				});
			this.items = [changingImage];
		}
		//Ext.get('myimg').dom.src = "http://www.sencha.com/img/20110215-feat-html5.png";
	    this.callParent();
    	return this;
	},
	
	close:function(){
		this.hide();
	}
});

