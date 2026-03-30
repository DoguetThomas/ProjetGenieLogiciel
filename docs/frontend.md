# Documentation Frontend - Dashboard

## Introduction

Ce document explique comment lancer, comprendre et vérifier le frontend du projet.

Le frontend permet de:

- Consulter la liste des activités sportives
- Sélectionner une activité
- Visualiser le résumé, la carte GPS et les graphiques de métriques

Il s'appuie sur un backend Java disponible sur `http://localhost:8090`.

## Technologies Utilisées

- **HTML5** : structure de la page (`index.html`)
- **CSS3** : style et responsive (`style.css`)
- **JavaScript Vanilla (ES6+)** : logique applicative (`app.js`)
- **Chart.js** (CDN) : affichage des graphiques
- **Leaflet.js** (CDN) : affichage de la carte et du tracé GPS
- **OpenStreetMap** : tuiles cartographiques utilisées par Leaflet

## Arborescence Frontend

Depuis `frontend/` :

- `index.html` : point d'entrée de l'application
- `style.css` : styles globaux, grille, cartes et graphiques
- `app.js` : appels API, chargement des données, rendu UI, graphiques et carte
- `models.js` : classes orientées objet pour représenter les activités (support pédagogique)
- `ui.js` : composants UI orientés objet (support pédagogique)

## Consignes de Lancement

### Prérequis

- **Python 3** installé
- **Backend Java** démarré sur le port `8090`
- Navigateur web moderne (Chrome, Firefox, Edge)

### Étape 1 : Lancer le backend

Dans un terminal, depuis `backend` :

```bash
javac -d out $(find src -name "*.java")
java -cp out Main
```

Le backend doit répondre sur `http://localhost:8090`.

### Étape 2 : Lancer le serveur frontend

Dans un **nouveau terminal**, depuis `frontend` :

```bash
python3 -m http.server 5500
```

### Étape 3 : Ouvrir l'application

Ouvrir l'URL :

`http://localhost:5500`

## Configuration API

Dans `app.js`, l'URL de base est :

```js
const API_BASE = 'http://localhost:8090/api';
```

Si vous changez le port du backend, mettez à jour cette constante.

## Appels API du frontend

Le frontend utilise les endpoints suivants :

- `GET /api/activities/all`
- `GET /api/activities/{id}/summary`
- `GET /api/activities/{id}/metrics/route`
- `GET /api/activities/{id}/metrics/altitude`
- `GET /api/activities/{id}/metrics/heart-rate`
- `GET /api/activities/{id}/metrics/power`
- `GET /api/activities/{id}/metrics/cadence`
- `GET /api/activities/{id}/metrics/ground-time`
- `GET /api/activities/{id}/metrics/pace`
- `GET /api/activities/{id}/metrics/zone`

### Endpoints User Profile

Le frontend utilise aussi les endpoints suivants pour le profil utilisateur :

- `GET /api/user-profile`
- `POST /api/user-profile`

Exemple de payload JSON pour `POST /api/user-profile` :

```json
{
 "gender": "MALE",
 "age": 29,
 "weight": 72.5,
 "height": 178.0
}
```

Note : le frontend tente aussi `GET/POST /api/userProfile` en fallback si l'endpoint kebab-case n'est pas disponible.
