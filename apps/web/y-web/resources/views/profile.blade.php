@extends('layouts.app')
<title>Y | Profile</title>
<style>
    .profile-header {
        display: flex;
        flex-direction: column;
        gap: 15px;
        padding: 20px;
        border-bottom: 1px solid #1da1f2;
    }

    .username-and-btn {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .header-username {
        margin: 0;
        font-size: 24px;
    }

    .bio-text {
        color: #555;
        word-wrap: break-word;
        padding-right: 20%;
    }


    .header-counters {
        display: flex;
        gap: 20px;
        font-weight: bold;
        color: #7a7676;
    }

    .header-counters>div>span {
        color: black;

    }

    .btn-editProfile {
        padding: 8px 16px;
        background: #1da1f2;
        color: white;
        border: none;
        border-radius: 50px;
        font-weight: bold;
        cursor: pointer;
    }

    .btn-follow {
        padding: 8px 16px;
        background: #1da1f2;
        color: white;
        border: none;
        border-radius: 50px;
        font-weight: bold;
        cursor: pointer;
    }

    .btn-unfollow {
        padding: 8px 16px;
        background: #e0245e;
        color: white;
        border: none;
        border-radius: 50px;
        font-weight: bold;
        cursor: pointer;
    }

    .profile-posts {
        margin-top: 10px;
    }

    .profile-card {
        position: relative;
        border-radius: 15px;
        box-shadow: 1px 0.5px 4px 0.5px rgba(163, 161, 161, 0.671);
    }

    .form-del-post {
        position: absolute;
        top: 10px;
        right: 10px;
    }

    .btn-del-post {
        border: none;
        color: white;
        border-radius: 50%;
        width: 30px;
        height: 30px;
        cursor: pointer;
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
        animation: fadeIn 0.3s ease-in-out;
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
    }
    .modal textarea{
        height: 6em;
    }

    .post-content {
        margin: 5px 0;
        word-wrap: break-word;
        padding-right: 20px;
    }

    .btn-delete {
        padding: 8px 15px;
        border: none;
        color: white;
        cursor: pointer;
        border-radius: 20px;
        background: #e0245e;
    }
</style>
@section('content')

    <div class="fixed">

        <div class="profile-header">
            <div class="username-and-btn">
                <div>
                    <h2 class="header-username">{{ $profile['username'] }}</h2>
                    {{-- <p style="margin:2px 0; color:gray;">{{ $profile['email'] }}</p> --}}
                </div>
                <div>
                    @if (session('user_id') != ($id ?? session('user_id')))
                        @if ($isFollowing ?? false)
                            <form method="POST" action="{{ url('/profile/' . $id . '/unfollow') }}">
                                @csrf
                                @method('DELETE')
                                <button class="btn-unfollow">Dejar de seguir</button>
                            </form>
                        @else
                            <form method="POST" action="{{ url('/profile/' . $id . '/follow') }}">
                                @csrf
                                <button class="btn-follow">Seguir</button>
                            </form>
                        @endif
                    @else
                        <button onclick="openModal()"class="btn-editProfile">Editar perfil</button>
                    @endif
                </div>
            </div>

            <div class="bio-container">
                <p class="bio-text">{{ $profile['bio'] }}</p>
            </div>

            <div class="header-counters">
                <div><span>{{ $profile['followers_count'] ?? 0 }}</span> Seguidores</div>
                <div><span>{{ $profile['following_count'] ?? 0 }}</span> Siguiendo</div>
            </div>
        </div>

        <h3>Publicaciones</h3>
        <div class="profile-posts scrollable">

            @forelse($posts ?? [] as $post)
                <div class="card profile-card">
                    <div class="username">{{ $profile['username'] }}</div>
                    <p class="post-content">{{ $post['content'] }}</p>
                    <small>Publicado el
                        {{ \Carbon\Carbon::parse($post['created_at'])->format('d/m/Y H:i') }}</small>

                    @if ($post['id_user'] == session('user_id'))
                        <form action="{{ url('/post/delete/' . $post['id']) }}" method="POST" class="form-del-post">
                            @csrf
                            @method('DELETE')
                            <button type="submit" class="btn-del-post" title="Eliminar publicación">🗑️</button>
                        </form>
                    @endif
                </div>
            @empty
                <p style="color:gray;">No hay publicaciones todavía.</p>
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
                <input type="text" name="username" value="{{ $profile['username'] }}" required maxlength="20">

                <label>Biografía</label>
                <textarea name="bio" rows="3" maxlength="150">{{ $profile['bio'] ?? '' }}</textarea>

                <button type="submit" class="btn">Guardar cambios</button>
            </form>

            <hr style="margin:20px 0;">

            <form method="POST" action="{{ url('/profile/delete') }}"
                onsubmit="return confirm('¿Seguro que quieres eliminar tu cuenta? Esta acción no se puede deshacer.')">
                @csrf
                @method('DELETE')

                <button type="submit" class="btn-delete">
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
