package org.howard.edu.lsp.midterm.crccards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A collection of {@link Book} objects that coordinates adding, lookup,
 * borrowing, and returning, and enforces a library-wide borrowing limit.
 *
 * <p>Books are stored in a {@link HashMap} keyed by ISBN so that duplicate
 * detection and lookup are constant-time. Borrowing and returning locate the
 * {@code Book} and then delegate the availability change to that object. At
 * most three books may be borrowed from this library at one time.</p>
 *
 * @author Arianna Stith
 */
public class Library {

    /** Maximum number of books that may be borrowed at once. */
    private static final int MAX_BORROWED_BOOKS = 3;

    /** Books belonging to this library, keyed by ISBN. */
    private final Map<String, Book> booksByIsbn = new HashMap<String, Book>();

    /**
     * Creates an empty library.
     */
    public Library() {
        // The backing map starts empty.
    }

    /**
     * Adds a book to this library.
     *
     * @param book the book to add
     * @throws IllegalArgumentException if a book with the same ISBN is already
     *         in the library; the original book is left unchanged
     */
    public void addBook(Book book) {
        String isbn = book.getIsbn();
        if (booksByIsbn.containsKey(isbn)) {
            throw new IllegalArgumentException("Duplicate ISBN: " + isbn);
        }
        booksByIsbn.put(isbn, book);
    }

    /**
     * Finds the book with the given ISBN.
     *
     * @param isbn the ISBN to search for
     * @return the matching {@code Book} instance, or {@code null} if it is not
     *         in this library
     */
    public Book findBook(String isbn) {
        return booksByIsbn.get(isbn);
    }

    /**
     * Borrows the book with the given ISBN by delegating to
     * {@link Book#borrowBook()}.
     *
     * @param isbn the ISBN of the book to borrow
     * @throws IllegalArgumentException if no book with that ISBN is in this
     *         library
     * @throws IllegalStateException if the book is already borrowed, or if
     *         three books are already borrowed from this library
     */
    public void borrowBook(String isbn) {
        Book book = requireBook(isbn);
        if (book.isAvailable() && getBorrowedBookCount() >= MAX_BORROWED_BOOKS) {
            throw new IllegalStateException(
                    "Cannot borrow more than " + MAX_BORROWED_BOOKS + " books at a time");
        }
        book.borrowBook();
    }

    /**
     * Returns the book with the given ISBN by delegating to
     * {@link Book#returnBook()}.
     *
     * @param isbn the ISBN of the book to return
     * @throws IllegalArgumentException if no book with that ISBN is in this
     *         library
     * @throws IllegalStateException if the book is already available
     */
    public void returnBook(String isbn) {
        Book book = requireBook(isbn);
        book.returnBook();
    }

    /**
     * Returns the books that are currently available.
     *
     * @return a list of available books, empty if none are available; order is
     *         not significant
     */
    public List<Book> getAvailableBooks() {
        List<Book> available = new ArrayList<Book>();
        for (Book book : booksByIsbn.values()) {
            if (book.isAvailable()) {
                available.add(book);
            }
        }
        return available;
    }

    /**
     * Returns every book whose author matches {@code author} exactly
     * (case-sensitive), including borrowed books.
     *
     * @param author the author to match
     * @return a list of matching books, empty if there are no matches; order is
     *         not significant
     */
    public List<Book> getBooksByAuthor(String author) {
        List<Book> matches = new ArrayList<Book>();
        for (Book book : booksByIsbn.values()) {
            if (author.equals(book.getAuthor())) {
                matches.add(book);
            }
        }
        return matches;
    }

    /**
     * Returns every book in this library sorted by ISBN in ascending
     * lexicographical order. The library contents and book states are not
     * changed.
     *
     * @return a new list of all books sorted by ISBN, empty if the library is
     *         empty
     */
    public List<Book> getBooksSortedByIsbn() {
        List<Book> sorted = new ArrayList<Book>(booksByIsbn.values());
        Collections.sort(sorted, new Comparator<Book>() {
            @Override
            public int compare(Book left, Book right) {
                return left.getIsbn().compareTo(right.getIsbn());
            }
        });
        return sorted;
    }

    /**
     * Returns how many books are currently borrowed from this library.
     *
     * @return the number of unavailable books
     */
    public int getBorrowedBookCount() {
        int count = 0;
        for (Book book : booksByIsbn.values()) {
            if (!book.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Locates a book by ISBN or reports that it is not in this library.
     *
     * @param isbn the ISBN to look up
     * @return the matching book
     * @throws IllegalArgumentException if the ISBN is not in this library
     */
    private Book requireBook(String isbn) {
        Book book = booksByIsbn.get(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Book not in library: " + isbn);
        }
        return book;
    }
}
