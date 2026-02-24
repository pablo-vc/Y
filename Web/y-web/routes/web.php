<?php

use Illuminate\Support\Facades\Route;

use App\Http\Controllers\AuthController;
use App\Http\Controllers\FeedController;
use App\Http\Controllers\ProfileController;
use App\Http\Controllers\NotificationController;
use App\Http\Controllers\PostController;

Route::get('/', [AuthController::class, 'showLogin']);
Route::post('/login', [AuthController::class, 'login']);
Route::post('/register', [AuthController::class, 'register']);
Route::get('/logout', [AuthController::class, 'logout']);

Route::get('/feed', [FeedController::class, 'index']);

Route::get('/notifications', [NotificationController::class, 'index']);


Route::get('/post', [PostController::class, 'create']);
Route::post('/post', [PostController::class, 'store']);
Route::delete('/post/delete/{id}', [PostController::class, 'destroy']);

Route::get('/profile', [ProfileController::class, 'index']);

Route::put('/profile/update', [ProfileController::class, 'update']);
Route::delete('/profile/delete', [ProfileController::class, 'destroy']);
Route::get('/profile/{id}', [ProfileController::class, 'show']);
Route::post('/profile/{id}/follow', [ProfileController::class, 'follow']);
Route::delete('/profile/{id}/unfollow', [ProfileController::class, 'unfollow']);

