<?php

if($_SERVER['REQUEST_METHOD']=='POST'){

    $con = mysqli_connect("localhost", "id8392575_jerry", "admin", "id8392575_paintball");

    $orderId = $_POST['id'];
    $game = $_POST['game'];
    $weapon = $_POST['weapon'];
    $date = $_POST['date'];
	$numberOfParticipants = $_POST['numberOfParticipants'];

    $Sql_Query = "UPDATE orders SET game = '$game', weapon = '$weapon', date = '$date', numberOfParticipants = '$numberOfParticipants' WHERE id = $orderId";

    if(mysqli_query($con,$Sql_Query))
    {
        echo 'Order updated successfully!';
    }
    else
    {
        echo 'Something went wrong';
    }
}
mysqli_close($con);
?>