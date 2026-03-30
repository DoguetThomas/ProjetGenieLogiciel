# Documentation des tests

Ce projet contient 2 types de tests :

- **Tests backend JUnit** dans `backend/test`
- **Tests black-box frontend** (UI web) dans `test-runner`

Les tests backend se lancent en CLI avec `javac` + `junit-platform-console-standalone`, ou plus simplement directement via l'interface IntelliJ IDEA via le bouton de lancement des tests (recommandé).

## 1. Tests backend (JUnit)

### Structure actuelle

- Code source backend : `backend/src`
- Tests JUnit backend : `backend/test`
- Bibliothèques : `backend/lib`

Exemples de suites présentes que vous pouvez augmenter avec vos propres tests :

- `backend/test/ActivityAnalyticsServiceTest.java`
- `backend/test/UserProfileServiceTest.java`

Le repo contient déjà `backend/lib/junit-platform-console-standalone-1.9.1.jar`.

## 2. Test runner frontend (black-box)

Le dossier `test-runner` contient une application web qui exécute des assertions API contre le backend, avec bundle obfusqué pour distribution étudiant.

### Prérequis

- Backend Java démarré sur `http://localhost:8090` (valeur par défaut dans le runner)

### Exécution locale

Depuis la racine du projet, ouvrez simplement :

- `test-runner/index.html` dans votre navigateur

Puis cliquez sur **Run Tests**.

Vous pouvez modifier l'URL API directement dans le champ **API base URL** si nécessaire.
