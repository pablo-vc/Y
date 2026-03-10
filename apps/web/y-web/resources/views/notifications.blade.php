@extends('layouts.app')
<title>Y | Notifications</title>
<style>
    .notification {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 15px 20px;
        border-radius: 15px;
        background: #f5f8fa;
        margin-bottom: 10px;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    }

    .notification-content {
        display: flex;
        flex-direction: column;
        color: #1da1f2;
    }

    h2 {
        padding-bottom: 20px;
        border-bottom: 2px solid #1da0f27b;
    }
    .username {
        display: flex;
        margin-bottom: 5px;
        align-items: center;
    }

    .username-text {
        text-decoration: none;
        color: #1da1f2;
        font-weight: bold;
    }

    .username-text:hover {
        color: black;
    }
</style>

@section('content')
    <div class="fixed">

        <h2>Notificaciones</h2>

        <div class="scrollable">

            @forelse($notifications ?? [] as $notification)
                <div class="notification">
                    <div class="notification-content">
                        <div class="username">
                            <a href="{{ url('/profile/' . $notification['id_follower']) }}" class="username-text">
                                {{ $notification['username'] }}
                            </a>
                        </div>
                        <span style="color:#555; font-size:14px;">Ha comenzado a seguirte</span>
                        <small style="color:gray; margin-top:5px;">{{ $notification['created_at'] }}</small>
                    </div>

                </div>
            @empty
                <p style="color:gray; margin-top:10px;">No tienes notificaciones.</p>
            @endforelse
        </div>
    </div>
@endsection
