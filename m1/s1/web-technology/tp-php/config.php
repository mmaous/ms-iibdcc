<?php
// Configuration de la base de données
define('DB_HOST', 'localhost');
define('DB_NAME', 'tp_crud_php');
define('DB_USER', 'root');
define('DB_PASS', '');
define('DB_CHARSET', 'utf8mb4');

// Fonction de connexion à la base de données
function getConnection() {
    try {
        // We switch 'mysql:' to 'sqlite:' and remove the host/user/pass requirements
        // This creates a file named 'database.sqlite' automatically
        $dsn = 'sqlite:database.sqlite';

        $pdo = new PDO($dsn); // No user/pass needed for SQLite

        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);

        // This line ensures foreign keys work in SQLite (optional but good)
        $pdo->exec("PRAGMA foreign_keys = ON;");

        return $pdo;
    } catch (PDOException $e) {
        die('Erreur de connexion : ' . $e->getMessage());
    }
}

// Fonction pour hasher un mot de passe
function hashPassword($password) {
    return password_hash($password, PASSWORD_DEFAULT);
}

// Fonction pour vérifier un mot de passe
function verifyPassword($password, $hash) {
    return password_verify($password, $hash);
}

// Fonction pour valider l'email
function validateEmail($email) {
    return filter_var($email, FILTER_VALIDATE_EMAIL);
}

// Fonction pour nettoyer les données
function cleanInput($data) {
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}
?>
