
window.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("forma");
  form.addEventListener("submit", (event) => {
    event.preventDefault();

    const formData = new FormData(form);
    for (let [key, value] of formData.entries()) {
      console.log(key, value);
    }
    console.log("Sending data...");

    fetch("/api", {
      method: "POST",
      body: formData,
    })
      .then((data) => {
        console.log(data);
      });
  });
});
