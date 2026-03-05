@extends('layouts.app')
<title>Y | Profile</title>

@section('content')

    <div class="fixed">

        <div class="profile-header card"
            style="display:flex; flex-direction:column; gap:15px; padding:20px; border-radius:15px;">
            <div style="display:flex; justify-content:space-between; align-items:center;">
                <div>
                    <h2 style="margin:0; font-size:24px;">{{ $profile['username'] }}</h2>
                    <p style="margin:2px 0; color:gray;">{{ $profile['email'] }}</p>
                </div>
                <div>
                    @if (session('user_id') != ($id ?? session('user_id')))
                        @if ($isFollowing ?? false)
                            <form method="POST" action="{{ url('/profile/' . $id . '/unfollow') }}">
                                @csrf
                                @method('DELETE')
                                <button
                                    style="padding:8px 16px; background:#e0245e; color:white; border:none; border-radius:50px; font-weight:bold; cursor:pointer;">
                                    Dejar de seguir
                                </button>
                            </form>
                        @else
                            <form method="POST" action="{{ url('/profile/' . $id . '/follow') }}">
                                @csrf
                                <button
                                    style="padding:8px 16px; background:#1da1f2; color:white; border:none; border-radius:50px; font-weight:bold; cursor:pointer;">
                                    Seguir
                                </button>
                            </form>
                        @endif
                    @else
                        <button onclick="openModal()"
                            style="padding:8px 16px; background:#1da1f2; color:white; border:none; border-radius:50px; font-weight:bold; cursor:pointer;">
                            Editar perfil
                        </button>
                    @endif
                </div>
            </div>

            <div>
                <p style="color:#555;">{{ $profile['bio'] ?? 'Esta persona no ha agregado biografía.' }}</p>
            </div>

            <div style="display:flex; gap:20px; font-weight:bold; color:#555;">
                <div><span style="color:black;">{{ $profile['followers_count'] ?? 0 }}</span> Seguidores</div>
                <div><span style="color:black;">{{ $profile['following_count'] ?? 0 }}</span> Siguiendo</div>
            </div>
        </div>

        <div class="profile-posts scrollable" style="margin-top:20px;">
            <h3 style="margin-bottom:10px;">Mis publicaciones</h3>

            @forelse($posts ?? [] as $post)
                <div class="card" style="border-radius:15px; padding:15px; margin-bottom:10px; position:relative;">
                    <div class="username" style="font-weight:bold;">{{ $profile['username'] }}</div>
                    <p style="margin:5px 0;">{{ $post['content'] }}</p>
                    <small style="color:gray;">Publicado el
                        {{ \Carbon\Carbon::parse($post['created_at'])->format('d/m/Y H:i') }}</small>

                    @if ($post['id_user'] == session('user_id'))
                        <form action="{{ url('/post/delete/' . $post['id']) }}" method="POST"
                            style="position:absolute; top:10px; right:10px;">
                            @csrf
                            @method('DELETE')
                            <button type="submit"
                                style="border:none; color:white; border-radius:50%; width:30px; height:30px; cursor:pointer;"
                                title="Eliminar publicación">🗑️</button>
                        </form>
                    @endif
                </div>
            @empty
                <p style="color:gray;">No has publicado nada todavía.</p>
            @endforelse
        </div>
    </div>

    <div id="editModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>

            <h3>Editar perfil</h3>

            <form method="POST" action="{{ url('/profile/update') }}">
                @csrf
                @method('PUT')

                <label>Username</label>
                <input type="text" name="username" value="{{ $profile['username'] }}" required>

                <label>Biografía</label>
                <textarea name="bio" rows="3" style="resize: none">{{ $profile['bio'] ?? '' }}</textarea>

                <button type="submit" class="btn">Guardar cambios</button>
            </form>

            <hr style="margin:20px 0;">

            <form method="POST" action="{{ url('/profile/delete') }}"
                onsubmit="return confirm('¿Seguro que quieres eliminar tu cuenta? Esta acción no se puede deshacer.')">
                @csrf
                @method('DELETE')

                <button type="submit" class="btn danger">
                    Eliminar cuenta
                </button>
            </form>
        </div>
    </div>

    <script>
        function openModal() {
            document.getElementById('editModal').style.display = 'flex';
        }

        function closeModal() {
            document.getElementById('editModal').style.display = 'none';
        }

        window.onclick = function(event) {
            const modal = document.getElementById('editModal');
            if (event.target === modal) {
                modal.style.display = "none";
            }
        }
    </script>
@endsection
