<?php
// TODO 1: Inclure le fichier header.php
include "includes/header.php";
// TODO 2: Récupérer les produits depuis l'API
// URL de l'API : https://dummyjson.com/products?limit=30
// Indices :
// - Utilisez file_get_contents() pour récupérer le JSON
// - Utilisez json_decode() avec true comme 2e paramètre pour obtenir un tableau
// - Les produits sont dans $data['products']
$reponse = file_get_contents("https://dummyjson.com/products?limit=30");
$data = json_decode($reponse, true);
$produits = $data["products"];
?>

<div class="container my-4">
    <h1 class="mb-4">Nos Produits</h1>

    <div class="row">
        <!-- TODO 3: Boucler sur les produits avec foreach -->
        <!-- Pour chaque produit, créer une carte Bootstrap qui contient :
             - Une colonne responsive (col-md-6 col-lg-4 mb-4)
             - Une carte avec classe "card h-100" pour hauteur égale
             - L'image du produit (utiliser $produit['thumbnail'])
               * Classe : card-img-top
               * Style : height: 200px; object-fit: cover;
             - Le corps de la carte avec :
               * Le titre (dans un h5 avec classe card-title)
               * La description (dans un p avec classe card-text)
               * En bas : prix et bouton d'ajout
        -->
        <!-- TODO 4: Dans chaque carte, ajouter un formulaire pour l'ajout au panier -->
        <!-- Le formulaire doit :
             - Utiliser method="POST" et action="actions/ajouter_panier.php"
             - Contenir des champs hidden pour : id, title, price, thumbnail
             - Avoir un bouton submit avec :
               * Classe : btn btn-primary
               * Icône : bi-cart-plus
               * Texte : "Ajouter"

             Structure suggérée pour le bas de la carte :
             <div class="d-flex justify-content-between align-items-center mt-auto">
                 <span class="h5 mb-0">[PRIX] €</span>
                 <form>...</form>
             </div>
        -->
        <?php foreach ($produits as $produit): ?>
            <div class="col-md-6 col-lg-4 mb-4">
                <div class="card h-100 shadow-sm">
                    <img src="<?= $produit['thumbnail'] ?>" class="card-img-top" alt="<?= $produit['title'] ?>" style="height: 200px; object-fit: cover;">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title"><?= htmlspecialchars($produit['title']) ?></h5>
                        <p class="card-text text-muted small"><?= htmlspecialchars(substr($produit['description'], 0, 100)) ?>...</p>

                        <div class="d-flex justify-content-between align-items-center mt-auto">
                            <span class="h5 mb-0"><?= $produit['price'] ?> €</span>
                            <form method="POST" action="actions/ajouter_panier.php">
                                <input type="hidden" name="id" value="<?= $produit['id'] ?>">
                                <input type="hidden" name="title" value="<?= htmlspecialchars($produit['title']) ?>">
                                <input type="hidden" name="price" value="<?= $produit['price'] ?>">
                                <input type="hidden" name="thumbnail" value="<?= $produit['thumbnail'] ?>">
                                <button type="submit" class="btn btn-primary btn-sm">
                                    <i class="bi bi-cart-plus"></i> Ajouter
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        <?php endforeach; ?>
    </div>
</div>

<!-- TODO 5: Inclure Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
