# Milestone 2

La deuxième partie du projet met à l'épreuve votre architecture, la facilité à la faire évoluer, et votre capacité à retravailler votre code pour intégrer de nouvelles fonctionnalités.

## Attendus et Fonctionnalités

À l'issue de cette deuxième partie, nous attendons, en plus des fonctionnalités implémentées durant le milestone 1, les fonctionnalités suivantes :

### Calcul du dénivelé positif pour les activités vélo

Dans le cadre de l'analyse des performances à vélo, une métrique essentielle est le dénivelé positif cumulé (D+), qui permet de quantifier l'effort fourni en montée.

Nous souhaitons implémenter le calcul du gain d'altitude total pour chaque activité de type vélo.

Les objectifs sont les suivants :

- Exploiter les données d'altitude (enhanced_altitude) associées aux points GPS de l'activité.
- Calculer la somme des variations positives d'altitude sur l'ensemble du parcours.
- Associer cette métrique aux activités de type vélo uniquement.
- Exposer cette information via les DTOs d'activité.

Opération à implémenter dans le service :

```java
EnhancedActivityService.getElevationGain(String activityId)
```

Route backend associée :

- `GET /api/activities/{id}/elevation`

Valeur attendu en retour :

- `Valeur du dénivelé positif total pour l'activité` en `double`

### Analyse de l’efficacité de la foulée pour la course à pied

Contrairement au vélo, la course à pied permet d’exploiter des métriques biomécaniques fines afin d’évaluer l’efficacité du mouvement.

Nous souhaitons implémenter le calcul du **rapport vertical moyen** pour les activités de type course à pied.

Les objectifs sont les suivants :

- Exploiter les données biomécaniques disponibles :
  - `ground_time`
  - `vertical_oscillation`
  - `cadence`
- Calculer le **rapport vertical** à partir de ces données en estimant la longueur de foulée.
- Calculer la moyenne de ce rapport sur l’ensemble de l’activité.
- Associer cette métrique aux activités de type course à pied uniquement.
- Permettre la comparaison de ce rapport entre différentes activités.

Source: [ici](https://www.montre-cardio-gps.fr/garmin-advanced-running-dynamics-je-vous-explique-tout/)

Opération à implémenter dans le service :

```java
EnhancedActivityService.getVerticalRatio(String activityId)
```

Route backend associée :

- `GET /api/activities/{id}/vertical-ratio`

Donnée attendue en retour :

- `Rapport vertical moyen sur l'activité (en double)`

### Synchronisation des activités Strava à partir de fichiers FIT

Cette fonctionnalité remplace l'idée initiale de synchronisation "live" du compte Strava.
La source de vérité est un ou plusieurs fichiers FIT exportés depuis le compte de l'athlète.

Pour simplifier le travail étudiant, la conversion du FIT en données normalisées peut être faite soit côté frontend, soit côté backend.
L'important est de conserver un flux minimal : un fichier FIT (ou payload équivalent) est transformé en activités normalisées, puis envoyé à l'endpoint de synchronisation.

Les objectifs sont les suivants :

- Importer un fichier FIT exporté depuis Strava.
- Mapper les données reçues vers des activités internes.
- Créer/mettre à jour les activités synchronisées dans le backend.

#### Architecture attendue (obligatoire)

Pour ce module, le projet doit respecter la construction suivante :

- `Controller`
- `Interface Service`
- `Implémentation Service`

Exemple attendu :

- `ImportController`
- `ImportService`
- `ImportServiceImpl`

Routes backend associées :

- `POST /api/import`

## Critères de validation

Le milestone 2 est considéré comme réussi si :

1. Les critères de validation du premier milestone sont toujours validés
2. Le dénivelé positif des activités vélo est correctement calculé et exposé
3. Le rapport vertical moyen est calculé pour les activités de course à pied et exploitable dans l'interface
4. Une synchronisation FIT Strava réussie crée l'activité attendue
