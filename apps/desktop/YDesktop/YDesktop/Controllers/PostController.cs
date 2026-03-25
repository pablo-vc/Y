using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using YDesktop.Models;

namespace YDesktop.ApiCalls
{
    internal class PostController
    {
        private static string BASE_URL = "http://127.0.0.1:8080/yapi/rest/posts";
        private static readonly HttpClient client = new HttpClient();

        private static readonly JsonSerializerOptions options = new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true
        };

        public static async Task<List<Post>> GetPostsAsync()
        {
            var response = await client.GetAsync(BASE_URL);

            if (!response.IsSuccessStatusCode)
                return new List<Post>();

            var json = await response.Content.ReadAsStringAsync();
            return JsonSerializer.Deserialize<List<Post>>(json, options) ?? new List<Post>();
        }

        public static async Task<List<Post>> GetUserPostsAsync(int id_user)
        {
            var response = await client.GetAsync(BASE_URL + "/" + id_user);

            if (!response.IsSuccessStatusCode)
                return new List<Post>();

            var json = await response.Content.ReadAsStringAsync();
            return JsonSerializer.Deserialize<List<Post>>(json, options) ?? new List<Post>();
        }

        public static async Task<List<Post>> GetFollowingPostsAsync(int id_user)
        {
            var response = await client.GetAsync(BASE_URL + "/following/" + id_user);

            if (!response.IsSuccessStatusCode)
                return new List<Post>();

            var json = await response.Content.ReadAsStringAsync();
            return JsonSerializer.Deserialize<List<Post>>(json, options) ?? new List<Post>();
        }

        public static async Task<bool> CreatePostAsync(int id_user, string content)
        {
            var data = new
            {
                id_user = id_user,
                content = content
            };

            var json = JsonSerializer.Serialize(data);
            var body = new StringContent(json, Encoding.UTF8, "application/json");

            var response = await client.PostAsync(BASE_URL + "/create", body);

            return response.IsSuccessStatusCode;
        }

        public static async Task<HttpResponseMessage> DeletePostAsync(int id)
        {
            return await client.DeleteAsync(BASE_URL + "/delete/" + id);
        }
    }
}
