package ui;

import domain.Book;
import domain.Client;
import domain.validators.BookValidator;
import domain.validators.ClientValidator;
import repository.BookFileRepository;
import repository.ClientFileRepository;
import service.BookService;
import service.ClientService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {
    String commandLayer;
    boolean running;

    BookValidator bookValidator = new BookValidator();
    ClientValidator clientValidator = new ClientValidator();

    BookFileRepository bookRepository = new BookFileRepository(bookValidator, "C:\\Users\\creta\\Desktop\\SDI\\BookStore\\src\\main\\files\\books.txt");
    BookService bookController = new BookService(bookRepository);
    ClientFileRepository clientRepository = new ClientFileRepository(clientValidator, "C:\\Users\\creta\\Desktop\\SDI\\BookStore\\src\\main\\files\\clients.txt");
    ClientService clientController = new ClientService(clientRepository);

    public UI() {
        this.commandLayer = "App";
        this.running = true;
    }

    public void start() {
        while (this.running) {
            this.printCommands();
            this.handleCommands();
        }
    }

    public void printCommands() {
        switch (this.commandLayer) {
            case "App" -> {
                System.out.println("\n---------APP---------");
                System.out.println("0: exit");
                System.out.println("1: manage books");
                System.out.println("2: manage clients");
                System.out.println("3: buy book");
            }
            case "Books" -> {
                System.out.println("\n---------BOOKS---------");
                System.out.println("0: back");
                System.out.println("1: create a new book");
                System.out.println("2: update an existing book");
                System.out.println("3: remove an existing book");
                System.out.println("4: show the best sellers");
                System.out.println("5: show the books with price in a specific range");
                System.out.println("6: show the list of books");
            }
            case "Clients" -> {
                System.out.println("\n---------CLIENTS---------");
                System.out.println("0: back");
                System.out.println("1: create a new client");
                System.out.println("2: update an existing client");
                System.out.println("3: remove an existing client");
                System.out.println("4: show the clients based on the spent amount of money");
                System.out.println("5: show the list of clients");
            }
        }
    }

    public void handleCommands() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        switch (this.commandLayer) {
            case "App" -> {
                switch (command) {
                    case "0" -> {
                        running = false;
                    }
                    case "1" -> {
                        commandLayer = "Books";
                    }
                    case "2" -> {
                        commandLayer = "Clients";
                    }
                    case "3" -> {
                        System.out.println("Client ID: ");
                        Long clientId = Long.parseLong(scanner.nextLine());
                        System.out.println("Book ID: ");
                        Long bookId = Long.parseLong(scanner.nextLine());
                        this.clientController.buyBook(clientId, bookId);
                    }
                    default -> {
                        System.out.println("Wrong command!");
                    }
                }
            }
            case "Books" -> {
                switch (command) {
                    case "0" -> {
                        commandLayer = "App";
                    }
                    case "1" -> {
                        this.bookController.addBook(this.readBook());
                    }
                    case "2" -> {
                        bookController.updateBook(this.readBook());
                    }
                    case "3" -> {
                        System.out.println("Book ID: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        bookController.removeBook(id);
                    }
                    case "4" -> {
                        List<Map.Entry<Long, Integer>> bestSellers = this.bookController.bestSellersList(this.clientController.getAllClients());

                        for (Map.Entry<Long, Integer> e : bestSellers)
                            System.out.println("Book " + e.getKey() + " was bought " + e.getValue() + " times.");
                    }
                    case "5" -> {
                        System.out.println("Minimum price: ");
                        Double minimumPrice = Double.parseDouble(scanner.nextLine());
                        System.out.println("Maximum price: ");
                        Double maximumPrice = Double.parseDouble(scanner.nextLine());
                        bookController.filterByPrice(minimumPrice, maximumPrice).forEach(b -> {
                            System.out.println(b.toString());
                        });
                    }
                    case "6" -> {
                        bookController.getAllBooks().forEach(b -> {
                            System.out.println(b.toString());
                        });
                    }
                    default -> {
                        System.out.println("Wrong command!");
                    }
                }
            }
            case "Clients" -> {
                switch (command) {
                    case "0" -> {
                        commandLayer = "App";
                    }
                    case "1" -> {
                        clientController.addClient(this.readClient());
                    }
                    case "2" -> {
                        clientController.updateClient(this.readClient());
                    }
                    case "3" -> {
                        System.out.println("Client ID: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        clientController.removeClient(id);
                    }
                    case "4" -> {
                        List<Map.Entry<Long, Double>> clientsReport = this.clientController.spentMoneyReport(
                                this.bookController.getAllBooks().stream()
                                        .collect(Collectors.toMap(Book::getId, Book::getPrice)));

                        clientsReport.forEach(
                                cp -> {
                                    System.out.println("Client " + cp.getKey() + " has spent " + cp.getValue());
                                }
                        );

                    }
                    case "5" -> {
                        clientController.getAllClients()
                                .forEach(
                                        System.out::println
                                );
                    }
                    default -> {
                        System.out.println("Wrong command!");
                    }
                }
            }
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
}
