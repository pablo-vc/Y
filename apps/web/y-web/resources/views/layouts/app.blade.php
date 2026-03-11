<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Y</title>
    <link rel="shortcut icon" href="{{ asset('YLogoAzul.ico') }}" type="image/x-icon">

    <style>
        * {
            box-sizing: border-box;
        }

        h1,
        h2,
        h3,
        h4,
        h5 {
            color: #1da1f2
        }
        form input,
        form textarea {
            margin-bottom: 12px;
            padding: 12px;
            border-radius: 8px;
            border: 1px solid #ddd;
            font-size: 14px;
        }

        form input:focus,
        form textarea:focus {
            outline: none;
            border-color: #18aaff;
        }
        textarea{
            resize: none;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f5f8fa;
        }

        .app {
            max-width: 1100px;
            margin: 0 auto;
            display: flex;
            height: 100vh;
            /* border: 1px solid rgb(191, 187, 187); */
            border: 1px solid #1da0f25f;
            border-radius: 10px;
        }

        .sidebar {
            width: 250px;
            padding: 20px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            border-right: 1px solid #1da0f25f;
        }

        .sidebar h2 {
            margin-top: 0;
        }

        .nav-link {
            display: block;
            padding: 12px 0;
            text-decoration: none;
            color: black;
            font-weight: bold;
        }

        .nav-link:hover {
            color: #1da1f2;
        }

        .sidebar-top,
        .sidebar-bottom {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }

        .logout-btn {
            color: white;
            background: #ff4d4f;
            text-align: center;
            display: block;
            padding: 8px 12px;
            border-radius: 50px;
            font-weight: bold;
            text-decoration: none;
        }

        .main {
            flex: 1;
            padding: 20px;
            border-left: 1px solid #e1e8ed;
            border-right: 1px solid #e1e8ed;
            background: white;
            border-radius: 10px;
            border-right: 1px solid #1da0f25f;
            height: 100vh;
            overflow: hidden;
            overflow-y: hidden;
        }

        .card {
            border-bottom: 1px solid #e1e8ed;
            border-radius: 10px;
            padding: 15px;
            margin-bottom: 7px;
            box-shadow: 0.5px 0.5px 0.5px 1px #1da0f23c;
        }

        .card>.username {
            font-weight: bold;
        }

        .card>.content {
            word-wrap: break-word;
        }

        .card>small {
            color: gray;
            float: right;
            font-size: 0.6em
        }



        .fixed {
            height: 100%;
            display: flex;
            flex-direction: column;
        }

        .scrollable {
            flex: 1;
            overflow-y: auto;
            padding: 5px;
        }

        *::-webkit-scrollbar {
            width: 6px;
        }

        *::-webkit-scrollbar-thumb {
            background: #ccc;
            border-radius: 10px;
        }


        @media (max-width: 750px) {
            .app {
                flex-direction: column;
            }

            .sidebar {
                width: 100%;
                flex-direction: row;
                justify-content: space-between;
                align-items: center;
                padding: 10px;
                border-bottom: 1px solid #e1e8ed;
            }

            .sidebar-top,
            .sidebar-bottom {
                flex-direction: row;
                gap: 10px;
            }

            .sidebar-top .nav-link {
                padding-right: 8px;
                font-size: 12px;
            }

            .sidebar-bottom {
                font-size: 12px;
                align-items: center;
            }

            .sidebar-bottom>div {
                display: none
            }

            .main {
                border: none;
                padding: 15px;
            }

        }



        .btn {
            padding: 8px 15px;
            border: none;
            background: #1da1f2;
            color: white;
            cursor: pointer;
            border-radius: 20px;
        }


        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: scale(0.95);
            }

            to {
                opacity: 1;
                transform: scale(1);
            }
        }
    </style>
</head>

<body>

    <div class="app">

        <div class="sidebar">
            <div class="sidebar-top">
                <a class="nav-link" href="/feed">Feed</a>
                <a class="nav-link" href="/profile">Profile</a>
                <a class="nav-link" href="/notifications">Notifications</a>
                <a class="nav-link" href="/post">Post</a>
            </div>

            <div class="sidebar-bottom">
                <div>@<b>{{ session('username') }}</b></div>
                <br>
                <a class="logout-btn" href="/logout">Logout</a>
            </div>
        </div>

        <div class="main">
            @yield('content')
        </div>

    </div>

</body>

</html>
