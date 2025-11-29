document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".register-form");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        // Captura los valores del formulario
        const email = document.getElementById("email").value.trim();
        const telefono_celular = document.getElementById("phone").value.trim();
        const nombre_usuario = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value;
        const password2 = document.getElementById("confirm_password").value;

        // Validación básica
        if (!email || !telefono_celular || !nombre_usuario || !password || !password2) {
            alert("Por favor, completa todos los campos.");
            return;
        }

        if (password !== password2) {
            alert("Las contraseñas no coinciden.");
            return;
        }

        // Cuerpo del JSON que pide tu endpoint
        const data = {
            email,
            telefono_celular,
            nombre_usuario,
            password,
            password2
        };

        try {
            const response = await fetch("https://img-users-90018350665.northamerica-south1.run.app/accounts/registro/", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                // Guarda el email para la página de activación
                localStorage.setItem("registro_email", email);

                alert("Cuenta creada con éxito. Revisa tu correo para activarla.");
                window.location.href = "active_account.html";
            } else {
                // Error en el registro
                const errorData = await response.json();
                console.error("Error en el registro:", errorData);

                let msg = "Error en el registro.";
                if (errorData.detail) msg = errorData.detail;
                else if (typeof errorData === "object") {
                    msg = Object.values(errorData).flat().join("\n");
                }

                alert(msg);
            }
        } catch (error) {
            console.error("Error de conexión:", error);
            alert("Error de conexión con el servidor.");
        }
    });
});
