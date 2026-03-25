using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace YDesktop.ApiCalls
{
    internal class FollowerController
    {
        private static string BASE_URL = "http://127.0.0.1:8080/yapi/rest/followers";
        private static readonly HttpClient client = new HttpClient();

        private static readonly JsonSerializerOptions options = new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true
        };

        public static async Task<bool> IsFollowing(int followerId, int followingId)
        {
            var response = await client.GetAsync($"{BASE_URL}/isFollowing/{followerId}/{followingId}");

            if (!response.IsSuccessStatusCode)
            {
                return false;
            }

            var json = await response.Content.ReadAsStringAsync();
            return JsonSerializer.Deserialize<bool>(json);
        }

        public static async Task Follow(int followerId, int followingId)
        {
            var response = await client.PostAsync(
                $"{BASE_URL}/{followerId}/follow/{followingId}", null);

            if (!response.IsSuccessStatusCode)
            {
                var error = await response.Content.ReadAsStringAsync();
                throw new Exception($"Follow failed: {error}");
            }
        }

        public static async Task Unfollow(int followerId, int followingId)
        {
            var response = await client.DeleteAsync(
                $"{BASE_URL}/{followerId}/unfollow/{followingId}");

            if (!response.IsSuccessStatusCode)
            {
                var error = await response.Content.ReadAsStringAsync();
                throw new Exception($"Unfollow failed: {error}");
            }
        }

        public static async Task<int> GetFollowersCount(int userId)
        {
            var response = await client.GetAsync($"{BASE_URL}/{userId}/followersCount");

            if (!response.IsSuccessStatusCode)
            {
                return 0;
            }

            var json = await response.Content.ReadAsStringAsync();
            return JsonSerializer.Deserialize<int>(json);
        }

        public static async Task<int> GetFollowingCount(int userId)
        {
            var response = await client.GetAsync($"{BASE_URL}/{userId}/followingCount");

            if (!response.IsSuccessStatusCode)
            {
                return 0;
            }

            var json = await response.Content.ReadAsStringAsync();
            return JsonSerializer.Deserialize<int>(json);
        }
    }
}
