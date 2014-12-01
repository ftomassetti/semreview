package it.polito.softeng.common.serialization;

import it.polito.softeng.common.exceptions.LoadingException;
import it.polito.softeng.common.exceptions.StoringException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public final class SerializationStorage {
	
	private SerializationStorage(){
		
	}

	public static void store(Serializable object, File file) throws StoringException {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
			out.writeObject(object);
			out.close();
		} catch (IOException e) {
			throw new StoringException(e);
		}

	}
	
	public static <T extends Serializable> T load(File file, Class<T> clazz) throws LoadingException{
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fis);
			Object result = in.readObject();
			in.close();
			if (!clazz.isInstance(result)){
				throw new LoadingException("Not expected class. Expected "+clazz+", actual "+result.getClass());
			}
			return clazz.cast(result);
		} catch (IOException e) {
			throw new LoadingException(e);
		} catch (ClassNotFoundException e) {
			throw new LoadingException(e);
		}

	}
}
