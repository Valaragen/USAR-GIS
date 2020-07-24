[![Build Status](https://travis-ci.com/Valaragen/USAR-GIS.svg?branch=master)](https://travis-ci.com/Valaragen/USAR-GIS)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Valaragen_USAR-GIS&metric=coverage)](https://sonarcloud.io/dashboard?id=Valaragen_USAR-GIS)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Valaragen_USAR-GIS&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=Valaragen_USAR-GIS)
# USAR-GIS
## Description
### À propos
**Système de gestion d'interventions.**  
Le projet respecte une **architecture orientée service**, il est composé des **microservices** (**batch**, **usargis-api**, **webui**) et des **edges microservices**(**config-server**, **eureka-server**, **gateway-server**). Tous ces microservices sont des projets **spring-boot** créés pour tourner de manière **autonome** sur des **serveurs tomcat**.  
Le projet est **sécurisé** par des appels vers [**keycloak**](https://www.keycloak.org/about.html "À propos de keycloak"), un **serveur de gestion d'identité et d'accès Opensource**.  
### Les microservices
#### Edges microservices
* ##### config-server
  **Serveur de configuration**, il est lié à au [**config-repo**](https://github.com/Valaragen/USAR-GIS/tree/master/config-repo) présent sur ce repository, il contient des **informations de configuration** des microservices du projet. **Tous les microservices**, tentent à leurs **lancement**, de récupérer des informations via ce serveur de configuration.  
  **Port** : 9101  
* ##### eureka-server
  **'Registration server'** , toutes les **instances** des microservices du projet **s'inscrivent** sur ce server à leur lancement. Les microservices passent par ce **registre** pour appeler d'autres microservices.  
  **Port** : 9102  
* ##### gateway-server
  **API gateway**, le point d'entrée unique pour les API et microservices back-end(**usargis-api**)  
  **Port** : 9103
#### Microservices de la bibliotheque
* ##### usargis-api
  **API** permettant de **créer, lire, mettre à jour et/ou supprimer** des **missions**, **événements** et de renseigner des **disponiblités**.  
  **L'API contient** des **endpoints protégés**, reservés aux **utilisateurs authentifiés** ou aux **membres du staff**, pour **obtenir l'autorisation** d'appeler ces endpoints il faut **fournir un token d'accès valide** dans l'entête de la requête http.  
  Ce **token d'accès** est fourni par **keycloak** lors d'une **authentification réussie** via un **client autorisé** à lancer un **protocole d'authentification** (**ex: webui**)  
  **Base de données** : Postgresql  
  **Nom de la base de données** : USARGIS_DB  
  **Port** : 9001  
* ##### webui  
  **Site web**, il ne permet pour l'instant que de se connecter et notamment de s'inscrire pour utiliser l'API.  
  **Port** : 8080  
#### Keycloak
Non integré sur ce repository git, keycloak est necessaire au fonctionnement des microservices de la bibliothèque. [Télécharger keycloak 10.0.2](https://www.keycloak.org/archive/downloads-10.0.2.html)  
**Version** : 10.0.2  
**Base de données** : Postgresql  
**Nom de la base de données** : USARGIS_KEYCLOAK_DB  
**Port** : 8180  
   
## Comment deployer le projet en local ?
### Prérequis
1. [Postgresql](https://www.postgresql.org/download/)
2. [Keycloak 10.0.2](https://www.keycloak.org/archive/downloads-10.0.2.html)
    1. [Configurer keycloak pour qu'il utilise une base de données potgresql](https://www.keycloak.org/docs/latest/server_installation/#_rdbms-setup-checklist) qui sera nommée **KEYCLOAK_DB**
### Installation
1. Télécharger la [**dernière release du projet**](https://github.com/Valaragen/USAR-GIS/releases).
2. Lancez pgAdmin et créez une nouvelle base de donnée nommée **USARGIS_DB** (Les tables et un jeu de données de base sera inséré automatiquement au premier lancement de l'api)
3. Créez une base de données nommée **USARGIS_KEYCLOAK_DB** et insérez-y le script **db_create_keycloak.sql**.  
4. Lancez dans l'ordre :  
   1. **Keycloak** en mode standalone **/bin/standalone.bat(widows)** ou **/bin/standalone.sh(linux)**
   2. **config-server.jar**
   3. **eureka-server.jar**
   4. **gateway-server.jar**
   5. **usargis-api.jar**
   6. **webui.jar**  
   
5. Vous pouvez vous connecter à la console admin de keycloak via l'url **http://localhost:8180** avec les identifiants -> **nom d'utilisateur:** admin **mdp:** admin
6. Pour tester l'api web  
   1. Il vous faut **générer un token d'accès** depuis votre outil de test d'api à l'aide de **ces informations**:  
   **Token name:** keycloak-bearer-token  
   **Grant type:** Authorization code  
   **Callback URL:** http://localhost:8080/sso/login  
   **Auth URL:** http://localhost:8180/auth/realms/bibliotheque/protocol/openid-connect/auth  
   **Access token URL:** http://localhost:8180/auth/realms/bibliotheque/protocol/openid-connect/token  
   **Client ID:** webui  
   **Client secret:** 8f5f6aec-ac03-4bf3-b9dd-bad50a478536  
   **Scope:** openid profile email  
   **State:** 1234  
   **Client authentication:** Send client credential in body  
      >  Vous pouvez consulter les différents endpoints accessible via l'url http://localhost:9001/swagger-ui.html#/  
      >  Vous pouvez importer toute la documentation de l'api sur votre outil de test d'API via l'url http://localhost:9001/v2/api-docs  
   2. Vous pouvez créer un compte via la webui ou utiliser l'un des 3 comptes ci-dessous pour tester l'API :
      1. **username:** dev-member **mdp:** Azerty
      2. **username:** dev-leader **mdp:** Azerty
      3. **username:** dev-admin **mdp:** Azerty
 
### Tests unitaires
Les différentes **fonctionalités** ont été développées en respectant les principes du **TDD** (Test-Driven Development).  
Les tests unitaires se trouvent dans le répertoire « **src/main/test/Java** » du module associé.   
**Le nom des classes de tests unitaires** se terminent par le mot clé **'Test'**

### Tests de composants
Des tests de **composants** testant le bon **fonctionnement** des **controllers**, des **repository** et du **contexte spring** ont été mis en place.  
Ces test **omettent les dépendances externes** et peuvent donc se lancer **sans pré-conditions**  
**Les tests de composants** se terminent par le mot clé **'CompTest'**

### Couverture de code et qualité
Des rapports de **couverture du code par les test** sont générés avec **Jacoco-maven-plugin**. L'analyse de ces rapports et de la qualité du code est faite avec **SonarCloud**.
> [Lien vers le rapport SonarCloud](https://sonarcloud.io/dashboard?id=Valaragen_USAR-GIS)

### Travis CI 
Mise en place d’un **service d’intégration continue** « **Travis CI** ».  
Dans ce projet, ce service a pour rôle d’exécuter tous les tests à chaque **commit ou pull request sur la branche master** et de **fournir un resultat en fonction de l'échec ou non des tests**.  
Il se charge aussi d'envoyer **les rapports de couverture du code** à **SonarCloud**. 
> Travis CI est configuré via le fichier [.travis.yml](https://github.com/Valaragen/USAR-GIS/blob/master/.travis.yml)  
> [Lien vers les builds travis CI du projet](https://travis-ci.com/github/Valaragen/USAR-GIS)

## Lancer les tests

**Les tests se lancent via le build maven**. Des **profiles maven** ont été créés pour **lancer les tests** et **activer les rapports de couverture de code**.
 - Lancer les tests unitaires : **`Mvn test`**  
 - Lancer uniquement les tests d’intégrations : **`Mvn verify -Pit-only`**  
 - Lancer les tests d’intégration et les tests unitaires : **`Mvn verify -Pall-test`**
 - Activer la couverture du code avec **-Pcoverage**  
_Exemple : couverture du code pour tous les tests : **`Mvn verify -Pall-test,coverage`**_
