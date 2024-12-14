//package oldMethod;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.text.*;
//
//public class Blockview extends JFrame {
//	JTextPane textPane = new JTextPane();
//	JPanel contPane = new JPanel();
//	JPanel ctrlPane = new JPanel();
//	Stenfatic st = new Stenfatic(){
//	};
//
//	public Blockview() {
//
//		// 以下是窗口与文字面板的初始化
//		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
//		setBounds((d.width - 900) / 2, (d.height - 600) / 2, 900, 600);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		contPane.setLayout(new BorderLayout());
//		contPane.add(new JScrollPane(textPane), BorderLayout.CENTER);
//		add(contPane, BorderLayout.CENTER);
//
//		// 以下是控制面板的初始化
//		ctrlPane.setLayout(new BoxLayout(ctrlPane, BoxLayout.Y_AXIS));
//		addButtons(ctrlPane);
//		add(ctrlPane, BorderLayout.EAST);
//
//		// 显示
//		String[] viewData = st.printchunk(1, 1, 1);
//		String[] text = new String[306];
//		Color[] textColor = new Color[306];
//		for (int i = 0; i <= 305; i++) {
//			text[i] = viewData[i * 4];
//			textColor[i] = new Color(Integer.valueOf(viewData[i * 4 + 1]), Integer.valueOf(viewData[i * 4 + 2]),
//					Integer.valueOf(viewData[i * 4 + 3]));
//		}
//		insertDocumnet(text, textColor, true);
//		setVisible(true);
//
//	}
//
//	public void addButtons(JPanel ctrlPane) {// 以下是为控制面板提供的指令
//
//		
//		st.read(st.filePath);
//		st.fill(0,1,0,5,1,5,"stone");
////		st.cleair();
//		st.save(st.evirPath, st.filePath);
////		st.close();
////setblock
//		JButton setblock = new JButton("setblock");
//		setblock.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				st.setblock(0, 1, 0, "stone");
//			}
//		});
//		ctrlPane.add(setblock);
////save
//		JButton save = new JButton("save");
//		save.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				st.save(st.evirPath, st.filePath);
//			}
//		});
//		ctrlPane.add(save);
////close
//		JButton close = new JButton("close");
//		close.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				st.close();
//			}
//		});
//		ctrlPane.add(close);
//	}
//
//	public void insertDocumnet(String[] text, Color[] textColor, Boolean ifClear)// 根据传入的颜色及文字，将文字插入文本域
//	{// 这个地方想做成循环插入文本
//		SimpleAttributeSet set = new SimpleAttributeSet();
//		StyleConstants.setFontSize(set, 20);// 设置字体大小
//		StyleConstants.setFontFamily(set, "Consolas");// 设置字体模型
//		int tt = text.length;
//		int ct = textColor.length;
//		if (ifClear)
//			textPane.setText("");
//		if (tt != ct)
//			System.out.println("字组数与色组数不匹配");
//		else {
//			Document doc = textPane.getStyledDocument();
//			try {
//				for (int i = 0; i < tt; i++) {
//					StyleConstants.setForeground(set, textColor[i]);// 设置文字颜色
//					doc.insertString(doc.getLength(), text[i], set);// 插入文字
//				}
//
//			} catch (BadLocationException e) {
//				e.printStackTrace();
//			}
//		}
//		setVisible(true);
//
//	}
//
//	public static void main(String[] args) {
//		new Blockview();
//	}
//
//}