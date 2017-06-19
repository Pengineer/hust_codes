package org.csdc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * ExtJS树节点
 * @author jintf
 * @date 2014-6-15
 */
public class TreeNode {
	private String id; //节点ID
	private String text; //节点文本
	private boolean leaf; //是否是叶子
	private List<TreeNode> children = new ArrayList<TreeNode>(); //子节点
	
	public TreeNode(String id,String text){
		this.id = id;
		this.text = text;
	}
	
	public TreeNode(String id,String text,boolean leaf){
		this.id = id;
		this.text = text;
		this.leaf = leaf;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	
}
