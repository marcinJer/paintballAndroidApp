<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

    $con = mysqli_connect("localhost", "id8392575_jerry", "admin", "id8392575_paintball");

    $userId = $_POST['id'];
    $price = $_POST['price'];
    $game = $_POST['game'];
    $weapon = $_POST['weapon'];
    $date= $_POST['date'];
    $numberOfParticipants = $_POST['numberOfParticipants'];

    $Sql_Query = "INSERT INTO orders (userId, price, game, weapon, date, numberOfParticipants) values ('$userId','$price','$game', '$weapon', '$date', '$numberOfParticipants')";

    if(mysqli_query($con,$Sql_Query))
    {
        echo 'Order Registered Successfully';
    }
    else
    {
        echo 'Something went wrong';
    }
}
mysqli_close($con);
?>