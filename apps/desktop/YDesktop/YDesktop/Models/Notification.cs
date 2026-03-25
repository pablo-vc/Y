using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace YDesktop.Models
{
    public class Notification
    {
        public int Id { get; set; }
        public int Id_user { get; set; }
        public int Id_follower { get; set; }
        public string Username { get; set; }
        public string Created_at { get; set; }

        public Notification() { }

    }
}
