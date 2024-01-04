function calculateTotal(element) {
  var productDiv = element.closest(".flex.border");

  var selectedQuantity = productDiv.querySelector(".quantity").value;
  var productPrice = parseFloat(
    productDiv.querySelector(".productPrice").getAttribute("data-price")
  );

  var totalPrice = (selectedQuantity * productPrice).toFixed(2);

  productDiv.querySelector(".totalPrice").innerText = totalPrice + " EUR";

  updateEstimatedTotal();
}

function updateEstimatedTotal() {
  var productTotals = document.querySelectorAll(".totalPrice");
  var shippingCost = 5;
  var tax = 2;

  var sum = 0;
  productTotals.forEach(function (totalElement) {
    sum += parseFloat(totalElement.innerText.replace(" EUR", ""));
  });

  var estimatedTotal = (sum + shippingCost + tax).toFixed(2);

  document.getElementById("estimatedTotal").innerText = estimatedTotal + " EUR";
}

window.addEventListener("DOMContentLoaded", function () {
  updateEstimatedTotal();
});
