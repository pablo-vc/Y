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
using static System.Net.Mime.MediaTypeNames;

namespace YDesktop.Forms
{
    public partial class RegisterForm : Form
    {

        public RegisterForm()
        {
            InitializeComponent();
            InitializeUI();
            setupEvents();
        }

        private void InitializeUI()
        {
            this.Text = "Register";
            this.Size = new Size(500, 650);
            this.StartPosition = FormStartPosition.CenterScreen;
            this.BackColor = Color.FromArgb(240, 242, 245);

            toolTip.AutoPopDelay = 5000;
            toolTip.InitialDelay = 200;
            toolTip.ReshowDelay = 200;
            toolTip.ShowAlways = true;

            panel.Size = new Size(350, 500);
            panel.BackColor = Color.White;
            panel.BorderStyle = BorderStyle.FixedSingle;
            CenterPanel();

            pbLogo.AutoSize = false;
            pbLogo.Width = panel.Width;
            pbLogo.Height = 80;
            pbLogo.Location = new Point(0, 20);

            txtUsername.Text = "Username";
            txtUsername.Width = 250;
            txtUsername.Location = new Point(50, 120);
            txtUsername.Font = new Font("Segoe UI", 10);

            txtEmail.Text = "Email";
            txtEmail.Width = 250;
            txtEmail.Location = new Point(50, 170);
            txtEmail.Font = new Font("Segoe UI", 10);

            txtPassword.Text = "Password";
            txtPassword.Width = 250;
            txtPassword.Location = new Point(50, 220);
            txtPassword.Font = new Font("Segoe UI", 10);
            txtPassword.UseSystemPasswordChar = false;

            txtConfirmPassword.Text = "ConfirmPassword";
            txtConfirmPassword.Width = 250;
            txtConfirmPassword.Location = new Point(50, 270);
            txtConfirmPassword.Font = new Font("Segoe UI", 10);
            txtConfirmPassword.UseSystemPasswordChar = false;

            btnRegister.Text = "Create Account";
            btnRegister.Width = 250;
            btnRegister.Height = 40;
            btnRegister.Location = new Point(50, 320);
            btnRegister.BackColor = Color.FromArgb(29, 161, 242);
            btnRegister.ForeColor = Color.White;
            btnRegister.FlatStyle = FlatStyle.Flat;
            btnRegister.FlatAppearance.BorderSize = 0;
            btnRegister.Cursor = Cursors.Hand;

            linkLogin.Text = "Already have an account? Login";
            linkLogin.AutoSize = true;
            linkLogin.Location = new Point(70, 370);
        }

        private void setupEvents()
        {
            this.Resize += (s, e) => CenterPanel();

            foreach (Control ctrl in panel.Controls)
            {
                if (ctrl is TextBox txt)
                {
                    string fieldName = txt.Name.Substring(3);
                    toolTip.SetToolTip(txt, fieldName != "ConfirmPassword" ? $"Enter your {fieldName.ToLower()}" :
                        "Confirm your password");
                    txt.Enter += (s, e) =>
                    {
                        if (txt.Text == fieldName)
                        {
                            txt.Text = "";
                            if (fieldName.Contains("Password"))
                            {
                                txt.UseSystemPasswordChar = true;
                            }
                        }
                    };
                    txt.Leave += (s, e) =>
                    {
                        if (string.IsNullOrEmpty(txt.Text.Trim()))
                        {
                            txt.Text = fieldName != "ConfirmPassword" ? fieldName.ToLower() :
                            "Confirm your password";
                            if (fieldName.Contains("Password"))
                            {
                                txt.UseSystemPasswordChar = false;
                            }
                        }
                    };
                }
            }

            btnRegister.MouseEnter += (s, e) =>
                btnRegister.BackColor = Color.FromArgb(26, 140, 216);

            btnRegister.MouseLeave += (s, e) =>
                btnRegister.BackColor = Color.FromArgb(29, 161, 242);

            linkLogin.Click += (s, e) =>
            {
                new LoginForm().Show();
                this.Close();
            };
        }
        private void CenterPanel()
        {
            panel.Left = (this.ClientSize.Width - panel.Width) / 2;
            panel.Top = (this.ClientSize.Height - panel.Height) / 2;
        }
        private bool validation()
        {
            var username = txtUsername.Text.Trim();
            var email = txtEmail.Text.Trim();
            var password = txtPassword.Text;
            var confirm = txtConfirmPassword.Text;

            if (string.IsNullOrEmpty(username) ||
                string.IsNullOrEmpty(email) ||
                string.IsNullOrWhiteSpace(password) ||
                string.IsNullOrWhiteSpace(confirm))
            {
                MessageBox.Show("Please fill all fields");
                return false;
            }
            if (!email.Contains("@"))
            {
                MessageBox.Show("Invalid email");
                return false;
            }
            if (password.Length < 6)
            {
                MessageBox.Show("Password must be at least 6 characters");
                return false;
            }
            if (password != confirm)
            {
                MessageBox.Show("Passwords do not match");
                return false;
            }
            return true;
        }
        private async void btnRegister_Click(object sender, EventArgs e)
        {
            if (validation())
            {
                var username = txtUsername.Text.Trim();
                var email = txtEmail.Text.Trim();
                var password = txtPassword.Text;
                var confirm = txtConfirmPassword.Text;
                btnRegister.Enabled = false;
                btnRegister.Text = "Creating...";
                try
                {
                    var api = ApiService.Instance;
                    var req = new RegisterRequest(username, email, password);

                    var response = await api.Register(req);
                    if (response.IsSuccessStatusCode)
                    {
                        var user = await api.Login(new LoginRequest(email, password));
                        if (user != null)
                        {
                            Session.CurrentUser = user;

                            MessageBox.Show("Account created!");

                            MainForm main = new MainForm();
                            main.Show();

                            this.Hide();
                            return;
                        }
                        else
                        {
                            MessageBox.Show("Account created, but login failed");
                        }
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
                btnRegister.Enabled = true;
                btnRegister.Text = "Create Account";
            }

        }

    }
}
