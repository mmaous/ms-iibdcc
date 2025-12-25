<?php
// TODO 1: Démarrer la session PHP
// Indice : Utilisez la fonction qui initialise ou reprend une session
session_start();
// TODO 2: Initialiser le panier dans $_SESSION s'il n'existe pas
// Indice : Vérifiez avec isset() et initialisez avec un tableau vide
if (!isset($_SESSION["panier"])) {
    $_SESSION["panier"] = [];
}
// TODO 3: Calculer le nombre total d'articles dans le panier
// Indice : Le panier est un tableau d'articles, chaque article a une 'quantite'
// Utilisez array_column() pour extraire toutes les quantités, puis array_sum()
$nombre_articles = array_sum(array_column($_SESSION["panier"], "quantite"));
?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ma Boutique en Ligne</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <!-- TODO 4: Créer la structure de navigation Bootstrap -->
            <!-- Inclure :
                 - Un lien "Ma Boutique" vers index.php (classe navbar-brand)
                 - Un bouton toggler pour mobile
                 - Un menu avec deux liens :
                   * "Produits" vers index.php
                   * "Panier" vers panier.php avec :
                     - Une icône panier (classe bi-cart)
                     - Un badge rouge affichant $nombre_articles
            -->
            <a class="navbar-brand" href="index.php">Ma Boutique</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="index.php">Produits</a> </li>
                    <li class="nav-item">
                        <a class="nav-link" href="panier.php">
                            <i class="bi bi-cart"></i> Panier
                            <span class="badge bg-danger"><?php echo $nombre_articles; ?></span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
