// Add an event listener for form submissions
document.addEventListener("DOMContentLoaded", function() {
    const addCarForm = document.querySelector("form[action='car_rental.php'][method='post']:nth-of-type(1)");
    const rentCarForm = document.querySelector("form[action='car_rental.php'][method='post']:nth-of-type(2)");
    const returnCarForm = document.querySelector("form[action='car_rental.php'][method='post']:nth-of-type(3)");

    addCarForm.addEventListener("submit", function(event) {
        if (!validateAddCarForm()) {
            event.preventDefault();
        }
    });

    rentCarForm.addEventListener("submit", function(event) {
        if (!validateRentCarForm()) {
            event.preventDefault();
        }
    });

    returnCarForm.addEventListener("submit", function(event) {
        if (!validateReturnCarForm()) {
            event.preventDefault();
        }
    });
});

// Validate Add Car Form
function validateAddCarForm() {
    // const carId = document.querySelector("input[name='carId']").value;
    const brand = document.querySelector("input[name='brand']").value;
    const model = document.querySelector("input[name='model']").value;
    const price = document.querySelector("input[name='price']").value;

    if (carId === "" || brand === "" || model === "" || price === "") {
        alert("All fields are required for adding a car.");
        return false;
    }
    if (isNaN(price) || price <= 0) {
        alert("Price must be a positive number.");
        return false;
    }
    return true;
}

// Validate Rent Car Form
function validateRentCarForm() {
    const carId = document.querySelector("form[action='car_rental.php'][method='post']:nth-of-type(2) input[name='carId']").value;
    const customerName = document.querySelector("input[name='customerName']").value;
    const days = document.querySelector("input[name='days']").value;

    if (carId === "" || customerName === "" || days === "") {
        alert("All fields are required for renting a car.");
        return false;
    }
    if (isNaN(days) || days <= 0) {
        alert("Number of days must be a positive number.");
        return false;
    }
    return true;
}

// Validate Return Car Form
function validateReturnCarForm() {
    const carId = document.querySelector("form[action='car_rental.php'][method='post']:nth-of-type(3) input[name='carId']").value;

    if (carId === "") {
        alert("Car ID is required for returning a car.");
        return false;
    }
    return true;
}
