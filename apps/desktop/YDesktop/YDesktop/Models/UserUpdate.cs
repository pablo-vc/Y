using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace YDesktop.Models
{
    internal class UserUpdate
    {
        [JsonPropertyName("username")]
        public string Username { get; set; }

        [JsonPropertyName("email")]
        public string Email { get; set; }

        [JsonPropertyName("bio")]
        public string Bio { get; set; }

        public UserUpdate(string username, string email, string bio)
        {
            Username = username;
            Email = email;
            Bio = bio;
        }
        public UserUpdate() { }

    }
}
