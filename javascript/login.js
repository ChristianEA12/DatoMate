document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector(".login-form");

    form.addEventListener("submit", async function(event) {
        event.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        try {
            const response = await fetch("https://img-users-90018350665.northamerica-south1.run.app/accounts/login/", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    identificador: email,
                    password: password
                })
            });

            if (!response.ok) {
                throw new Error("Error en la autenticación");
            }

            const data = await response.json();
            console.log("Respuesta del servidor:", data);

            // Verificamos que existan los tokens
            if (data.access_token && data.refresh_token) {
                // Guardamos los tokens y la info del usuario
                localStorage.setItem("access_token", data.access_token);
                localStorage.setItem("refresh_token", data.refresh_token);
                localStorage.setItem("usuario", JSON.stringify(data.user));

                // Redirigimos al dashboard
                window.location.href = "principal.html";
            } else {
                alert("Error: el servidor no devolvió tokens válidos.");
            }

        } catch (error) {
            console.error("Error:", error);
            alert("Credenciales incorrectas o problema de conexión.");
        }
    });
});