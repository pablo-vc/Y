using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using YDesktop.Models;
using YDesktop.Services;

namespace YDesktop.Forms.Controls
{
    public partial class EditProfileForm : Form
    {
        private readonly ApiService api = ApiService.Instance;
        public EditProfileForm()
        {
            InitializeComponent();
            initUI();
            setupEvents();
        }
        private void initUI()
        {
            this.Size = new Size(700, 600);
            this.StartPosition = FormStartPosition.CenterScreen;
            this.BackColor = Color.FromArgb(240, 242, 245);

            toolTip.AutoPopDelay = 5000;
            toolTip.InitialDelay = 200;
            toolTip.ReshowDelay = 200;
            toolTip.ShowAlways = true;

            panel.Size = new Size(500, 500);
            panel.BackColor = Color.White;
            panel.BorderStyle = BorderStyle.FixedSingle;
            CenterPanel();

            txtUsername.Text = Session.CurrentUser.Username;
            txtUsername.Width = 300;
            txtUsername.Location = new Point(100, 50);
            txtUsername.Font = new Font("Segoe UI", 10);

            txtBio.Text = Session.CurrentUser.Bio;
            txtBio.Width = 300;
            txtBio.Height = 120;
            txtBio.Location = new Point(100, 100);
            txtBio.Font = new Font("Segoe UI", 10);

            txtEmail.Text = Session.CurrentUser.Email;
            txtEmail.Width = 300;
            txtEmail.Location = new Point(100, 240);
            txtEmail.Font = new Font("Segoe UI", 10);

            btnCancel.Location = new Point(100, 300);

            btnSave.Location = new Point(280, 300);

            btnDeleteAccount.Location = new Point(185, 370);
        }


        private void setupEvents()
        {
            this.Resize += (s, e) => CenterPanel();
            foreach (Control ctrl in panel.Controls)
            {
                if (ctrl.GetType() == typeof(TextBox))
                {
                    string fieldName = ctrl.Name.Substring(3);
                    toolTip.SetToolTip(ctrl, $"Enter your {fieldName.ToLower()}");
                }
                if (ctrl is Button btn)
                {
                    ConfigureButton(btn);
                }
            }

        }
        private void CenterPanel()
        {
            panel.Left = (this.ClientSize.Width - panel.Width) / 2;
            panel.Top = (this.ClientSize.Height - panel.Height) / 2;
        }
        private void ConfigureButton(Button btn)
        {
            btn.FlatStyle = FlatStyle.Flat;
            btn.FlatAppearance.BorderSize = 0;
            btn.BackColor = Color.FromArgb(29, 161, 242);
            btn.ForeColor = Color.White;
            btn.Cursor = Cursors.Hand;
            btn.Width = 120;
            btn.Height = 40;

            RoundButton(btn, 40);

            if (btn.Name.Contains("Delete"))
            {
                btn.BackColor = Color.FromArgb(220, 53, 69);
            }

            btn.Resize += (s, e) => RoundButton(btn, btn.Height);
            btn.MouseEnter += (s, e) =>
                btn.BackColor = Color.FromArgb(26, 140, 216);
            btn.MouseLeave += (s, e) =>
                btn.BackColor = Color.FromArgb(29, 161, 242);
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

        private async void btnSave_Click(object sender, EventArgs e)
        {
            var username = txtUsername.Text.Trim();
            var bio = txtBio.Text.Trim();
            var email = txtEmail.Text.Trim();

            // VALIDACIÓN
            if (string.IsNullOrEmpty(username) || string.IsNullOrEmpty(email))
            {
                MessageBox.Show("Username and email are required");
                return;
            }
            if (username == Session.CurrentUser.Username &&
                bio == Session.CurrentUser.Bio &&
                email == Session.CurrentUser.Email)
            {
                MessageBox.Show("No changes detected");
                return;
            }

            btnSave.Enabled = false;
            btnSave.Text = "Saving...";

            try
            {
                var update = new UserUpdate(username, email, bio);

                var response = await api.UpdateUser(Session.CurrentUser.Id, update);

                if (response.IsSuccessStatusCode)
                {
                    Session.CurrentUser.Username = username;
                    Session.CurrentUser.Email = email;
                    Session.CurrentUser.Bio = bio;

                    MessageBox.Show("Profile updated successfully");

                    this.DialogResult = DialogResult.OK;
                    this.Close();
                }
                else
                {
                    var error = await response.Content.ReadAsStringAsync();
                    MessageBox.Show("Error: " + error);
                }
            }
            catch
            {
                MessageBox.Show("Error connecting to server");
            }

            btnSave.Enabled = true;
            btnSave.Text = "Save";
        }

        private async void btnDeleteAccount_Click(object sender, EventArgs e)
        {
            var confirm = MessageBox.Show(
                "This will delete your account permanently. Continue?",
                "Warning",
                MessageBoxButtons.YesNo
            );

            if (confirm == DialogResult.Yes)
            {

                var response = await api.DeleteUser(Session.CurrentUser.Id);

                if (response.IsSuccessStatusCode)
                {
                    Session.CurrentUser = null;

                    new LoginForm().Show();

                    Application.OpenForms["MainForm"]?.Close();

                    this.Close();
                }
                else
                {
                    MessageBox.Show("Error deleting account");
                }
            }
        }
    }
}
