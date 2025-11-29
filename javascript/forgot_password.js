document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".forgot-form");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const email = document.getElementById("email").value.trim();
        const telefono_celular = document.getElementById("phone")?.value.trim(); // opcional si agregas un campo de teléfono

        if (!email && !telefono_celular) {
            alert("Ingresa tu correo o teléfono para recuperar la contraseña.");
            return;
        }

        const data = email ? { email } : { telefono_celular };

        try {
            const response = await fetch("https://img-users-90018350665.northamerica-south1.run.app/accounts/forgot/", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });

            const result = await response.json();

            if (response.ok && result.message?.trim() === "Se envió un codigo para recuperar la contraseña") {
                // Guarda el email o teléfono en localStorage para usar en reset_password.html
                if (email) localStorage.setItem("reset_email", email);
                if (telefono_celular) localStorage.setItem("reset_phone", telefono_celular);

                alert(result.message);
                window.location.href = "reset_password.html";
            } else {
                let msg = result.message || "Ocurrió un error al enviar el código.";
                alert(msg);
            }

        } catch (error) {
            console.error("Error de conexión:", error);
            alert("Error de conexión con el servidor.");
        }
    });
});
