package ui;

import domain.Book;
import domain.Client;
import domain.Purchase;
import domain.validators.BookValidator;
import domain.validators.ClientValidator;
import domain.validators.PurchaseValidator;
import repository.FileRepositories.BookFileRepository;
import repository.FileRepositories.ClientFileRepository;
import repository.FileRepositories.PurchaseFileRepository;
import repository.XMLRepositories.BookXMLRepository;
import repository.XMLRepositories.ClientXMLRepository;
import service.BookService;
import service.ClientService;
import service.PurchaseService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {
    String commandLayer;
    boolean running;

    BookValidator bookValidator = new BookValidator();
    ClientValidator clientValidator = new ClientValidator();
    PurchaseValidator purchaseValidator = new PurchaseValidator();

    BookFileRepository bookRepository = new BookFileRepository(bookValidator, "src\\main\\files\\books.txt");
    ClientFileRepository clientRepository = new ClientFileRepository(clientValidator, "src\\main\\files\\clients.txt");
    PurchaseFileRepository purchaseRepository = new PurchaseFileRepository(purchaseValidator, "src\\main\\files\\purchases.txt");

    BookXMLRepository bookXMLRepository = new BookXMLRepository(bookValidator, "src\\main\\files\\books.xml");
    ClientXMLRepository clientXMLRepository = new ClientXMLRepository(clientValidator, "src\\main\\files\\clients.xml");

    BookService bookController = new BookService(bookXMLRepository);
    ClientService clientController = new ClientService(clientXMLRepository);
    PurchaseService purchaseController = new PurchaseService(purchaseRepository);

    public UI() {
        this.commandLayer = "App";
        this.running = true;
    }

    public void start() throws ParseException {
        while (this.running) {
            this.printCommands();
            this.handleCommands();
        }
    }

    public void printCommands() {
        switch (this.commandLayer) {
            case "App":
                System.out.println("\n---------APP---------");
                System.out.println("0: exit");
                System.out.println("1: manage books");
                System.out.println("2: manage clients");
                System.out.println("3: buy book");
                break;
            case "Books":
                System.out.println("\n---------BOOKS---------");
                System.out.println("0: back");
                System.out.println("1: create a new book");
                System.out.println("2: update an existing book");
                System.out.println("3: remove an existing book");
                System.out.println("4: show the best sellers");
                System.out.println("5: show the books with price in a specific range");
                System.out.println("6: show the list of books");
                break;
            case "Clients":
                System.out.println("\n---------CLIENTS---------");
                System.out.println("0: back");
                System.out.println("1: create a new client");
                System.out.println("2: update an existing client");
                System.out.println("3: remove an existing client");
                System.out.println("4: show the clients based on the spent amount of money");
                System.out.println("5: show the list of clients");
                break;
        }
    }

    public void handleCommands() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        switch (this.commandLayer) {
            case "App":
                switch (command) {
                    case "0":
                        running = false;
                        break;
                    case "1":
                        commandLayer = "Books";
                        break;
                    case "2":
                        commandLayer = "Clients";
                        break;
                    case "3":
                        this.purchaseController.addPurchase(this.readPurchase());
                        break;
                    default:
                        System.out.println("Wrong command!");
                }
                break;
            case "Books":
                switch (command) {
                    case "0":
                        commandLayer = "App";
                        break;
                    case "1":
                        this.bookController.addBook(this.readBook());
                        break;
                    case "2":
                        bookController.updateBook(this.readBook());
                        break;
                    case "3":
                        System.out.println("Book ID: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        bookController.removeBook(id);
                        break;
                    case "4":
                        List<Map.Entry<Long, Integer>> bestSellers = this.bookController.bestSellersList(this.purchaseController.getAllPurchases());
                        for (Map.Entry<Long, Integer> e : bestSellers)
                            System.out.println("Book " + e.getKey() + " was bought " + e.getValue() + " times.");
                        break;
                    case "5":
                        System.out.println("Minimum price: ");
                        Double minimumPrice = Double.parseDouble(scanner.nextLine());
                        System.out.println("Maximum price: ");
                        Double maximumPrice = Double.parseDouble(scanner.nextLine());
                        bookController.filterByPrice(minimumPrice, maximumPrice).forEach(b ->
                                System.out.println(b.toString())
                        );
                        break;
                    case "6":
                        bookController.getAllBooks().forEach(b ->
                                System.out.println(b.toString())
                        );
                        break;
                    default:
                        System.out.println("Wrong command!");
                }
                break;
            case "Clients":
                switch (command) {
                    case "0":
                        commandLayer = "App";
                        break;
                    case "1":
                        clientController.addClient(this.readClient());
                        break;
                    case "2":
                        clientController.updateClient(this.readClient());
                        break;
                    case "3": {
                        System.out.println("Client ID: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        clientController.removeClient(id);
                        break;
                    }
                    case "4": {
                        Map<Long, Double> clientsSpentAmountOfMoney = this.clientController.getAllClients().stream()
                                .collect(Collectors.toMap(Client::getId, c -> 0D));
                        System.out.println(clientsSpentAmountOfMoney);
                        break;
                    }
                    case "5":
                        clientController.getAllClients()
                                .forEach(
                                        System.out::println
                                );
                        break;
                    default:
                        System.out.println("Wrong command!");
                }
                break;
            default:
                System.out.println("Wrong command!");
        }
    }

    public Book readBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Book ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.println("Name: ");
        String name = scanner.nextLine();
        System.out.println("Author: ");
        String author = scanner.nextLine();
        System.out.println("Price: ");
        Double price = Double.parseDouble(scanner.nextLine());

        Book book = new Book(name, author, price);
        book.setId(id);
        return book;
    }

    public Client readClient() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Client ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.println("Name: ");
        String name = scanner.nextLine();

        Client client = new Client(name);
        client.setId(id);
        return client;
    }

    public Purchase readPurchase() throws ParseException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Purchase ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.println("Client ID: ");
        Long clientId = Long.parseLong(scanner.nextLine());
        System.out.println("Book ID: ");
        Long bookId = Long.parseLong(scanner.nextLine());
        System.out.println("Date (dd/MM/yyyy): ");
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());

        Purchase purchase = new Purchase(clientId, bookId, date);
        purchase.setId(id);
        return purchase;
    }
}
