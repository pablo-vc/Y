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
    internal class NotificationController
    {
        private static string BASE_URL = "http://127.0.0.1:8080/yapi/rest/notifications";
        private static readonly HttpClient client = new HttpClient();

        private static readonly JsonSerializerOptions options = new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true
        };

        public static async Task<List<Notification>> GetUserNotifications(int userId)
        {
            var response = await client.GetAsync($"{BASE_URL}/{userId}");

            if (!response.IsSuccessStatusCode)
                return new List<Notification>();

            var json = await response.Content.ReadAsStringAsync();
            return JsonSerializer.Deserialize<List<Notification>>(json, options)
                   ?? new List<Notification>();
        }
    }
}
