# TDD-Android

Android unit testing and test-driven development practice (Udemy course).

The Android app is the standard empty template; the substance is the
**String Calculator kata**, written test-first as a plain JVM class so it can
be exercised by local unit tests rather than an emulator.

## The kata

[`StringCalculator`](app/src/main/java/com/hamzajg/tddandroid/StringCalculator.java)
sums the numbers in a string:

| Rule | Example |
|---|---|
| An empty string (or `null`) is 0 | `"" -> 0` |
| Numbers are separated by commas | `"1,2" -> 3` |
| A newline also separates numbers | `"1\n2,3" -> 6` |
| A custom delimiter can be declared | `"//;\n1;2" -> 3` |
| Negative numbers are rejected, and every negative is reported | `"-1,2,-3" -> IllegalArgumentException("negatives not allowed: -1, -3")` |
| Numbers greater than 1000 are ignored | `"2,1001" -> 2` |

[`StringCalculatorTest`](app/src/test/java/com/hamzajg/tddandroid/StringCalculatorTest.java)
has one test per rule, in the order the rules were added — the history of the
kata.

## Requirements

- JDK 8 (the Android Gradle Plugin 3.4.1 / Gradle 5.1.1 pair requires it)
- Android SDK platform 28 and build-tools 28.0.3

## Run the unit tests

```bash
sh gradlew test
```

These are local JVM tests; no emulator or device is needed. The report is
written to `app/build/reports/tests/testDebugUnitTest/index.html`.

The test class is ordinary JUnit 4, so it can also be run directly:

```bash
javac -d out \
  app/src/main/java/com/hamzajg/tddandroid/StringCalculator.java \
  app/src/test/java/com/hamzajg/tddandroid/StringCalculatorTest.java
java -cp out:junit-4.12.jar:hamcrest-core-1.3.jar \
  org.junit.runner.JUnitCore com.hamzajg.tddandroid.StringCalculatorTest
```

## Layout

```
app/src/main/java/com/hamzajg/tddandroid/StringCalculator.java   the kata
app/src/test/java/com/hamzajg/tddandroid/StringCalculatorTest.java
app/src/main/java/com/hamzajg/tddandroid/MainActivity.java        the template shell
app/src/androidTest/...                                           Espresso template test
```

## Continuous integration

[`.github/workflows/ci.yml`](.github/workflows/ci.yml) installs Android SDK 28
and JDK 8, then runs `gradlew test` on every push and pull request to `master`.

## License

See [LICENSE](LICENSE).
