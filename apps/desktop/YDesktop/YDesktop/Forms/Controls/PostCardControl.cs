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
using System.Xml;
using YDesktop.Models;
using YDesktop.Services;

namespace YDesktop.Forms.Controls
{
    public partial class PostCardControl : UserControl
    {
        private Post post;

        private Color normalColor = Color.White;
        private Color hoverColor = Color.FromArgb(245, 248, 250);
        private bool showDelete = false;

        public PostCardControl(Post post, bool showDelete = false)
        {
            InitializeComponent();
            this.post = post;
            this.showDelete = showDelete;
            SetupUI();
            BindData();
            SetupEvents();
        }

        private void SetupUI()
        {
            this.Height = 100;
            this.BackColor = normalColor;
            this.Cursor = Cursors.Hand;

            panelAvatar.Width = 40;
            panelAvatar.Height = 40;
            panelAvatar.Location = new Point(10, 10);
            panelAvatar.BackColor = GetColorFromUsername(post.Username);

            lblInitial.Font = new Font("Segoe UI", 14, FontStyle.Bold);
            lblInitial.ForeColor = Color.White;
            lblInitial.TextAlign = ContentAlignment.MiddleCenter;
            lblInitial.Location = new Point(9, 7);

            lblUsername.Font = new Font("Segoe UI", 10, FontStyle.Bold);
            lblUsername.ForeColor = Color.Black;
            lblUsername.Location = new Point(60, 10);

            lblContent.Font = new Font("Segoe UI", 10);
            lblContent.ForeColor = Color.Black;
            lblContent.MaximumSize = new Size(450, 0);
            lblContent.AutoSize = true;
            lblContent.Location = new Point(60, 30);

            lblDate.Font = new Font("Segoe UI", 9);
            lblDate.ForeColor = Color.Gray;
            lblDate.Location = new Point(60, 60);

            btnDelete.Text = "X";
            btnDelete.Size = new Size(30, 25);
            btnDelete.Location = new Point(this.Width - btnDelete.Width - 10, 10);

            btnDelete.FlatStyle = FlatStyle.Flat;
            btnDelete.FlatAppearance.BorderSize = 0;
            btnDelete.BackColor = Color.Transparent;
            btnDelete.ForeColor = Color.Red;
            btnDelete.Cursor = Cursors.Hand;
            btnDelete.Anchor = AnchorStyles.Top | AnchorStyles.Right;
            btnDelete.Visible = false;
            if (post.Id_user == Session.CurrentUser.Id && showDelete)
            {
                btnDelete.Visible = true;
            }

            panelDivider.BackColor = Color.FromArgb(230, 230, 230);
        }

        private void BindData()
        {
            string username = post.Username ?? "User";

            lblInitial.Text = username.Substring(0, 1).ToUpper();
            lblUsername.Text = username;
            lblContent.Text = post.Content;

            lblDate.Text = FormatDate(post.Created_at);
        }

        private void SetupEvents()
        {
            this.MouseEnter += OnHover;
            this.MouseLeave += OnLeave;
            this.Click += (s, e) => OpenProfile();

            foreach (Control ctrl in this.Controls)
            {
                ctrl.MouseEnter += OnHover;
                ctrl.MouseLeave += OnLeave;
                if (ctrl != btnDelete)
                {
                    ctrl.Click += (s, e) => this.OnClick(EventArgs.Empty);
                }
            }

            panelAvatar.Paint += (s, e) =>
            {
                var g = new System.Drawing.Drawing2D.GraphicsPath();
                g.AddEllipse(0, 0, panelAvatar.Width - 1, panelAvatar.Height - 1);
                panelAvatar.Region = new Region(g);
            };

        }
        private void OpenProfile()
        {
            var parent = this.FindForm() as MainForm;

            parent?.OpenProfile(post.Id_user);
        }
        private async void btnDelete_Click(object sender, EventArgs e)
        {
            var confirm = MessageBox.Show(
            "Delete this post?",
            "Confirm",
            MessageBoxButtons.YesNo
            );

            if (confirm == DialogResult.Yes)
            {
                btnDelete.Enabled = false;
                var api = ApiService.Instance;

                var response = await api.DeletePost(post.Id);

                if (response.IsSuccessStatusCode)
                {
                    this.Parent.Controls.Remove(this);
                    this.Dispose();
                }
                else
                {
                    MessageBox.Show("Error deleting post");
                    btnDelete.Enabled = true;
                }
            }
        }

        private void OnHover(object sender, EventArgs e)
        {
            this.BackColor = hoverColor;
        }

        private void OnLeave(object sender, EventArgs e)
        {
            this.BackColor = normalColor;
        }

        private Color GetColorFromUsername(string username)
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

    }
}
