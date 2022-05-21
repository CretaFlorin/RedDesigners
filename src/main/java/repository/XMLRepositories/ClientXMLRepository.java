package repository.XMLRepositories;

import domain.Client;
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

public class ClientXMLRepository extends InMemoryRepository<Long, Client> {
    String fileName;

    public ClientXMLRepository(Validator<Client> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private Client createClientFromElement(Element clientElement) {
        Node nameNode = clientElement.getElementsByTagName("name").item(0);
        Client client = new Client(nameNode.getTextContent());
        client.setId(Long.valueOf(clientElement.getAttribute("id")));
        return client;
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

    private Set<Client> parseXMLFile() {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileName);

            Element root = document.getDocumentElement();

            NodeList children = root.getChildNodes();
            return IntStream
                    .range(0, children.getLength())
                    .mapToObj(children::item)
                    .filter(node -> node instanceof Element)
                    .map(node -> createClientFromElement((Element) node))
                    .collect(Collectors.toSet());
        } catch (ValidatorException | ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    private Node clientToNode(Client client, Document document) {
        Element clientElement = document.createElement("client");

        clientElement.setAttribute("id", client.getId().toString());

        Element nameElement = document.createElement("name");
        nameElement.setTextContent(client.getName());
        clientElement.appendChild(nameElement);

        return clientElement;
    }

    @Override
    public Optional<Client> save(Client client) throws ValidatorException {
        Optional<Client> optional = super.save(client);
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
    public Optional<Client> update(Client client) throws ValidatorException {
        Optional<Client> optional = super.update(client);

        try {
            saveToFile();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> delete(Long id) throws ValidatorException {
        Optional<Client> optional = super.delete(id);

        try {
            saveToFile();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void saveToFile() throws TransformerException, ParserConfigurationException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        Element root = document.createElement("clients");
        document.appendChild(root);

        super.findAll().forEach(client -> {
            Node child = clientToNode((Client) client, document);
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