[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/LylzhpHc)
# TP Maven-JUnit
L'objet de cet exercice est de réaliser une classe `ChaineCryptee` qui permettra de chiffrer/déchiffrer une chaîne de caractères (**composée de lettres majuscules et d'espace**).
Le chiffrement utilise une méthode par décalage.
La valeur du décalage représente la clé de chiffrement.
Par exemple, une clé de valeur trois transformera un `'A'` en `'D'`.

La classe `ChaineCryptee` disposera
* des attributs pour la chaîne *en clair* et le décalage,
* un constructeur pour initialiser les instances à partir d'une chaîne en clair et du décalage,
* la méthode `String decrypte()` qui retourne la chaîne en clair,
* la méthode `String crypte()` qui retourne la chaîne chiffrée,
Vous pourrez utilisez pour cela la [méthode `decaleCaractere`](https://gist.github.com/hal91190/4f3a0941e652e82a5e1c572834d97827),
* la possibilité d'initialiser une instance à partir d'une chaîne cryptée et d'un décalage.
Pour éviter l'ambiguïté au niveau du constructeur, vous utiliserez le [modèle de conception Fabrication](http://fr.wikipedia.org/wiki/Fabrique_%28patron_de_conception%29#Autres_avantages_et_variantes).
Pour cela,
    * créez les méthodes de classe `ChaineCryptee deCryptee(String, int)` et `ChaineCryptee deEnClair(String, int)`,
    * rendez le constructeur privé.
La création des instances se fait maintenant à l'aide des deux méthodes de classe.

De plus, le projet sera géré avec [Maven](https://maven.apache.org/) et vous utiliserez une approche pilotée par les tests pour la réalisation, i.e. écrivez toujours un test avant le code et pensez au refactoring.
Le framework de tests devra être [JUnit 5](https://junit.org/junit5/).
Vous rédigerez votre code source en respectant les [conventions de codage](https://google.github.io/styleguide/javaguide.html) du guide de stype _Google_.
Enfin, la prise en compte des contraintes se fera par de la gestion d’erreurs et des exceptions.

Il pourra éventuellement être nécessaire de [configurer le proxy](http://maven.apache.org/guides/mini/guide-proxies.html) (http://wwwcache.uvsq.fr:3128) pour accéder au dépôt Maven.

À chaque étape, validez vos modifications avec `git` et si nécessaire, exécutez le cycle maven adapté pour vérifier vos manipulations.

1.  Quelles conventions utilise Maven pour l’arborescence des répertoires ?
    > convention over configuration
1.  Examinez le projet Maven ainsi que le fichier de description et répondez aux questions suivantes :
    1.  Dans quel répertoire se trouvent les fichiers sources du projet ? Les sources des tests ?
        >src/main/java/fr/uvsq/cporg/mvnjunit
        >src/test/java/fr/uvsq/cprog/mvnjunit
    1. Quelles sont les coordonnées du projet ?
        >groupId: fr.uvsq.cprog
        >artifactId: mvnjunit
        >version: 1.0-SNAPSHOT
    1. À quoi sert la propriété `project.build.sourceEncoding` ?
        > Elle spécifie l'encodage utilisé par Maven pour lire les fichiers
    1. Quelles versions des sources et de la JVM sont utilisés ?
        > 21
    1. Quelle version de JUnit est configurée ? À quoi sert la balise `scope` ?
        > Pour dire a mvn d'utiliser cette dépandance durant juste le test ou build ...
    1. À quoi sert la section `pluginManagement` ?
        > Utiliser pour les grande projets pour le management des sous-projets dans le meme repertoire pour eviter la répétition de définition de pom config 
1.  Modifiez la configuration du projet de la façon suivante :
    1.  fixez la version des sources et des .class pour utiliser la version 17 de Java
    1.  utilisez la dernière version stable de JUnit 4 (cf. [MVNRepository](https://mvnrepository.com/))
1.  Ajoutez un fichier `.gitignore` adapté aux projets Maven (cf. [A collection of .gitignore templates](https://github.com/github/gitignore)) et ajoutez-y les fichiers et répertoires de votre IDE.
1.  Quelle commande Maven permet de :
    1.  créer un `jar` du projet ?
        > mvn package
    1. lancer les tests ?
        > mvn test
    1. supprimer tous les fichiers issus de la compilation ?
        > mvn clean 
1.  Ajoutez une classe `ChaineCryptee` et une classe `ChaineCrypteeTest` dans les répertoires et packages appropriés.
    Supprimez les classes d'exemple `App` et `AppTest`.
1.  Énumérez une liste de cas de tests à réaliser en n'oubliant pas les cas d'erreur.
    > test de creation d'une chaine clair 
    > test de creation d'une chaine cryptée
    > test de cryptage 
    > test de decryptage 
    > test de cryptage pour les boucle de chaine de caractère 
    > test de decryptage avec boucle 
    > test de décalage négative 
    > test de décalage sup a 26
    > test avec valeur nulle
1.  Pour chaque cas de test,
    1. écrivez le test JUnit correspondant dans la classe de test,
    1. vérifiez qu’il échoue,
    1. implémentez la fonctionnalité dans la classe,
    1. vérifiez que le test passe,
    1. appliquez un étape de refactoring sur les tests et la classe si nécessaire.
1.  Comment se comporte votre classe si la chaîne passée au constructeur est `null` ?
Vous pouvez utiliser le débogueur pour identifier le problème (s'il y a un problème) au niveau de `crypte`.
    > There are test failures.
    1. ajoutez un test pour prendre en compte la chaîne `null`,
    1. si nécessaire, modifiez la classe pour faire passer le test
1. Changez la représentation interne de la classe : seule la chaîne cryptée est stockée (plus la chaîne en clair).
    * effectuez les modifications nécessaires sans changer l'interface de la classe (les tests ne seront donc pas impactés).
1.  Modifiez le POM pour intégrer la vérification des conventions de codage avec [`checkstyle`](http://maven.apache.org/plugins/maven-checkstyle-plugin/) en utilisant les conventions _Google_.
1.  Ajoutez une méthode `main` qui démontre quelques fonctionnalités de la classe, puis modifiez le POM pour que le jar généré soit exécutable (cf. [Apache Maven JAR Plugin](https://maven.apache.org/plugins/maven-jar-plugin/index.html))
1.  Modifiez la méthode `main` pour que les différents affichages se fassent à l'aide de la bibliothèque de logging [`SLF4J`](http://www.slf4j.org/)
1. En utilisant le plugin [assembly](https://maven.apache.org/plugins/maven-assembly-plugin/) (ou le plugin [shade](https://maven.apache.org/plugins/maven-shade-plugin/)), générez une archive du projet contenant ses dépendances (uber-jar)
