# Rapport de TP : Inversion de Contrôle et Injection de Dépendances

## 1. Introduction
Ce rapport présente la mise en œuvre de l'Inversion de Contrôle (IoC) et de l'Injection de Dépendances (DI) pour garantir un couplage faible entre les composants.

## 2. Partie 1 : Implémentations Standards
### Conception
L'application est basée sur une interface `IDao` pour l'accès aux données et `IMetier` pour la logique métier.

### Méthodes d'injection testées
* **Injection Statique** : Utilisation de l'instanciation directe.
* **Injection Dynamique** : Utilisation de la réflexion Java et d'un fichier `config.txt`.
* **Framework Spring** :
    * Version XML via `ClassPathXmlApplicationContext`.
    * Version Annotations via `@Component` et `@Autowired`.
### Résultats et Captures d'écran:
Les tests suivants valident le bon fonctionnement de l'application. Dans chaque cas, le résultat attendu est 10.0 (2 x 5), car la couche métier multiplie par 2 la valeur retournée par le DAO.
* **Injection Statique** : Instanciation manuelle.
  ![Presentation Statique](assets/PresentationStatique.png)

* **Injection Dynamique** : Chargement via `config.txt` et l'API Reflection.
  ![Presentation Dynamique](assets/PresentationDynamique.png)

* **Spring XML** : Configuration via le fichier `spring-ioc.xml`.
  ![Presentation Spring XML](assets/PresentationSpringXML.png)

* **Spring Annotations** : Détection automatique via `@Component` et `@Autowired`.
  ![Presentation Spring Annotations](assets/PresentationSpringAnnotations.png)