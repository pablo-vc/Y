namespace YDesktop.Forms.Controls
{
    partial class ProfileControl
    {
        /// <summary> 
        /// Variable del diseñador necesaria.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary> 
        /// Limpiar los recursos que se estén usando.
        /// </summary>
        /// <param name="disposing">true si los recursos administrados se deben desechar; false en caso contrario.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Código generado por el Diseñador de componentes

        /// <summary> 
        /// Método necesario para admitir el Diseñador. No se puede modificar
        /// el contenido de este método con el editor de código.
        /// </summary>
        private void InitializeComponent()
        {
            this.panelHeader = new System.Windows.Forms.Panel();
            this.btnAction = new System.Windows.Forms.Button();
            this.panelStats = new System.Windows.Forms.Panel();
            this.lblFollowing = new System.Windows.Forms.Label();
            this.lblFollowers = new System.Windows.Forms.Label();
            this.lblBio = new System.Windows.Forms.Label();
            this.lblUsername = new System.Windows.Forms.Label();
            this.picAvatar = new System.Windows.Forms.PictureBox();
            this.flowPosts = new System.Windows.Forms.FlowLayoutPanel();
            this.panelHeader.SuspendLayout();
            this.panelStats.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picAvatar)).BeginInit();
            this.SuspendLayout();
            // 
            // panelHeader
            // 
            this.panelHeader.Controls.Add(this.btnAction);
            this.panelHeader.Controls.Add(this.panelStats);
            this.panelHeader.Controls.Add(this.lblBio);
            this.panelHeader.Controls.Add(this.lblUsername);
            this.panelHeader.Controls.Add(this.picAvatar);
            this.panelHeader.Dock = System.Windows.Forms.DockStyle.Top;
            this.panelHeader.Location = new System.Drawing.Point(0, 0);
            this.panelHeader.Name = "panelHeader";
            this.panelHeader.Size = new System.Drawing.Size(702, 105);
            this.panelHeader.TabIndex = 0;
            // 
            // btnAction
            // 
            this.btnAction.Location = new System.Drawing.Point(549, 12);
            this.btnAction.Name = "btnAction";
            this.btnAction.Size = new System.Drawing.Size(121, 53);
            this.btnAction.TabIndex = 4;
            this.btnAction.Text = "button1";
            this.btnAction.UseVisualStyleBackColor = true;
            this.btnAction.Click += new System.EventHandler(this.btnAction_Click);
            // 
            // panelStats
            // 
            this.panelStats.Controls.Add(this.lblFollowing);
            this.panelStats.Controls.Add(this.lblFollowers);
            this.panelStats.Location = new System.Drawing.Point(131, 64);
            this.panelStats.Name = "panelStats";
            this.panelStats.Size = new System.Drawing.Size(139, 41);
            this.panelStats.TabIndex = 3;
            // 
            // lblFollowing
            // 
            this.lblFollowing.AutoSize = true;
            this.lblFollowing.Location = new System.Drawing.Point(69, 8);
            this.lblFollowing.Name = "lblFollowing";
            this.lblFollowing.Size = new System.Drawing.Size(51, 20);
            this.lblFollowing.TabIndex = 1;
            this.lblFollowing.Text = "label2";
            // 
            // lblFollowers
            // 
            this.lblFollowers.AutoSize = true;
            this.lblFollowers.Location = new System.Drawing.Point(12, 8);
            this.lblFollowers.Name = "lblFollowers";
            this.lblFollowers.Size = new System.Drawing.Size(51, 20);
            this.lblFollowers.TabIndex = 0;
            this.lblFollowers.Text = "label1";
            // 
            // lblBio
            // 
            this.lblBio.AutoSize = true;
            this.lblBio.Location = new System.Drawing.Point(172, 41);
            this.lblBio.Name = "lblBio";
            this.lblBio.Size = new System.Drawing.Size(51, 20);
            this.lblBio.TabIndex = 2;
            this.lblBio.Text = "label2";
            // 
            // lblUsername
            // 
            this.lblUsername.AutoSize = true;
            this.lblUsername.Location = new System.Drawing.Point(172, 7);
            this.lblUsername.Name = "lblUsername";
            this.lblUsername.Size = new System.Drawing.Size(98, 20);
            this.lblUsername.TabIndex = 1;
            this.lblUsername.Text = "lblUsername";
            // 
            // picAvatar
            // 
            this.picAvatar.Image = global::YDesktop.Properties.Resources.avatar;
            this.picAvatar.Location = new System.Drawing.Point(28, 5);
            this.picAvatar.Name = "picAvatar";
            this.picAvatar.Size = new System.Drawing.Size(79, 67);
            this.picAvatar.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.picAvatar.TabIndex = 0;
            this.picAvatar.TabStop = false;
            // 
            // flowPosts
            // 
            this.flowPosts.Dock = System.Windows.Forms.DockStyle.Fill;
            this.flowPosts.Location = new System.Drawing.Point(0, 105);
            this.flowPosts.Name = "flowPosts";
            this.flowPosts.Size = new System.Drawing.Size(702, 396);
            this.flowPosts.TabIndex = 1;
            // 
            // ProfileControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.flowPosts);
            this.Controls.Add(this.panelHeader);
            this.Name = "ProfileControl";
            this.Size = new System.Drawing.Size(702, 501);
            this.Load += new System.EventHandler(this.ProfileControl_Load);
            this.panelHeader.ResumeLayout(false);
            this.panelHeader.PerformLayout();
            this.panelStats.ResumeLayout(false);
            this.panelStats.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picAvatar)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panelHeader;
        private System.Windows.Forms.PictureBox picAvatar;
        private System.Windows.Forms.Label lblBio;
        private System.Windows.Forms.Label lblUsername;
        private System.Windows.Forms.Panel panelStats;
        private System.Windows.Forms.Button btnAction;
        private System.Windows.Forms.FlowLayoutPanel flowPosts;
        private System.Windows.Forms.Label lblFollowing;
        private System.Windows.Forms.Label lblFollowers;
    }
}
