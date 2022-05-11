package repository.XMLRepositories;

import domain.Book;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
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

public class BookXMLRepository extends InMemoryRepository<Long, Book> {
    private final String fileName;

    public BookXMLRepository(Validator<Book> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        this.loadData();
    }

    public Book extractEntity(Element element) {
        String id = element.getAttribute("id");
        NodeList nods = element.getChildNodes();
        String name = element.getElementsByTagName("name")
                .item(0)
                .getTextContent();
        String author = element.getElementsByTagName("author")
                .item(0)
                .getTextContent();
        String price = element.getElementsByTagName("price")
                .item(0)
                .getTextContent();

        Book book = new Book(name, author, Double.parseDouble(price));
        book.setId(Long.parseLong(id));
        return book;
    }

    public Element createElementFromEntity(Document document, Book book) {
        Element e = document.createElement("Book");
        e.setAttribute("id", book.getId().toString());

        Element name = document.createElement("name");
        name.setTextContent(book.getName());
        e.appendChild(name);

        Element author = document.createElement("author");
        author.setTextContent(book.getAuthor());
        e.appendChild(author);

        Element price = document.createElement("price");
        price.setTextContent(book.getPrice().toString());
        e.appendChild(price);

        return e;
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
