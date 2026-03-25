using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using YDesktop.Models;
using YDesktop.Services;

namespace YDesktop.Forms.Controls
{
    public partial class ProfileControl : UserControl
    {
        private readonly ApiService api = ApiService.Instance;

        private int userId;
        private bool isFollowing = false;
        private bool isOwnProfile = false;
        private List<Post> posts = new List<Post>();

        public ProfileControl(int userId)
        {
            this.userId = userId;
            InitializeComponent();
            this.DoubleBuffered = true;
            SetupUI();
        }
        private async void ProfileControl_Load(object sender, EventArgs e)
        {
            await LoadProfile();
        }

        private void SetupUI()
        {
            this.BackColor = Color.FromArgb(240, 242, 245);

            panelHeader.BackColor = Color.White;
            panelHeader.Height = 180;
            panelHeader.Dock = DockStyle.Top;

            picAvatar.Size = new Size(80, 80);
            picAvatar.Location = new Point(20, 20);
            picAvatar.SizeMode = PictureBoxSizeMode.Zoom;
            picAvatar.BackColor = Color.LightGray;

            lblUsername.Font = new Font("Segoe UI", 16, FontStyle.Bold);
            lblUsername.ForeColor = Color.Black;
            lblUsername.AutoSize = true;
            lblUsername.Location = new Point(120, 20);

            lblBio.Font = new Font("Segoe UI", 10);
            lblBio.ForeColor = Color.Gray;
            lblBio.AutoSize = true;
            lblBio.Location = new Point(120, 50);
            lblBio.MaximumSize = new Size(300, 0);

            panelStats.Location = new Point(120, 120);
            panelStats.AutoSize = true;

            lblFollowers.Font = new Font("Segoe UI", 10, FontStyle.Bold);
            lblFollowers.ForeColor = Color.Black;
            lblFollowers.AutoSize = true;
            lblFollowers.Location = new Point(0, 0);


            lblFollowing.Font = new Font("Segoe UI", 10, FontStyle.Bold);
            lblFollowing.ForeColor = Color.Black;
            lblFollowing.AutoSize = true;
            lblFollowing.Location = new Point(120, 0);

            btnAction.Size = new Size(120, 35);
            btnAction.Location = new Point(panelHeader.Width - 10, 20);
            btnAction.BackColor = Color.FromArgb(29, 161, 242);
            btnAction.ForeColor = Color.White;
            btnAction.FlatStyle = FlatStyle.Flat;
            btnAction.Text = "Follow";
            btnAction.FlatAppearance.BorderSize = 0;
            btnAction.Cursor = Cursors.Hand;
            RoundButton(btnAction, btnAction.Height);

            flowPosts.Dock = DockStyle.Fill;
            flowPosts.AutoScroll = true;
            flowPosts.WrapContents = false;
            flowPosts.FlowDirection = FlowDirection.TopDown;
            flowPosts.Padding = new Padding(10);

            flowPosts.BringToFront();
        }

        private async Task LoadProfile()
        {
            try
            {
                var userTask = api.GetUser(userId);
                var followersTask = api.GetFollowersCount(userId);
                var followingTask = api.GetFollowingCount(userId);

                await Task.WhenAll(userTask, followersTask, followingTask);

                var user = await userTask ?? throw new Exception("Usuario not found");
                int followers = await followersTask;
                int following = await followingTask;

                lblUsername.Text = user.Username;
                lblBio.Text = user.Bio;
                lblFollowers.Text = $"{followers} Followers";
                lblFollowing.Text = $"{following} Following";

                isOwnProfile = userId == Session.CurrentUser.Id;

                if (isOwnProfile)
                {
                    SetupEditProfile();
                }
                else
                {
                    await SetupFollowState();
                }

                await LoadUserPosts(user.Id);
            }
            catch (HttpRequestException)
            {
                MessageBox.Show("Couldn't load the profile. Check your connection.",
                                "Network error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            catch (TaskCanceledException)
            {
                MessageBox.Show("Profile load took too much time.",
                                "Timeout", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
                MessageBox.Show("Error while loading the profile.",
                                "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private async Task LoadUserPosts(int userId)
        {
            try
            {
                posts = await api.GetUserPosts(userId);
                paintPosts();
            }
            catch (HttpRequestException)
            {
                MessageBox.Show("Could load the posts.",
                                "Network error", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
                MessageBox.Show("Error while loading the posts.",
                                "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
        private void paintPosts()
        {
            flowPosts.SuspendLayout();
            flowPosts.Controls.Clear();

            foreach (var post in posts)
            {
                var card = new PostCardControl(post, true)
                {
                    Width = flowPosts.Width - 25,
                    Margin = new Padding(0, 0, 0, 10)
                };
                flowPosts.Controls.Add(card);
            }

            flowPosts.ResumeLayout();
        }

        private void SetupEditProfile()
        {
            btnAction.Text = "Edit Profile";
            btnAction.BackColor = Color.FromArgb(29, 161, 242);
            btnAction.ForeColor = Color.White;
        }

        private async Task SetupFollowState()
        {
            try
            {
                isFollowing = await api.IsFollowing(Session.CurrentUser.Id, userId);
                UpdateFollowButton();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
                MessageBox.Show("Couldn't verify the following state.",
                                "Error", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
        }

        private void UpdateFollowButton()
        {
            if (isFollowing)
            {
                btnAction.Text = "Following";
                btnAction.BackColor = Color.LightGray;
                btnAction.ForeColor = Color.Black;
            }
            else
            {
                btnAction.Text = "Follow";
                btnAction.BackColor = Color.FromArgb(29, 161, 242);
                btnAction.ForeColor = Color.White;
            }
        }

        private async Task ToggleFollow()
        {
            btnAction.Enabled = false;
            try
            {
                if (isFollowing)
                {
                    await api.Unfollow(Session.CurrentUser.Id, userId);
                    isFollowing = false;
                }
                else
                {
                    await api.Follow(Session.CurrentUser.Id, userId);
                    isFollowing = true;
                }

                UpdateFollowButton();
                int followers = await api.GetFollowersCount(userId);
                lblFollowers.Text = $"{followers} Followers";
            }
            catch (HttpRequestException)
            {
                MessageBox.Show("Network error. Try again.",
                                "Network", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            catch (InvalidOperationException)
            {
                MessageBox.Show("Couldn't update the following state.",
                                "Invalid operation", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
                MessageBox.Show("An error occurred.",
                                "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            finally
            {
                btnAction.Enabled = true;
            }
        }

        private async void btnAction_Click(object sender, EventArgs e)
        {
            if (isOwnProfile)
            {
                var form = new EditProfileForm();
                if (form.ShowDialog() == DialogResult.OK)
                {
                    await LoadProfile();
                }
            }
            else
            {
                await ToggleFollow();
            }

        }

        protected override void OnResize(EventArgs e)
        {
            base.OnResize(e);
            paintPosts();
        }
        private void RoundButton(Button btn, int radius)
        {
            GraphicsPath path = new GraphicsPath();
            path.StartFigure();

            path.AddArc(0, 0, radius, radius, 180, 90);
            path.AddArc(btn.Width - radius, 0, radius, radius, 270, 90);
            path.AddArc(btn.Width - radius, btn.Height - radius, radius, radius, 0, 90);
            path.AddArc(0, btn.Height - radius, radius, radius, 90, 90);

            path.CloseFigure();

            btn.Region = new Region(path);
        }


    }
}
