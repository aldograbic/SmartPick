function filterByParams(params) {
  const queryParams = new URLSearchParams(window.location.search);

  // Iterate through the parameters and set them in the URL
  for (const [key, value] of Object.entries(params)) {
    // If the value is null, remove the parameter from the URL
    if (value === null) {
      queryParams.delete(key);
    } else {
      queryParams.set(key, value);
    }
  }

  const newUrl = window.location.pathname + "?" + queryParams.toString();
  window.location.href = newUrl;
}
