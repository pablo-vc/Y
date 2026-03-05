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

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f5f8fa;
        }

        .app {
            max-width: 1100px;
            margin: 0 auto;
            display: flex;
            min-height: 100vh;
            border: 1px solid rgb(191, 187, 187);
            border-radius: 10px;
        }

        .sidebar {
            width: 250px;
            padding: 20px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;

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

            height: 100vh;
            overflow: hidden;
            overflow-y: hidden;

        }

        .card {
            padding: 15px;
            border-bottom: 1px solid #e1e8ed;
        }

        .tabs {
            display: flex;
            border-bottom: 1px solid #ddd;
        }

        .tab {
            flex: 1;
            text-align: center;
            padding: 15px;
            cursor: pointer;
            font-weight: bold;
        }

        .tab.active {
            border-bottom: 3px solid #1da1f2;
            color: #1da1f2;
        }


        .fixed {
            height: 100%;
            display: flex;
            flex-direction: column;
        }

        *::-webkit-scrollbar {
            width: 6px;
        }

        *::-webkit-scrollbar-thumb {
            background: #ccc;
            border-radius: 10px;
        }

        .scrollable {
            flex: 1;
            overflow-y: auto;
        }

        @media (max-width: 900px) {
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

            .sidebar h2 {
                display: none;
            }

            .sidebar-top {
                flex-direction: row;
                gap: 10px;
            }

            .sidebar-top .nav-link {
                padding: 8px;
                font-size: 14px;
            }

            .sidebar-bottom {
                flex-direction: row;
                gap: 10px;
                align-items: center;
            }

            .main {
                border: none;
                padding: 15px;
            }

        }

        .modal {
            display: none;
            position: fixed;
            inset: 0;
            background: rgba(0, 0, 0, 0.4);
            justify-content: center;
            align-items: center;
            z-index: 1000;
        }

        .modal-content {
            background: white;
            padding: 25px;
            width: 400px;
            max-width: 90%;
            border-radius: 15px;
            animation: fadeIn 0.2s ease-in-out;
        }

        .close {
            float: right;
            font-size: 22px;
            cursor: pointer;
        }

        .modal input,
        .modal textarea {
            width: 100%;
            padding: 8px;
            margin: 8px 0 15px 0;
            border: 1px solid #ddd;
            border-radius: 8px;
        }

        .btn {
            padding: 8px 15px;
            border: none;
            background: #1da1f2;
            color: white;
            cursor: pointer;
            border-radius: 20px;
        }

        .btn.danger {
            background: #e0245e;
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
