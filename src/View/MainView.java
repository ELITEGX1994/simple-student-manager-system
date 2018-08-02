package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import DAO.DAO;
import DAO.BaseDAO;
import DAO.StudentDAO;
import Main.Constant;

/**
 * 模块说明： 主页模块，包括初始化，增删改查的按钮
 * 
 */
public class MainView extends JFrame {

	private static final long serialVersionUID = 5870864087464173884L;

	private final int maxPageNum = 99;

	private JPanel jPanelNorth, jPanelSouth, jPanelCenter;
	private JButton jButtonFirst, jButtonLast, jButtonNext, jButtonPre, jButtonAdd, jButtonDelete, jButtonUpdate,
			jButtonFind;
	private JLabel currPageNumJLabel;
	private JTextField condition;
	public static JTable jTable;
	private JScrollPane jScrollPane;
	private DefaultTableModel myTableModel;

	public static String[] column = { "id", Constant.STUDENT_NAME, Constant.STUDENT_SNO,
			Constant.STUDENT_SEX, Constant.STUDENT_DEPARTMETN, Constant.STUDENT_HOMETOWN,
			Constant.STUDENT_MARK, Constant.STUDENT_EMAIL, Constant.STUDENT_TEL };
	public static int currPageNum = 1;

	public MainView() {
		init();
	}

	private void init() {
		setTitle(Constant.MAINVIEW_TITLE);

		// north panel
		jPanelNorth = new JPanel();
		jPanelNorth.setLayout(new GridLayout(1, 5));
		condition = new JTextField(Constant.PARAM_FIND_CONDITION);
		condition.addKeyListener(new FindListener());
		jPanelNorth.add(condition);
		// query by name
		jButtonFind = new JButton(Constant.PARAM_FIND);
		jButtonFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				find();
			}
		});
		jButtonFind.addKeyListener(new FindListener());
		// add
		jPanelNorth.add(jButtonFind);
		jButtonAdd = new JButton(Constant.PARAM_ADD);
		jButtonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddView();
			}
		});
		jPanelNorth.add(jButtonAdd);
		// delete
		jButtonDelete = new JButton(Constant.PARAM_DELETE);
		jButtonDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DeleteView();
			}
		});
		jPanelNorth.add(jButtonDelete);
		// update
		jButtonUpdate = new JButton(Constant.PARAM_UPDATE);
		jButtonUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UpdateView();
			}
		});
		jPanelNorth.add(jButtonUpdate);

		// center panel
		jPanelCenter = new JPanel();
		jPanelCenter.setLayout(new GridLayout(1, 1));

		// init jTable
		String[][] result = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).list(currPageNum);
		myTableModel = new DefaultTableModel(result, column);
		jTable = new JTable(myTableModel);
		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
		cr.setHorizontalAlignment(JLabel.CENTER);
		jTable.setDefaultRenderer(Object.class, cr);
		initJTable(jTable, result);

		jScrollPane = new JScrollPane(jTable);
		jPanelCenter.add(jScrollPane);

		// south panel
		jPanelSouth = new JPanel();
		jPanelSouth.setLayout(new GridLayout(1, 5));

		jButtonFirst = new JButton(Constant.MAINVIEW_FIRST);
		jButtonFirst.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currPageNum = 1;
				String[][] result = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).list(currPageNum);
				initJTable(jTable, result);
				currPageNumJLabel.setText(Constant.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
						+ Constant.MAINVIEW_PAGENUM_JLABEL_YE);
			}
		});
		jButtonPre = new JButton(Constant.MAINVIEW_PRE);
		jButtonPre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currPageNum--;
				if (currPageNum <= 0) {
					currPageNum = 1;
				}
				String[][] result = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).list(currPageNum);
				initJTable(jTable, result);
				currPageNumJLabel.setText(Constant.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
						+ Constant.MAINVIEW_PAGENUM_JLABEL_YE);
			}
		});
		jButtonNext = new JButton(Constant.MAINVIEW_NEXT);
		jButtonNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currPageNum++;
				if (currPageNum > maxPageNum) {
					currPageNum = maxPageNum;
				}
				String[][] result = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).list(currPageNum);
				initJTable(jTable, result);
				currPageNumJLabel.setText(Constant.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
						+ Constant.MAINVIEW_PAGENUM_JLABEL_YE);
			}
		});
		jButtonLast = new JButton(Constant.MAINVIEW_LAST);
		jButtonLast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currPageNum = maxPageNum;
				String[][] result = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).list(currPageNum);
				initJTable(jTable, result);
				currPageNumJLabel.setText(Constant.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
						+ Constant.MAINVIEW_PAGENUM_JLABEL_YE);
			}
		});

		currPageNumJLabel = new JLabel(
				Constant.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum + Constant.MAINVIEW_PAGENUM_JLABEL_YE);
		currPageNumJLabel.setHorizontalAlignment(JLabel.CENTER);

		jPanelSouth.add(jButtonFirst);
		jPanelSouth.add(jButtonPre);
		jPanelSouth.add(currPageNumJLabel);
		jPanelSouth.add(jButtonNext);
		jPanelSouth.add(jButtonLast);

		this.add(jPanelNorth, BorderLayout.NORTH);
		this.add(jPanelCenter, BorderLayout.CENTER);
		this.add(jPanelSouth, BorderLayout.SOUTH);

		setBounds(400, 200, 750, 340);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public static void initJTable(JTable jTable, String[][] result) {
		((DefaultTableModel) jTable.getModel()).setDataVector(result, column);
		jTable.setRowHeight(20);
		TableColumn firsetColumn = jTable.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(30);
		firsetColumn.setMaxWidth(30);
		firsetColumn.setMinWidth(30);
		TableColumn secondColumn = jTable.getColumnModel().getColumn(1);
		secondColumn.setPreferredWidth(60);
		secondColumn.setMaxWidth(60);
		secondColumn.setMinWidth(60);
		TableColumn thirdColumn = jTable.getColumnModel().getColumn(2);
		thirdColumn.setPreferredWidth(90);
		thirdColumn.setMaxWidth(90);
		thirdColumn.setMinWidth(90);
		TableColumn fourthColumn = jTable.getColumnModel().getColumn(3);
		fourthColumn.setPreferredWidth(30);
		fourthColumn.setMaxWidth(30);
		fourthColumn.setMinWidth(30);
		TableColumn seventhColumn = jTable.getColumnModel().getColumn(6);
		seventhColumn.setPreferredWidth(30);
		seventhColumn.setMaxWidth(30);
		seventhColumn.setMinWidth(30);
		TableColumn ninthColumn = jTable.getColumnModel().getColumn(8);
		ninthColumn.setPreferredWidth(90);
		ninthColumn.setMaxWidth(90);
		ninthColumn.setMinWidth(90);
	}

	private class FindListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				find();
			}
		}
	}

	private void find() {
		currPageNum = 0;
		String param = condition.getText();
		if ("".equals(param) || param == null) {
			initJTable(MainView.jTable, null);
			currPageNumJLabel.setText(Constant.MAINVIEW_FIND_JLABEL);
			return;
		}
		String[][] result = ((StudentDAO) BaseDAO.getAbilityDAO(DAO.StudentDAO)).queryByName(param);
		condition.setText("");
		initJTable(MainView.jTable, result);
		currPageNumJLabel.setText(Constant.MAINVIEW_FIND_JLABEL);
	}

}
