@extends('layouts.app')
<title>Y | Post</title>
<style>
    textarea {
        width: 100%;
        border: 1px solid #1da1f2;
        border-radius: 7px;
    }

    textarea::placeholder {
        margin-left: 5px;
        color: #999;
        font-style: italic;
        opacity: 0.7;
    }

    button {
        float: right;
        width: 100px;
        height: 40px;
    }
</style>
@section('content')
    <h2>Crear publicación</h2>

    <form method="POST" action="/post">
        @csrf
        <textarea name="content" rows="4" maxlength="350" required placeholder=" ¿Que estás pensando?"></textarea>
        <br><br>
        <button class="btn" type="submit">Publicar</button>
    </form>
@endsection
