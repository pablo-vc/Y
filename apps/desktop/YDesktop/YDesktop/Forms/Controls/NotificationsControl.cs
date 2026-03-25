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
    public partial class NotificationsControl : UserControl
    {
        private readonly ApiService api = ApiService.Instance;
        private const int FEED_WIDTH = 600;
        private bool isLoading = false;

        public NotificationsControl()
        {
            InitializeComponent();
            this.DoubleBuffered = true;
            SetupUI();

        }
        private async void NotificationsControl_Load(object sender, EventArgs e)
        {
            await LoadNotifications();
        }
        private void SetupUI()
        {
            this.BackColor = Color.FromArgb(240, 242, 245);

            panelContainer.Dock = DockStyle.Fill;
            panelContainer.BackColor = this.BackColor;

            flowLayoutPanel1.Parent = panelContainer;

            flowLayoutPanel1.AutoSize = false;
            flowLayoutPanel1.WrapContents = false;
            flowLayoutPanel1.FlowDirection = FlowDirection.TopDown;
            flowLayoutPanel1.AutoScroll = true;

            flowLayoutPanel1.Width = FEED_WIDTH;
            flowLayoutPanel1.Height = panelContainer.Height;

            flowLayoutPanel1.Padding = new Padding(0, 10, 0, 10);
            flowLayoutPanel1.Margin = new Padding(0);
            flowLayoutPanel1.BackColor = Color.Transparent;

            // Evento scroll (para lazy load)
            //flowLayoutPanel1.Scroll += FlowLayoutPanel1_Scroll;

            CenterFeed();
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

        private async Task LoadNotifications()
        {
            if (isLoading) return;

            isLoading = true;

            var notifications = await api.GetNotifications(Session.CurrentUser.Id);

            flowLayoutPanel1.SuspendLayout();

            flowLayoutPanel1.Controls.Clear();

            foreach (var notif in notifications)
            {
                var card = new NotificationCardControl(notif);

                card.Width = FEED_WIDTH - 20;
                card.Margin = new Padding(0, 0, 0, 10);

                flowLayoutPanel1.Controls.Add(card);
            }

            flowLayoutPanel1.ResumeLayout();

            isLoading = false;
        }

        private async void FlowLayoutPanel1_Scroll(object sender, ScrollEventArgs e)
        {
            var panel = flowLayoutPanel1;

            if (panel.VerticalScroll.Value + panel.Height >= panel.VerticalScroll.Maximum - 100)
            {
                await LoadNotifications();
            }
        }
    }
}
