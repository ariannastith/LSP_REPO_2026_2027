package org.howard.edu.lsp.midterm.crccards;

/**
 * A library book identified by ISBN, with a title, author, and availability
 * state.
 *
 * <p>A newly constructed book starts available. Availability is changed only
 * through {@link #borrowBook()} and {@link #returnBook()}; other classes cannot
 * set it directly.</p>
 *
 * @author Arianna Stith
 */
public class Book {

    private final String isbn;
    private final String title;
    private final String author;
    private boolean available;

    /**
     * Creates an available book with the given identifying details.
     *
     * @param isbn the book's ISBN
     * @param title the book's title
     * @param author the book's author
     */
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    /**
     * Returns this book's ISBN.
     *
     * @return the ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Returns this book's title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns this book's author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Reports whether this book is currently available to borrow.
     *
     * @return {@code true} if the book is available; {@code false} if it is borrowed
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Marks this book as borrowed.
     *
     * @throws IllegalStateException if the book is already borrowed
     */
    public void borrowBook() {
        if (!available) {
            throw new IllegalStateException("Book is already borrowed: " + isbn);
        }
        available = false;
    }

    /**
     * Marks this book as available.
     *
     * @throws IllegalStateException if the book is already available
     */
    public void returnBook() {
        if (available) {
            throw new IllegalStateException("Book is already available: " + isbn);
        }
        available = true;
    }

    /**
     * Returns a display string that includes ISBN, title, author, and
     * availability.
     *
     * @return a string of the form
     *         {@code B101 Effective Java by Joshua Bloch [AVAILABLE]}
     */
    @Override
    public String toString() {
        String status = available ? "AVAILABLE" : "BORROWED";
        return isbn + " " + title + " by " + author + " [" + status + "]";
    }
}
