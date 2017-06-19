/**
 * 权限树
 */
Ext.define('app.sm.security.RightTree', {
    extend: 'Ext.tree.Panel',    
    id:'rightTree',
    requires: [
        'Ext.data.TreeStore'
    ],
    xtype: 'check-tree', 
    rootVisible: false,
    useArrows: true,
    title: '选择权限',
    width: 300,
    height: 360,
    formType:'toAdd', 
    
    initComponent: function(){
		this.store = Ext.create('Ext.data.TreeStore', {
	        proxy: {
	            type: 'ajax',
	            url: basePath+'sm/security/role/'+this.formType+'/rightTree?roleId='+this.roleId
	        }
        });
       
        this.addListener("checkchange",this.checkChangeHandler);
        this.callParent();
    },
    
    /**
     * checkbox事件监听
     * @param {} node
     * @param {} checked
     */
    checkChangeHandler:function(node,checked){
    	if(checked){         
    		 node.eachChild(function (child) {
    		                 chd(child,true);        
    		 });       
    	}else{        
    		 node.eachChild(function (child) {   
    		             chd(child,false);        
    		 });       
    	}            
    },
    

    onCheckedNodesClick: function(){
        var records = this.getView().getChecked(),
            names = [];
                   
        Ext.Array.each(records, function(rec){
            names.push(rec.get('text'));
        });
                    
        Ext.MessageBox.show({
            title: 'Selected Nodes',
            msg: names.join('<br />'),
            icon: Ext.MessageBox.INFO
        });
    }
})