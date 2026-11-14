# ADR 0001 — Initial architecture

Date: 2026-11-13 (baseline recorded retroactively)
Status: accepted (describes what is built)

## Context

A TDD course exercise: the String Calculator kata inside an Android
project, chosen so tests run as local JVM unit tests.

## Decisions

1. **Kata logic as plain Java, no Android deps.** `StringCalculator` is a
   POJO; tests run under `testDebugUnitTest` without an emulator.
   Rationale: the exercise is TDD, not Android — keep the test loop fast.
2. **JUnit 4 for the kata tests.** Matches course material; the manual
   `javac`/`JUnitCore` fallback in README depends on JUnit 4.12 jars.
3. **AGP 3.4.1 / Gradle 5.1.1 / JDK 8 / SDK 28 stack.** Period-accurate
   for the course; CI pins exactly that. Rationale: reproducibility — the
   old toolchain is a constraint, accepted.
4. **Stock template for the app shell.** `MainActivity` and Espresso test
   remain the default Android template — no app features were added.
   Rationale: the kata is the product; the shell is scaffolding.
5. **One test per rule, in rule order.** `StringCalculatorTest` documents
   the kata's evolution. Rationale: readable TDD history.

## Consequences

- The toolchain is frozen at JDK 8/SDK 28 — any modernisation is a
  dedicated migration (AGP + Gradle wrapper + compileSdk + JDK), not a
  tweak.
- Instrumented tests exist but aren't exercised by CI — emulator-required
  tests are out of scope for automation here.
- Calculator rules are pinned by tests; changing delimiter semantics is a
  spec change, not a refactor.
