package ui;

import domain.Book;
import domain.validators.BookValidator;
import domain.validators.ClientValidator;
import repository.BookFileRepository;
import repository.ClientFileRepository;
import service.BookService;
import service.ClientService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
//                        this.clientController.buyBook(clientId, this.bookController.getBookById(bookId));
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
                        HashMap<Long, Integer> booksFr = new HashMap<>();
                        clientController.getAllClients().forEach(c -> {
                            c.getBoughtBooks().forEach(bookId -> {
                                int count = booksFr.getOrDefault(bookId, 0);
                                booksFr.put(bookId, count + 1);
                            });
                        });
                        for (Map.Entry<Long, Integer> e :
                                booksFr.entrySet()
                                        .stream()
                                        .sorted((e1, e2) -> Integer.compare(e1.getValue(), e2.getValue()))
                                        .toList())
                        {
                            System.out.println("book " + e.getKey() + " was bought " + e.getValue() + " times.");
                        }
                    } case "5" -> {
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
            } case "Clients" -> {
                switch (command) {
                    case "0" -> {
                        commandLayer = "App";
                    }
                    case "1" -> {
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
//                        clientController.createClient(name);
                    }
                    case "2" -> {
                        System.out.println("Client ID: ");
                        Integer id = Integer.parseInt(scanner.nextLine());
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
//                        clientController.updateClient(id, name);
                    }
                    case "3" -> {
                        System.out.println("Client ID: ");
                        Integer id = scanner.nextInt();
//                        clientController.removeClient(id);
                    }
                    case "4" -> {
//                        this.clientController.spentMoneyReport()
//                                .forEach(c -> {
//                                    System.out.println(c.toString());
//                                });
                    }
                    case "5" -> {
//                        clientController.getClientRepository().getClients()
//                                .forEach(
//                                        c -> System.out.println(c.toString())
//                                );
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
}
