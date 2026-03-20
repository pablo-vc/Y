<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Http;
use Illuminate\Http\Request;
use App\Services\ApiService;

class ProfileController extends Controller
{
    private $api;

    public function __construct(ApiService $api)
    {
        $this->api = $api;
    }

    public function index()
    {
        $userId = session('user_id');

        $profile = $this->api->getUserProfile($userId)->json();
        $posts = $this->api->getUserPosts($userId)->json();
        $followers = $this->api->getFollowers($userId)->json();
        $following = $this->api->getFollowing($userId)->json();

        $profile['followers_count'] = $followers;
        $profile['following_count'] = $following;

        return view('profile', compact('profile', 'posts'));
    }

    public function update(Request $request)
    {
        $userId = session('user_id');
        session([
            'username' => $request['username']
        ]);

        $this->api->updateProfile($userId, [
            'username' => $request->username,
            'email'=> $request->email,
            'bio' => $request->bio,
        ]);

        return redirect('/profile');
    }

    public function destroy()
    {
        $userId = session('user_id');

        $this->api->deleteAccount($userId);

        session()->flush();

        return redirect('/');
    }

    public function show($id)
    {
        $currentUserId = session('user_id');

        $profile = $this->api->getUserProfile($id)->json();
        $posts = $this->api->getUserPosts($id)->json();

        $followers = $this->api->getFollowers($id)->json();
        $following = $this->api->getFollowing($id)->json();

        $profile['followers_count'] = $followers;
        $profile['following_count'] = $following;

        $isFollowing = false;

        if ($currentUserId != $id) {
            $isFollowing = $this->api
                ->isFollowing($currentUserId, $id)
                ->json();
        }

        return view('profile', compact('profile', 'posts', 'isFollowing', 'id'));
    }

    public function follow($id)
    {
        $currentUserId = session('user_id');

        $this->api->followUser($currentUserId, $id);

        return back();
    }

    public function unfollow($id)
    {
        $currentUserId = session('user_id');

        $this->api->unfollowUser($currentUserId, $id);

        return back();
    }
}
