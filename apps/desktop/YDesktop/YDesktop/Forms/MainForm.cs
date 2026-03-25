using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using YDesktop.Forms.Controls;
using YDesktop.Services;

namespace YDesktop.Forms
{
    public enum AppView
    {
        Feed,
        Profile,
        Notifications
    }
    public partial class MainForm : Form
    {
        public MainForm()
        {
            InitializeComponent();
            setupUI();
            setupEvents();
            Navigate(AppView.Feed);
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            setupUI();
            Navigate(AppView.Feed);
        }
        private void setupUI()
        {
            this.MinimumSize = new Size(900, 500);
            this.Size = new Size(1100, 600);
            this.StartPosition = FormStartPosition.CenterScreen;
            this.BackColor = Color.FromArgb(240, 242, 245);

            panelSidebar.Width = 220;
            panelSidebar.Dock = DockStyle.Left;
            panelSidebar.BackColor = Color.White;
            panelSidebar.Padding = new Padding(10, 20, 10, 10);

            StyleSidebarButton(btnFeed);
            StyleSidebarButton(btnProfile);
            StyleSidebarButton(btnNotifications);
            StyleSidebarButton(btnNewPost);

            btnNewPost.BackColor = Color.FromArgb(29, 161, 242);
            btnNewPost.ForeColor = Color.White;
            btnNewPost.Font = new Font("Segoe UI", 10, FontStyle.Bold);
            
            btnLogout.Text = "Logout";
            btnLogout.Height = 40;
            btnLogout.Dock = DockStyle.Bottom;
            btnLogout.FlatStyle = FlatStyle.Flat;
            btnLogout.FlatAppearance.BorderSize = 0;
            btnLogout.BackColor = Color.FromArgb(240, 242, 245);
            btnLogout.Cursor = Cursors.Hand;
            btnLogout.ForeColor = Color.Red;

            panelContent.Dock = DockStyle.Fill;
            panelContent.BackColor = Color.FromArgb(240, 242, 245);
            panelContent.Padding = new Padding(20);
        }

        private void setupEvents()
        {
            btnNewPost.MouseEnter += (s, e) =>
            {
                btnNewPost.BackColor = Color.FromArgb(26, 145, 218);
            };
            btnNewPost.MouseLeave += (s, e) =>
            {
                btnNewPost.BackColor = Color.FromArgb(29, 161, 242);
            };

            btnLogout.MouseEnter += (s, e) =>
                btnLogout.BackColor = Color.FromArgb(220, 220, 220);

            btnLogout.MouseLeave += (s, e) =>
                btnLogout.BackColor = Color.FromArgb(240, 242, 245);
        }
        private void StyleSidebarButton(Button btn)
        {
            btn.FlatStyle = FlatStyle.Flat;
            btn.FlatAppearance.BorderSize = 0;
            btn.Height = 45;
            btn.Width = 180;

            btn.TextAlign = ContentAlignment.MiddleLeft;
            btn.Padding = new Padding(10, 0, 0, 0);

            btn.Font = new Font("Segoe UI", 10, FontStyle.Regular);
            btn.BackColor = Color.White;
            btn.ForeColor = Color.Black;

            btn.Cursor = Cursors.Hand;
            btn.MouseEnter += (s, e) =>
            {
                btn.BackColor = Color.FromArgb(245, 248, 250);
            };

            btn.MouseLeave += (s, e) =>
            {
                btn.BackColor = Color.White;
            };
        }

        private void Navigate(AppView view)
        {
            panelContent.Controls.Clear();
            ResetButtons();
            UserControl control = null;
            switch (view)
            {
                case AppView.Feed:
                    control = new FeedControl();
                    this.Text = "Feed";
                    btnFeed.ForeColor = Color.FromArgb(29, 161, 242);
                    break;
                case AppView.Profile:
                    control = new ProfileControl(Session.CurrentUser.Id);
                    this.Text = "Profile";
                    btnProfile.ForeColor = Color.FromArgb(29, 161, 242);
                    break;
                case AppView.Notifications:
                    control = new NotificationsControl();
                    this.Text = "Notifications";
                    btnNotifications.ForeColor = Color.FromArgb(29, 161, 242);
                    break;
                default:
                    control = new FeedControl();
                    this.Text = "Feed";
                    btnFeed.ForeColor = Color.FromArgb(29, 161, 242);
                    break;
            }
            control.Dock = DockStyle.Fill;
            panelContent.Controls.Add(control);
        }

        private void btnFeed_Click(object sender, EventArgs e)
        {
            Navigate(AppView.Feed);
        }

        private void btnProfile_Click(object sender, EventArgs e)
        {
            Navigate(AppView.Profile);
        }

        private void btnNotifications_Click(object sender, EventArgs e)
        {
            Navigate(AppView.Notifications);
        }

        private void btnNewPost_Click(object sender, EventArgs e)
        {
            var form = new CreatePostForm();
            if (form.ShowDialog() == DialogResult.OK)
            {
                Navigate(AppView.Feed);
            }
        }

        public void OpenProfile(int userId)
        {
            panelContent.Controls.Clear();

            var control = new ProfileControl(userId);
            control.Dock = DockStyle.Fill;

            panelContent.Controls.Add(control);
        }

        private void ResetButtons()
        {
            btnFeed.ForeColor = Color.Black;
            btnProfile.ForeColor = Color.Black;
            btnNotifications.ForeColor = Color.Black;
        }

        private void btnLogout_Click(object sender, EventArgs e)
        {
            var confirm = MessageBox.Show(
        "Are you sure you want to logout?",
        "Logout",
        MessageBoxButtons.YesNo
    );

            if (confirm == DialogResult.Yes)
            {
                Session.CurrentUser = null;

                LoginForm login = new LoginForm();
                login.Show();

                this.Close();
            }
        }
    }
}
