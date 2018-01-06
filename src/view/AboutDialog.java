package view;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
/**
 * 
 * @author 薛浩
 * date:2018.01.06
 * 类功能说明：实现前台功能，为后台功能留下接口。
 * 
 */
public class AboutDialog extends JFrame {
	
	JTextPane textReadPane = new JTextPane();
	JTextPane textWritePane = new JTextPane();
	JScrollPane scrollWritePane = new JScrollPane(textWritePane);
	JFileChooser filechooser = new JFileChooser();

	public AboutDialog() {
		super("R语言解释器");
		Action[] actions =
		{
				new NewAction(),
				new OpenAction(),
				new SaveAction(),
				new CutAction(),
				new CopyAction(),
				new PasteAction(),
				new ExitAction(),
				new AboutAction(),


		};

		setJMenuBar(createJMenuBar(actions));
		Container container = getContentPane();

		container.add(createJToolBar(actions), BorderLayout.NORTH);
		container.add(scrollWritePane, BorderLayout.CENTER);
		container.add(textReadPane, BorderLayout.SOUTH);

		setSize(1280, 720);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JMenuBar createJMenuBar(Action[] actions) {
		JMenuBar menubar = new JMenuBar();
		JMenu menuFile = new JMenu("文件");
		JMenu menuEdit = new JMenu("编辑");
		JMenu menuHelp = new JMenu("帮助");

		menuFile.add(new JMenuItem(actions[0]));
		menuFile.add(new JMenuItem(actions[1]));
		menuFile.add(new JMenuItem(actions[2]));
		menuEdit.add(new JMenuItem(actions[3]));
		menuEdit.add(new JMenuItem(actions[4]));
		menuEdit.add(new JMenuItem(actions[5]));
		menuEdit.add(new JMenuItem(actions[6]));
		menuHelp.add(new JMenuItem(actions[7]));
		menubar.add(menuFile);
		menubar.add(menuEdit);
		menubar.add(menuHelp);

		return menubar;
	}

	private JToolBar createJToolBar(Action[] actions) {
		JToolBar toolBar = new JToolBar();
		JButton bt = new JButton(actions[0]);
		bt.setRequestFocusEnabled(false);
		toolBar.add(bt);
		JButton bt1 = new JButton(actions[1]);
		bt1.setRequestFocusEnabled(false);
		toolBar.add(bt1);
		JButton bt2 = new JButton(actions[2]);
		bt2.setRequestFocusEnabled(false);
		toolBar.add(bt2);
		JButton bt6 = new JButton(actions[6]);
		bt6.setRequestFocusEnabled(false);
		toolBar.add(bt6);
		JButton bt7 = new JButton(actions[7]);
		bt7.setRequestFocusEnabled(false);
		toolBar.add(bt7);
		JButton bt8 = new JButton("运行");
		bt8.setRequestFocusEnabled(false);
		bt8.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
		toolBar.add(bt8);
		return toolBar;
	}

	class NewAction extends AbstractAction {
		public NewAction() {
			super("新文件");
		}
		public void actionPerformed(ActionEvent e) {
			textWritePane.setDocument(new DefaultStyledDocument());
		}
	}

	class OpenAction extends AbstractAction {
		public OpenAction() {
			super("打开");
		}
		public void actionPerformed(ActionEvent e) {
			int i = filechooser.showOpenDialog(AboutDialog.this);
			if (i == JFileChooser.APPROVE_OPTION) {
				File f = filechooser.getSelectedFile();
				try {
					InputStream is = new FileInputStream(f);
					textWritePane.read(is, "d");
				} catch (Exception ex) {
				ex.printStackTrace();
				}
			}
		}
	}

	class SaveAction extends AbstractAction {
		public SaveAction() {
			super("保存");
		}
		public void actionPerformed(ActionEvent e) {
			int i = filechooser.showSaveDialog(AboutDialog.this);
			if (i == JFileChooser.APPROVE_OPTION) {
				File f = filechooser.getSelectedFile();
				try {
					FileOutputStream out = new FileOutputStream(f); 
					out.write(textWritePane.getText().getBytes());    
				} catch (Exception ex) {
				ex.printStackTrace();
				}
			}
		}
	}

	class ExitAction extends AbstractAction {
		public ExitAction() {
			super("退出");
		}
		public void actionPerformed(ActionEvent e) {
			System.exit(0); 
		}
	}

	class CutAction extends AbstractAction {
		public CutAction() {
			super("剪切");
		}
		public void actionPerformed(ActionEvent e) {
			textWritePane.cut(); 
		}
	}

	class CopyAction extends AbstractAction {
		public CopyAction() {
			super("复制");
	}
		public void actionPerformed(ActionEvent e) {
			textWritePane.copy(); 
		}
	}

	class PasteAction extends AbstractAction { 
		public PasteAction() {
			super("粘贴");
		}
		public void actionPerformed(ActionEvent e) {
			textWritePane.paste();
		}
	}

	class AboutAction extends AbstractAction { 
		public AboutAction() {
			super("关于");
		}
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(AboutDialog.this, "由Java制作的简单的R语言解释器");
		}
	}
	
	
	public static void main(String[] args) {
		new AboutDialog();
	}
}
