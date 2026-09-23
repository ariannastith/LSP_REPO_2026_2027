# Assignment #3 — Design Discussion

Arianna Stith · CSCI 363 Large Scale Programming

## How to run

From the `LSP_Fall_2026` project root (the working directory Eclipse uses by default):

```
javac -d bin src/org/howard/edu/lsp/assignment3/*.java
java -cp bin org.howard.edu.lsp.assignment3.ETLPipeline
```

The program takes no arguments and no keyboard input. It reads `data/employees.csv`,
writes `data/transformed_employees.csv`, and prints the run summary.

## How Assignment #2 was organized

Assignment #2 was a single class, `ETLPipeline`, holding one `main` method and ten
`private static` helper methods (`extract`, `transform`, `load`, `calculatePay`,
`applyItBonus`, `determinePayLevel`, `determineEmploymentStatus`, `roundMoney`,
`formatTwoDecimals`, `isInteger`, `parseDecimal`). Every payroll constant was a
`static final` field on that same class.

The program worked, but it was procedural rather than object-oriented:

- There were no objects at all. Data moved through the program as `String`s and
  loose `BigDecimal`s, and an employee only existed as a `String[]` inside
  `transform` before being flattened straight back into a comma-separated `String`.
- `transform` did too much. One method normalized fields, validated them, computed
  base pay, overtime, and the bonus, rounded the result, classified the pay level
  and employment status, and formatted the output row.
- Invalid rows were reported by returning `null`, so the reason a row was rejected
  was thrown away and the caller had to remember to check for `null`.
- The row counters were three local `int` variables in `main`, incremented by hand,
  and the CSV header was a constant on the pipeline class even though only the
  writing code used it.

## What changed in Assignment #3

The behavior is unchanged — the same output file and the same console summary — but
the work is now divided among objects that each own one responsibility.

| Class | Responsibility |
| --- | --- |
| `ETLPipeline` | Coordinates the run: read, transform, write, print. Holds no payroll rules. |
| `EmployeeCsvReader` | Extract. Reads the input file and drops the header row. |
| `EmployeeRowParser` | Normalizes and validates one raw row, producing an `Employee`. |
| `InvalidRowException` | Reports why a row was rejected. |
| `Employee` | Immutable, validated employee data. |
| `PayrollCalculator` | The payroll rules: base pay, overtime, department bonus, rounding. |
| `PayrollRecord` | A transformed employee; derives its own classifications and renders its CSV row. |
| `PayLevel` | Enum of pay levels plus the thresholds that assign them. |
| `EmploymentStatus` | Enum of employment statuses plus the hours threshold. |
| `PayrollCsvWriter` | Load. Writes the header and the records to the output file. |
| `PipelineSummary` | Counts transformed and skipped rows and prints the summary. |

## Why these abstractions

**`Employee` and `PayrollRecord` give the data a shape.** `Employee` is the
validated input; `PayrollRecord` is the transformed result. Because an `Employee`
can only be created by the parser, every later stage can use its values without
re-validating them, which is what let the giant `transform` method disappear.
`PayrollRecord` derives its pay level and employment status in its constructor, so
a record is never half-built, and it knows how to render its own CSV row.

**Validation and calculation are now separate objects.** `EmployeeRowParser` owns
everything about the *format* of the input (field count, trimming, upper-casing,
what counts as a bad value), and `PayrollCalculator` owns everything about *money*
(the 40-hour overtime threshold, the 1.5 multiplier, the 5% IT bonus, half-up
rounding to two places). In Assignment #2 those two concerns were interleaved in
one method. If the pay rules change, only `PayrollCalculator` changes; if the input
format changes, only `EmployeeRowParser` changes.

**The classification rules moved into enums.** `PayLevel` and `EmploymentStatus`
replace two helper methods that returned bare strings. Each enum keeps its labels
and its thresholds together, so an invalid pay level is no longer representable and
the output labels cannot drift away from the rules that produce them.

**Rejected rows are reported, not silenced.** `InvalidRowException` replaces the
`null` return. The caller is now forced by the compiler to handle a bad row, and
the exception carries a specific reason ("HourlyRate is not a number: bad") instead
of discarding it.

**I/O is encapsulated at both ends.** `EmployeeCsvReader` and `PayrollCsvWriter`
each own a `Path` and the details of reading or writing it. The header constant
moved onto `PayrollRecord`, next to the method that produces matching rows, so the
header and the rows can no longer fall out of step. The writer just asks each
record for its row.

**`PipelineSummary` replaces the loose counters.** Rows are reported to it as
transformed or skipped, and it derives `rowsRead` from those two, so the three
numbers cannot contradict each other the way three hand-incremented `int`s could.

## Why this is an improvement

- **Single responsibility.** No class does more than one job, and `ETLPipeline` is
  now only a coordinator — its `transform` loop is six lines and contains no payroll
  logic.
- **Encapsulation.** Payroll constants live with the code that uses them, the header
  lives with the row it describes, and the counters live behind methods instead of
  being mutable locals.
- **Cohesion over static utilities.** Assignment #2's helpers were stateless static
  methods operating on primitives; Assignment #3 has objects that hold their own data
  and behavior, which is the main change I was asked to demonstrate.
- **Easier to change and to test.** `PayrollCalculator`, `EmployeeRowParser`,
  `PayLevel`, and `EmploymentStatus` can each be exercised on their own, and
  `ETLPipeline` can be pointed at different files through its constructor.

I deliberately did not create a class per method. Each class above either models
something in the problem domain (an employee, a payroll record, a pay level) or owns
one stage of the pipeline (read, parse, calculate, write, report).

## Verification

I compiled both versions and ran them against the same `data/employees.csv`. The
Assignment #3 output file is byte-for-byte identical to the Assignment #2 output
file, and the console summary is the same:

```
Rows read: 14
Rows transformed: 7
Rows skipped: 7
Output file: data/transformed_employees.csv
```

I also checked that the two versions behave the same when the input file is
missing, when it contains only a header, and when it is completely empty.

## AI and Internet resources

I used Cursor (Claude) as an AI assistant to help refactor my Assignment #2
solution and to draft this design discussion. I reviewed, compiled, and tested the
resulting code myself and verified that its output matches my Assignment #2 output.

Transcript of my AI interaction: <!-- TODO: paste the shared transcript link here before submitting -->

I did not use any Internet resources or third-party libraries for this assignment;
the solution uses only the standard Java library.
