# 🃏 Card Game with JUnit Testing

A lightweight Java-based card game implementation with extensive test coverage, designed to demonstrate clean logic and automated testing practices.

Built as one of my university assignments, this project focuses on core gameplay mechanics and robust verification through JUnit tests. It serves as a good example of my ability to write well-tested code and think through game logic systematically.

## 🎮 Project Summary

This app simulates a turn-based card game. Players draw cards, play turns, and resolve game outcomes based on custom game mechanics. While the game itself is minimal, the code emphasizes correctness and testability.

## ✅ Highlights

- Object-oriented structure using classes for players, cards, and game state
- Consistent use of **JUnit tests** to validate game behavior
- Tested for:
  - Player creation and "shield" logic
  - Turn order and winner resolution
  - Edge cases like overdrawing or invalid states
- Clean `main()` flow for simulating a full game loop

## 🧪 Testing Focus

The accompanying test suite (`MainTest.java`) covers nearly all logical branches of the game. Tests are designed to:
- Validate expected behavior across different scenarios
- Prevent regressions by checking state transitions
- Demonstrate use of assertions

## 🚀 Technologies

- Java 17
- JUnit 5
- IntelliJ (IDE)

## 🔧 How to Run Locally

### 1. Clone the repository

```bash
git clone https://github.com/UzonnaA/Card-Game-JUnit
cd Card-Game-JUnit
```

### 2. Run tests
```bash
mvn test
```
### 3. Run the game
```bash
cd src
javac -d out main/java/org/example/Main.java
java -cp out org.example.Main
```