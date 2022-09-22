package com.edu.ulab.app_ylab.storage;

import com.edu.ulab.app_ylab.entity.Book;
import com.edu.ulab.app_ylab.entity.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This storage-class contains 2 data structures where users and books are stored.
 * The function of this class to contain all users and all books that users have
 * When saving books to storage, they are also highlighted in  one more list in the user's entity
 * This is done so that we have access to list of all application books by identifier, and a list of books of specific user.
 */

@Component
public class Storage {

    private final static Map<Long, User> users = new HashMap<>();
    private final static Map<Long, Book> books = new HashMap<>();

    public void saveUser(User user) {
        users.put(user.getId(), user);
    }

    public User getUser(Long id) {
        return users.get(id);
    }

    public void updateUser(User user) {
        users.put(user.getId(), user);
    }

    public Map<Long, User> getAllUsers() {
        return users;
    }

    public List<Book> getBookListByUser(Long userId) {
        return users.entrySet().stream()
                .filter((user) -> user.getKey().equals(userId))
                .map(user -> user.getValue().getBookList())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public void saveBook(Book book) {
        books.put(book.getId(), book);
        users.entrySet().stream()
                .filter(user -> Objects.equals(user.getKey(), book.getUserId()))
                .forEach(user -> user.getValue().addBooksToUser(book));
    }

    public Book getBook(Long id) {
        return books.get(id);
    }

    public void removeUser(Long id) {
        users.remove(id);
    }

    public void removeBook(Long id) {
        books.remove(id);
    }
}
