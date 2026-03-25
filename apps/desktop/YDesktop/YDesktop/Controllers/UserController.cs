using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows.Forms;
using YDesktop.Models;
using YDesktop.Services;

namespace YDesktop.ApiCalls
{
    internal class UserController
    {
        private static string BASE_URL = "http://127.0.0.1:8080/yapi/rest/users";
        private static readonly HttpClient client = new HttpClient();

        private static readonly JsonSerializerOptions options = new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true
        };

        public static async Task<User> Login(LoginRequest req)
        {
            var url = BASE_URL + "/login";

            var jsonReq = JsonSerializer.Serialize(req);
            var content = new StringContent(jsonReq, Encoding.UTF8, "application/json");

            var response = await client.PostAsync(url, content);

            var responseText = await response.Content.ReadAsStringAsync();

            if (!response.IsSuccessStatusCode)
            {
                return null;
            }

            var options = new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true
            };

            return JsonSerializer.Deserialize<User>(responseText, options);
        }

        public static async Task<HttpResponseMessage> Register(RegisterRequest req)
        {
            var url = BASE_URL + "/register";

            var json = JsonSerializer.Serialize(req);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            return await client.PostAsync(url, content);
        }

        public static async Task<User> GetUser(int id)
        {
            var url = BASE_URL + "/" + id;

            var response = await client.GetAsync(url);

            if (!response.IsSuccessStatusCode)
            {
                return null;
            }

            var json = await response.Content.ReadAsStringAsync();
            return JsonSerializer.Deserialize<User>(json, options);
        }

        public static async Task<HttpResponseMessage> UpdateUserAsync(int id, UserUpdate update)
        {
            var url = BASE_URL + "/update/" + id;
            var data = JsonSerializer.Serialize(update);
            var content = new StringContent(data, Encoding.UTF8, "application/json");
            return await client.PutAsync(url, content);
        }

        public static async Task<HttpResponseMessage> DeleteUserAsync(int id)
        {
            return await client.DeleteAsync(BASE_URL + "/delete/" + id);
        }

    }
}
