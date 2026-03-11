# SimpleFraction

Projet Java realise dans le cadre du TD 1 de Complements de programmation.

## Objectif

Ce projet propose une classe `Fraction` permettant de manipuler des nombres rationnels, ainsi qu'une classe `Main` qui presente un exemple d'utilisation des principales fonctionnalites.

## Fonctionnalites

- creation de fractions avec plusieurs constructeurs
- constantes `ZERO` et `UN`
- acces au numerateur et au denominateur
- conversion en `double`, `float`, `int` et `long`
- addition de deux fractions
- test d'egalite entre fractions equivalentes
- comparaison selon l'ordre naturel
- gestion du cas invalide d'un denominateur nul

## Structure

- `src/main/java/com/uvsq22507100/Fraction.java` : implementation de la classe `Fraction`
- `src/main/java/com/uvsq22507100/Main.java` : programme de demonstration

## Execution

Avec Maven :

```bash
mvn compile
mvn package
java -jar target/SimpleFraction-1.0-SNAPSHOT.jar
```

## Auteur

Abderrahmane Deffaf
