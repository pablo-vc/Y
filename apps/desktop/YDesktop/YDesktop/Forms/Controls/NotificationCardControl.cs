using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using YDesktop.Models;

namespace YDesktop.Forms.Controls
{
    public partial class NotificationCardControl : UserControl
    {
        private Notification notification;

        private Color normalColor = Color.White;
        private Color hoverColor = Color.FromArgb(245, 248, 250);

        public NotificationCardControl(Notification notification)
        {
            InitializeComponent();
            this.notification = notification;

            SetupUI();
            BindData();
            SetupEvents();
        }

        private void SetupUI()
        {
            this.Height = 100;
            this.BackColor = normalColor;
            this.Cursor = Cursors.Hand;

            panelAvatar.Size = new Size(40, 40);
            panelAvatar.Location = new Point(10, 15);
            panelAvatar.BackColor = GetColorUsername(notification.Username);

            lblInitial.Font = new Font("Segoe UI", 14, FontStyle.Bold);
            lblInitial.ForeColor = Color.White;
            lblInitial.TextAlign = ContentAlignment.MiddleCenter;
            lblInitial.Location = new Point(9, 7);

            lblText.Location = new Point(60, 25);
            lblText.AutoSize = true;
            lblText.MaximumSize = new Size(450, 0);
            lblText.Font = new Font("Segoe UI", 10, FontStyle.Regular);
            lblText.ForeColor = Color.Black;

            lblTime.Location = new Point(60, 60);
            lblTime.AutoSize = true;
            lblTime.Font = new Font("Segoe UI", 8);
            lblTime.ForeColor = Color.Gray;


        }

        private void BindData()
        {
            string username = notification.Username ?? "User";

            lblInitial.Text = username.Substring(0, 1).ToUpper();

            lblText.Text = $"{username} started following you";
            lblTime.Text = FormatDate(notification.Created_at);
        }
        private void SetupEvents()
        {
            this.MouseEnter += OnHover;
            this.MouseLeave += OnLeave;
            this.Click += OnClick;

            foreach (Control ctrl in this.Controls)
            {
                ctrl.MouseEnter += OnHover;
                ctrl.MouseLeave += OnLeave;
                ctrl.Click += OnClick;
            }

            panelAvatar.Paint += (s, e) =>
            {
                var g = new System.Drawing.Drawing2D.GraphicsPath();
                g.AddEllipse(0, 0, panelAvatar.Width - 1, panelAvatar.Height - 1);
                panelAvatar.Region = new Region(g);
            };
        }

        private void OnHover(object sender, EventArgs e)
        {
            this.BackColor = hoverColor;
        }

        private void OnLeave(object sender, EventArgs e)
        {
            this.BackColor = normalColor;
        }

        private void OnClick(object sender, EventArgs e)
        {
            OpenProfile();
        }
        private void OpenProfile()
        {
            var parent = this.FindForm() as MainForm;

            parent?.OpenProfile(notification.Id_follower);
        }
        public static string FormatDate(string dateString)
        {
            if (!DateTime.TryParseExact(
                    dateString,
                    "yyyy-MM-dd HH:mm:ss",
                    CultureInfo.InvariantCulture,
                    DateTimeStyles.None,
                    out DateTime date))
            {
                return dateString;
            }

            var now = DateTime.Now;
            var diff = now - date;
            var culture = new CultureInfo("es-ES");

            if (diff.TotalSeconds < 60)
            {
                return "now";
            }
            if (diff.TotalMinutes < 60)
            {
                return $"{(int)diff.TotalMinutes} min ago";
            }
            if (diff.TotalHours < 24)
            {
                return $"{(int)diff.TotalHours} h ago";
            }
            if (diff.TotalDays < 2)
            {
                return "yesterday";
            }
            if (diff.TotalDays < 7)
            {
                return $"{(int)diff.TotalDays} days ago";
            }
            if (date.Year == now.Year)
            {
                return date.ToString("d MMM", culture);
            }
            return date.ToString("d MMM yyyy", culture);
        }

        private Color GetColorUsername(string username)
        {
            if (string.IsNullOrEmpty(username))
            {
                return Color.Gray;
            }

            int hash = username.GetHashCode();
            Random rnd = new Random(hash);

            return Color.FromArgb(
                255,
                rnd.Next(50, 200),
                rnd.Next(50, 200),
                rnd.Next(50, 200)
            );
        }


    }
}
