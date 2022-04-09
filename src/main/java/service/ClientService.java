package service;

import domain.Book;
import domain.Client;
import domain.validators.ValidatorException;
import repository.InMemoryRepository;
import repository.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService {
    private final Repository<Long, Client> repository;

    public ClientService(InMemoryRepository<Long, Client> repository) {
        this.repository = repository;
    }

    public void addClient(Client client) throws ValidatorException {
        repository.save(client);
    }

    public Set<Client> getAllClients() {
        Iterable<Client> clients = repository.findAll();
        return StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());
    }


    public Set<Client> filterClientsByName(String s) {
        Iterable<Client> clients = repository.findAll();
        //version 1
//        Set<Client> filteredClients = StreamSupport.stream(Clients.spliterator(), false)
//                .filter(Client -> Client.getName().contains(s)).collect(Collectors.toSet());

        //version 2
        Set<Client> filteredClients = new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(Client -> !Client.getName().contains(s));

        return filteredClients;
    }

    public void buyBook(Long clientId, Long bookId) {
        System.out.println(this.repository.findOne(clientId).getClass());
    }

//    public ArrayList<Client> spentMoneyReport() {
//        return this.repository.findAll()
//                .stream()
//                .sorted(Comparator.comparing(Client::getSpentMoney).reversed())
//                .collect(Collectors.toCollection(ArrayList::new));
//    }


}
