<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Services\ApiService;


class PostController extends Controller
{
    private $api;

    public function __construct(ApiService $api)
    {
        $this->api = $api;
    }

    public function create()
    {
        return view('post');
    }

    public function store(Request $request)
    {
        $response = $this->api->createPost([
            'id_user' => session('user_id'),
            'content' => $request->content
        ]);

        if ($response->successful()) {
            return redirect('/feed');
        }

        return back()->with('error', 'No se pudo crear el post');
    }
    public function destroy($id)
    {
        $this->api->deletePost($id);
        return redirect('/profile');
    }
}
