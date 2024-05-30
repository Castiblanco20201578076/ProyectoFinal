<?php
function conectar(){
    $host="localhost";// nombre del host donde esta almacenada la BD
    $user="root";//nombre del usuario de la bd
    $pass="";// la contraseña de la bd
    $dbname="final";//el nombre de la BD
    //Conectar la BD
    $link=mysqli_connect($host,$user,$pass) or die ("ERROR AL CONECTAR LA BD".mysqli_error($link));
    //seleccionar la BD
    mysqli_select_db($link,$dbname) or die ("ERROR AL SELECCIONAR LA BD".mysqli_error($link));
    return $link;
}

$link = conectar();
if ($link) {
    echo "Conexión exitosa a la base de datos.";
} else {
    echo "Error al conectar a la base de datos.";
}

?>