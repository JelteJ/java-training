package nl.jeltej.training;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Predicate;

public class Main implements Closeable {

	public static void main(String[] args) throws Exception {
		try(Main man = new Main()) {
			// code die mogelijk een exception gooit

			// throw VerySpecificError()
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	static void usePredicate(Predicate<String> predicate, String thing) {
		System.out.println("Testing:");
		predicate.test(thing);
	}

	@Override
	public void close() throws IOException {

	}
}


/// Code crawler ///
// CodeSearchRequest
// 
// FileCrawler (recursive dir crawler)
// FileProcessQueue
// FileParseCondition
// FileReader