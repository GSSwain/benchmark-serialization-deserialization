package com.girija.learning.serialization;

public class MessageMetadata {
	byte[] message;
	String format;
	Long serializationTime;
	Long deserializationTime;
	
	public MessageMetadata(byte[] message, String format, Long serializationTime,
	Long deserializationTime) {
		this.message = message;
		this.format = format;
		this.serializationTime = serializationTime;
		this.deserializationTime = deserializationTime;
	}
	
	public int getSize() {
		return this.message.length;
	}
	
	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}

	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}

	public Long getSerializationTime() {
		return serializationTime;
	}

	public void setSerializationTime(Long serializationTime) {
		this.serializationTime = serializationTime;
	}

	public Long getDeserializationTime() {
		return deserializationTime;
	}

	public void setDeserializationTime(Long deserializationTime) {
		this.deserializationTime = deserializationTime;
	}
	
}
