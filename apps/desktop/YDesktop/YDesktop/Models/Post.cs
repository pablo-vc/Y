using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace YDesktop.Models
{
    public class Post
    {
        public int Id { get; set; }
        public int Id_user { get; set; }
        public string Content { get; set; }
        public string Created_at { get; set; }
        public string Username { get; set; }

        public Post() { }

    }
}
