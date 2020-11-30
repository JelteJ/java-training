package nl.jeltej.training.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Reflection/reflectie cheatsheet retrieved from: https://pastebin.com/2DtfQHG2
 * Video: https://www.youtube.com/watch?v=_nqPCc9SVwQ
 */
class ReflectionMain {

	public static void main(String[] args) throws Exception {
		/*
		 * If you are using something that is inherited, replace...
		 *
		 * getDeclaredField -> getField
		 * getDeclaredMethod -> getMethod
		 */
		
		Person p = new Person("Pogo");
		
		// System.out.println(p.name); // Error, not accessible.

		try {
			Field field = p.getClass().getDeclaredField("name");
			field.setAccessible(true);
			System.out.println(field.get(p));
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * Field reflection
		 */
		
		// System.out.println(Person.numPeople); // Error, not accessible.
		
		try {
			Field field = Person.class.getDeclaredField("numPeople");
			field.setAccessible(true);
			System.out.println(field.get(null));
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * Method reflection (without args)
		 */
		
		System.out.println(p.getName());
		
		try {
			Method method = p.getClass().getDeclaredMethod("getName");
			System.out.println(method.invoke(p));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * Method reflection (with args)
		 */
		
		p.setName("PogoStick29");
		System.out.println(p.getName());
		p.setName("Pogo");
		
		try {
			Method method = p.getClass().getDeclaredMethod("setName", String.class);
			method.invoke(p, "PogoStick29");
			System.out.println(p.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * Static method reflection
		 */
		
		Person.printPerson(p);
		
		try {
			Method method = Person.class.getDeclaredMethod("printPerson", Person.class);
			method.invoke(null, p); // 1st argument null because it's a static method
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * Constructor reflection
		 */
		
		Person other = new Person("Ogop");
		Person.printPerson(other);
		
		try {
			Constructor<Person> constructor = Person.class.getDeclaredConstructor(String.class);
			other = constructor.newInstance("Ogop");
			Person.printPerson(other);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * Instantiation of a class by class/package name string
		 */
		Class classObject = Class.forName("nl.jeltej.training.reflection.Person");

		Constructor constructor = classObject.getDeclaredConstructor(String.class);
		Object obj = constructor.newInstance("test");

		System.out.println(Arrays.asList(obj.getClass().getDeclaredMethods()));
		System.out.println(obj.getClass());
		System.out.println(obj.toString());
	}
}