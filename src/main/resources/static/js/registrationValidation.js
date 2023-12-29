let passwordCriteriaMet = {};

function validateForm() {
  const password = document.getElementById("password").value;
  const confirmPassword = document.getElementById("confirmPassword").value;

  if (password !== confirmPassword) {
    return false;
  }

  if (
    !passwordCriteriaMet.length ||
    !passwordCriteriaMet.hasNumber ||
    !passwordCriteriaMet.hasSpecialChar
  ) {
    return false;
  }

  return true;
}

function validatePassword() {
  const password = document.getElementById("password").value;
  const lengthCriteria = document.getElementById("lengthCriteria");
  const numberCriteria = document.getElementById("numberCriteria");
  const specialCharCriteria = document.getElementById("specialCharCriteria");

  passwordCriteriaMet = {
    length: password.length >= 6,
    hasNumber: /\d/.test(password),
    hasSpecialChar: /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/.test(password),
  };

  const gray = "#4B5563";
  const lightGreen = "rgb(34, 197, 94)";

  lengthCriteria.style.color = passwordCriteriaMet.length ? lightGreen : gray;
  numberCriteria.style.color = passwordCriteriaMet.hasNumber
    ? lightGreen
    : gray;
  specialCharCriteria.style.color = passwordCriteriaMet.hasSpecialChar
    ? lightGreen
    : gray;
}

function checkPasswordsMatch() {
  const password = document.getElementById("password").value;
  const confirmPasswordElement = document.getElementById("confirmPassword");
  const confirmPassword = confirmPasswordElement.value;

  if (password === confirmPassword) {
    confirmPasswordElement.style.border = "1px solid #D2D6DC";
    confirmPasswordElement.style.borderBottom = "2px solid #D2D6DC";
  } else {
    confirmPasswordElement.style.border = "1px solid #E02424";
    confirmPasswordElement.style.borderBottom = "2px solid #E02424";
  }
}
