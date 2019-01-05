<?php
$con = mysqli_connect("localhost", "id8392575_jerry", "admin", "id8392575_paintball");
    
$name = $_POST["name"];
$surname = $_POST["surname"];
$username = $_POST["username"];
$password = $_POST["password"];
$statement = mysqli_prepare($con, "INSERT INTO user (name, surname, username, password) VALUES (?, ?, ?, ?)");
mysqli_stmt_bind_param($statement, "ssss", $name, $surname, $username, $password);
mysqli_stmt_execute($statement);
    
$response = array();
$response["success"] = true;  
    
echo json_encode($response);
?>
