package service;

import domain.Book;
import domain.Client;
import repository.ClientRepository;

import java.util.Objects;


public class ClientController {
    ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public void createClient(String name) {
        this.clientRepository.createEntity(new Client(name));
    }

    public void updateClient(Integer id, String name) {
        this.clientRepository.updateEntity(new Client(id, name));
    }

    public void removeClient(Integer id) {
        this.clientRepository.deleteEntity(
                this.clientRepository.getClients()
                        .stream()
                        .filter(c -> Objects.equals(c.getId(), id))
                        .findFirst().orElse(null)
        );
    }

    public void buyBook(Integer clientId, Book book) {
        this.clientRepository.getEntityById(clientId).getBoughtBooks().add(book);
    }
}
