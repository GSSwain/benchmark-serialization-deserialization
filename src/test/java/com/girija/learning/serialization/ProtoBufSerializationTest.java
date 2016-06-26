package com.girija.learning.serialization;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ProtoBufSerializationTest {
	
	IdAndNameOuterClass.IdAndName.Builder idAndNameBuilder = IdAndNameOuterClass.IdAndName.newBuilder();
	IdAndNameOuterClass.IdAndName idAndName;
	byte[] expectedMessageInBytes = new byte[]{10, 9, 83, 111, 109, 101, 32, 78, 97, 109, 101, 16, 1};
	
	{
		idAndName = idAndNameBuilder.setId(1).setName("Some Name").build();
	}
	
	@Test
	public void testSerialization() throws JsonProcessingException{
		byte[] bytes = idAndName.toByteArray();
		assertThat(bytes, equalTo(expectedMessageInBytes));
	}
	
	@Test
	public void testDeserialization() throws JsonParseException, JsonMappingException, IOException{
		IdAndNameOuterClass.IdAndName idAndNameTemp = IdAndNameOuterClass.IdAndName.parseFrom(expectedMessageInBytes);
		assertThat(idAndNameTemp, notNullValue());
		assertThat(idAndNameTemp.getId(), equalTo(idAndName.getId()));
		assertThat(idAndNameTemp.getName(), equalTo(idAndName.getName()));
	}
}
