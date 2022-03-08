package repository;

import domain.Book;
import domain.Client;

import java.util.ArrayList;
import java.util.Objects;

public class ClientRepository implements Repository<Client> {

    ArrayList<Client> clients;

    public ClientRepository() {
        clients = new ArrayList<>();
    }


    public ArrayList<Client> getClients() {
        return clients;
    }

    @Override
    public Client getEntityById(Integer id) {
        return clients
                .stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst().orElse(null);
    }

    @Override
    public void createEntity(Client client) {
        clients.add(client);
    }

    @Override
    public void updateEntity(Client client) throws NullPointerException {
        Client clientToUpdate = clients
                .stream()
                .filter(c -> Objects.equals(c.getId(), client.getId()))
                .findFirst().orElse(null);

        if (clientToUpdate == null)
            throw new NullPointerException();

        clientToUpdate.setName(client.getName());
        clientToUpdate.setBoughtBooks(client.getBoughtBooks());
    }

    @Override
    public void deleteEntity(Client client) {
        clients.remove(client);
    }
}
