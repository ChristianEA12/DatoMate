document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".forgot-form");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const otp = document.getElementById("verification_code").value.trim();
        const newPassword = document.getElementById("new_password").value.trim();
        const confirmPassword = document.getElementById("confirm_new_password").value.trim();

        if (!otp || !newPassword || !confirmPassword) {
            alert("Por favor completa todos los campos.");
            return;
        }

        if (newPassword !== confirmPassword) {
            alert("Las contraseñas no coinciden.");
            return;
        }

        // Recupera email o teléfono desde localStorage
        const telefono_celular = localStorage.getItem("reset_phone");
        const email = localStorage.getItem("reset_email");

        if (!telefono_celular && !email) {
            alert("No se encontró la información de contacto. Vuelve a solicitar el código.");
            window.location.href = "forgot_password.html";
            return;
        }

        // Prepara el cuerpo de la solicitud según lo que tengas
        const data = telefono_celular
            ? { telefono_celular, otp, new_password: newPassword, new_password2: confirmPassword }
            : { email, otp, new_password: newPassword, new_password2: confirmPassword };

        try {
            const response = await fetch("https://img-users-90018350665.northamerica-south1.run.app/accounts/reset/", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });

            const result = await response.json();

            if (response.ok && result.message?.trim() === "Contraseña restablecida correctamente") {
                alert(result.message);
                // Limpia localStorage
                localStorage.removeItem("reset_email");
                localStorage.removeItem("reset_phone");
                // Redirige al login
                window.location.href = "login.html";
            } else {
                let msg = result.message || "Ocurrió un error al restablecer la contraseña.";
                alert(msg);
            }

        } catch (error) {
            console.error("Error de conexión:", error);
            alert("Error de conexión con el servidor.");
        }
    });
});
