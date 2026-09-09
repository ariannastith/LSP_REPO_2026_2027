# CSCI 363 Assignment #2 — Employee Payroll ETL Pipeline

Arianna Stith

## Project layout

```
LSP_Fall_2026                (project root)
├── data
│   ├── employees.csv
│   └── transformed_employees.csv
└── src
    └── org/howard/edu/lsp/assignment2/ETLPipeline.java
```

## How to run

Run `ETLPipeline.main` with `LSP_Fall_2026` as the working directory (this is the
project root, which is what Eclipse and IntelliJ use by default). The program takes
no arguments and no keyboard input.

From the command line:

```
cd LSP_Fall_2026
javac -d bin src/org/howard/edu/lsp/assignment2/ETLPipeline.java
java -cp bin org.howard.edu.lsp.assignment2.ETLPipeline
```

It reads `data/employees.csv`, writes `data/transformed_employees.csv`, and prints
the run summary to the console:

```
Rows read: 14
Rows transformed: 7
Rows skipped: 7
Output file: data/transformed_employees.csv
```

## Design notes

- The pipeline is split into `extract`, `transform`, and `load` steps, with helper
  methods for the pay calculation, IT bonus, pay level, and employment status.
- Payroll math uses `BigDecimal` so the gross pay is rounded exactly once, at the
  end, with round-half-up. `HourlyRate` is never rounded before the calculation
  (employee 113 is paid on 16.666 but displayed as 16.67).
- Malformed rows (blank lines, wrong field counts, unparsable or negative numbers)
  are skipped, counted in the summary, and left out of the output file.

## AI / Internet disclosure

I used Cursor (Claude) as an AI assistant while writing and reviewing this
assignment, and I verified the compiled program's output against the grading
dataset in the assignment specification. No third-party CSV, ETL, or
data-processing libraries are used; only the standard Java library.
