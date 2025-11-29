document.getElementById("logoutBtn").addEventListener("click", async () => {
  const refreshToken = localStorage.getItem("refresh_token"); // o donde lo guardes

  if (!refreshToken) {
    alert("No hay token de sesión activo.");
    window.location.href = "login.html";
    return;
  }

  try {
    const response = await fetch("https://img-users-90018350665.northamerica-south1.run.app/accounts/logout/", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ refresh_token: refreshToken }),
    });

    if (response.ok) {
      // El logout fue exitoso
      localStorage.removeItem("access_token");
      localStorage.removeItem("refresh_token");
      alert("Sesión cerrada correctamente.");
      window.location.href = "login.html"; // o la ruta de tu login
    } else {
      const data = await response.json();
      console.error("Error al cerrar sesión:", data);
      alert("Hubo un problema al cerrar sesión.");
    }
  } catch (error) {
    console.error("Error de red:", error);
    alert("Error de conexión al cerrar sesión.");
  }
});