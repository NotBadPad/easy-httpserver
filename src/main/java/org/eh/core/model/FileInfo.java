package org.eh.core.model;

public class FileInfo {
	
	private String filename;	//文件名
	private String fieldname; // 表单中参数名
	private int length;	//文件长度
	private byte[] bytes;	//文件字节数组
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	
	
}
