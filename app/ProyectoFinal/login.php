<?php
header("Content-Type: application/json");

// Configuración de la base de datos
$servername = "192.168.20.4";
$username = "root";
$password = "";
$dbname = "final";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Verificar conexión
if ($conn->connect_error) {
    die(json_encode(array("success" => false, "message" => "Connection failed: " . $conn->connect_error)));
}

// Obtener datos del cuerpo de la solicitud
$data = json_decode(file_get_contents("php://input"), true);

if (isset($data['email']) && isset($data['contrasena'])) {
    $email = $data['email'];
    $contrasena= $data['contrasena'];

    // Consulta SQL
    $stmt = $conn->prepare("SELECT * FROM Administrador WHERE email = ? AND contrasena = ?");
    $stmt->bind_param("ss", $email, $contrasena);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        echo json_encode(array("success" => true, "message" => "Login successful"));
    } else {
        echo json_encode(array("success" => false, "message" => "Invalid username or password"));
    }

    $stmt->close();
} else {
    echo json_encode(array("success" => false, "message" => "Username and password required"));
}

$conn->close();
?>

