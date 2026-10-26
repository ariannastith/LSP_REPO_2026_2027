# Proposed Design — CRC Cards

Arianna Stith · CSCI 363 Midterm, Question 2, Part 2

The original `CourseRegistration` class is split so that each class below captures one abstraction. `RegistrationService` coordinates the process; it does not own student data, tuition rules, file I/O, or messaging.

Class:
Student

Responsibilities:
- Store the student’s identifier, name, and email.
- Provide the contact information needed to send a confirmation.
- Hide student fields behind a constructor and accessors so they cannot be changed arbitrarily during registration.

Collaborators:
None

Class:
Course

Responsibilities:
- Store the course code and credit hours.
- Provide credit hours to the object that computes tuition.
- Represent catalog information only; a course does not know about money, files, or email.

Collaborators:
None

Class:
TuitionCalculator

Responsibilities:
- Compute tuition from a course’s credit hours using the university’s per-credit rate.
- Apply the heavy-course-load discount when credit hours are at least 12.
- Keep those money rules in one place so the rate or discount can change without editing Student, Course, or storage.

Collaborators:
Course

Class:
Registration

Responsibilities:
- Represent one completed registration: which student, which course, and the tuition that was charged.
- Provide the data that persistence and confirmation need (student id, course code, tuition, student contact).
- Exist as a real object instead of a leftover pile of public fields.

Collaborators:
Student, Course

Class:
RegistrationStore

Responsibilities:
- Save a registration record.
- Hide the physical storage mechanism from the rest of the system.
- Report storage failure so a confirmation is not sent for a registration that was not saved.

Collaborators:
Registration

Class:
ConfirmationSender

Responsibilities:
- Notify the student that registration succeeded.
- Use the student’s contact information and the registration details to build the message.
- Hide the delivery mechanism (how the message actually goes out) from the registration process.

Collaborators:
Student, Registration

Class:
RegistrationLog

Responsibilities:
- Record that a registration was processed and when it happened.
- Keep logging out of the tuition and storage classes.

Collaborators:
Registration

Class:
RegistrationService

Responsibilities:
- Coordinate a registration: take a Student and a Course, ask TuitionCalculator for the amount, create a Registration, ask RegistrationStore to save it, ask ConfirmationSender to notify the student, and ask RegistrationLog to record the event.
- Own the order of those steps and stop if a step fails.
- Hold no student fields, no credit-hour fields, and no FileWriter of its own.

Collaborators:
Student, Course, TuitionCalculator, Registration, RegistrationStore, ConfirmationSender, RegistrationLog
