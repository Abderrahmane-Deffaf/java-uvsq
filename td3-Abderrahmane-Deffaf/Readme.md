# Projet TD3 - DNS avec collections

Ce projet simule un mini serveur DNS en Java 17. Il charge une base texte, valide des noms de machine et des adresses IP, permet des recherches, liste les machines d'un domaine et peut ajouter une nouvelle entree dans la base.

## Structure

- `AddresseIP` valide une adresse IPv4.
- `NomMachine` valide un nom qualifie de machine.
- `DnsItem` represente une association `nom <-> IP`.
- `Dns` charge `src/main/resources/db.txt` a partir de `config.properties` et fournit les operations de recherche et d'ajout.
- `Commande`, `RechercherItem`, `RechercheDomaine` et `AjouterItem` appliquent le patron Commande demande dans le sujet.
- `DnsTUI` et `DnsApp` fournissent le mode interactif.
- `App` sert de point d'entree de demonstration.

## Lancer le projet

Pour executer la demonstration en lecture seule :

```bash
./mvnw exec:java -Dexec.mainClass="fr.uvsq.cprog.collex.App"
```

Pour lancer la console interactive :

```bash
./mvnw exec:java -Dexec.mainClass="fr.uvsq.cprog.collex.App" -Dexec.args="interactive"
```

Pour tester aussi un ajout persistant dans `db.txt` :

```bash
./mvnw exec:java -Dexec.mainClass="fr.uvsq.cprog.collex.App" -Dexec.args="add-demo"
```

## Ce que montre `App`

La classe `App` illustre le fonctionnement global du projet :

- creation d'objets `AddresseIP`, `NomMachine` et `DnsItem`,
- recherche d'une machine par nom,
- recherche d'une machine par adresse IP,
- liste des machines d'un domaine avec `RechercheDomaine`,
- ajout d'une nouvelle entree avec `Dns.addItem` si l'option `add-demo` est utilisee.

## Commandes interactives

Selon l'implementation actuelle de `DnsTUI`, les commandes disponibles sont :

- `search <nom-ou-ip>`
- `ls -a <domaine>`
- `add <addresse_ip> <nom_machine>`
- `exit`

## Lien avec le sujet

Le fichier `Td3.adoc` demande une application DNS basee sur les collections Java, la persistance dans un fichier texte et le patron Commande. Le projet couvre deja ces points avec une base simple, des objets metier valides et une interface en ligne de commande minimale.
