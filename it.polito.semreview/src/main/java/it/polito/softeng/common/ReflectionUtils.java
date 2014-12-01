package it.polito.softeng.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Federico Tomassetti <federico.tomassetti@polito.it>
 */
public final class ReflectionUtils {

	/**
	 * Prevent instantiation.
	 */
	private ReflectionUtils() {

	}

	/**
	 * Return the public methods which are annotated with the given annotation.
	 */
	public static final Iterable<Method> getPublicAnnotatedMethods(
			final Class<? extends Annotation> annotationClass,
			final Class<?> classToExplore) {
		List<Method> methods = new LinkedList<Method>();
		for (Method method : classToExplore.getMethods()) {
			if (method.isAnnotationPresent(annotationClass)) {
				methods.add(method);
			}
		}
		return methods;
	}

	/**
	 * @param <C>
	 * @param clazz
	 * @param requiredParameters
	 * @return
	 * @throws IllegalArgumentException
	 *             if not public constructor with fiven parameteres is found
	 */
	public static final <C> Constructor<C> getPublicConstructor(Class<C> clazz,
			Class<?>... requiredParameters) {
		@SuppressWarnings("unchecked")
		// according to the JavaDoc this cast is safe
		Constructor<C>[] constructors = (Constructor<C>[]) clazz
				.getConstructors();
		for (Constructor<C> aConstructor : constructors) {
			Class<?>[] actualParameterTypes = aConstructor.getParameterTypes();
			if (Arrays.equals(requiredParameters, actualParameterTypes)) {
				return aConstructor;
			}
		}
		throw new IllegalArgumentException("Class " + clazz
				+ " has not a constructor with the parameters "
				+ Arrays.toString(requiredParameters));
	}

	public static final Class<?> tryToFindClass(String className) {
		Class<?> c;
		c = tryToGetClass(className);
		if (c != null)
			return c;
		c = tryToGetClass("java.lang." + className);
		return c;
	}

	public static final Class<?> tryToGetClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public static final List<Method> getAllAccessibleMethods(Class<?> clazz) {
		List<Method> methods = new LinkedList<Method>();
		for (Method publicMethod : clazz.getMethods()) {
			methods.add(publicMethod);
		}
		for (Method method : clazz.getDeclaredMethods()) {
			if (!methods.contains(method)) {
				methods.add(method);
			}
		}
		return methods;
	}
}
