# Complements de programmation - Vue globale du depot

Ce depot regroupe plusieurs projets realises dans le cadre des TD de complementation de programmation. Chaque dossier correspond a un travail pratique distinct, avec une progression qui va d'un petit projet Java/Maven a un mini-projet de manipulation de requetes sur fichiers CSV.

L'objectif de ce `README` est de donner une vue d'ensemble du depot, du role de chaque projet, et des principaux modules implementes dans chaque TD.

## Structure du depot

```text
code/
|- cpl-prog-td1-Abderrahmane-Deffaf/
|- td2-Abderrahmane-Deffaf/
|- td3-Abderrahmane-Deffaf/
|- td4-banc_tests/
`- td4-miniprojet-Abderrahmane-Deffaf/
```

## Resume des TD

### TD1 - Fractions, Git et prise en main du projet Java

Dossier : `cpl-prog-td1-Abderrahmane-Deffaf`

Ce premier TD sert surtout a prendre en main l'environnement de travail : utilisation de `git`, structure d'un projet Maven simple, compilation Java et completion d'une classe metier.

Projet principal :

- `SimpleFraction` : mini-projet Java centré sur une classe `Fraction`.

Modules / classes principales :

- `Fraction` : represente un nombre rationnel avec plusieurs constructeurs, des acces au numerateur/denominateur, des conversions numeriques, l'addition, l'egalite et la comparaison.
- `Main` : programme de demonstration montrant l'utilisation de la classe.
- `TD1.md` : enonce du TD, avec une partie outillage (`git`, shell, IDE) et une partie implementation autour des fractions.

Ce TD pose les bases :

- modelisation d'un objet metier simple ;
- principes de validation des donnees ;
- usage de Maven pour compiler et packager ;
- premiers automatismes de travail en ligne de commande.

### TD2 - Chaine chiffree, tests unitaires et qualite

Dossier : `td2-Abderrahmane-Deffaf`

Le TD2 est un projet Maven/JUnit autour d'un chiffrement de Cesar. Le coeur du projet est une classe qui sait produire une version cryptee d'une chaine en majuscules, ou reconstruire la chaine en clair a partir d'une version deja chiffree.

Modules / classes principales :

- `ChaineCryptee` : logique metier de chiffrement/dechiffrement avec decalage.
- `Main` : point d'entree de demonstration.
- `ChaineCrypteeTest` : tests unitaires JUnit verifies sur le comportement attendu.
- `simplelogger.properties` : configuration de logs simples pour l'execution.
- `pom.xml` : configuration Maven avec JUnit, Checkstyle, SLF4J et creation d'un JAR executable.

Ce TD introduit plus clairement :

- les tests unitaires ;
- la verification du style de code avec Checkstyle ;
- la creation d'un executable Java complet avec dependances ;
- une separation plus nette entre code applicatif et code de test.

### TD3 - Mini serveur DNS avec collections et patron Commande

Dossier : `td3-Abderrahmane-Deffaf`

Le TD3 implemente une petite application Java de type DNS simplifie. Le projet charge une base texte contenant des associations entre noms de machines et adresses IP, puis propose des operations de recherche, de listing par domaine et d'ajout d'entrees.

Modules / classes principales :

- `AddresseIP` : validation et encapsulation d'une adresse IPv4.
- `NomMachine` : validation d'un nom de machine qualifie.
- `DnsItem` : association entre un nom de machine et une adresse IP.
- `Dns` : coeur metier, chargement de la base, recherches et ajouts.
- `Commande` : interface ou abstraction du patron Commande.
- `RechercherItem`, `RechercheDomaine`, `AjouterItem` : commandes correspondant aux actions fonctionnelles demandees.
- `DnsTUI` et `DnsApp` : interface texte / mode interactif.
- `App` : point d'entree de demonstration.
- `src/main/resources/db.txt` : base de donnees DNS d'exemple.
- `config.properties` : configuration d'acces a la base.

Ce TD met l'accent sur :

- l'usage des collections Java ;
- la validation metier des objets ;
- une persistance simple basee sur fichier texte ;
- l'application du patron Commande ;
- la mise en place d'une interface en ligne de commande.

### TD4 - Mini-projet d'algebre relationnelle sur CSV

Dossier principal : `td4-miniprojet-Abderrahmane-Deffaf`

Le TD4 est le projet le plus complet du depot. Il s'agit d'un moteur simple de traitement de requetes relationnelles sur des fichiers CSV. L'application sait :

- executer une requete decrite dans un fichier `.json` ;
- traduire une requete algebrique ecrite dans un fichier `.alg` vers un plan JSON ;
- produire un resultat au format CSV.

Modules / packages principaux :

- `mma.App` : point d'entree principal.
- `mma.io` : detection du type de fichier d'entree et delegation du traitement.
- `mma.algtojson` : construction d'un JSON a partir d'une requete algebrique.
- `mma.jsonexecution` : execution d'un plan de requete JSON.
- `mma.operators` : operateurs relationnels (`SCAN`, `SELECT`, `PROJECT`, `JOIN`).
- `mma.csv` : lecture et ecriture des fichiers CSV.
- `mma.model` : representation en memoire des tables.
- `src/test/java/mma/AppTest.java` : base de tests.

Fonctionnalites couvertes :

- lecture de tables CSV ;
- projection de colonnes ;
- selection de lignes selon un predicat ;
- jointure entre tables ;
- interpretation d'un plan d'execution ;
- traduction d'une requete algebrique vers JSON.

Aspects d'outillage presents dans ce TD :

- Maven ;
- JUnit 5 ;
- Gson pour la lecture/ecriture JSON ;
- Checkstyle ;
- JaCoCo pour la couverture de tests ;
- Shade Plugin pour produire un JAR executable.

### TD4 - Banc de tests et jeux de donnees

Dossier : `td4-banc_tests`

Ce dossier n'est pas un projet Maven independant, mais un banc de tests de donnees utilise par le mini-projet du TD4. Il contient plusieurs repertoires de scenarios :

- `TSelect` : cas de test focalise sur l'operateur `SELECT`.
- `TProject` : cas de test focalise sur l'operateur `PROJECT`.
- `TJoinProj` : cas de test combinant jointure et projection.

Chaque repertoire contient typiquement :

- des tables sources au format CSV ;
- un fichier de requete (`req.json`, parfois aussi `req.alg`) ;
- un fichier `expected.csv` pour la sortie attendue ;
- des sorties generees (`output.csv`, dossier `results/`).

Ce dossier sert de support fonctionnel au TD4 :

- pour valider l'interpretation des requetes ;
- pour comparer les sorties produites au resultat attendu ;
- pour tester plusieurs operateurs sur des jeux de donnees communs.

## Relations entre les projets

Le depot ne suit pas une structure multi-modules Maven unique. Chaque TD est gere comme un projet autonome, avec son propre `pom.xml`, ses propres sources et parfois sa propre version de Java :

- TD1 : projet Maven simple autour des fractions ;
- TD2 : projet Maven avec tests, logs et controle de style ;
- TD3 : projet Java 17 autour des collections et du patron Commande ;
- TD4 : mini-moteur de requetes CSV, appuye par un banc de tests externe.

Le seul lien direct entre dossiers est celui entre :

- `td4-miniprojet-Abderrahmane-Deffaf` : moteur d'execution ;
- `td4-banc_tests` : fichiers d'entree, cas de test et sorties associees.

## Commandes utiles

Les commandes suivantes se lancent depuis le dossier du projet concerne.

### TD1

```bash
mvn compile
mvn package
java -jar target/SimpleFraction-1.0-SNAPSHOT.jar
```

### TD2

```bash
mvn clean
mvn test
mvn package
java -jar target/ChaineCryptee-1.0-SNAPSHOT.jar
```

### TD3

```bash
mvn clean package
java -jar target/collex-1.0-SNAPSHOT.jar
java -jar target/collex-1.0-SNAPSHOT.jar interactive
```

### TD4

```bash
mvn clean test
mvn package
java -jar target/mma-1.0-SNAPSHOT.jar ../td4-banc_tests/TJoinProj/req.json
java -jar target/mma-1.0-SNAPSHOT.jar ../td4-banc_tests/TJoinProj/req.alg
```

## En bref

Ce depot montre une progression pedagogique assez nette :

- TD1 : modeliser une classe simple et prendre en main l'environnement ;
- TD2 : ajouter tests, qualite et packaging ;
- TD3 : manipuler des collections et structurer l'application avec des patterns ;
- TD4 : construire une application plus complete, orientee traitement de donnees et interpretation de requetes.

Pour le detail de chaque exercice, consulter les fichiers d'enonce (`TD1.md`, `Td2.md`, `Td3.adoc`, `Td4.adoc`) ainsi que les `README` presents dans les sous-projets.
