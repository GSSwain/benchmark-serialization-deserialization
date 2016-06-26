package com.girija.learning.serialization;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

public class JavaSerializationTest {
	IdAndName idAndName = Helper.getIdAndName();
	ByteArrayOutputStream byteArrayOutputStream;
    ObjectOutputStream objectOutputStream;
	ByteArrayInputStream byteArrayInputStream;
    ObjectInputStream objectInputStream;
    byte[] expectedByteArray = new byte[]{-84, -19, 0, 5, 115, 114, 0, 43, 99, 111, 109, 46, 103, 105, 114, 105, 106, 97, 46, 108, 101, 97, 114, 110, 105, 110, 103, 46, 115, 101, 114, 105, 97, 108, 105, 122, 97, 116, 105, 111, 110, 46, 73, 100, 65, 110, 100, 78, 97, 109, 101, 89, -40, -15, 125, -45, 41, -40, 31, 2, 0, 2, 73, 0, 2, 105, 100, 76, 0, 4, 110, 97, 109, 101, 116, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 120, 112, 0, 0, 0, 1, 116, 0, 9, 83, 111, 109, 101, 32, 78, 97, 109, 101};
    
    {
    	byteArrayOutputStream = new ByteArrayOutputStream();
    	byteArrayInputStream = new ByteArrayInputStream(expectedByteArray);
    	try {
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @Test
	public void testSerialization() throws IOException {
		objectOutputStream.writeObject(idAndName);
		byte[] bytes = byteArrayOutputStream.toByteArray();
		assertThat(bytes, equalTo(expectedByteArray));
	}
	
	@Test
	public void testDeserialization() throws ClassNotFoundException, IOException{
		IdAndName idAndNameTemp = (IdAndName) objectInputStream.readObject();
		assertThat(idAndNameTemp, notNullValue());
		assertThat(idAndNameTemp.getId(), equalTo(idAndName.getId()));
		assertThat(idAndNameTemp.getName(), equalTo(idAndName.getName()));
	}

}
