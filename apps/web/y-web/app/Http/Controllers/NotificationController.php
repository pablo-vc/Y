<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Http;
use Illuminate\Http\Request;
use App\Services\ApiService;

class NotificationController extends Controller
{
    private $api;

    public function __construct(ApiService $api)
    {
        $this->api = $api;
    }

    public function index()
    {
        $userId = session('user_id');

        $notifications = $this->api->getNotifications($userId)->json();

        return view('notifications', compact('notifications'));
    }
}
