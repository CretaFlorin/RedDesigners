package repository.XMLRepositories;

import domain.Book;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import repository.InMemoryRepository;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BookXMLRepository extends InMemoryRepository<Long, Book> {
    String fileName;

    public BookXMLRepository(Validator<Book> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private Book createBookFromElement(Element bookElement) {
        Node nameNode = bookElement.getElementsByTagName("name").item(0);
        Node authorNode = bookElement.getElementsByTagName("author").item(0);
        Node priceNode = bookElement.getElementsByTagName("price").item(0);
        Book book = new Book(nameNode.getTextContent(), authorNode.getTextContent(), Double.parseDouble(priceNode.getTextContent()));
        book.setId(Long.valueOf(bookElement.getAttribute("id")));
        return book;
    }

    public void loadData() {
        parseXMLFile().forEach(entity -> {
            try {
                super.save(entity);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        });
    }

    private Set<Book> parseXMLFile() {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileName);

            Element root = document.getDocumentElement();

            NodeList children = root.getChildNodes();
            return IntStream
                    .range(0, children.getLength())
                    .mapToObj(children::item)
                    .filter(node -> node instanceof Element)
                    .map(node -> createBookFromElement((Element) node))
                    .collect(Collectors.toSet());
        } catch (ValidatorException | ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    private Node bookToNode(Book book, Document document) {
        Element bookElement = document.createElement("book");

        bookElement.setAttribute("id", book.getId().toString());

        Element nameElement = document.createElement("name");
        nameElement.setTextContent(book.getName());
        bookElement.appendChild(nameElement);
        Element authorElement = document.createElement("author");
        authorElement.setTextContent(book.getName());
        bookElement.appendChild(authorElement);
        Element priceElement = document.createElement("price");
        priceElement.setTextContent(book.getName());
        bookElement.appendChild(priceElement);


        return bookElement;
    }

    @Override
    public Optional<Book> save(Book book) throws ValidatorException {
        Optional<Book> optional = super.save(book);
        if (optional.isPresent()) {
            return optional;
        }
        try {
            saveToFile();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> update(Book book) throws ValidatorException {
        Optional<Book> optional = super.update(book);

        try {
            saveToFile();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> delete(Long id) throws ValidatorException {
        Optional<Book> optional = super.delete(id);

        try {
            saveToFile();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void saveToFile() throws TransformerException, ParserConfigurationException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        Element root = document.createElement("books");
        document.appendChild(root);

        super.findAll().forEach(book-> {
            Node child = bookToNode((Book) book, document);
            root.appendChild(child);
        });

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(fileName));
        transformer.transform(domSource, streamResult);
    }
}