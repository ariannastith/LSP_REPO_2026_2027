package org.howard.edu.lsp.midterm.crccards;

import java.util.List;

/** Instructor-provided verification driver for Question 1. */
public class Driver {
    private static int passed = 0;
    private static int failed = 0;

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("PASS - " + name);
        } else {
            failed++;
            System.out.println("FAIL - " + name);
        }
    }

    private static boolean containsIsbn(List<Book> books, String isbn) {
        if (books == null) return false;
        for (Book book : books) {
            if (book != null && isbn.equals(book.getIsbn())) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("QUESTION 1 - LIBRARY LENDING SYSTEM TESTS");
        Library library = new Library();
        Book b1 = new Book("B101", "Effective Java", "Joshua Bloch");
        Book b2 = new Book("B102", "Clean Code", "Robert Martin");
        Book b3 = new Book("B103", "Java Concurrency", "Brian Goetz");
        Book b4 = new Book("B104", "Clean Architecture", "Robert Martin");

        System.out.println("\nBOOK INITIALIZATION");
        check("ISBN getter", "B101".equals(b1.getIsbn()));
        check("Title getter", "Effective Java".equals(b1.getTitle()));
        check("Author getter", "Joshua Bloch".equals(b1.getAuthor()));
        check("New books start available", b1.isAvailable());
        check("Available book string", "B101 Effective Java by Joshua Bloch [AVAILABLE]".equals(b1.toString()));

        System.out.println("\nLIBRARY ADD AND LOOKUP");
        library.addBook(b3);
        library.addBook(b1);
        library.addBook(b4);
        library.addBook(b2);
        check("Find existing book returns same object", library.findBook("B102") == b2);
        check("Find missing book returns null", library.findBook("B999") == null);
        check("Initially four available", library.getAvailableBooks() != null && library.getAvailableBooks().size() == 4);
        check("Initially zero borrowed", library.getBorrowedBookCount() == 0);
        try {
            library.addBook(new Book("B102", "Duplicate", "Other"));
            check("Duplicate ISBN rejected", false);
        } catch (IllegalArgumentException ex) {
            check("Duplicate ISBN rejected", true);
        } catch (Exception ex) {
            check("Duplicate ISBN rejected (wrong exception)", false);
        }
        check("Duplicate did not replace original", library.findBook("B102") == b2);
        check("Duplicate did not alter available count", library.getAvailableBooks().size() == 4);

        System.out.println("\nAUTHOR SEARCH");
        List<Book> martin = library.getBooksByAuthor("Robert Martin");
        check("Author search finds both matches", martin != null && martin.size() == 2 && containsIsbn(martin, "B102") && containsIsbn(martin, "B104"));
        check("Author comparison case sensitive", library.getBooksByAuthor("robert martin") != null && library.getBooksByAuthor("robert martin").isEmpty());
        check("Unknown author returns empty list", library.getBooksByAuthor("Nobody") != null && library.getBooksByAuthor("Nobody").isEmpty());

        System.out.println("\nSORTED ISBN RETRIEVAL");
        List<Book> sorted = library.getBooksSortedByIsbn();
        check("Sorted list contains all books", sorted != null && sorted.size() == 4);
        check("Books returned in ascending ISBN order", sorted != null && sorted.size() == 4
                && "B101".equals(sorted.get(0).getIsbn())
                && "B102".equals(sorted.get(1).getIsbn())
                && "B103".equals(sorted.get(2).getIsbn())
                && "B104".equals(sorted.get(3).getIsbn()));
        check("Sorting does not change library", library.getAvailableBooks().size() == 4
                && library.getBorrowedBookCount() == 0);
        Library emptyLibrary = new Library();
        check("Empty library returns empty sorted list", emptyLibrary.getBooksSortedByIsbn() != null
                && emptyLibrary.getBooksSortedByIsbn().isEmpty());

        System.out.println("\nBORROWING AND LIMIT");
        library.borrowBook("B101");
        check("Borrow makes book unavailable", !b1.isAvailable());
        check("Borrowed book string", "B101 Effective Java by Joshua Bloch [BORROWED]".equals(b1.toString()));
        check("Borrow count increments", library.getBorrowedBookCount() == 1);
        check("Available list excludes borrowed book", library.getAvailableBooks().size() == 3 && !containsIsbn(library.getAvailableBooks(), "B101"));
        try {
            library.borrowBook("B101");
            check("Already borrowed book rejected", false);
        } catch (IllegalStateException ex) {
            check("Already borrowed book rejected", true);
        } catch (Exception ex) {
            check("Already borrowed book rejected (wrong exception)", false);
        }
        check("Failed borrow does not change count", library.getBorrowedBookCount() == 1);
        library.borrowBook("B102");
        library.borrowBook("B103");
        check("Three borrowed", library.getBorrowedBookCount() == 3);
        try {
            library.borrowBook("B104");
            check("Fourth borrow rejected", false);
        } catch (IllegalStateException ex) {
            check("Fourth borrow rejected", true);
        } catch (Exception ex) {
            check("Fourth borrow rejected (wrong exception)", false);
        }
        check("Limit failure leaves fourth book available", b4.isAvailable());
        check("Limit failure leaves count at three", library.getBorrowedBookCount() == 3);

        System.out.println("\nRETURNING AND REBORROWING");
        library.returnBook("B101");
        check("Return makes book available", b1.isAvailable());
        check("Return reduces count", library.getBorrowedBookCount() == 2);
        check("Returned book appears in available list", containsIsbn(library.getAvailableBooks(), "B101"));
        try {
            library.returnBook("B101");
            check("Returning available book rejected", false);
        } catch (IllegalStateException ex) {
            check("Returning available book rejected", true);
        } catch (Exception ex) {
            check("Returning available book rejected (wrong exception)", false);
        }
        check("Invalid return leaves count unchanged", library.getBorrowedBookCount() == 2);
        library.borrowBook("B104");
        check("Borrow succeeds after return", !b4.isAvailable() && library.getBorrowedBookCount() == 3);

        List<Book> sortedAfterBorrow = library.getBooksSortedByIsbn();
        check("Sorted retrieval includes borrowed books", sortedAfterBorrow != null
                && sortedAfterBorrow.size() == 4 && "B101".equals(sortedAfterBorrow.get(0).getIsbn())
                && "B104".equals(sortedAfterBorrow.get(3).getIsbn()));

        System.out.println("\nMISSING ISBN OPERATIONS");
        try {
            library.borrowBook("B999");
            check("Borrow missing ISBN rejected", false);
        } catch (IllegalArgumentException ex) {
            check("Borrow missing ISBN rejected", true);
        } catch (Exception ex) {
            check("Borrow missing ISBN rejected (wrong exception)", false);
        }
        try {
            library.returnBook("B999");
            check("Return missing ISBN rejected", false);
        } catch (IllegalArgumentException ex) {
            check("Return missing ISBN rejected", true);
        } catch (Exception ex) {
            check("Return missing ISBN rejected (wrong exception)", false);
        }
        check("Missing ISBN operations leave count unchanged", library.getBorrowedBookCount() == 3);
        check("Author search includes borrowed books", library.getBooksByAuthor("Robert Martin").size() == 2);

        System.out.println("\nDIRECT BOOK STATE VALIDATION");
        Book standalone = new Book("B200", "Standalone", "Test Author");
        standalone.borrowBook();
        check("Direct borrow changes state", !standalone.isAvailable());
        try {
            standalone.borrowBook();
            check("Direct double borrow rejected", false);
        } catch (IllegalStateException ex) {
            check("Direct double borrow rejected", true);
        } catch (Exception ex) {
            check("Direct double borrow rejected (wrong exception)", false);
        }
        standalone.returnBook();
        check("Direct return changes state", standalone.isAvailable());
        try {
            standalone.returnBook();
            check("Direct double return rejected", false);
        } catch (IllegalStateException ex) {
            check("Direct double return rejected", true);
        } catch (Exception ex) {
            check("Direct double return rejected (wrong exception)", false);
        }

System.out.println("\nRESULT: " + passed + " PASSED, " + failed + " FAILED");
System.out.printf("FUNCTIONAL SCORE: %.2f / 25%n", 25.0 * passed / (passed + failed));
    }
}
