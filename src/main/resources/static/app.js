document.getElementById("login-form").addEventListener("submit", async function (event) {
  event.preventDefault();

  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;

  try {
    const response = await axios.post("http://localhost:8080/login", {
      username: username,
      password: password,
    });

    const token = response.data.token;
    localStorage.setItem("jwtToken", token);
    alert("Login successful!");
    window.location.href = "chat.html";
    // Redirect to another page or update the UI accordingly
  } catch (error) {
    document.getElementById("error-message").innerText = "로그인에 실패했습니다. \n다시 시도해주세요.";
  }
});
