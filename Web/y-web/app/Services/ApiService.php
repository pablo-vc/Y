<?php

namespace App\Services;

use Illuminate\Support\Facades\Http;

class ApiService
{
    private $baseUrl;

    public function __construct()
    {
        $this->baseUrl = 'http://localhost:8080/yapi/rest';
    }

    public function login($email, $password)
    {
        $response = Http::post($this->baseUrl . '/users/login', [
            'email' => $email,
            'password' => $password,
        ]);

        return $response;
    }

    public function register($data)
    {
        $response = Http::post($this->baseUrl . '/users/register', $data);

        return $response;
    }
    public function createPost($data)
    {
        return Http::post($this->baseUrl . '/posts/create', $data);
    }
    public function getAllPosts()
    {
        return Http::get($this->baseUrl . "/posts");
    }
    public function getFollowingPosts($userId)
    {
        return Http::get($this->baseUrl . "/posts/following/$userId");
    }

    public function getUserProfile($userId)
    {
        return Http::get($this->baseUrl . "/users/$userId");
    }

    public function getUserPosts($userId)
    {
        return Http::get($this->baseUrl . "/posts/$userId");
    }
    public function getFollowers($userId)
    {
        return Http::get($this->baseUrl . "/followers/$userId/followersCount");
    }
    public function getFollowing($userId)
    {
        return Http::get($this->baseUrl . "/followers/$userId/followingCount");
    }

    public function getNotifications($userId)
    {
        return Http::get($this->baseUrl . "/notifications/$userId");
    }
    public function deletePost($postId)
    {
        return Http::delete($this->baseUrl . "/posts/delete/$postId");
    }
    public function updateProfile($userId, $data)
    {
        return Http::put($this->baseUrl . "/users/update/$userId", $data);
    }

    public function deleteAccount($userId)
    {
        return Http::delete($this->baseUrl . "/users/delete/$userId");
    }

    public function followUser($followerId, $followingId)
    {
        return Http::post($this->baseUrl . "/followers/$followerId/follow/$followingId");
    }

    public function unfollowUser($followerId, $followingId)
    {
        return Http::delete($this->baseUrl . "/followers/$followerId/unfollow/$followingId");
    }

    public function isFollowing($followerId, $followingId)
    {
        return Http::get($this->baseUrl . "/followers/isFollowing/$followerId/$followingId");
    }
}
