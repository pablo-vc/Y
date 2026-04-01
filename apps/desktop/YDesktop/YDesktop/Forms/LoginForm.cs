using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Windows.Forms.Design;
using YDesktop.Models;
using YDesktop.Services;
using static System.Collections.Specialized.BitVector32;

namespace YDesktop.Forms
{
    public partial class LoginForm : Form
    {
        private readonly ApiService apiService = ApiService.Instance;
        public LoginForm()
        {
            InitializeComponent();
            initUI();
            setupEvents();
        }
        private void initUI()
        {
            this.Text = "Login";
            this.Size = new Size(500, 600);
            this.StartPosition = FormStartPosition.CenterScreen;
            this.BackColor = Color.FromArgb(240, 242, 245);

            toolTip.AutoPopDelay = 5000;
            toolTip.InitialDelay = 200;
            toolTip.ReshowDelay = 200;
            toolTip.ShowAlways = true;

            panel.Size = new Size(350, 400);
            panel.BackColor = Color.White;
            panel.BorderStyle = BorderStyle.FixedSingle;
            CenterPanel();

            pbLogo.AutoSize = false;
            pbLogo.Width = panel.Width;
            pbLogo.Height = 80;
            pbLogo.Location = new Point(0, 20);

            txtEmail.Text = "Email";
            txtEmail.Width = 250;
            txtEmail.Location = new Point(50, 120);
            txtEmail.Font = new Font("Segoe UI", 10);

            txtPassword.Text = "Password";
            txtPassword.Width = 250;
            txtPassword.Location = new Point(50, 170);
            txtPassword.Font = new Font("Segoe UI", 10);
            txtPassword.UseSystemPasswordChar = false;

            btnLogin.Text = "Login";
            btnLogin.Width = 250;
            btnLogin.Height = 40;
            btnLogin.Location = new Point(50, 230);
            btnLogin.BackColor = Color.FromArgb(29, 161, 242);
            btnLogin.ForeColor = Color.White;
            btnLogin.FlatStyle = FlatStyle.Flat;
            btnLogin.FlatAppearance.BorderSize = 0;
            btnLogin.Cursor = Cursors.Hand;


            linkRegister.Text = "Don't have an account yet? Register";
            linkRegister.AutoSize = true;
            linkRegister.Location = new Point(80, 310);
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
                        if (txt.Text.ToLower() == fieldName.ToLower() || txt.Text == "Confirm your password")
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
                            txt.Text = fieldName != "ConfirmPassword" ? fieldName :
                            "Confirm your password";
                            if (fieldName.Contains("Password"))
                            {
                                txt.UseSystemPasswordChar = false;
                            }
                        }
                    };
                }
            }
            btnLogin.MouseEnter += (s, e) =>
                btnLogin.BackColor = Color.FromArgb(26, 140, 216);

            btnLogin.MouseLeave += (s, e) =>
                btnLogin.BackColor = Color.FromArgb(29, 161, 242);

            linkRegister.Click += (s, e) =>
            {
                new RegisterForm().Show();
                this.Hide();
            };
        }

        private void CenterPanel()
        {
            panel.Left = (this.ClientSize.Width - panel.Width) / 2;
            panel.Top = (this.ClientSize.Height - panel.Height) / 2;
        }

        private async void btnLogin_Click(object sender, EventArgs e)
        {
            string email = txtEmail.Text.Trim();
            string password = txtPassword.Text;

            if (string.IsNullOrEmpty(email) || string.IsNullOrWhiteSpace(password))
            {
                MessageBox.Show("Fill all fields","Validation", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }

            btnLogin.Enabled = false;
            btnLogin.Text = "Logging in...";

            try
            {
                var req = new LoginRequest(email, password);
                var user = await apiService.Login(req);
                if (user == null)
                {
                    MessageBox.Show("Wrong email or password", "Login failed", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
                Session.CurrentUser = user;

                MainForm main = new MainForm();
                main.Show();
                this.Hide();

                this.FormClosed += (s, args) => this.Dispose();
            }
            catch (HttpRequestException)
            {
                MessageBox.Show("Counldn't connect with the server. Verify your connection.",
                                "Connection error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            catch (TaskCanceledException)
            {
                MessageBox.Show("The request took too much time. Try again.",
                                "Timeout", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);

                MessageBox.Show("Unexpected error occurred. Try again later.",
                                "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            finally
            {
                btnLogin.Enabled = true;
                btnLogin.Text = "Login";
            }
        }
    }
}
