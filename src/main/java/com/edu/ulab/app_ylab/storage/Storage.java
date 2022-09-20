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

 In this spring module we can:
 * 1) create a user with associated books.
 * 2) edit this user with books by id:
 *   - you can edit fullName, title, age for the user himself
 *   - you can add new books to the user's list
 * 3) get the user and his books by id
 * 4) Remove the user along with the books. At the same time, books will also be deleted from the general list. Since this list
 *    contains books with the userId of the user.
 */

@Component
public class Storage {

    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();


    public void saveUser(User user) {
        users.add(user);
    }

    public User getUser(Long id) {
        return users.stream()
                .filter(Objects::nonNull)
                .filter((e) -> Objects.equals(e.getId(), id))
                .findAny()
                .orElse(null);
    }


    public User updateUser(User updatedUser) {
        return users.stream()
                .filter(Objects::nonNull)
                .filter(user -> user.getId().equals(updatedUser.getId()))
                .peek(user -> {
                    user.setFullName(updatedUser.getFullName());
                    user.setTitle(updatedUser.getTitle());
                    user.setAge(updatedUser.getAge());
                })
                .findAny()
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return users;
    }

    public List<Book> getBookListByUser(Long userId) {
        return users.stream()
                .filter(Objects::nonNull)
                .filter((user) -> user.getId().equals(userId))
                .map(User::getBookList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public void saveBook(Book book) {
        books.add(book);
        users.stream()
                .filter(Objects::nonNull)
                .filter((user) -> Objects.equals(user.getId(), book.getUserId()))
                .forEach(user -> user.addBooksToUser(book));
    }

    public Book getBook(Long id) {
        return books.stream()
                .filter(Objects::nonNull)
                .filter((e) -> Objects.equals(e.getId(), id))
                .findAny()
                .orElse(null);
    }


    public void removeUser(Long id) {
        users.removeIf(user -> Objects.equals(user.getId(), id));
    }

    public void removeBook(Long id) {
        books.removeIf(book -> Objects.equals(book.getId(), id));
    }
}
