package com.taotao.common.pojo;

/**
 * EasyUI树形控件节点
 * 
 * @author Alan Wang
 *
 */
public class EUTreeNode {
	private long id;
	private String text;
	private String state;

	public EUTreeNode() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "EUTreeNode [id=" + id + ", text=" + text + ", state=" + state + "]";
	}
}
