<?php


if($_SERVER['REQUEST_METHOD']=='POST'){


    $orderId= $_POST['id'];

    $con = mysqli_connect("localhost", "id8392575_jerry", "admin", "id8392575_paintball");

    if ($con->connect_error) {

        die("Connection failed: " . $conn->connect_error);
    }

    $sql = "SELECT * FROM orders where id = '$orderId'" ;

    $result = $con->query($sql);

    if ($result->num_rows >0) {


        while($row[] = $result->fetch_assoc()) {

            $tem = $row;

            $json = json_encode($tem);

        }

    } else {
        echo "No Results Found.";
    }
    echo $json;

    $con->close();
}
