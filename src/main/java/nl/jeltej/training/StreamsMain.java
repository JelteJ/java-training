package nl.jeltej.training;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsMain {

	public static void main(String[] args) throws IOException {
		threeDigit(Optional.empty());

		collectAmountOfWordsByStringLength();

		partitionStringsByLengthEquals();

		walkDirectory();

	}

	// Determine if an int has tree digits
	// Highlights the ease of using streams vs nested ifs if (optionalispresent && string.length == 3)
	private static void threeDigit (Optional<Integer> optional) {
		optional.map(n -> "" + n)
				.filter(s -> s.length() == 3)
				.ifPresent(System.out::println);
	}

	// Get amount of words by length
	private static void collectAmountOfWordsByStringLength() {
		var stringsList = Stream.of("een", "twee", "drie");
		Map<Integer, Long> map = stringsList.collect(
				Collectors.groupingBy(
					String::length,
					Collectors.counting()));
		System.out.println(map);
	}

	private static void partitionStringsByLengthEquals() {
		var stringsList = Stream.of("een", "twee", "drie");
		Map<Boolean, List<String>> map = stringsList.collect(
				Collectors.partitioningBy(s -> s.length() == 4));
		System.out.println(map);
	}

	private static void walkDirectory() throws IOException {
		var rootPath = Path.of("C:\\Users\\Jelte Jaarsma\\Documents\\");
		try(var s = Files.walk(rootPath)) {
			long size = s.parallel()
					.filter(path -> !Files.isDirectory(path))
					.mapToLong(StreamsMain::getSize)
					.sum();
			System.out.format("Size of the dir: %.2f mb", size/10000000.0 );
		}
	}

	public static long getSize(Path path) {
		try {
			return Files.size(path);
		} catch (IOException e) {

		}
		return 0L;
	}
}