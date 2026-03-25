using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using YDesktop.Services;

namespace YDesktop.Forms
{
    public partial class CreatePostForm : Form
    {
        public CreatePostForm()
        {
            InitializeComponent();
            initUI();
        }

        private void initUI()
        {
            this.Size = new Size(600, 400);
            this.StartPosition = FormStartPosition.CenterParent;
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.BackColor = Color.White;
            this.Resize += (s, e) => CenterContent();

            panel.AutoSize = true;
            panel.AutoSizeMode = AutoSizeMode.GrowAndShrink;

            lblTitle.Text = "What's going on?";
            lblTitle.Font = new Font("Segoe UI", 11);
            lblTitle.Location = new Point(0, 0);

            txtContent.Width = 400;
            txtContent.Height = 120;
            txtContent.Multiline = true;
            txtContent.MaxLength = 280;
            txtContent.Font = new Font("Segoe UI", 11);
            txtContent.ForeColor = Color.Black;
            txtContent.Location = new Point(0, 30); 
            txtContent.BorderStyle = BorderStyle.FixedSingle;
            txtContent.BackColor = Color.FromArgb(250, 250, 250);

            btnPost.Text = "Post";
            btnPost.Size = new Size(100, 35);
            btnPost.BackColor = Color.FromArgb(29, 161, 242);
            btnPost.ForeColor = Color.White;
            btnPost.FlatStyle = FlatStyle.Flat;
            btnPost.FlatAppearance.BorderSize = 0;
            btnPost.Font = new Font("Segoe UI", 10, FontStyle.Bold);
            btnPost.Cursor = Cursors.Hand;
            btnPost.Location = new Point(txtContent.Width - btnPost.Width, txtContent.Bottom + 10);

            toolTip = new ToolTip();
            toolTip.SetToolTip(txtContent, "Post content");

            CenterContent();
        }

        private void CenterContent()
        {
            panel.Left = (this.ClientSize.Width - panel.Width) / 2;
            panel.Top = (this.ClientSize.Height - panel.Height) / 2;
        }

        private async void btnPost_Click(object sender, EventArgs e)
        {
            string content = txtContent.Text.Trim();

            if (string.IsNullOrEmpty(content))
            {
                MessageBox.Show("Write something");
                return;
            }
            btnPost.Enabled = false;
            try
            {
                await ApiService.Instance.CreatePost(
                    Session.CurrentUser.Id,
                    content
                );
                MessageBox.Show("Posted!");
                this.Close();
            }
            catch (Exception)
            {
                MessageBox.Show("Error creating post");
            }
        }
    }
}
