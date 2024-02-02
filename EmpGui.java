package empswing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EmpGui extends JFrame {

	JTextField tf1 = new JTextField(5);
	JTextField tf2 = new JTextField(5);
	JTextField tf3 = new JTextField(5);
	JTextField tf4 = new JTextField(5);
	JTextField tf5 = new JTextField(6);
	JTextField tf6 = new JTextField(5);
	JTextField tf7 = new JTextField(5);
	JTextField tf8 = new JTextField(5);

	
	JTextArea ta = new JTextArea(20, 50);
	Connection conn;
	Statement stmt;

	public EmpGui() {
		
		String url = "jdbc:mysql://localhost:3306/firm";
		String id = "root";
		String pass = "mysql";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pass);
			stmt = conn.createStatement();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		JLabel lb1 = new JLabel("사번:");
		JLabel lb2 = new JLabel("이름:");
		JLabel lb3 = new JLabel("직책:");
		JLabel lb4 = new JLabel("상사사번:");
		JLabel lb5 = new JLabel("입사일:");
		JLabel lb6 = new JLabel("급여:");
		JLabel lb7 = new JLabel("인센티브:");
		JLabel lb8 = new JLabel("부서번호:");
		
		JButton bt1 = new JButton("전체 내용");
		JButton bt2 = new JButton("입력");
		JButton bt3 = new JButton("삭제");
		JButton bt4 = new JButton("수정");
		JButton bt5 = new JButton("종료");
		
		bt1.setPreferredSize(new Dimension(120, 40));
		bt2.setPreferredSize(new Dimension(120, 40));
		bt3.setPreferredSize(new Dimension(120, 40));
		bt4.setPreferredSize(new Dimension(120, 40));
		bt5.setPreferredSize(new Dimension(120, 40));
		
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		JPanel jp1 = new JPanel(new FlowLayout());
		jp1.add(bt1); jp1.add(bt2);
		jp1.add(bt3); jp1.add(bt4);
		jp1.add(bt5);
		con.add(jp1, BorderLayout.SOUTH);
		JScrollPane scroll = new JScrollPane(ta);
		JPanel jp2 = new JPanel(new FlowLayout());
		jp2.add(scroll);
		con.add(jp2, BorderLayout.CENTER);

		JPanel jp3 = new JPanel(new FlowLayout());
		con.add(jp3, BorderLayout.NORTH);
		jp3.add(lb1); jp3.add(tf1);
		jp3.add(lb2); jp3.add(tf2);
		jp3.add(lb3); jp3.add(tf3);
		jp3.add(lb4); jp3.add(tf4);
		jp3.add(lb5); jp3.add(tf5);
		jp3.add(lb6); jp3.add(tf6);
		jp3.add(lb7); jp3.add(tf7);
		jp3.add(lb8); jp3.add(tf8);

		this.setTitle("emp 관리");
		this.setLocation(400, 400);
		this.setSize(900, 470);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		bt1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				select();
			}
		});

		bt2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insert();
				clearTextField();
				select();
			}
		});

		bt3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delete();
				clearTextField();
				select();
			}
		});

		bt4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				update();

			}
		});

		bt5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	private void clearTextField() {
		tf1.setText(""); tf2.setText("");
		tf3.setText(""); tf4.setText("");
		tf5.setText(""); tf6.setText("");
		tf7.setText(""); tf8.setText("");
	}

	// 데이터 보기
	public void select() {
		String sql = "select * from emp";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			ta.setText("");
			while (rs.next()) {
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				int mgr = rs.getInt("mgr");
				String hiredate = rs.getString("hiredate");
				double sal = rs.getDouble("sal");
				double comm = rs.getDouble("comm");
				int deptno = rs.getInt("deptno");

				String str = String.format("%d, %s, %s, %d, %s, %.2f, %.2f, %d\n", empno, ename, job, mgr, hiredate,
						sal, comm, deptno);
				ta.append(str);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 데이터입력
	public void insert() {
		String sql = String.format("INSERT INTO emp VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
				tf1.getText(), tf2.getText(), tf3.getText(), tf4.getText(), tf5.getText(), tf6.getText(), tf7.getText(),
				tf8.getText());

		try {
			int res = stmt.executeUpdate(sql);

			if (res == 1) {
				System.out.println("데이터 삽입 성공");
				select(); 
			} else {
				System.out.println("데이터 삽입 실패");
			}
		} catch (SQLException e) {
			System.err.println("데이터 삽입 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// 데이터삭제
	public void delete() {
		try {
			int empnoToDelete = Integer.parseInt(tf1.getText());
			String sql = String.format("DELETE FROM emp WHERE empno = %d", empnoToDelete);

			int res = stmt.executeUpdate(sql);

			if (res == 1) {
				System.out.println("데이터 삭제 성공");
				select(); 
			} else {
				System.out.println("데이터 삭제 실패");
			}
		} catch (SQLException | NumberFormatException e) {
			System.err.println("데이터 삭제 중 오류 발생: " + e.getMessage());
		}
	}

	// 데이터 수정
	public void update() {
		try {
			int empno = Integer.parseInt(tf1.getText());
			String Name = tf2.getText();
			String Job = tf3.getText();
			int Mgr = Integer.parseInt(tf4.getText());
			String Hiredate = tf5.getText();
			double Sal = Double.parseDouble(tf6.getText());
			double Comm = Double.parseDouble(tf7.getText());
			int Deptno = Integer.parseInt(tf8.getText());

			String sql = String.format(
					"UPDATE emp SET ename = '%s', job = '%s', mgr = %d, hiredate = '%s', sal = %.2f, comm = %.2f, deptno = %d WHERE empno = %d",
					Name, Job, Mgr, Hiredate, Sal, Comm, Deptno, empno);

			int res = stmt.executeUpdate(sql);

			if (res == 1) {
				System.out.println("데이터 수정 성공");
				select();
			} else {
				System.out.println("데이터 수정 실패");
			}
		} catch (SQLException | NumberFormatException e) {
			System.err.println("데이터 수정 중 오류 발생: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		new EmpGui();
	}
}