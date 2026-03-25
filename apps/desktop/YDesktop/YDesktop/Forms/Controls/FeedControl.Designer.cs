namespace YDesktop.Forms.Controls
{
    partial class FeedControl
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
            this.panelContainer = new System.Windows.Forms.Panel();
            this.flowLayoutPanel1 = new System.Windows.Forms.FlowLayoutPanel();
            this.panelTabs = new System.Windows.Forms.Panel();
            this.btnFollowing = new System.Windows.Forms.Button();
            this.btnGlobal = new System.Windows.Forms.Button();
            this.tableTabs = new System.Windows.Forms.TableLayoutPanel();
            this.panelContainer.SuspendLayout();
            this.panelTabs.SuspendLayout();
            this.tableTabs.SuspendLayout();
            this.SuspendLayout();
            // 
            // panelContainer
            // 
            this.panelContainer.Controls.Add(this.flowLayoutPanel1);
            this.panelContainer.Controls.Add(this.panelTabs);
            this.panelContainer.Location = new System.Drawing.Point(23, 109);
            this.panelContainer.Name = "panelContainer";
            this.panelContainer.Size = new System.Drawing.Size(456, 357);
            this.panelContainer.TabIndex = 1;
            // 
            // flowLayoutPanel1
            // 
            this.flowLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.flowLayoutPanel1.Location = new System.Drawing.Point(0, 122);
            this.flowLayoutPanel1.Name = "flowLayoutPanel1";
            this.flowLayoutPanel1.Size = new System.Drawing.Size(456, 235);
            this.flowLayoutPanel1.TabIndex = 3;
            // 
            // panelTabs
            // 
            this.panelTabs.Controls.Add(this.tableTabs);
            this.panelTabs.Dock = System.Windows.Forms.DockStyle.Top;
            this.panelTabs.Location = new System.Drawing.Point(0, 0);
            this.panelTabs.Name = "panelTabs";
            this.panelTabs.Size = new System.Drawing.Size(456, 122);
            this.panelTabs.TabIndex = 2;
            // 
            // btnFollowing
            // 
            this.btnFollowing.Location = new System.Drawing.Point(144, 3);
            this.btnFollowing.Name = "btnFollowing";
            this.btnFollowing.Size = new System.Drawing.Size(135, 32);
            this.btnFollowing.TabIndex = 1;
            this.btnFollowing.Text = "Following";
            this.btnFollowing.UseVisualStyleBackColor = true;
            // 
            // btnGlobal
            // 
            this.btnGlobal.Location = new System.Drawing.Point(3, 3);
            this.btnGlobal.Name = "btnGlobal";
            this.btnGlobal.Size = new System.Drawing.Size(135, 32);
            this.btnGlobal.TabIndex = 0;
            this.btnGlobal.Text = "Global";
            this.btnGlobal.UseVisualStyleBackColor = true;
            // 
            // tableTabs
            // 
            this.tableTabs.ColumnCount = 2;
            this.tableTabs.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableTabs.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableTabs.Controls.Add(this.btnFollowing, 1, 0);
            this.tableTabs.Controls.Add(this.btnGlobal, 0, 0);
            this.tableTabs.Location = new System.Drawing.Point(76, 21);
            this.tableTabs.Name = "tableTabs";
            this.tableTabs.RowCount = 2;
            this.tableTabs.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableTabs.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableTabs.Size = new System.Drawing.Size(282, 77);
            this.tableTabs.TabIndex = 2;
            // 
            // FeedControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.panelContainer);
            this.Name = "FeedControl";
            this.Size = new System.Drawing.Size(520, 481);
            this.Load += new System.EventHandler(this.FeedControl_Load);
            this.panelContainer.ResumeLayout(false);
            this.panelTabs.ResumeLayout(false);
            this.tableTabs.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion
        private System.Windows.Forms.Panel panelContainer;
        private System.Windows.Forms.Panel panelTabs;
        private System.Windows.Forms.FlowLayoutPanel flowLayoutPanel1;
        private System.Windows.Forms.Button btnFollowing;
        private System.Windows.Forms.Button btnGlobal;
        private System.Windows.Forms.TableLayoutPanel tableTabs;
    }
}
