using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using YDesktop.Models;
using YDesktop.Services;

namespace YDesktop.Forms.Controls
{
    public partial class FeedControl : UserControl
    {
        private readonly ApiService api = ApiService.Instance;

        private const int FEED_WIDTH = 600;
        private int currentPage = 0;
        private bool isLoading = false;
        private bool showFollowing = false;


        public FeedControl()
        {
            InitializeComponent();
            this.DoubleBuffered = true;
            SetupUI();
        }

        private async void FeedControl_Load(object sender, EventArgs e)
        {
            await LoadFeed();
        }
        private void SetupUI()
        {
            this.BackColor = Color.FromArgb(240, 242, 245);

            panelContainer.Dock = DockStyle.Fill;
            panelContainer.BackColor = this.BackColor;

            panelContainer.Dock = DockStyle.Fill;
            panelContainer.BackColor = this.BackColor;

            panelTabs.Dock = DockStyle.Top;
            panelTabs.Height = 50;
            panelTabs.BackColor = Color.White;

            tableTabs.Dock = DockStyle.Fill;
            tableTabs.ColumnCount = 2;
            tableTabs.RowCount = 1;

            tableTabs.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 50));
            tableTabs.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 50));


            btnGlobal.Dock = DockStyle.Fill;
            btnGlobal.Text = "Global";
            ConfigTabButton(btnGlobal);
            btnGlobal.ForeColor = Color.FromArgb(29, 161, 242);

            btnFollowing.Dock = DockStyle.Fill;
            btnFollowing.Text = "Following";
            ConfigTabButton(btnFollowing);

            Panel panelFeedWrapper = new Panel();
            panelFeedWrapper.Dock = DockStyle.Fill;
            panelFeedWrapper.BackColor = this.BackColor;

            panelContainer.Controls.Add(panelFeedWrapper);
            panelContainer.Controls.SetChildIndex(panelFeedWrapper, 1);

            flowLayoutPanel1.Parent = panelFeedWrapper;
            flowLayoutPanel1.Dock = DockStyle.None;
            flowLayoutPanel1.Width = FEED_WIDTH;
            flowLayoutPanel1.Anchor = AnchorStyles.Top;
            flowLayoutPanel1.WrapContents = false;
            flowLayoutPanel1.FlowDirection = FlowDirection.TopDown;
            flowLayoutPanel1.AutoScroll = true;
            flowLayoutPanel1.Padding = new Padding(0, 10, 0, 10);
            flowLayoutPanel1.BackColor = Color.Transparent;
            flowLayoutPanel1.ForeColor = Color.FromArgb(29, 161, 242);

            // Evento scroll (para lazy load)
            //flowLayoutPanel1.Scroll += FlowLayoutPanel1_Scroll;

            CenterFeed();
        }
        private void ConfigTabButton(Button btn)
        {
            btn.FlatStyle = FlatStyle.Flat;
            btn.FlatAppearance.BorderSize = 0;
            btn.BackColor = Color.White;
            btn.Font = new Font("Segoe UI", 10, FontStyle.Bold);
            btn.ForeColor = Color.Gray;
            btn.Cursor = Cursors.Hand;

            btn.Click += async (s, e) =>
            {
                showFollowing = !showFollowing;
                UpdateTabs();
                await ReloadFeed();
            };
            btn.MouseEnter += (s, e) =>
                btn.BackColor = Color.FromArgb(230, 230, 230);

            btn.MouseLeave += (s, e) =>
                btn.BackColor = Color.White;
        }
        private async Task LoadFeed()
        {
            if (isLoading) return;

            isLoading = true;

            var posts = await api.GetFeed();

            flowLayoutPanel1.SuspendLayout();

            PaintPosts(posts);

            flowLayoutPanel1.ResumeLayout();

            currentPage++;
            isLoading = false;
        }
        private async Task ReloadFeed()
        {
            flowLayoutPanel1.Controls.Clear();

            List<Post> posts;

            if (showFollowing)
            {
                posts = await api.GetFollowingPosts(Session.CurrentUser.Id);
            }
            else
            {
                posts = await api.GetFeed();
            }
            PaintPosts(posts);
        }
        private void PaintPosts(List<Post> posts)
        {
            foreach (var post in posts)
            {
                var card = new PostCardControl(post);

                card.Width = FEED_WIDTH - 20;
                card.Margin = new Padding(0, 0, 0, 10);

                flowLayoutPanel1.Controls.Add(card);
            }
        }
        private void UpdateTabs()
        {
            if (showFollowing)
            {
                btnFollowing.ForeColor = Color.FromArgb(29, 161, 242);
                btnGlobal.ForeColor = Color.Gray;
            }
            else
            {
                btnGlobal.ForeColor = Color.FromArgb(29, 161, 242);
                btnFollowing.ForeColor = Color.Gray;
            }
        }
        private void CenterFeed()
        {
            flowLayoutPanel1.Left = (panelContainer.Width - FEED_WIDTH) / 2;
            flowLayoutPanel1.Top = 0;
            flowLayoutPanel1.Height = panelContainer.Height;
        }

        protected override void OnResize(EventArgs e)
        {
            base.OnResize(e);
            CenterFeed();
        }

        private async void FlowLayoutPanel1_Scroll(object sender, ScrollEventArgs e)
        {
            var panel = flowLayoutPanel1;

            // cuando estás cerca del final carga más
            if (panel.VerticalScroll.Value + panel.Height >= panel.VerticalScroll.Maximum - 100)
            {
                await LoadFeed();
            }
        }


    }
}
