# AI Usage Report — Assignment 6 (JUnit IntegerSet)

## AI Conversations

- ChatGPT conversation used to explain Assignment #6 and give step-by-step
  instructions for copying `IntegerSet`, setting up JUnit 5, completing the 47
  test methods, and submitting the package:
  https://chatgpt.com/share/6af7796a-0c64-83ea-9315-cfbc404e24cb

- Cursor chat on November 13, 2026, used to copy `IntegerSet` into the
  `assignment6` package, implement the 47 tests from the instructor template,
  and run the suite. This Cursor session does not publish a public share URL.

### What the AI was used for

- The ChatGPT conversation explained the assignment, the Arrange–Act–Assert
  pattern, and the JUnit assertions used here (`assertEquals`, `assertTrue`,
  `assertFalse`, `assertNotSame`, and `assertThrows`). It also mapped the 47
  tests onto the `IntegerSet` operations, including the assignment-specific
  definition of `complement(b)`.
- Cursor copied `IntegerSet.java` from Assignment #5 and changed only the
  package declaration. The public interface and behavior were left as specified
  in Assignment #5.
- Cursor replaced each TODO in the instructor `IntegerSetTest.java` template
  with assertions that check result contents, exact ascending `toString`
  formatting, and that set-returning operations do not change their inputs.
  `largest()` and `smallest()` on an empty set are checked with `assertThrows`
  for `IllegalStateException`. The 47 method names, `@Test` annotations, and
  Javadoc descriptions were kept.
- Compiled and ran `IntegerSetTest` with JUnit 5 (JUnit Platform Console
  1.11.4). All 47 tests passed.

### What was written by hand vs. AI-assisted

`IntegerSet.java` is the Assignment #5 implementation, moved into the new
package. `IntegerSetTest.java` was produced in the Cursor session from the
instructor template, the Assignment #5 behavioral cases, and the testing
approach described in the ChatGPT conversation above. No `Driver.java` is
included.

## External References

- JUnit User Guide — Assertions (`assertEquals`, `assertTrue`, `assertThrows`):
  https://docs.junit.org/6.0.0/writing-tests/assertions.html
- JUnit Jupiter API — `org.junit.jupiter.api.Assertions`:
  https://docs.junit.org/6.0.0/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html
- Java SE 21 API — `IllegalStateException`:
  https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/IllegalStateException.html
