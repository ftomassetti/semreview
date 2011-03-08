package it.polito.semreview.cachesystem;

import it.polito.softeng.common.exceptions.LoadingException;
import it.polito.softeng.common.exceptions.StoringException;
import it.polito.softeng.common.serialization.SerializationStorage;

import java.io.File;
import java.io.Serializable;

public class OperationCaching {

	public static <I extends Serializable, J extends I> I get(Operation<I> operation, File file, Class<J> clazz) throws LoadingException, StoringException{
		if (file.exists()){
			return SerializationStorage.load(file, clazz);
		} else {
			I result = operation.execute();
			SerializationStorage.store(result, file);
			return result;
		}
	}
	
}
