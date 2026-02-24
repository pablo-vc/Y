@extends('layouts.app')
<title>Y | Notifications</title>

@section('content')
    <h2 style="margin-bottom:20px;">Notificaciones</h2>

    @forelse($notifications ?? [] as $notification)
        <div class="card"
            style="
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 15px 20px;
            border-radius: 15px;
            background: #f5f8fa;
            margin-bottom: 10px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            transition: background 0.2s;
        ">
            <div style="display:flex; flex-direction:column;">
                <strong style="color:#1da1f2;">{{ $notification['username'] }}</strong>
                <span style="color:#555; font-size:14px;">Ha comenzado a seguirte</span>
                <small style="color:gray; margin-top:5px;">{{ $notification['created_at'] }}</small>
            </div>

        </div>
    @empty
        <p style="color:gray; margin-top:10px;">No tienes notificaciones.</p>
    @endforelse
@endsection
