<?php
// TODO 1: Démarrer la session
session_start();
// TODO 2: Vider le panier
// Indice : Réaffecter un tableau vide à $_SESSION['panier']
$_SESSION['panier'] = [];
// TODO 3: Rediriger vers la page du panier
// Indice : Utilisez header() avec le chemin ../panier.php
// N'oubliez pas exit()
header('Location: ../panier.php');
exit();
?>
