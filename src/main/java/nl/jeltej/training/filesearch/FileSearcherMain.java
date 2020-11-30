package nl.jeltej.training.filesearch;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileSearcherMain {

	public static void main(String[] args) throws IOException {
		FileSearcherMain fileSearcher = new FileSearcherMain();
		long timeInMillis = Calendar.getInstance().getTimeInMillis();
		Path rootPath = Path.of("C:\\Users\\");
		final String finalQuery = "stream";

		List<Path> files = fileSearcher.searchInDirectory(rootPath, finalQuery);

		System.out.println("Duration: " + (Calendar.getInstance().getTimeInMillis() - timeInMillis));

		files.forEach(path -> System.out.println(path.toAbsolutePath()));
	}

	public List<Path> searchInDirectory(final Path rootPath, final String query) throws IOException {

		final PathFileVisitor visitor = new PathFileVisitor(path -> path.getFileName().toString().endsWith(".java"));

		Files.walkFileTree(rootPath, visitor);

			return visitor.getResults().stream()
					.parallel()
					.filter(path -> isFileMatch(query, path))
					.collect(Collectors.toList());
	}

	private boolean isFileMatch(String query, Path path) {
		if (path.getFileName().toString().contains(query)) {
			return true;
		}

		try (Stream<String> fileStream = Files.lines(path)) {
			return fileStream
					.parallel()
					.anyMatch(s ->
							s.toLowerCase().contains(query));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}