# Development Log — Question 3

## Resources used

- Cursor (Grok 4.6), this conversation. Complete transcript is included below.
- Java Language Specification / standard inheritance and overriding behavior, used to confirm that `Employee.calculatePay()` can be abstract and that `Driver` can hold an `Employee` reference to a `FullTimeEmployee`, `PartTimeEmployee`, or `Contractor`.
- The provided `Driver.java` and `EmployeePayCalculator` snippet from the midterm exam, used as the specification for class names, constructors, and expected pay amounts.

No other websites, textbooks, or people were consulted for this question.

## How the resources helped

The Driver is the contract: it constructs `new FullTimeEmployee(hours, rate)`, `new PartTimeEmployee(hours, rate)`, and `new Contractor(hours, rate)`, stores them as `Employee`, and calls `calculatePay()` with no arguments. That forced a superclass `Employee` plus three subclasses, each in its own file, with `calculatePay()` overridden rather than selected by a `String` type code.

The original class paid full-time and part-time the same way (`hours * rate`) and paid contractors `hours * rate * 1.15`. The exam adds overtime for full-time employees: hours beyond 40 are paid at 1.5 times the hourly rate. The Driver checks that:

- 45 hours at $20 = `40 * 20 + 5 * 20 * 1.5` = 950
- 50 hours at $10 = `40 * 10 + 10 * 10 * 1.5` = 550
- part-time 45 hours at $20 = 900 (no overtime)
- contractor 30 hours at $20 = 690 (15% on all hours, no overtime)

The exam says not to use a design pattern. The implementation is ordinary inheritance: an abstract `Employee` with hours and rate, and three subclasses that override `calculatePay()`. There is no Strategy, Factory, or extra policy object.

Compiled and executed with the unmodified Driver:

```
RESULT: 12 PASSED, 0 FAILED
```

## Complete AI conversation transcript (Question 3 portion)

**User prompt (this session):** `Mid-term.docx`

The attached exam file is the CSCI 363 Fall 2026 midterm. After reading it, the assistant implemented Questions 1–3 in `LSP_Fall_2026/src/org/howard/edu/lsp/midterm/` and refused to write Question 4 because the exam forbids AI assistance on that question.

**Assistant work on Question 3:**

1. Identified required types from the Driver: abstract/concrete superclass `Employee` with `calculatePay()`, subclasses `FullTimeEmployee`, `PartTimeEmployee`, and `Contractor`, each constructed as `(double hoursWorked, double hourlyRate)`.
2. Wrote `Employee` as an abstract class holding private `hoursWorked` and `hourlyRate`, protected getters, an abstract `calculatePay()`, and Javadoc on the class, constructor, and public method.
3. Wrote `FullTimeEmployee.calculatePay()` as straight-time up to 40 hours and 1.5x thereafter.
4. Wrote `PartTimeEmployee.calculatePay()` as `hours * rate` with no overtime.
5. Wrote `Contractor.calculatePay()` as `hours * rate * 1.15` on all hours.
6. Copied the provided `Driver.java` into `org.howard.edu.lsp.midterm.inheritance` without changing its tests.
7. Compiled and ran the Driver; all 12 checks passed.

**Assistant response (substance):** The refactoring replaces the `if (employeeType.equals(...))` chain with polymorphism. Each employment type is a class that knows its own pay rule. `Driver.test` already treats employees uniformly as `Employee`, which is the point of the question. Overtime is a full-time rule only, which is why it lives in `FullTimeEmployee` and not in the superclass.
