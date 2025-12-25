<?php
// TODO 1: Inclure le header
include 'includes/header.php';
// TODO 2: Initialiser une variable $total à 0
$total = 0;
?>

<div class="container my-4">
    <h1 class="mb-4">Mon Panier</h1>

    <!-- TODO 3: Vérifier si le panier est vide avec empty() -->
    <!-- Si vide : afficher une alerte Bootstrap info avec un lien vers index.php -->
    <!-- Si non vide : afficher le tableau du panier -->

    <?php if (empty($_SESSION['panier'])): ?>
        <div class="alert alert-info">
            Votre panier est vide. <a href="index.php">Retourner à la boutique</a>.
        </div>
    <?php else: ?>
    <!-- Structure du tableau Bootstrap responsive : -->
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th>Produit</th>
                        <th>Prix unitaire</th>
                        <th>Quantité</th>
                        <th>Sous-total</th>
                    </tr>
                </thead>
                <tbody>
                    <!--
                    TODO 4: Boucler sur $_SESSION['panier']
                    Pour chaque article :
                    - Calculer le sous-total (prix × quantité)
                    - Ajouter le sous-total au total général
                    - Afficher une ligne avec :
                    * Image miniature (50x50px) + nom du produit
                    * Prix unitaire
                    * Quantité
                    * Sous-total formaté avec number_format($sous_total, 2) -->
                    <?php foreach ($_SESSION['panier'] as $item):
                        $sous_total = $item['price'] * $item['quantite'];
                        $total += $sous_total;
                    ?>
                        <tr>
                            <td>
                                <img src="<?= $item['thumbnail'] ?>" alt="" style="width: 50px; height: 50px; object-fit: cover;" class="me-2 rounded">
                                <?= htmlspecialchars($item['title']) ?>
                            </td>
                            <td><?= $item['price'] ?> €</td>
                            <td><?= $item['quantite'] ?></td>
                            <td><?= number_format($sous_total, 2) ?> €</td>
                        </tr>
                    <?php endforeach; ?>
                </tbody>
                <tfoot>
                    <tr>
                        <th colspan="3" class="text-end">Total :</th>
                        <!-- <th>TODO: Afficher le total formaté</th> -->
                        <th class="h5"><?= number_format($total, 2) ?> €</th>
                    </tr>
                </tfoot>
            </table>
        </div>
    -->

    <!-- TODO 5: Ajouter les boutons d'action -->
    <!-- Structure suggérée :
    <div class="d-flex justify-content-between mt-4">
        - Bouton "Continuer les achats" (lien vers index.php)
        - Formulaire pour vider le panier (POST vers actions/vider_panier.php)
          avec un bouton "Vider le panier" (classe btn-danger)
    </div>
    -->
        <div class="d-flex justify-content-between mt-4">
            <a href="index.php" class="btn btn-outline-secondary">Continuer les achats</a>
            <form method="POST" action="actions/vider_panier.php">
                <button type="submit" class="btn btn-danger">Vider le panier</button>
            </form>
        </div>
    <?php endif; ?>
</div>

<!-- TODO 6: Inclure Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
