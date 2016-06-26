package com.girija.learning.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.thoughtworks.xstream.XStream;

public class MessageSizeReport {
	
	static List<MessageMetadata>  messageMetdataList = new ArrayList<MessageMetadata>();
	static IdAndName idAndName = Helper.getIdAndName();
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		prepareReport();
		printReport();
	}

	private static void printReport() {
		System.out.printf("%-40s%10s%30s%30s%30s%n", "Format", "Size", "Serialization Time(Nano)", "Deserialization Time(Nano)", "Total Time(Milli)");
		for(MessageMetadata messageMetadata : messageMetdataList){
			System.out.printf("%-40s%10d%30d%30d%30d %n", messageMetadata.getFormat(), messageMetadata.getSize(), messageMetadata.getSerializationTime(), messageMetadata.getDeserializationTime(), (messageMetadata.getSerializationTime() + messageMetadata.getDeserializationTime())/1000000);
		}
	}

	private static void prepareReport() throws IOException, ClassNotFoundException {
		messageMetdataList.add(getMessageMetadataJavaNativeSerialization());
		messageMetdataList.add(getMessageMetadataJSON());
		messageMetdataList.add(getMessageMetadataXML());
		messageMetdataList.add(getMessageMetadataProtoBuf());
	}

	private static MessageMetadata getMessageMetadataProtoBuf() throws InvalidProtocolBufferException {
		IdAndNameOuterClass.IdAndName idAndNameProtoBuf = IdAndNameOuterClass.IdAndName.newBuilder().setId(1).setName("Some Name").build();
		long beforeSerialization = System.nanoTime();
		byte[] serializedMessage = idAndNameProtoBuf.toByteArray();
		long afterSerialization = System.nanoTime();
		long beforeDeserialization = System.nanoTime();
		IdAndNameOuterClass.IdAndName idAndNameTemp = IdAndNameOuterClass.IdAndName.parseFrom(serializedMessage);
		System.out.println("Deserialized message " + idAndNameTemp);
		long afterDeserialization = System.nanoTime();
		return new MessageMetadata(serializedMessage, "Proto-buf", afterSerialization - beforeSerialization, afterDeserialization - beforeDeserialization);
	}

	private static MessageMetadata getMessageMetadataXML() {
		XStream xStream = new XStream();
		long beforeSerialization = System.nanoTime();
		String serializedMessage = xStream.toXML(idAndName);
		long afterSerialization = System.nanoTime();
		long beforeDeserialization = System.nanoTime();
		IdAndName idAndNameTemp = (IdAndName) xStream.fromXML(serializedMessage);
		System.out.println("Deserialized message " + idAndNameTemp);
		long afterDeserialization = System.nanoTime();
		return new MessageMetadata(serializedMessage.getBytes(), "XML", afterSerialization - beforeSerialization, afterDeserialization - beforeDeserialization);
	}

	private static MessageMetadata getMessageMetadataJSON() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		long beforeSerialization = System.nanoTime();
		String serializedMessage = objectMapper.writeValueAsString(idAndName);
		long afterSerialization = System.nanoTime();
		long beforeDeserialization = System.nanoTime();
		IdAndName idAndNameTemp = objectMapper.readValue(serializedMessage, IdAndName.class);
		System.out.println("Deserialized message " + idAndNameTemp);
		long afterDeserialization = System.nanoTime();
		return new MessageMetadata(serializedMessage.getBytes(), "JSON", afterSerialization - beforeSerialization, afterDeserialization - beforeDeserialization);
	}

	private static MessageMetadata getMessageMetadataJavaNativeSerialization() throws IOException, ClassNotFoundException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		ByteArrayInputStream byteArrayInputStream;
	    ObjectInputStream objectInputStream;
	    long beforeSerialization = System.nanoTime();
	    objectOutputStream.writeObject(idAndName);
		long afterSerialization = System.nanoTime();
		byte[] serializedMessage = byteArrayOutputStream.toByteArray();
		byteArrayInputStream = new ByteArrayInputStream(serializedMessage);
		objectInputStream = new ObjectInputStream(byteArrayInputStream);
		long beforeDeserialization = System.nanoTime();
		IdAndName idAndNameTemp = (IdAndName) objectInputStream.readObject();
		System.out.println("Deserialized message " + idAndNameTemp);
		long afterDeserialization = System.nanoTime();
	    return new MessageMetadata(serializedMessage, "Java Serialization", afterSerialization - beforeSerialization, afterDeserialization - beforeDeserialization);
	}
	
	
	
}
