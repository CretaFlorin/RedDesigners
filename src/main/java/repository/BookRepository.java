//package repository;
//
//import domain.Book;
//
//import java.util.ArrayList;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//public class BookRepository implements Repository<Book> {
//
//    ArrayList<Book> books;
//
//    public BookRepository() {
//        books = new ArrayList<>();
//    }
//
//    public ArrayList<Book> getBooks() {
//        return books;
//    }
//
//    public void setBooks(ArrayList<Book> books) {
//        this.books = books;
//    }
//
//    @Override
//    public Book getEntityById(Integer id) {
//        return books
//                .stream()
//                .filter(b -> Objects.equals(b.getId(), id))
//                .findFirst().orElse(null);
//    }
//
//    @Override
//    public void createEntity(Book book) {
//        books.add(book);
//    }
//
//    @Override
//    public void updateEntity(Book book) throws NullPointerException {
//        Book bookToUpdate = books
//                .stream()
//                .filter(b -> Objects.equals(b.getId(), book.getId()))
//                .findFirst().orElse(null);
//
//        if (bookToUpdate == null)
//            throw new NullPointerException();
//
//        bookToUpdate.setName(book.getName());
//        bookToUpdate.setAuthor(book.getAuthor());
//        bookToUpdate.setPrice(book.getPrice());
//    }
//
//    @Override
//    public void deleteEntity(Book book) {
//        books.remove(book);
//    }
//}