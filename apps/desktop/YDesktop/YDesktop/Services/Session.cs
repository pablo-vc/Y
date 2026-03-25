using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using YDesktop.Models;

namespace YDesktop.Services
{
    internal static class Session
    {
        public static User CurrentUser { get; set; }

        public static void SetUser(User user)
        {
            CurrentUser = user;
        }

        public static void Clear()
        {
            CurrentUser = null;
        }

        public static bool IsLogged => CurrentUser != null;
    }
}
