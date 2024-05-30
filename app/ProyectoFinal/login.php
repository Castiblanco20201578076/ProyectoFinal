<?php
header("Content-Type: application/json");
require_once("./conexion.php");

$link =conectar();

$email = $_POST['email'];
$contrasena = $_POST['password'];

$sql = "SELECT * FROM Administrador WHERE  email = '".$email."' AND contrasena = '".$contrasena."'";


if($email == ""  || $contrasena == ""){
    echo  "ERROR1";
}else{
    $consula = mysqli_query($link, $sql) or die (mysqli_error());
    if(mysqli_num_rows($consula)==0){
        echo "ERROR2";
    }else{
        echo "ACCESO";
    }
}

// Obtener datos del cuerpo de la solicitud
/*$data = json_decode(file_get_contents("php://input"), true);

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

$conn->close();*/
?>

