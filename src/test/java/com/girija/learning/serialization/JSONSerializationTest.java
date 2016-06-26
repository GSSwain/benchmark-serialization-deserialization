package com.girija.learning.serialization;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONSerializationTest {
	
	ObjectMapper objectMapper = new ObjectMapper();
	IdAndName idAndName = Helper.getIdAndName();
	String expectedContent = "{\"id\":1,\"name\":\"Some Name\"}";
	
	@Test
	public void testSerialization() throws JsonProcessingException{
		String str = objectMapper.writeValueAsString(idAndName);
		assertThat(str, equalTo(expectedContent));
	}
	
	@Test
	public void testDeserialization() throws JsonParseException, JsonMappingException, IOException{
		IdAndName idAndNameTemp = objectMapper.readValue(expectedContent, IdAndName.class);
		assertThat(idAndNameTemp, notNullValue());
		assertThat(idAndNameTemp.getId(), equalTo(idAndName.getId()));
		assertThat(idAndNameTemp.getName(), equalTo(idAndName.getName()));
	}

}
