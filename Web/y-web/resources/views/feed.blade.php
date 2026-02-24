@extends('layouts.app')
<title>Y | Feed</title>

@section('content')

<div class="tabs">
    <div class="tab active" onclick="showTab('all')">Todas</div>
    <div class="tab" onclick="showTab('following')">Siguiendo</div>
</div>

<div id="all-tab">
    @forelse($allPosts ?? [] as $post)
        <div class="card">
            <div class="username">
                <a href="{{ url('/profile/' . $post['id_user']) }}" 
                   style="text-decoration:none; color:black;">
                    {{ $post['username'] }}
                </a>
            </div>
            <div>{{ $post['content'] }}</div>
        </div>
    @empty
        <p>No hay publicaciones.</p>
    @endforelse
</div>

<div id="following-tab" style="display:none;">
    @forelse($followingPosts ?? [] as $post)
        <div class="card">
            <div class="username">
                <a href="{{ url('/profile/' . $post['id_user']) }}" 
                   style="text-decoration:none; color:black;">
                    {{ $post['username'] }}
                </a>
            </div>
            <div>{{ $post['content'] }}</div>
        </div>
    @empty
        <p>No sigues a nadie.</p>
    @endforelse
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