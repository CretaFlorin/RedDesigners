package repository;

import domain.Client;
import domain.validators.Validator;
import domain.validators.ValidatorException;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClientFileRepository extends InMemoryRepository<Long, Client> {
    private final String fileName;

    public ClientFileRepository(Validator<Client> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String name = items.get(1);
                String boughtBooks_str = items.get(2);


                List<Long> boughtBooks = Arrays.stream(boughtBooks_str.split("-")).toList()
                        .stream()
                        .map(Long::parseLong)
                        .toList();


                Client client = new Client(name);
                client.setBoughtBooks(new ArrayList<Long>(boughtBooks));
                client.setId(id);

                try {
                    super.save(client);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Client> save(Client client) throws ValidatorException {
        Optional<Client> optional = super.save(client);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(client);
        return Optional.empty();
    }

    private void saveToFile(Client entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(entity.getId() + "," + entity.getName() + ",");
            for (int i = 0; i < entity.getBoughtBooks().size() - 1; i++)
                bufferedWriter.write(entity.getBoughtBooks().get(i).toString() + "-");
            bufferedWriter.write(entity.getBoughtBooks().get(Math.toIntExact(entity.getBoughtBooks().get(entity.getBoughtBooks().size() - 1))).toString());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


