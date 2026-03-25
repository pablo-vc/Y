using System.Text.Json.Serialization;

namespace YDesktop.Services
{
    internal class LoginRequest
    {
        [JsonPropertyName("email")]
        public string Email { get; set; }

        [JsonPropertyName("password")]
        public string Password { get; set; }

        public LoginRequest(string email, string password)
        {
            Email = email;
            Password = password;
        }
        public LoginRequest() { }
    }
}