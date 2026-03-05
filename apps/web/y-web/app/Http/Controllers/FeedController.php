<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Services\ApiService;
use Illuminate\Support\Facades\Http;

class FeedController extends Controller
{
    private $api;

    public function __construct(ApiService $api)
    {
        $this->api = $api;
    }
    public function index()
    {
        $userId = session('user_id');

        $response = $this->api->getAllPosts();
        $response2 = $this->api->getFollowingPosts($userId);

        $allPosts = $response->json();
        $followingPosts = $response2->json();

        return view('feed', compact('allPosts', 'followingPosts'));
    }
}
