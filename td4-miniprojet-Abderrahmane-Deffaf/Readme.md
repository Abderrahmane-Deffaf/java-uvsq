# MMA Mini-Project

This project is a small Java application that manipulates relational algebra queries on CSV files. It supports two main workflows:

- executing a query described in a `.json` file;
- translating a query written in a `.alg` file into a `.json` file.

The application is built as a Maven project and produces an executable JAR. Internally, queries are executed through a tree of operators implementing a simple relational algebra engine.

## What the project does

The engine works on CSV tables and supports these operators:

- `SCAN`: reads a CSV table;
- `SELECT`: filters rows with a simple predicate;
- `PROJECT`: keeps only selected columns;
- `JOIN`: performs an equi-join between two inputs.

The codebase is organized around:

- `mma.App`: application entry point;
- `mma.io`: dispatch based on input file extension;
- `mma.algtojson`: translation from `.alg` to `.json`;
- `mma.jsonexecution`: execution of JSON query plans;
- `mma.operators`: relational operators;
- `mma.csv` and `mma.model`: CSV I/O and in-memory table model.

## Build

Build the project with:

```bash
mvn package
```

This generates the runnable JAR in `target/`, notably:

```bash
target/mma-1.0-SNAPSHOT.jar
```

## Run

The program accepts a single argument, which can be either a `.json` file or a `.alg` file.

### Option 1: run a `.json` query

Use a JSON file that describes:

- the input tables;
- the execution plan;
- the query to run on the CSV files.

Command example:

```bash
java -jar target/mma-1.0-SNAPSHOT.jar ./../td4-banc_tests/TJoinProj/req.json
```

Example output:

```text
Traitement fichier JSON: ./../td4-banc_tests/TJoinProj/req.json
Résultat sauvegardé dans: D:\1 study\datasclae-master-1\s1\complement de programmation\code\td4-miniprojet-Abderrahmane-Deffaf\.\..\td4-banc_tests\TJoinProj\results\output.csv
Requête exécutée avec succès!
```

When a `.json` file is executed, the result is written to the corresponding `results/output.csv` file in the same test directory.

### Option 2: translate a `.alg` query

If the input is a `.alg` file, the application translates it into a `.json` file with the same base name in the same directory.

Example:

```bash
java -jar target/mma-1.0-SNAPSHOT.jar ./../td4-banc_tests/TJoinProj/req.alg
```

This does not execute the query result. It only generates:

```text
./../td4-banc_tests/TJoinProj/req.json
```

## Input files and results

The input files and generated results are expected to be placed in `td4-banc_tests`, for example:

- `../td4-banc_tests/TProject/`
- `../td4-banc_tests/TSelect/`
- `../td4-banc_tests/TJoinProj/`

Each test directory may contain:

- CSV source tables such as `users.csv` and `orders.csv`;
- a request file such as `req.json` or `req.alg`;
- a `results/` directory containing the generated `output.csv`.

## `.alg` file format

A `.alg` file contains:

1. table declarations;
2. a blank line;
3. algebra operations.

Example:

```text
users: users.csv
orders: orders.csv

R1 = SELECT users age < 25
R = PROJ R1 name
```

Arguments are separated by spaces. String constants in predicates should be written with single quotes.
