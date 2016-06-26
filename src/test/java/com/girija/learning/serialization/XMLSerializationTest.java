package com.girija.learning.serialization;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.thoughtworks.xstream.XStream;

public class XMLSerializationTest {
	XStream xStream = new XStream();
	IdAndName idAndName = Helper.getIdAndName();
	String expectedContent = "<IdAndName>\n  <id>1</id>\n  <name>Some Name</name>\n</IdAndName>";
	
	{
		xStream.alias("IdAndName", IdAndName.class);
	}
	
	@Test
	public void testSerialization() throws JsonProcessingException{
		String str = xStream.toXML(idAndName);
		assertThat(expectedContent, equalTo(str));
	}
	
	@Test
	public void testDeserialization() throws JsonParseException, JsonMappingException, IOException{
		IdAndName idAndNameTemp = (IdAndName) xStream.fromXML(expectedContent); 
		assertThat(idAndNameTemp, notNullValue());
		assertThat(idAndNameTemp.getId(), equalTo(idAndName.getId()));
		assertThat(idAndNameTemp.getName(), equalTo(idAndName.getName()));
	}
}
