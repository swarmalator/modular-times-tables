package main.java;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class TTGui extends JFrame implements ActionListener, MouseListener, KeyListener {
	/**
	 * Circular Times Tables
	 * Geometric Demonstration
	 * TCU Physics
	 * 12/5/2016
	 * @author Cole H. Turner
	 */
	private static final long serialVersionUID = 1L;
	private static final int resolution = Toolkit.getDefaultToolkit().getScreenResolution();
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	private static Font dataFont = new Font(Font.DIALOG_INPUT, Font.BOLD, resolution / 4);

	private double n, dn; 
	private int m, dm;
	private boolean mod, num;

	public TTGui(){
		super("Times Tables");
		Point screenCenter = new Point(screenSize.width / 2, screenSize.height / 2);
		n = 1.00;
		m = 0;
		dn = 1;
		dm = 10;
		mod = true;
		Container pane = new JPanel(){
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics gr){
				Graphics2D g = (Graphics2D) gr;
				g.setColor(Color.black);
				g.fillRect(0, 0, getWidth(), getHeight());
				int r = getHeight() / 3;
				g.translate(screenCenter.x, screenCenter.y);
				g.setColor(Color.white);
				g.setFont(dataFont);
				g.drawOval(-r, -r, 2 * r, 2 * r);
				int l = g.getFontMetrics().getHeight();
				g.drawString("N: " + String.format("%.2f", n), -r - l, -r - l);
				if(num){
					g.drawString(">", -r - 2 * l, - r - l);
				}
				g.drawString("MOD: " + m, -r - l, -r);
				if(mod){
					g.drawString(">", -r - 2 * l, -r);
				}

				g.scale(1, -1);
				int x1, y1, x2, y2, x3, y3;
				double a1, a2;
				for(int i = 0; i < m; i++){
					a1 = (i * Math.PI * 2) / m;
					a2 = (n * i * Math.PI * 2) / m;
					x1 = (int)(r * Math.cos(a1));
					y1 = (int)(r * Math.sin(a1));
					x2 = (int)(r * Math.cos(a2));
					y2 = (int)(r * Math.sin(a2));
					g.drawLine(x1, y1, x2, y2);
					x3 = x1 + (int)(l * Math.cos(a1));
					y3 = y1 + (int)(l * Math.sin(a1));
					g.drawLine(x1, y1, x3, y3);
				}
			}
		};

		this.setSize(screenSize);
		this.setContentPane(pane);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.setLocation(screenCenter.x - this.getWidth() / 2, screenCenter.y - this.getHeight() / 2);
		this.setUndecorated(true);
		this.setVisible(true);
	}
	
	public void update(){
		this.repaint();
	}

	public void actionPerformed(ActionEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_M :
			mod = !mod;
			num = false;
			break;
		case KeyEvent.VK_N :
			num = !num;
			mod = false;
			break;
		case KeyEvent.VK_SHIFT :
			dn = 0.01;
			dm = 1;
			break;
		case KeyEvent.VK_UP :
			if(mod) m += dm;
			else if(num) n += dn ;
			break;
		case KeyEvent.VK_DOWN :
			if(mod) m = m < dm ? 0 : m - dm;
			else if(num) n = n < dn ? 0 : n - dn;
			break;
		case KeyEvent.VK_ESCAPE :
			System.exit(0);
		}
		update();
	}
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_SHIFT :
			dn = 1.0;
			dm = 10;
			break;
		}
		update();
	}
	public void keyTyped(KeyEvent e) {}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new TTGui();
			}
		});
	}
}
