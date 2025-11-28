# Activité Pratique N° 2 Technologie XML

> [showcase](https://mmaous.github.io/ms-iibdcc/m1/s1/xml-webservices/tp2/)

Un exercice pratique sur les technologies XML, DTD, XSD, et les transformations XSLT, basé sur le support de cours de M. Youssfi.

Ce projet démontre le traitement d'un relevé bancaire via les standards XML :

 - Définition d'une structure de données (Relevé, Opérations, Solde).

 - Validation de la structure via DTD et Schéma XML (XSD).

 - Transformation des données XML en rapports HTML dynamiques (Totaux et Filtres).

## Contenu du projet

- releve.xml: Le document XML de base contenant les données du relevé bancaire (DTD).

- releve_xsd.xml: Le document XML de base contenant les données du relevé bancaire (XSD).

- releve.dtd: La Définition de Type de Document (DTD) pour la validation structurelle.

- releve.xsd: Le Schéma XML (XSD) pour une validation stricte (types de données, énumérations).

- releve_totaux.xsl: Feuille de style XSLT pour transformer le XML en un rapport HTML affichant les totaux (Débit/Crédit).

- releve_credit.xsl: Feuille de style XSLT pour extraire et afficher uniquement les opérations de type CRÉDIT.
