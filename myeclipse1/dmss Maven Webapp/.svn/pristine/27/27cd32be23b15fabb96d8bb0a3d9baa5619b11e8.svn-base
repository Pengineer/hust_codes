/**
 * 集群概览面板
 */
Ext.define('app.sm.cluster.ImageModel', {
    extend: 'Ext.data.Model',
    fields: [
       {name: 'name'},
       {name: 'host'},
       {name: 'url'}
    ]
});
    
Ext.define('app.sm.cluster.Overview',{
	extend: 'Ext.panel.Panel',
	title: '集群概要',   
	id:'overview',
	autoScroll:true,
	
	selectedNode:'',
	initComponent: function(){
		//Nginx集群
		var nginxStore = Ext.create('Ext.data.Store', {
        	model: 'app.sm.cluster.ImageModel',
        	proxy: {
	            type: 'ajax',
	            fields: ['name', 'host','url'],
	            url: basePath+'sm/cluster/overview/nginxNodes',
	            reader: {
	                type: 'json',
	                root: ''
	            }
	        }
	    });
	    nginxStore.load();
		var nginxItems = Ext.create('Ext.view.View', {
            store: nginxStore,
            tpl: [
                '<tpl for=".">',
                    '<div class="thumb-wrap" id="{name:stripTags}">',
                        '<div class="thumb"><img src="{url}" title="{name:htmlEncode}"></div>',
                        '<span class="x-editable">{name:htmlEncode}</span>',
                        '<span class="x-editable">{host:htmlEncode}</span>',
                    '</div>',
                '</tpl>',
                '<div class="x-clear"></div>'
            ],
            multiSelect: true,
            height: 150,
            trackOver: true,
            itemSelector: 'div.thumb-wrap',
            listeners: {
            	scope:this,
                itemcontextmenu: this.onRightCilckHandler
            }
		});
		//hadoop集群
		var hadoopStore = Ext.create('Ext.data.Store', {
        	model: 'app.sm.cluster.ImageModel',
        	proxy: {
	            type: 'ajax',
	            fields: ['name', 'host','url'],
	            url: basePath+'sm/cluster/overview/hadoopNodes',
	            reader: {
	                type: 'json',
	                root: ''
	            }
	        }
	    });
	    hadoopStore.load();
		var hadoopItems = Ext.create('Ext.view.View', {
            store: hadoopStore,
            tpl: [
                '<tpl for=".">',
                    '<div class="thumb-wrap" id="{name:stripTags}">',
                        '<div class="thumb"><img src="{url}" title="{name:htmlEncode}"></div>',
                        '<span class="x-editable">{name:htmlEncode}</span>',
                        '<span class="x-editable">{host:htmlEncode}</span>',
                    '</div>',
                '</tpl>',
                '<div class="x-clear"></div>'
            ],
            multiSelect: true,
            height: 150,
            trackOver: true,
            itemSelector: 'div.thumb-wrap',
            listeners: {
            	scope:this,
                itemcontextmenu: this.onRightCilckHandler
            }
		});
		//solr集群
		var solrStore = Ext.create('Ext.data.Store', {
        	model: 'app.sm.cluster.ImageModel',
        	proxy: {
	            type: 'ajax',
	            fields: ['name', 'host','url'],
	            url: basePath+'sm/cluster/overview/solrNodes',
	            reader: {
	                type: 'json',
	                root: ''
	            }
	        }
	    });
	    solrStore.load();
		var solrItems = Ext.create('Ext.view.View', {
            store: solrStore,
            tpl: [
                '<tpl for=".">',
                    '<div class="thumb-wrap" id="{name:stripTags}">',
                        '<div class="thumb"><img src="{url}" title="{name:htmlEncode}"></div>',
                        '<span class="x-editable">{name:htmlEncode}</span>',
                        '<span class="x-editable">{host:htmlEncode}</span>',
                    '</div>',
                '</tpl>',
                '<div class="x-clear"></div>'
            ],
            multiSelect: true,
            height: 150,
            trackOver: true,
            itemSelector: 'div.thumb-wrap',
            listeners: {
            	scope:this,
                itemcontextmenu: this.onRightCilckHandler
            }
		});
		//tomcat集群
		var tomcatStore = Ext.create('Ext.data.Store', {
        	model: 'app.sm.cluster.ImageModel',
        	proxy: {
	            type: 'ajax',
	            fields: ['name', 'host','url'],
	            url: basePath+'sm/cluster/overview/tomcatNodes',
	            reader: {
	                type: 'json',
	                root: ''
	            }
	        }
	    });
	    tomcatStore.load();
		var tomcatItems = Ext.create('Ext.view.View', {
            store: tomcatStore,
            tpl: [
                '<tpl for=".">',
                    '<div class="thumb-wrap" id="{name:stripTags}">',
                        '<div class="thumb"><img src="{url}" title="{name:htmlEncode}"></div>',
                        '<span class="x-editable">{name:htmlEncode}</span>',
                        '<span class="x-editable">{host:htmlEncode}</span>',
                    '</div>',
                '</tpl>',
                '<div class="x-clear"></div>'
            ],
            multiSelect: true,
            height: 150,            
            trackOver: true,
            itemSelector: 'div.thumb-wrap',
            listeners: {
            	scope:this,
                itemcontextmenu: this.onRightCilckHandler
            }
		});
		
       	this.items = [
       		{html:'<span>HADOOP集群</span>'},hadoopItems,
       		{html:'<span>SOLRCLOUD集群</span>'},solrItems,
       		{html:'<span>TOMCAT集群</span>'},tomcatItems,
       		{html:'<span>NGINX服务器</span>'},nginxItems
       	];
       	
       	
		this.addListener('afterrender',this.doAfterRender,this); 
	    this.callParent();	 
    	return this;
	},
	
	doAfterRender:function(){
		
	},
	
	onRightCilckHandler: function (view, record, item, index, e, eOpts ){
		e.preventDefault();
		this.selectedNode = record.data.name;
		var menu = Ext.create('Ext.menu.Menu',{
			items:[
				{text: '状态',handler: this.showServerStatus,scope:this},
				{text: '监控',handler: this.showServerStatistic,scope:this},
				{text: '参数',handler: this.showServerParams,scope:this}
			]
		});
		menu.showAt(e.getXY());
	},
	
	showServerStatus:function(){
		var win = Ext.create('app.sm.cluster.overview.ServerStatus',{nodeName:this.selectedNode});
		win.show();
	},
	
	showServerStatistic: function(){
		if(this.selectedNode.indexOf('tomcat') != -1){
			var win = Ext.create('app.sm.cluster.overview.TomcatMonitor',{nodeName:this.selectedNode});
			win.show();
		}
	},
	
	showServerParams: function(){
		
	}
	
	
	
	
});