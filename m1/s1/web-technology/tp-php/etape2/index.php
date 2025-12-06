<?php
require_once '../config.php';

$action = isset($_GET['action']) ? $_GET['action'] : 'list';
$id = isset($_GET['id']) ? $_GET['id'] : null;

$pdo = getConnection();

switch ($action) {
    case 'create':
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $email = cleanInput($_POST['email']);
            $password = cleanInput($_POST['password']);
            $role = cleanInput($_POST['role']);

            if (validateEmail($email) && !empty($password)) {
                $hashedPassword = hashPassword($password);

                $sql = "INSERT INTO users (email, password, role) VALUES (:email, :password, :role)";
                $stmt = $pdo->prepare($sql);

                $stmt->bindValue(':email', $email, PDO::PARAM_STR);
                $stmt->bindValue(':password', $hashedPassword, PDO::PARAM_STR);
                $stmt->bindValue(':role', $role, PDO::PARAM_STR);

                $stmt->execute();

                header('Location: index.php?action=list&message=User created successfully');
                exit;
            } else {
                $error = 'Invalid data provided.';
            }
        }
        break;

    case 'edit':
        if ($id) {
            if ($_SERVER['REQUEST_METHOD'] === 'POST') {
                $email = cleanInput($_POST['email']);
                $role = cleanInput($_POST['role']);
                $password = cleanInput($_POST['password']);

                if (validateEmail($email)) {
                    if (!empty($password)) {
                        $hashedPassword = hashPassword($password);
                        $sql = "UPDATE users SET email = :email, password = :password, role = :role WHERE id = :id";
                        $stmt = $pdo->prepare($sql);
                        $stmt->bindValue(':password', $hashedPassword, PDO::PARAM_STR);
                        $stmt->bindValue(':email', $email, PDO::PARAM_STR);
                        $stmt->bindValue(':role', $role, PDO::PARAM_STR);
                        $stmt->bindValue(':id', $id, PDO::PARAM_INT); // ...
                    } else {
                        $sql = "UPDATE users SET email = :email, role = :role WHERE id = :id";
                        $stmt = $pdo->prepare($sql);
                        $stmt->bindValue(':email', $email, PDO::PARAM_STR);
                        $stmt->bindValue(':role', $role, PDO::PARAM_STR);
                        $stmt->bindValue(':id', $id, PDO::PARAM_INT);
                    }
                    $stmt->execute();

                    header('Location: index.php?action=list&message=User updated successfully');
                    exit;
                } else {
                    $error = 'Invalid data provided.';
                }
            }

            $sql = "SELECT * FROM users WHERE id = :id";
            $stmt = $pdo->prepare($sql);
            $stmt->bindValue(':id', $id, PDO::PARAM_INT);
            $stmt->execute();
            $user = $stmt->fetch(); //
        }
        break;

    case 'delete':
        if ($id) {
            $sql = "DELETE FROM users WHERE id = :id";
            $stmt = $pdo->prepare($sql);
            $stmt->bindValue(':id', $id, PDO::PARAM_INT);
            $stmt->execute();

            header('Location: index.php?action=list&message=User deleted successfully');
            exit;
        }
        break;

    case 'view':
        if ($id) {
            $sql = "SELECT * FROM users WHERE id = :id";
            $stmt = $pdo->prepare($sql);
            $stmt->bindValue(':id', $id, PDO::PARAM_INT);
            $stmt->execute();
            $user = $stmt->fetch();
        }
        break;

    case 'list':
    default:
        $sql = "SELECT * FROM users ORDER BY created_at DESC";
        $stmt = $pdo->query($sql);
        $users = $stmt->fetchAll(); //
        break;
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TP CRUD PHP (Sandbox Mode)</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <?php if (isset($_GET['message'])): ?>
            <div class="alert alert-success"><?php echo htmlspecialchars($_GET['message']); ?></div>
        <?php endif; ?>
        <?php if (isset($error)): ?>
            <div class="alert alert-danger"><?php echo htmlspecialchars($error); ?></div>
        <?php endif; ?>

        <?php if ($action === 'list'): ?>
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h1>User List</h1>
                <a href="?action=create" class="btn btn-primary">Add User</a>
            </div>
            <?php if (empty($users)): ?>
                <div class="alert alert-info">No users found!!!!</div>
            <?php else: ?>
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php foreach ($users as $user): ?>
                            <tr>
                                <td><?php echo $user['id']; ?></td>
                                <td><?php echo htmlspecialchars($user['email']); ?></td>
                                <td><span class="badge bg-secondary"><?php echo htmlspecialchars($user['role']); ?></span></td>
                                <td>
                                    <a href="?action=view&id=<?php echo $user['id']; ?>" class="btn btn-info btn-sm"><i class="bi bi-eye"></i></a>
                                    <a href="?action=edit&id=<?php echo $user['id']; ?>" class="btn btn-warning btn-sm"><i class="bi bi-pencil"></i></a>
                                    <a href="?action=delete&id=<?php echo $user['id']; ?>" class="btn btn-danger btn-sm" onclick="return confirm('Delete this user?');"><i class="bi bi-trash"></i></a>
                                </td>
                            </tr>
                        <?php endforeach; ?>
                    </tbody>
                </table>
            <?php endif; ?>

        <?php elseif ($action === 'view' && isset($user)): ?>
            <h1>View User</h1>
            <div class="card">
                <div class="card-body">
                    <dl class="row">
                        <dt class="col-sm-3">ID</dt>
                        <dd class="col-sm-9"><?php echo $user['id']; ?></dd>
                        <dt class="col-sm-3">Email</dt>
                        <dd class="col-sm-9"><?php echo htmlspecialchars($user['email']); ?></dd>
                        <dt class="col-sm-3">Role</dt>
                        <dd class="col-sm-9"><?php echo htmlspecialchars($user['role']); ?></dd>
                        <dt class="col-sm-3">Created At</dt>
                        <dd class="col-sm-9"><?php echo $user['created_at']; ?></dd>
                    </dl>
                    <a href="?action=list" class="btn btn-secondary">Back to List</a>
                </div>
            </div>

        <?php elseif ($action === 'create' || ($action === 'edit' && isset($user))): ?>
            <h1><?php echo $action === 'create' ? 'Create User' : 'Edit User'; ?></h1>
            <div class="card">
                <div class="card-body">
                    <form action="?action=<?php echo $action; ?><?php echo isset($user) ? '&id=' . $user['id'] : ''; ?>" method="post">
                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" value="<?php echo isset($user) ? htmlspecialchars($user['email']) : ''; ?>" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password <?php if ($action === 'edit'): ?><small class="text-muted">(leave blank to keep current)</small><?php endif; ?></label>
                            <input type="password" class="form-control" id="password" name="password" <?php echo $action === 'create' ? 'required' : ''; ?>>
                        </div>
                        <div class="mb-3">
                            <label for="role" class="form-label">Role</label>
                            <select class="form-select" id="role" name="role" required>
                                <option value="guest" <?php echo (isset($user) && $user['role'] === 'guest') ? 'selected' : ''; ?>>Guest</option>
                                <option value="author" <?php echo (isset($user) && $user['role'] === 'author') ? 'selected' : ''; ?>>Author</option>
                                <option value="editor" <?php echo (isset($user) && $user['role'] === 'editor') ? 'selected' : ''; ?>>Editor</option>
                                <option value="admin" <?php echo (isset($user) && $user['role'] === 'admin') ? 'selected' : ''; ?>>Admin</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary"><?php echo $action === 'create' ? 'Create' : 'Update'; ?></button>
                        <a href="?action=list" class="btn btn-secondary">Cancel</a>
                    </form>
                </div>
            </div>
        <?php else: ?>
            <div class="alert alert-warning">User not found or invalid action.</div>
            <a href="?action=list" class="btn btn-secondary">Back to List</a>
        <?php endif; ?>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
