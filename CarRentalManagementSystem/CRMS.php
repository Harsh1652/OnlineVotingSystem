<?php
$servername = "localhost";
$username = "root";
$password = "your_password";
$dbname = "car_rental_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if (isset($_POST['addCar'])) {
    $carId = $_POST['carId'];
    $brand = $_POST['brand'];
    $model = $_POST['model'];
    $price = $_POST['price'];
    $isAvailable = 1;

    $sql = "INSERT INTO cars (car_id, brand, model, base_price_per_day, is_available) VALUES ('$carId', '$brand', '$model', '$price', '$isAvailable')";
    if ($conn->query($sql) === TRUE) {
        echo "Car added successfully!";
    } else {
        echo "Error: " . $conn->error;
    }
}

if (isset($_POST['rentCar'])) {
    $carId = $_POST['carId'];
    $customerName = $_POST['customerName'];
    $days = $_POST['days'];

    $checkAvailability = "SELECT is_available FROM cars WHERE car_id='$carId'";
    $result = $conn->query($checkAvailability);
    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        if ($row['is_available']) {
            $sql = "INSERT INTO rentals (car_id, customer_id, days) VALUES ('$carId', '$customerName', '$days')";
            $updateAvailability = "UPDATE cars SET is_available=0 WHERE car_id='$carId'";

            if ($conn->query($sql) === TRUE && $conn->query($updateAvailability) === TRUE) {
                echo "Car rented successfully!";
            } else {
                echo "Error: " . $conn->error;
            }
        } else {
            echo "Car is not available.";
        }
    } else {
        echo "Car not found.";
    }
}

if (isset($_POST['returnCar'])) {
    $carId = $_POST['carId'];

    $checkRental = "SELECT * FROM rentals WHERE car_id='$carId'";
    $result = $conn->query($checkRental);
    if ($result->num_rows > 0) {
        $sql = "DELETE FROM rentals WHERE car_id='$carId'";
        $updateAvailability = "UPDATE cars SET is_available=1 WHERE car_id='$carId'";

        if ($conn->query($sql) === TRUE && $conn->query($updateAvailability) === TRUE) {
            echo "Car returned successfully!";
        } else {
            echo "Error: " . $conn->error;
        }
    } else {
        echo "Car is not rented.";
    }
}

if (isset($_POST['searchCar'])) {
    $sql = "SELECT * FROM cars WHERE is_available=1";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        echo "<table>";
        echo "<tr><th>Car ID</th><th>Brand</th><th>Model</th><th>Price Per Day</th></tr>";
        while($row = $result->fetch_assoc()) {
            echo "<tr><td>" . $row["car_id"]. "</td><td>" . $row["brand"]. "</td><td>" . $row["model"]. "</td><td>" . $row["base_price_per_day"]. "</td></tr>";
        }
        echo "</table>";
    } else {
        echo "No available cars found.";
    }
}

$conn->close();
?>
