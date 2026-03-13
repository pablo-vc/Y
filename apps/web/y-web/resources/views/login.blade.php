<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Y | Access</title>
    <link rel="shortcut icon" href="{{ asset('YLogoAzul.ico') }}" type="image/x-icon">
    <style>
        * {
            box-sizing: border-box;
            font-family: 'Segoe UI', sans-serif;
        }

        body {
            margin: 0;
            height: 100vh;
            background: linear-gradient(135deg, #18aaff, #011730);
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            width: 380px;
            /* width: 100%; */
            /* max-width: 380px; */
            padding: 20px;
        }

        .card {
            width: 100%;
            height: 100%;
            background: #fff;
            border-radius: 16px;
            padding: 30px 25px;
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.2);
            text-align: center;
        }

        h1 {
            margin: 0;
            color: #18aaff;
        }

        .subtitle {
            font-size: 14px;
            color: #666;
            margin-bottom: 25px;
        }

        input[type="radio"] {
            display: none;
        }

        .tab-buttons {
            display: flex;
            margin-bottom: 20px;
        }

        .tab-buttons label {
            flex: 1;
            padding: 10px;
            cursor: pointer;
            background: #f0f0f0;
            color: #555;
            font-weight: 600;
            transition: 0.3s;
        }

        /* BOTÓN ACTIVO */
        #login:checked~.tab-buttons label[for="login"],
        #register:checked~.tab-buttons label[for="register"] {
            background: #18aaff;
            color: #fff;
        }

        .form {
            display: none;
            flex-direction: column;
        }

        /* MOSTRAR SEGÚN CHECK */
        #login:checked~.forms .login-form {
            display: flex;
        }

        #register:checked~.forms .register-form {
            display: flex;
        }

        .form input {
            margin-bottom: 12px;
            padding: 12px;
            border-radius: 8px;
            border: 1px solid #ddd;
            font-size: 14px;
        }

        .form input:focus {
            outline: none;
            border-color: #18aaff;
        }

        button {
            padding: 12px;
            border: none;
            border-radius: 8px;
            background: #18aaff;
            color: #fff;
            font-size: 15px;
            font-weight: bold;
            cursor: pointer;
        }

        button:hover {
            background: #18aaff;
        }

        .bad-pass {
            background: rgba(255, 0, 0, 0.258);

        }

        .not-visible {
            display: none
        }

        .visible {
            display: block
        }
    </style>
</head>

<body>

    <div class="container">
        <div class="card">
            <h1><img src="{{ asset('YLogoAzul.ico') }}" alt="Y"
                    style="width: 70px;height:70px;border-radius:10px"></h1>

            <p class="subtitle">Comparte tus opiniones con el mundo</p>

            <input type="radio" name="tab" id="login" checked>
            <input type="radio" name="tab" id="register">

            <div class="tab-buttons">
                <label for="login">Iniciar sesión</label>
                <label for="register">Registrarse</label>
            </div>

            <div class="forms">
                <form class="form login-form" method="POST" action="/login">
                    @csrf
                    <input type="email" name="email" placeholder="Email" required>
                    <input type="password" name="password" placeholder="Password" required>
                    <button type="submit">Log In</button>
                </form>
                @if (session('error'))
                    <p style="color:red; margin-top:10px;">
                        {{ session('error') }}
                    </p>
                @endif
                <form class="form register-form" method="POST" action="/register">
                    @csrf
                    <input type="text" name="username" placeholder="Username" pattern="^[a-zA-Z0-9_]{2,20}$"
                        minlength="2" maxlength="20" required>
                    <input type="email" name="email" placeholder="Email" required>
                    <input type="password" name="password" placeholder="Password" required>
                    <input type="password" name="password_confirmation" placeholder="Repeat password" required>
                    <span id="msg" class="not-visible" style="color:red;font-size:12px">Passwords doesn't
                        match</span>
                    <br>
                    <button type="submit" id="register-button" disabled>Create Account</button>
                </form>
            </div>

        </div>
    </div>

    <script>
        var pass1 = document.getElementsByName("password")[1];
        var pass2 = document.getElementsByName("password_confirmation")[0];
        var msg = document.getElementById("msg");
        var btnReg = document.getElementById("register-button");
        pass1.addEventListener("keyup", confirmPass);
        pass2.addEventListener("keyup", confirmPass);

        function confirmPass() {
            msg.classList.toggle("visible", pass1.value != pass2.value)
            if (pass1.value != pass2.value) {
                btnReg.setAttribute("disabled", "true");
                pass1.classList.add("bad-pass");
                pass2.classList.add("bad-pass");
            } else {
                btnReg.removeAttribute("disabled");
                pass1.classList.remove("bad-pass");
                pass2.classList.remove("bad-pass");
            }
        }

    </script>

</body>

</html>
