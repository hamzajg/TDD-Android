# Architecture — TDD-Android

Current-state map. Descriptive, not aspirational; see `docs/adr/` for why.

## Shape

```
TDD-Android/   (Android Gradle project, AGP 3.4.1 / Gradle 5.1.1)
├── app/
│   ├── build.gradle              compileSdk 28, applicationId com.hamzajg.tddandroid
│   └── src/
│       ├── main/java/.../StringCalculator.java   the kata (plain Java)
│       ├── main/java/.../MainActivity.java       stock template
│       ├── test/java/.../StringCalculatorTest.java  JUnit4, one test/rule
│       └── androidTest/.../ExampleInstrumentedTest.java  Espresso template
├── build.gradle / settings.gradle / gradle.properties / gradlew(bat)
└── .github/workflows/ci.yml     JDK 8 + SDK 28 → gradlew test
```

## Design

- **Kata as plain JVM class.** `StringCalculator` has zero Android imports
  so local unit tests (`testDebugUnitTest`) exercise it without an
  emulator — the whole point of the repo.
- **Test = rule history.** `StringCalculatorTest` adds one test per rule in
  the order the kata introduced them.
- **Template shell elsewhere.** `MainActivity` + Espresso test are the
  stock Android template, untouched — the app itself does nothing.

## Toolchain constraints

AGP 3.4.1 + Gradle 5.1.1 ⇒ **JDK 8 only**, SDK 28 + build-tools 28.0.3.
CI replicates exactly that. Modernising (AGP/Gradle/JDK/compileSdk) is a
separate migration project, not a version bump.
