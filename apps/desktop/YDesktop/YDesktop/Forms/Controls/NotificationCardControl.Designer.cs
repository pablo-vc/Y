namespace YDesktop.Forms.Controls
{
    partial class NotificationCardControl
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
            this.lblText = new System.Windows.Forms.Label();
            this.lblTime = new System.Windows.Forms.Label();
            this.panelAvatar = new System.Windows.Forms.Panel();
            this.lblInitial = new System.Windows.Forms.Label();
            this.panelAvatar.SuspendLayout();
            this.SuspendLayout();
            // 
            // lblText
            // 
            this.lblText.AutoSize = true;
            this.lblText.Location = new System.Drawing.Point(251, 88);
            this.lblText.Name = "lblText";
            this.lblText.Size = new System.Drawing.Size(0, 20);
            this.lblText.TabIndex = 1;
            // 
            // lblTime
            // 
            this.lblTime.AutoSize = true;
            this.lblTime.Location = new System.Drawing.Point(352, 88);
            this.lblTime.Name = "lblTime";
            this.lblTime.Size = new System.Drawing.Size(0, 20);
            this.lblTime.TabIndex = 2;
            // 
            // panelAvatar
            // 
            this.panelAvatar.Controls.Add(this.lblInitial);
            this.panelAvatar.Location = new System.Drawing.Point(95, 38);
            this.panelAvatar.Name = "panelAvatar";
            this.panelAvatar.Size = new System.Drawing.Size(122, 72);
            this.panelAvatar.TabIndex = 4;
            // 
            // lblInitial
            // 
            this.lblInitial.AutoSize = true;
            this.lblInitial.Location = new System.Drawing.Point(33, 25);
            this.lblInitial.Name = "lblInitial";
            this.lblInitial.Size = new System.Drawing.Size(51, 20);
            this.lblInitial.TabIndex = 0;
            this.lblInitial.Text = "label1";
            // 
            // NotificationCardControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.panelAvatar);
            this.Controls.Add(this.lblTime);
            this.Controls.Add(this.lblText);
            this.Name = "NotificationCardControl";
            this.Size = new System.Drawing.Size(711, 447);
            this.panelAvatar.ResumeLayout(false);
            this.panelAvatar.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Label lblText;
        private System.Windows.Forms.Label lblTime;
        private System.Windows.Forms.Panel panelAvatar;
        private System.Windows.Forms.Label lblInitial;
    }
}
