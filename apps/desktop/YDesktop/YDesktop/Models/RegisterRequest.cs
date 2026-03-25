using System.Text.Json.Serialization;

namespace YDesktop.Services
{
    public class RegisterRequest
    {
        [JsonPropertyName("username")]
        public string Username { get; set; }
        [JsonPropertyName("email")]
        public string Email { get; set; }
        [JsonPropertyName("password")]
        public string Password { get; set; }

        public RegisterRequest() { }

        public RegisterRequest(string username, string email, string password)
        {
            Username = username;
            Email = email;
            Password = password;
        }
    }
}