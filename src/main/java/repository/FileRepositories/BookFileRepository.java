package repository.FileRepositories;

import domain.Book;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import repository.InMemoryRepository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class BookFileRepository extends InMemoryRepository<Long, Book> {
    private final String fileName;

    public BookFileRepository(Validator<Book> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        this.loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String name = items.get(1);
                String author = items.get(2);
                Double price = Double.parseDouble(items.get((3)));

                Book book = new Book(name, author, price);
                book.setId(id);

                try {
                    super.save(book);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Book> save(Book book) throws ValidatorException {
        Optional<Book> optional = super.save(book);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(book);
        return Optional.empty();
    }

    private void saveToFile(Book entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.newLine();
            bufferedWriter.write(entity.getId() + "," + entity.getName() + "," + entity.getAuthor() + "," + entity.getPrice());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
