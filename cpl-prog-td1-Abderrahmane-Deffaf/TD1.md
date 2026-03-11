# Compléments de programmation - TD 1

Nom, Prénom, No Etudiant, Nc > Deffaf, Abderrahmane, 22507100, 84

## Remarques préliminaires

- Pour l'ensemble des TDs, vous créerez un compte individuel sur [github](https://github.com/) si vous n'en possédez pas déjà un.
  Vous nommerez ce compte de la façon suivante: `uvsq<MonNuméroÉtudiant>`.
  Par exemple, pour un étudiant de numéro _21601234_, le compte sera `uvsq21601234`.
- Les commandes `git` sont à taper en ligne de commande dans un _shell_.
- Vous pouvez utiliser l'IDE de votre choix.
  Sur le cartable numérique, [Eclipse](www.eclipse.org), [IntelliJ IDEA](http://www.jetbrains.com/idea/) et [Visual Studio Code](https://code.visualstudio.com/) sont installés.
- Vous répondrez aux questions directement dans ce fichier en complétant les emplacements correspondants.
  Ajoutez ensuite ce fichier au dépôt `git`.

## Partie I (à faire durant le TD) : découverte de `git`

Dans cet exercice, vous créerez une classe `Fraction` représentant un nombre rationnel et une classe `Main` qui testera les méthodes de la classe `Fraction` **avec des assertions** (cf. [Utilisation d'assertions](https://koor.fr/Java/Tutorial/java_assert.wp)).
À chaque étape, consultez le statut des fichiers du projet (`git status`) ainsi que l'historique (`git log`).

1.  Sur la forge, créez le dépôt (_repository_) `SimpleFraction`;
    En terme de _commits_, quelle différence constatez-vous entre cocher une (ou plusieurs) des cases _Initialize this repository with_ et n'en cocher aucune ? > Répondre ici

        *Pour la suite, ne cochez aucune de ces cases*.

1.  Localement, configurez `git` avec votre nom (`user.name`) et votre email (`user.email`) (cf. [Personnalisation de Git](https://git-scm.com/book/fr/v2/Personnalisation-de-Git-Configuration-de-Git));
    ```bash
      git config --global user.name "D-Abderrahmane"
      git config --global user.email "deabdou55@gmail.com"
    ```
1.  Initialisez le dépôt `git` local pour le projet (cf. [Démarrer un dépôt Git](https://git-scm.com/book/fr/v2/Les-bases-de-Git-D%C3%A9marrer-un-d%C3%A9p%C3%B4t-Git));
    ```bash
      git init
    ```
1.  Dans votre IDE, créez la classe `Fraction` (vide pour le moment) et la classe `Main` (avec un simple affichage) dans le projet (cf. [Méthode `main`](https://docs.oracle.com/javase/specs/jls/se19/html/jls-12.html#jls-12.1.4));
    Vérifiez que le projet compile et s'exécute dans l'IDE;
    Validez les changements (cf. [Enregistrer des modifications dans le dépôt](https://git-scm.com/book/fr/v2/Les-bases-de-Git-Enregistrer-des-modifications-dans-le-d%C3%A9p%C3%B4t));
    # Commandes pour valider les changements
    ```bash
      git add .
      git commit -m "first commit"  
    ```

1.  Ajoutez la méthode `toString` à la classe `Fraction` (cf. [`Object.toString`](<https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/Object.html#toString()>)) qui retournera la chaîne `"Je suis une fraction."` et modifiez la classe `Main` en conséquence;
    Validez les changements;
    `Java
// Code pour tester toString
`
1.  Publiez vos modifications sur le dépôt distant (cf. [Travailler avec des dépôts distants](https://git-scm.com/book/fr/v2/Les-bases-de-Git-Travailler-avec-des-d%C3%A9p%C3%B4ts-distants));
    Vous utiliserez le protocole `https` pour cela;
    Vérifiez avec le navigateur;
    # Commandes pour publier les modifications
    ```bash
    git push -u origin main
    ```
1.  Sur la forge, ajoutez un fichier de documentation `README.md`.
    Quelle syntaxe est utilisée pour ce fichier ? > Répondre ici
1.  Récupérez localement les modifications effectuées sur la forge.
    ```bash
    # Répondre ici
    C est la languge des balise de markdown
    ```
1.  Ajoutez les répertoires et fichiers issus de la compilation aux fichiers ignorés par `git` (cf. [`.gitignore` pour Java](https://github.com/github/gitignore/blob/main/Java.gitignore));
    ```bash
    # Copier ici le contenu de `.gitignore`
    target/
    ```
1.  Retirez les fichiers de configuration de l'IDE du projet;

    ```bash
    # Répondre ici
    /.vscode/settings.json

    ```

    Ajoutez-les aux fichiers ignorés par `git`.

    # Copier ici les modifications de `.gitignore`
    ```bash
    @@ -0,0 +1 @@
    +target/
    \ No newline at end of file
`
    @@ -1 +1,2 @@
    -target/
    \ No newline at end of file
    +target/
    +.vscode/
    ```

1.  Configurez l'accès par clé publique/clé privée à la forge (cf. [Connecting to GitHub with SSH](https://docs.github.com/en/authentication/connecting-to-github-with-ssh)).
    > Expliquez la procédure de façon synthétique
    1. Il faut générer un key-ssh on utilisant cette command: 
    ```bash
    ssh-keygen -t rsa -b 4096 -C "deabdou55@gmail.com"
    ``` 
    1. Aprés il faut ajouter ssh-agent utilisant cette command: 
    ```bash 
      eval $(ssh-agent -s) 
     # et cette command : 
     ssh-add ~/.ssh/id_github
    ```
    1. Aprés on va copier le public key :
    ```bash 
      clip < ./id_github.pub
     ```
    1. Et en fin le mettre dans github

## Partie II (à faire durant le TD) : compléter la classe `Fraction`

Dans cet partie, vous compléterez les classes `Fraction` et `Main`.
Un exemple d'interface pour une telle classe est donné par la classe [`Fraction`](http://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/org/apache/commons/math3/fraction/Fraction.html) de la bibliothèque [Apache Commons Math](http://commons.apache.org/math/).

Vous respecterez les consignes ci-dessous :

- chaque méthode de `Fraction` sera testée dans `Main` **avec des assertions** (cf. [Utilisation d'assertions](https://koor.fr/Java/Tutorial/java_assert.wp));
- à la fin de chaque question, consultez le statut des fichiers du projet (`git status`) ainsi que l'historique (`git log`) puis validez les changements.

1. Ajoutez les attributs représentants le numérateur et le dénominateur (nombres entiers).
   ```Java
   private int numerateur 
   private int denominateur
   ```
1. Ajoutez les constructeurs (cf. [Constructor Declarations](https://docs.oracle.com/javase/specs/jls/se19/html/jls-8.html#jls-8.8)) suivants :
   - initialisation avec un numérateur et un dénominateur,
   - initialisation avec juste le numérateur (dénominateur égal à _1_),
   - initialisation sans argument (numérateur égal _0_ et dénominateur égal à _1_),
   ```Java
   // Assertions pour tester les constructeurs (avec toString)
    Fraction f1 = new Fraction(3, 4);
    assert f1.toString().equals("3/4");

    Fraction f2 = new Fraction(5);
    assert f2.toString().equals("5/1");

    Fraction f3 = new Fraction();
    assert f3.toString().equals("0/1");
   ```
1. Ajoutez les fractions constantes ZERO (0, 1) et UN (1, 1) (cf. [Constants in Java](https://www.baeldung.com/java-constants-good-practices)),
   ```Java
   // Déclaration des constantes
   public static final Fraction ZERO = new Fraction(0, 1);
   public static final Fraction UN = new Fraction(1, 1);
      
   ```
1. Ajoutez une méthode de consultation du numérateur et du dénominateur (par convention, en Java, une méthode retournant la valeur de l'attribut `anAttribute` est nommée `getAnAttribute`),
   ```Java
   // Définition des getters
    public int getNumerateur() {
        return numerateur;
    }
    
    public int getDenominateur() {
        return denominateur;
    }
   ```
1. Ajoutez une méthode de consultation de la valeur sous la forme d'un nombre en virgule flottante (méthode `doubleValue()`) (cf. [`java.lang.Number`](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/Number.html)),
   ```Java
    // Assertions pour tester la conversion
    Fraction f = new Fraction(1, 2);
    assert Math.abs(f.doubleValue() - 0.5) < 1E-8
   ```
1. Ajoutez une méthode permettant l'addition de deux fractions (la méthode `add` prend en paramètre _une_ fraction et _retourne_ la somme de la fraction courante et du paramètre),
   ```Java
    // Assertions pour tester l'addition
    Fraction f1 = new Fraction(1, 2);
    Fraction f2 = new Fraction(1, 3);
    Fraction resultat = f1.add(f2);
    assert resultat.getNumerateur() == 5 && resultat.getDenominateur() == 6;
   ```
1. Ajoutez le test d'égalité entre fractions (deux fractions sont égales si elles représentent la même fraction réduite) (cf. [`java.lang.Object.equals`](<https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/Object.html#equals(java.lang.Object)>)),
   ```Java
    // Assertions pour tester l'égalité
    Fraction f1 = new Fraction(1, 2);
    Fraction f2 = new Fraction(2, 4);
    assert f1.equals(f2);
   ```
1. Ajoutez la comparaison de fractions selon l'ordre naturel (cf. [`java.lang.Comparable`](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/Comparable.html)).
   ```Java
    // Assertions pour tester la comparaison
   public void testCompareTo() {
         Fraction f9 = new Fraction(1, 3);
         Fraction f10 = new Fraction(1, 2);
         assertTrue(f9.compareTo(f10) < 0);
         assertTrue(f10.compareTo(f9) > 0);
         assertTrue(f9.compareTo(new Fraction(2, 6)) == 0);
      }
   ```
1. Faites hériter votre classe `Fraction` de la classe [`java.lang.Number`](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/Number.html) et complétez les méthodes
   ```Java
    // Vérifiez avec le code ci-dessous
    Number aNumber = java.math.BigDecimal.ONE;
    Number anotherNumber = new Fraction(1, 2);
    assert java.lang.Math.abs(aNumber.doubleValue() + anotherNumber.doubleValue() - 1.5) < 1E-8;
   ```

## Partie III (à faire à la maison) : révisions et perfectionnement _shell_ et _IDE_

### Maîtriser le _shell_ de commandes

L'objectif de cet exercice est de vous faire réviser/découvrir les commandes de base du _shell_ de votre machine.
Vous pouvez répondre en utilisant le shell de votre choix (_bash_, _Powershell_, …).
Pour répondre à ces questions, vous devez effectuer les recherches documentaires adéquates (livre, web, …).

1. Quel OS et quel shell de commande utilisez-vous ?
   > Répondre ici
     
    - OS: Windows
    - shel: bash + powershell
    
1. Quelle commande permet d'obtenir de l'aide ?
   Donnez un exemple.
   ```bash
    git help <command>
    git help add
   ```
1. Donnez la ou les commandes shell permettant de
   1. afficher les fichiers d'un répertoire triés par taille (taille affichée lisiblement)
      ```powershell
      # Répondre ici
       Get-ChildItem | Sort-Object Length -Descending
      ```
   1. compter le nombre de ligne d'un fichier
      ```powershell
      # Répondre ici
      (Get-Content TD1.md | Measure-Object -Line).Lines
      ```
   1. afficher les lignes du fichier `Main.java` contenant la chaîne `uneVariable`
      ```powershell
      # Répondre ici
      Select-String "uneVariable" Main.java
      ```
   1. afficher récursivement les fichiers `.java` contenant la chaîne `uneVariable`
      ```powershell
      # Répondre ici
      Get-ChildItem -Recurse -Filter "*.java" | Select-String "uneVariable"
      ```
   1. trouver les fichiers (pas les répertoires) nommés `README.md` dans une arborescence de répertoires
      ```powershell
      # Répondre ici
      Get-ChildItem -Recurse -Name "README.md" -File
      ```
   1. afficher les différences entre deux fichiers textes
      ```powershell
      # Répondre ici
      Compare-Object (Get-Content TD1.md) (Get-Content README.md)
      ```
1. Expliquez en une ou deux phrases le rôle de ces commandes et dans quel contexte elles peuvent être utiles pour un développeur.
   - `ssh`
      Permet de se connecter de manière sécurisée à un serveur distant via le protocole SSH. Utile pour déployer des applications, administrer des serveurs de production ou accéder à des machines de développement distantes.
   - `screen`/`tmux`
     Multiplexeurs de terminaux permettant de créer plusieurs sessions persistantes et de les détacher/rattacher. Essentiels pour maintenir des processus actifs même après déconnexion SSH ou pour organiser plusieurs tâches de développement simultanément.
   - `curl`/[HTTPie](https://httpie.org/)
     Outils en ligne de commande pour effectuer des requêtes HTTP/HTTPS. Indispensables pour tester des APIs REST, déboguer des services web, automatiser des téléchargements ou interactions avec des services externes.
   - [jq](https://stedolan.github.io/jq/)
     Processeur JSON en ligne de commande permettant de parser, filtrer et transformer des données JSON. Très utile pour analyser les réponses d'APIs, extraire des informations spécifiques et manipuler des configurations JSON.

### Découverte de votre _IDE_

Dans cet exercice, vous expliquerez en quelques phrases comment vous réalisez les actions ci-dessous dans votre IDE.
Vous pouvez choisir l'IDE/éditeur de texte de votre choix.
Pour réaliser cette exercice, vous devez bien évidemment vous reporter à la documentations de l'IDE ([IntelliJ IDEA](https://www.jetbrains.com/help/idea/discover-intellij-idea.html#developer-tools), [Visual Studio Code](https://code.visualstudio.com/docs), [Eclipse](https://help.eclipse.org/2020-09/index.jsp), …).

1. Quels IDE ou éditeurs de texte utilisez-vous pour le développement Java ?

   VSCode

   Pour la suite, ne considérez que l'un de vos choix.

1. Comment vérifier/définir que l'encodage utilisé est _UTF-8_ ?
   Afficher dans la barre de status dans vscode
1. Comment choisir le JDK à utiliser dans un projet ?
   Ctrl+Shift+P puis "Java: Configure Java Runtime"
1. Comment préciser la version Java des sources dans un projet ?
   on le préciser dans le fichier pom de maven dans : 
   ```xml
    <maven.compiler.release>version</maven.compiler.release>
    ```
1. Comment ajouter une bibliothèque externe dans un projet ?
   dans le fichier pom dans : 
    ```xml
    <dependencies>
    ```
1. Comment reformater un fichier source Java ?
   Shift+Alt+F ou clic droit → "Format Document". 
1. Comment trouver la déclaration d'une variable ou méthode ?
   Ctrl + f aprés le nom de méthode ou variable 
1. Comment insérer un bloc de code prédéfini (_snippet_) ?
  juste tapper le début de snippet et click enter sur la bonne. 
1. Comment renommer une classe dans l'ensemble du projet ?
   F2 sur le nom de la classe (Rename Symbol) 
1. Comment exécuter le programme en lui passant un paramètre en ligne de commande ?
   en tappe la commande aprés les args a insérer 
1. Comment déboguer le programme en visualisant le contenu d'une ou plusieurs variables ?
   Placer des points d'arrêt (F9), lancer en mode debug (F5)
1. Quels paramètres ou fonctionnalités vous semblent particulièrement importants/utiles pour le développement Java ?
   IntelliSense avancé, refactoring automatique, intégration Git native, terminal intégré, support Maven/Gradle, détection d'erreurs en temps réel, débogueur intégré, extensions pour Spring Boot, et la possibilité de gérer plusieurs projets dans un même workspace.
