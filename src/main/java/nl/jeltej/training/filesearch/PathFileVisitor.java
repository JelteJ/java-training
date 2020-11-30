package nl.jeltej.training.filesearch;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

class PathFileVisitor implements FileVisitor<Path> {
    private Predicate<Path> condition;
    private List<Path> results = new ArrayList<>();
    private AtomicInteger filesProcessed = new AtomicInteger(0);

    public PathFileVisitor(Predicate<Path> condition) {
        this.condition = condition;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        filesProcessed.incrementAndGet();
        if (this.condition.test(file)) {
            this.results.add(file);
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public int getFilesProcessed() {
        return filesProcessed.get();
    }

    public List<Path> getResults() {
        return List.copyOf(results);
    }
}
