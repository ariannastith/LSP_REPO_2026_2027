# Development Log — Question 2

## Resources used

- Cursor (Grok 4.6), this conversation. Complete transcript is included below.
- Arthur J. Riel, *Object-Oriented Design Heuristics* (Addison-Wesley), heuristics referenced in the evaluation:
  - 2.1 All data should be hidden within its class
  - 2.8 A class should capture one and only one key abstraction
  - 2.9 Keep related data and behavior in one place
  - 2.10 Spin off non-related information into another class
  - 3.1 Distribute system intelligence horizontally as uniformly as possible
  - 3.2 Do not create god classes/objects
  - 3.4 Beware of classes that have too much noncommunicating behavior
  - 4.1 Minimize the number of classes with which another class collaborates
- The provided `CourseRegistration` class in the midterm exam.

No other websites were used. No other people were consulted.

## How the resources helped

Riel’s heuristics gave a vocabulary for the problems in `CourseRegistration`: public fields (2.1), more than one abstraction (2.8 / 3.2), tuition/email/file code each using different subsets of the fields (2.9, 2.10, 3.4), and all of the work sitting in one method (3.1). That evaluation is what the CRC split in Part 2 is answering: `Student`, `Course`, `TuitionCalculator`, `Registration`, `RegistrationStore`, `ConfirmationSender`, `RegistrationLog`, and `RegistrationService`.

Part 3’s new requirements (file or database; email or text) map onto `RegistrationStore` and `ConfirmationSender`. The important ownership decision in `design_rationale.md` is that the store owns *how* a record is written, and the service owns only the *order* of the registration steps. That is the same split Assignment 3 used when `ETLPipeline` stopped owning payroll rules and file format at the same time.

## Complete AI conversation transcript (Question 2 portion)

**User prompt (this session):** `Mid-term.docx`

**Assistant:** Read Question 2 as a design-only question (no Java implementation). Produced three Markdown files plus this log.

**Prompt implied by the exam text:** Evaluate `CourseRegistration` using Arthur Riel’s heuristics; propose a CRC redesign; explain how that design absorbs file-vs-database storage and email-vs-text confirmation.

**Assistant evaluation (substance, also written to `evaluation.md`):**

`CourseRegistration` is a god class. Public fields violate Heuristic 2.1. The class mixes student identity, course catalog data, tuition policy, file persistence, email, and logging, which violates 2.8 and 3.2. `registerStudent()` is a procedural script: hardcoded $750 rate and 10% discount, `System.out` as both UI and “email,” `FileWriter` to `registrations.txt`, and a `catch (Exception)` that prints a stack trace so a failed save can still look successful. Different lines of the method use different subsets of the fields (noncommunicating behavior, 3.4). Intelligence is not distributed (3.1).

**Assistant CRC design (substance, also written to `design.md`):**

| Class | Responsibility | Collaborators |
| --- | --- | --- |
| Student | id, name, email | none |
| Course | code, credit hours | none |
| TuitionCalculator | per-credit rate and heavy-load discount | Course |
| Registration | one student + one course + computed tuition | Student, Course |
| RegistrationStore | save a registration | Registration |
| ConfirmationSender | notify the student | Student, Registration |
| RegistrationLog | record that registration happened | Registration |
| RegistrationService | order the steps; no FileWriter of its own | the seven classes above |

**Assistant rationale (substance, also written to `design_rationale.md`):**

Add `FileRegistrationStore` / `DatabaseRegistrationStore` under `RegistrationStore`, and `EmailConfirmationSender` / `TextConfirmationSender` under `ConfirmationSender`. The deployment environment injects the pair. `RegistrationService` still collaborates with the abstract store and sender. The key decision: persistence belongs to `RegistrationStore`, not to `RegistrationService`, so a database requirement does not rewrite the registration process or the student/course/tuition classes.

Question 2 was not implemented in Java, as the exam does not require it.
