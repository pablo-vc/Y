@extends('layouts.app')
<title>Y | Post</title>

@section('content')
    <h2>Crear publicación</h2>

    <form method="POST" action="/post">
        @csrf
        <textarea name="content" rows="4" style="width:100%; resize: none;" required></textarea>
        <br><br>
        <button class="btn" type="submit">Publicar</button>
    </form>
@endsection
