package ui;

import domain.Book;
import domain.Client;
import repository.BookRepository;
import repository.ClientRepository;
import service.BookController;
import service.ClientController;

import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    String commandLayer;
    boolean running;

    BookRepository bookRepository = new BookRepository();
    ClientRepository clientRepository = new ClientRepository();

    BookController bookController = new BookController(bookRepository);
    ClientController clientController = new ClientController(clientRepository);

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
                System.out.println("0: exit");
                System.out.println("1: manage books");
                System.out.println("2: manage clients");
                System.out.println("3: buy book");
            }
            case "Books" -> {
                System.out.println("0: back");
                System.out.println("1: create a new book");
                System.out.println("2: update an existing book");
                System.out.println("3: remove an existing book");
                System.out.println("4: show the best sellers");
                System.out.println("5: show the books with price in a specific range");
                System.out.println("6: show the list of books");
            }
            case "Clients" -> {
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
                        Integer clientId = scanner.nextInt();
                        System.out.println("Book ID: ");
                        Integer bookId = scanner.nextInt();
                        this.clientController.buyBook(clientId, this.bookController.getBookById(bookId));
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
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Author: ");
                        String author = scanner.nextLine();
                        System.out.println("Price: ");
                        Float price = scanner.nextFloat();
                        this.bookController.createBook(name, author, price);
                    }
                    case "2" -> {
                        System.out.println("Book ID: ");
                        Integer id = Integer.parseInt(scanner.nextLine());
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Author: ");
                        String author = scanner.nextLine();
                        System.out.println("Price: ");
                        Float price = scanner.nextFloat();
                        bookController.updateBook(id, name, author, price);
                    }
                    case "3" -> {
                        System.out.println("Book ID: ");
                        Integer id = scanner.nextInt();
                        bookController.removeBook(id);
                    }
                    case "4" -> {
                        // to be implemented
                    }
                    case "5" -> {
                        System.out.println("Minimum price: ");
                        Float minimumPrice = scanner.nextFloat();
                        System.out.println("Maximum price: ");
                        Float maximumPrice = scanner.nextFloat();
                        bookController.filterByPrice(minimumPrice, maximumPrice)
                                .forEach(b -> {
                                    System.out.println(b.toString());
                                });
                    }
                    case "6" -> {
                        bookController.getBookRepository().getBooks()
                                .forEach(b -> {
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
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
                        clientController.createClient(name);
                    }
                    case "2" -> {
                        System.out.println("Client ID: ");
                        Integer id = Integer.parseInt(scanner.nextLine());
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
                        clientController.updateClient(id, name);
                    }
                    case "3" -> {
                        System.out.println("Client ID: ");
                        Integer id = scanner.nextInt();
                        clientController.removeClient(id);
                    }
                    case "4" -> {
                        this.clientController.spentMoneyReport()
                                .forEach(c -> {
                                    System.out.println(c.toString());
                                });
                    }
                    case "5" -> {
                        clientController.getClientRepository().getClients()
                                .forEach(
                                        c -> System.out.println(c.toString())
                                );
                    }
                    default -> {
                        System.out.println("Wrong command!");
                    }
                }
            }
        }
    }
}
