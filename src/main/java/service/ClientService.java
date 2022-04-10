package service;

import domain.Client;
import domain.validators.ValidatorException;
import repository.InMemoryRepository;
import repository.Repository;

import java.util.List;
import java.util.Map;
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

    public void updateClient(Client newClient) {
        repository.update(newClient);
    }

    public void removeClient(Long clientId) {
        repository.delete(clientId);
    }

    public Client getClientById(Long clientId) {
        return this.repository.findOne(clientId)
                .stream()
                .findFirst().orElse(null);
    }

    public Set<Client> getAllClients() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toSet());
    }

    public List<Map.Entry<Long, Double>> spentMoneyReport(Map<Long, Double> prices) {
        return this.getAllClients()
                .stream()
                .collect(Collectors.toMap(
                                Client::getId,
                                client -> {
                                    return client.getBoughtBooks()
                                            .stream()
                                            .map(prices::get)
                                            .reduce(0D, Double::sum);
                                }
                        )
                )
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .collect(Collectors.toList());
    }

    public void buyBook(Long clientId, Long bookId) {
        this.getClientById(clientId).buyBook(bookId);
    }

}
