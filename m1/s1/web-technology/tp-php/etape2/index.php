<?php
require_once '../config.php';

$action = $_GET['action'] ?? 'list';
$id = $_GET['id'] ?? null;
$pdo = getConnection();
$message = '';
$error = '';

try {
    switch ($action) {
        case 'create':
            if ($_SERVER['REQUEST_METHOD'] === 'POST') {
                $email = cleanInput($_POST['email']);
                $password = cleanInput($_POST['password']); // In real app, use password_hash()
                $role = cleanInput($_POST['role']);

                if (filter_var($email, FILTER_VALIDATE_EMAIL) && !empty($password)) {
                    $sql = "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";
                    $stmt = $pdo->prepare($sql);

                    //  Binding values (implicitly via execute array)
                    $stmt->execute([$email, $password, $role]);

                    header('Location: index.php?action=list&message=User created successfully');
                    exit;
                } else {
                    $error = "Invalid email or empty password.";
                }
            }
            break;

        case 'edit':
            if ($id && $_SERVER['REQUEST_METHOD'] === 'POST') {
                $email = cleanInput($_POST['email']);
                $role = cleanInput($_POST['role']);
                $password = cleanInput($_POST['password']);

                // Logic: Update password only if provided
                if (!empty($password)) {
                    $sql = "UPDATE users SET email = ?, password = ?, role = ? WHERE id = ?";
                    $params = [$email, $password, $role, $id];
                } else {
                    $sql = "UPDATE users SET email = ?, role = ? WHERE id = ?";
                    $params = [$email, $role, $id];
                }

                $stmt = $pdo->prepare($sql);
                $stmt->execute($params);
                header('Location: index.php?action=list&message=User updated successfully');
                exit;
            }
            // Fetch user for the form
            if ($id) {
                $stmt = $pdo->prepare("SELECT * FROM users WHERE id = ?");
                $stmt->execute([$id]);
                $user = $stmt->fetch();
            }
            break;

        case 'delete':
            if ($id) {
                $stmt = $pdo->prepare("DELETE FROM users WHERE id = ?");
                $stmt->execute([$id]);
                header('Location: index.php?action=list&message=User deleted successfully');
                exit;
            }
            break;

        case 'list':
        default:
            //  Using query() for simple SELECT statements without parameters
            $stmt = $pdo->query("SELECT * FROM users ORDER BY id DESC");
            //fetchAll() retrieves all rows
            $users = $stmt->fetchAll();
            break;
    }
} catch (PDOException $e) {
    // Global error handling
    $error = "Database Error: " . $e->getMessage();
}
?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TP CRUD PDO</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="card shadow">
            <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                <h4 class="mb-0">Gestion des Utilisateurs</h4>
                <?php if($action !== 'list'): ?>
                    <a href="?action=list" class="btn btn-sm btn-light">Retour</a>
                <?php endif; ?>
            </div>
            <div class="card-body">

                <?php if (!empty($_GET['message'])): ?>
                    <div class="alert alert-success"><?= htmlspecialchars($_GET['message']) ?></div>
                <?php endif; ?>
                <?php if (!empty($error)): ?>
                    <div class="alert alert-danger"><?= htmlspecialchars($error) ?></div>
                <?php endif; ?>

                <?php if ($action === 'create' || ($action === 'edit' && isset($user))): ?>
                    <form method="POST">
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" required
                                   value="<?= isset($user) ? htmlspecialchars($user['email']) : '' ?>">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mot de passe</label>
                            <input type="password" name="password" class="form-control"
                                   <?= $action === 'create' ? 'required' : '' ?>
                                   placeholder="<?= $action === 'edit' ? 'Laisser vide pour ne pas changer' : '' ?>">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Rôle</label>
                            <select name="role" class="form-select">
                                <option value="user" <?= (isset($user) && $user['role'] == 'user') ? 'selected' : '' ?>>Utilisateur</option>
                                <option value="admin" <?= (isset($user) && $user['role'] == 'admin') ? 'selected' : '' ?>>Administrateur</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-success">Enregistrer</button>
                    </form>

                <?php else: ?>
                    <div class="mb-3 text-end">
                        <a href="?action=create" class="btn btn-success">Nouveau Utilisateur</a>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered">
                            <thead class="table-light">
                                <tr>
                                    <th>ID</th>
                                    <th>Email</th>
                                    <th>Rôle</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <?php if (empty($users)): ?>
                                    <tr><td colspan="4" class="text-center">Aucun utilisateur trouvé.</td></tr>
                                <?php else: ?>
                                    <?php foreach ($users as $user): ?>
                                        <tr>
                                            <td><?= $user['id'] ?></td>
                                            <td><?= htmlspecialchars($user['email']) ?></td>
                                            <td><span class="badge bg-secondary"><?= htmlspecialchars($user['role']) ?></span></td>
                                            <td>
                                                <a href="?action=edit&id=<?= $user['id'] ?>" class="btn btn-warning btn-sm">Modifier</a>
                                                <a href="?action=delete&id=<?= $user['id'] ?>" class="btn btn-danger btn-sm" onclick="return confirm('Confirmer la suppression ?')">Supprimer</a>
                                            </td>
                                        </tr>
                                    <?php endforeach; ?>
                                <?php endif; ?>
                            </tbody>
                        </table>
                    </div>
                <?php endif; ?>
            </div>
        </div>
    </div>
</body>
</html>
