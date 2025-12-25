<?php
// TODO 1: Démarrer la session
session_start();
// TODO 2: Vérifier que la requête est bien en POST
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    // TODO 3: Récupérer les données du formulaire
    // Variables nécessaires : $id, $title, $price, $thumbnail
    $id = $_POST['id'];
    $title = $_POST['title'];
    $price = $_POST['price'];
    $thumbnail = $_POST['thumbnail'];
    // TODO 4: Vérifier si le produit existe déjà dans le panier
    // Indice : Parcourir $_SESSION['panier'] avec foreach
    // Si le produit existe (même id), incrémenter sa quantité
    // Utilisez une variable $produit_existe pour savoir si on l'a trouvé

    $produit_existe = false;
    // TODO: Parcourir le panier et chercher le produit
    // IMPORTANT : Utilisez &$item dans le foreach pour modifier directement l'élément
    // foreach ($_SESSION['panier'] as &$item) { ... }
    foreach ($_SESSION['panier'] as &$item) {
        if ($item['id'] == $id) {
            $item['quantite']++;
            $produit_existe = true;
            break;
        }
    }
    // TODO 5: Si le produit n'existe pas, l'ajouter au panier
    // Structure d'un article dans le panier :
    // [
    //     'id' => $id,
    //     'title' => $title,
    //     'price' => $price,
    //     'thumbnail' => $thumbnail,
    //     'quantite' => 1
    // ]
    if (!$produit_existe) {
        $_SESSION['panier'][] = [
            'id' => $id,
            'title' => $title,
            'price' => $price,
            'thumbnail' => $thumbnail,
            'quantite' => 1
        ];
    }
}

// TODO 6: Rediriger vers la page précédente
// Indice : Utilisez header('Location: ' . $_SERVER['HTTP_REFERER']);
// N'oubliez pas exit(); après la redirection
header('Location: ' . $_SERVER['HTTP_REFERER']);
exit();
?>
