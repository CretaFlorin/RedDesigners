package service;

import domain.Client;
import domain.Purchase;
import domain.validators.ValidatorException;
import repository.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class PurchaseService {
    private final Repository<Long, Purchase> repository;

    public PurchaseService(Repository<Long, Purchase> repository) {
        this.repository = repository;
    }

    public void addPurchase(Purchase purchase) throws ValidatorException {
        repository.save(purchase);
    }

    public void updatePurchase(Purchase newPurchase) {
        repository.update(newPurchase);
    }

    public void removePurchase(Long purchaseId) {
        repository.delete(purchaseId);
    }

    public Purchase getPurchaseById(Long purchaseId) {
        return this.repository.findOne(purchaseId)
                .stream()
                .findFirst().orElse(null);
    }

    public Set<Purchase> getAllPurchases() {
        Iterable<Purchase> Purchases = repository.findAll();
        return StreamSupport.stream(Purchases.spliterator(), false).collect(Collectors.toSet());
    }
}
