# Development Log — Question 1

## Resources used

- Cursor (Grok 4.6), this conversation. Complete transcript is included below.
- Java SE `HashMap` documentation, used to choose the library’s internal collection:
  https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/HashMap.html
- Java SE `Collections.sort` / `Comparator` documentation, used for `getBooksSortedByIsbn()`:
  https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Collections.html
- The midterm exam (`Mid-term.docx`): CRC cards, method list, business rules, and the provided `Driver.java`.

No other people were consulted. Lecture notes and textbooks were not used for this question beyond the exam itself.

## How the resources helped

`Library` needed a collection that makes “add and find by ISBN” cheap and that can reject a duplicate ISBN without replacing the original book. A `HashMap<String, Book>` keyed by ISBN does that: `containsKey` before `put` preserves the original object, and `get` returns the same instance the caller added (which the Driver checks with `==`).

Availability stays on `Book`. The exam says the library must locate the book and delegate the state change, and that failed operations must not change the collection, the borrow count, or availability. That is why `Library.borrowBook` (1) looks the book up and throws `IllegalArgumentException` if it is missing, (2) refuses a fourth borrow *before* calling `Book.borrowBook()` if three books are already out, and (3) otherwise delegates so an already-borrowed book throws `IllegalStateException` from `Book` without the map changing.

`getBooksSortedByIsbn()` copies the map values into a new `ArrayList` and sorts that copy by ISBN. Sorting a copy means insertion order and borrow state in the `HashMap` are not disturbed, which is what the Driver checks.

Compiled and executed with the unmodified Driver:

```
RESULT: 44 PASSED, 0 FAILED
FUNCTIONAL SCORE: 25.00 / 25
```

## Complete AI conversation transcript

**User:** `Mid-term.docx`

**Assistant:** Found `/Users/ariannastith/Downloads/Mid-term.docx` and read the CSCI 363 Fall 2026 midterm (Questions 1–4, 100 points). AI is permitted for Questions 1–3 with a full development-log transcript. AI is prohibited for Question 4, so that file was not written.

**Question 1 work in this session:**

1. Copied the instructor `Driver.java` into `org.howard.edu.lsp.midterm.crccards` without changing the tests.
2. Implemented `Book`:
   - fields `isbn`, `title`, `author`, `available`
   - constructor starts the book available
   - getters only; no setter for availability
   - `borrowBook()` / `returnBook()` throw `IllegalStateException` on an illegal transition
   - `toString()` format `B101 Effective Java by Joshua Bloch [AVAILABLE]` or `[BORROWED]`
3. Implemented `Library` with a `HashMap<String, Book>`:
   - `addBook` rejects duplicate ISBNs with `IllegalArgumentException`
   - `findBook` returns the stored instance or `null`
   - `borrowBook` / `returnBook` locate then delegate; missing ISBN → `IllegalArgumentException`; already borrowed, already available, or fourth borrow → `IllegalStateException`
   - borrowing limit of three is enforced in `Library` before delegation
   - `getAvailableBooks`, `getBooksByAuthor` (case-sensitive, includes borrowed), `getBooksSortedByIsbn` (copy, ascending ISBN), `getBorrowedBookCount`
4. Added Javadoc on every class and public method, including `@param`, `@return`, and `@throws`.
5. Wrote `crccards.md` (why `Library` collaborates with `Book` and why the limit is a library rule).
6. Compiled and ran the Driver: 44 passed, 0 failed.

**Assistant design notes given in this session:**

- `Book` collaborators: none. A book does not know about libraries.
- `Library` collaborators: `Book`. The library asks books for ISBN/author/availability and tells them to borrow/return.
- The three-book cap cannot live on `Book` because one book cannot see the other loans.
- `HashMap` is the collection because the CRC “add and find books” operations are ISBN-keyed; sorting is a read-only copy, not the storage order.
