package it.polito.softeng.common.serialization;

import static org.junit.Assert.assertEquals;
import it.polito.softeng.common.exceptions.LoadingException;
import it.polito.softeng.common.exceptions.StoringException;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SerializationStorageTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStoreAndLoad() throws IOException, StoringException, LoadingException {
		tryToStoreAndLoad("A String");
		tryToStoreAndLoad(12);
		tryToStoreAndLoad(14.0);
	}

	public void tryToStoreAndLoad(Serializable object) throws IOException, StoringException, LoadingException {		
		File tmpFile = File.createTempFile("testingSerializationStorage", "serializedObject");
		try {
		SerializationStorage.store(object, tmpFile);
		Object loadedObject = SerializationStorage.load(tmpFile, Serializable.class);
		assertEquals(object,loadedObject);
		} finally {
			if (tmpFile.exists()){
				tmpFile.delete();
			}
		}
	}

}
