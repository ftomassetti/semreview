package it.polito.semreview.cachesystem;

import it.polito.softeng.common.serialization.SerializationStorage;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * Utils class to execute operation which can be cached
 */
public class OperationCaching {

    /**
     * Prevent instantiation.
     */
	private OperationCaching(){
		
	}

    /**
     * If the given file exist the value is loaded from that file and return,
     * otherwise the operation is executed and the result saved to file.
     * @throws Exception any kind of potential error can be thrown by the Operation.
     */
	public static <I extends Serializable, J extends I> I get(Operation<I> operation, File file, Class<J> clazz) throws Exception {
		if (file.exists()) {
			return SerializationStorage.load(file, clazz);
		} else {
			I result = operation.execute();
			File dir = file.getParentFile();
			if (!dir.exists()&&!dir.mkdirs()){
				throw new IOException("Parent dir "+dir+" can not be created");
			}
			SerializationStorage.store(result, file);
			return result;
		}
	}
	
}
