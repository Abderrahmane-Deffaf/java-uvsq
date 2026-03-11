# TD2 - ChaineCryptee

Petit projet Maven en Java autour d'une classe `ChaineCryptee` qui permet de chiffrer et déchiffrer une chaîne en majuscules avec un décalage de type César.

## Contenu

- `ChaineCryptee` : création depuis une chaîne en clair ou déjà chiffrée
- tests unitaires avec JUnit
- démonstration via une classe `Main`
- génération d'un JAR exécutable avec ses dépendances

## Structure

- `src/main/java` : code source
- `src/test/java` : tests
- `pom.xml` : configuration Maven

## Commandes utiles

```bash
mvn clean
mvn test
mvn package
java -jar target/ChaineCryptee-1.0-SNAPSHOT.jar
```

## Remarque

Le projet utilise `SLF4J` pour les affichages dans `Main` et `Checkstyle` pour la vérification du style.
