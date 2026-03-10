@extends('layouts.app')
<title>Y | Feed</title>
<style>
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

    .username {
        display: flex;
        margin-bottom: 5px;
        align-items: center;
    }

    .username>img {
        width: 20px;
        height: 20px;
        margin-right: 2px;
    }

    .username-text {
        text-decoration: none;
        color: #1da1f2;
    }

    .username-text:hover {
        color: black;
    }

    .content {
        margin: 0 0 5px 4px;
    }
</style>
@section('content')
    <div class="fixed">
        <div class="tabs">
            <div class="tab active" onclick="showTab('all')">Todas</div>
            <div class="tab" onclick="showTab('following')">Siguiendo</div>
        </div>

        <div class="scrollable">
            <div id="all-tab">
                @forelse($allPosts ?? [] as $post)
                    <div class="card">
                        <div class="username">
                            <img src="{{ asset('images/image.png') }}" alt="">
                            <a href="{{ url('/profile/' . $post['id_user']) }}" class="username-text">
                                {{ $post['username'] }}
                            </a>
                        </div>
                        <div class="content">{{ $post['content'] }}</div>
                        <small style="color:gray;">Publicado el
                            {{ \Carbon\Carbon::parse($post['created_at'])->format('d/m/Y H:i') }}</small>
                    </div>
                @empty
                    <p>No hay publicaciones.</p>
                @endforelse
            </div>

            <div id="following-tab" style="display:none;">
                @forelse($followingPosts ?? [] as $post)
                    <div class="card">
                        <div class="username">
                            <img src="{{ asset('images/image.png') }}" alt="">
                            <a href="{{ url('/profile/' . $post['id_user']) }}" class="username-text">
                                {{ $post['username'] }}
                            </a>
                        </div>
                        <div class="content">{{ $post['content'] }}</div>
                        <small style="color:gray;">Publicado el
                            {{ \Carbon\Carbon::parse($post['created_at'])->format('d/m/Y H:i') }}</small>
                    </div>
                @empty
                    <p>No sigues a nadie.</p>
                @endforelse
            </div>
        </div>
    </div>

    <script>
        function showTab(tab) {
            document.getElementById('all-tab').style.display = tab === 'all' ? 'block' : 'none';
            document.getElementById('following-tab').style.display = tab === 'following' ? 'block' : 'none';

            document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
            event.target.classList.add('active');
        }
    </script>
@endsection
