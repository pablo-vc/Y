<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Http;
use Illuminate\Http\Request;
use App\Services\ApiService;

class AuthController extends Controller
{
    private $api;

    public function __construct(ApiService $api)
    {
        $this->api = $api;
    }

    public function showLogin()
    {
        return view('login');
    }

    public function login(Request $request)
    {
        $response = $this->api->login(
            $request->email,
            $request->password
        );

        if ($response->successful()) {

            $user = $response->json();

            session([
                'user_id' => $user['id'],
                'username' => $user['username']
            ]);

            return redirect('/feed');
        }

        return back()->with('error', 'Credenciales incorrectas');
    }

    public function register(Request $request)
    {
        $response = $this->api->register(
            $request->only(['username', 'email', 'password'])
        );

        if ($response->successful()) {
            return $this->login($request);
        }

        return back()->with('error', 'Error al registrar');
    }

    public function logout()
    {
        session()->flush();
        return redirect('/');
    }
}
