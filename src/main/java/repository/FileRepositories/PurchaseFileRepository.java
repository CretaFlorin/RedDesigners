package repository.FileRepositories;

import domain.Purchase;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import repository.InMemoryRepository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PurchaseFileRepository extends InMemoryRepository<Long, Purchase> {
    private final String fileName;

    public PurchaseFileRepository(Validator<Purchase> validator, String fileName) {
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
                Long clientId = Long.parseLong(items.get(1));
                Long bookId = Long.parseLong(items.get(2));
                Date date = null;
                try {
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(items.get((3)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Purchase purchase = new Purchase(clientId, bookId, date);
                purchase.setId(id);

                try {
                    super.save(purchase);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Purchase> save(Purchase purchase) throws ValidatorException {
        Optional<Purchase> optional = super.save(purchase);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(purchase);
        return Optional.empty();
    }

    private void saveToFile(Purchase purchase) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.newLine();
            bufferedWriter.write(purchase.getId() + "," + purchase.getBookId() + "," + purchase.getClientId() + "," + new SimpleDateFormat("dd/MM/yyyy").format(purchase.getDate()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
