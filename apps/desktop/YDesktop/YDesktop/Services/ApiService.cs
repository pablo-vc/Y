using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using YDesktop.ApiCalls;
using YDesktop.Models;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.ListView;

namespace YDesktop.Services
{
    internal class ApiService
    {
        private static ApiService instance;

        public static ApiService Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new ApiService();
                }

                return instance;
            }
        }
        // USERS
        public async Task<User> Login(LoginRequest req)
        {
            return await UserController.Login(req);
        }

        public async Task<HttpResponseMessage> Register(RegisterRequest req)
        {
            return await UserController.Register(req);
        }

        public async Task<User> GetUser(int id)
        {
            return await UserController.GetUser(id);
        }

        public async Task<HttpResponseMessage> UpdateUser(int id, UserUpdate update)
        {
            return await UserController.UpdateUserAsync(id, update);
        }
        public async Task<HttpResponseMessage> DeleteUser(int id)
        {
            return await UserController.DeleteUserAsync(id);
        }

        // POSTS

        public async Task<List<Post>> GetFeed()
        {
            return await PostController.GetPostsAsync();
        }

        public async Task<List<Post>> GetUserPosts(int id)
        {
            return await PostController.GetUserPostsAsync(id);
        }

        public async Task<List<Post>> GetFollowingPosts(int id)
        {
            return await PostController.GetFollowingPostsAsync(id);
        }

        public async Task<bool> CreatePost(int userId, string content)
        {
            return await PostController.CreatePostAsync(userId, content);
        }

        public async Task<HttpResponseMessage> DeletePost(int id)
        {
            return await PostController.DeletePostAsync(id);
        }

        // FOLLOWERS

        public Task<bool> IsFollowing(int followerId, int followingId)
        {
            return FollowerController.IsFollowing(followerId, followingId);
        }

        public Task Follow(int followerId, int followingId)
        {
            return FollowerController.Follow(followerId, followingId);
        }

        public Task Unfollow(int followerId, int followingId)
        {
            return FollowerController.Unfollow(followerId, followingId);
        }

        public Task<int> GetFollowersCount(int userId)
        {
            return FollowerController.GetFollowersCount(userId);
        }

        public Task<int> GetFollowingCount(int userId)
        {
            return FollowerController.GetFollowingCount(userId);
        }

        // NOTIFICATIONS

        public async Task<List<Notification>> GetNotifications(int userId)
        {
            return await NotificationController.GetUserNotifications(userId);
        }


    }
}
