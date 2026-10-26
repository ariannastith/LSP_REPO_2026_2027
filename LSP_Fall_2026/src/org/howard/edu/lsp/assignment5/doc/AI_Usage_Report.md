# AI Usage Report — Assignment 5 (IntegerSet)

## AI Conversations

- ChatGPT conversation used to design and implement `IntegerSet.java`, copy the provided
  `Driver.java` into the required package, and run the grading driver:
  https://chatgpt.com/share/6adf1f6d-437c-83ea-8ffe-f980f2c3a9d3

### What the AI was used for

- Discussed internal representation options and settled on a `java.util.TreeSet<Integer>`,
  which gives duplicate rejection and ascending iteration order for `toString()` for free.
- Implemented the required public methods and their Javadoc comments.
- Confirmed the assignment-specific definition of `complement(b)` (elements of `b` that are
  not in the current set) and that every set-returning operation must produce an independent
  object.
- Compiled and ran the unmodified `Driver.java`; the saved output in
  `console_output.txt` shows 47/47 tests and a functional score of 92/92.

### What was written by hand vs. AI-assisted

All code in `IntegerSet.java` was produced in the AI-assisted session above and then reviewed
line by line for correctness against the behavioral specification in the assignment.
`Driver.java` is a verbatim copy of the instructor-provided driver and was not modified.

## External References

- Java SE 21 API — `java.util.Set`:
  https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Set.html
- Java SE 21 API — `java.util.TreeSet`:
  https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/TreeSet.html
- Java SE 21 API — `java.util.Collection` (`addAll`, `retainAll`, `removeAll`, `containsAll`):
  https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Collection.html
- Java Tutorials — The Set Interface:
  https://docs.oracle.com/javase/tutorial/collections/interfaces/set.html
