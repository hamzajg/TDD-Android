# AGENTS.md — TDD-Android

Guidance for AI agents (and humans) working in this repository.

## Project

Android unit-testing / TDD practice (Udemy course). The app is the stock
empty Android template; the substance is the **String Calculator kata** —
a plain JVM class written test-first, exercised by local unit tests (no
emulator needed).
Stack: Android (AGP 3.4.1 / Gradle 5.1.1), Java 8, Android SDK 28,
JUnit 4, Espresso (template only).

## Commands

Verified 2026-11-13 against CI (`.github/workflows/ci.yml`) and README.

| Task | Command |
|---|---|
| Unit tests | `sh gradlew test --no-daemon` (CI: JDK 8 + Android SDK 28) |
| Test report | `app/build/reports/tests/testDebugUnitTest/index.html` |
| Run tests without Gradle | `javac`/`java org.junit.runner.JUnitCore` recipe in README (needs junit-4.12 + hamcrest jars) |

Requires: **JDK 8** (AGP 3.4.1/Gradle 5.1.1 can't run on newer), Android SDK
platform 28 + build-tools 28.0.3. Instrumented tests (`androidTest`) need
an emulator — CI only runs local unit tests.

## Architecture

```
app/src/main/java/com/hamzajg/tddandroid/
  StringCalculator.java    the kata (133 lines, rules in javadoc)
  MainActivity.java        stock template shell
app/src/test/java/.../StringCalculatorTest.java   one test per rule,
                                                in the order rules were added
app/src/androidTest/.../ExampleInstrumentedTest.java  Espresso template
app/build.gradle          compileSdk 28, applicationId com.hamzajg.tddandroid
build.gradle / settings.gradle / gradlew(bat)     Gradle 5.1.1 wrapper
```

## The kata rules (the spec)

Empty/null → 0; commas & newlines separate; custom delimiter `//<d>\n`;
bracketed multi-char/multi delimiters `//[***]\n`, `//[*][%]\n`; malformed
brackets → `IllegalArgumentException`; negatives rejected with all listed;
numbers > 1000 ignored.

## Conventions

- **Tests first, one test per rule** — `StringCalculatorTest` reads as the
  kata's history; new rules get a new test.
- `StringCalculator` is deliberately plain Java (no Android deps) so it
  runs under local JVM unit tests — keep it that way.
- Plain JUnit 4 (`org.junit.Test`, `@Test(expected=...)` or try/catch) —
  match existing style.
- KISS: this is a kata repo; no architecture layers, no DI.

## Landmines

- **Default branch is `master`, not `main`.**
- **JDK 8 is mandatory** — AGP 3.4.1 + Gradle 5.1.1 fail on modern JDKs;
  CI pins `java-version: "8"`. Don't bump the wrapper/AGP casually.
- `compileSdkVersion 28` — needs SDK platform 28 + build-tools 28.0.3;
  newer SDKs may break the old AGP.
- `testDebugUnitTest` is the unit-test task — `connectedAndroidTest`
  needs a device and isn't in CI.
- README documents a manual javac/JUnitCore fallback — useful when the
  Android toolchain is unavailable.

## Agent rules

- Branch from `master`; name branches `feat/*`, `fix/*`, `chore/*`, `docs/*`.
- Docs-only changes still go through a PR; never commit to `master` directly.
- Verify before claiming: `sh gradlew test --no-daemon` on JDK 8 — say if
  you couldn't run it.
- Keep changes minimal and consistent with surrounding code. No drive-by
  refactors.
- New calculator behaviour needs a test in `StringCalculatorTest` first.
- Update AGENTS.md / docs/ARCHITECTURE.md when rules, structure, or
  toolchain change.
