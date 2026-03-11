@extends('layouts.app')
<title>Y | Post</title>
<style>
    textarea {
        width: 100%;
        margin-bottom: 12px;
        padding: 12px;
        border-radius: 8px;
        border: 1.5px solid #ddd;
        font-size: 14px;
    }

    textarea:focus {
        outline: none;
        border-color: #18aaff;
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
        <textarea name="content" rows="4" maxlength="280" required placeholder="¿Que estás pensando?"></textarea>
        <br><br>
        <button class="btn" type="submit">Publicar</button>
    </form>
@endsection
