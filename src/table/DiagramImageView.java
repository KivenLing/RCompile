package table;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.io.FileInputStream;
import java.text.AttributedCharacterIterator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author 王启航
 * @since 2018.01.06
 * 创建一个JFrame框架，用来承载image的输出
 * 以便展示图表。
 */
public class DiagramImageView extends JFrame {
	
	private JPanel myPanel;
	
	public DiagramImageView(String title, String url) {
		setTitle(title);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	    setSize(1020, 800);
	    setResizable(false);
	    getContentPane().setLayout(null);
	    this.myPanel = new ImagePanel(url);
	    myPanel.setBounds(0, 0, 1000, 750);
	    getContentPane().add(myPanel);
	    setVisible(true);
	}
}

/*
 * 利用JPanel的paint方法在JFrame上绘制图片
 * 用于显示生成的图表文件
 */
class ImagePanel extends JPanel {
	//保存图形
	private Image image = null;
	//获取图片，路径非法时报错。（生成图片失败）
	public ImagePanel(String url) {
		try {
			image = ImageIO.read(new FileInputStream(url));
		} catch (Exception e) {
			System.out.println("图片不存在");
		}
	}
	
	@Override
	public void paintComponent(Graphics g1) {
		Graphics g = (Graphics) g1;
		if (null == image) {
            return;
		}
		g.drawImage(image, 0, 0, 1000, 750, this);
		g = null;
	}
}
