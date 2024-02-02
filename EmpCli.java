package empCLI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class EmpCli {

	private static final String url = "jdbc:mysql://localhost:3306/firm";
	private static final String id = "root";
	private static final String pass = "mysql";
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection conn = DriverManager.getConnection(url, id, pass)) {

			boolean exit = false;

			while (!exit) {
				System.out.println("-------------------");
				System.out.println("1. 데이터 보기");
				System.out.println("2. 데이터 삽입");
				System.out.println("3. 데이터 수정");
				System.out.println("4. 데이터 삭제");
				System.out.println("5. 종료");
				System.out.println("-------------------");
				System.out.print("선택하세요: ");

				String choice = scan.nextLine();

				switch (choice) {
				case "1":
					viewData(conn);
					break;
				case "2":
					insertData(conn);
					break;
				case "3":
					updateData(conn);
					break;
				case "4":
					deleteData(conn);
					break;
				case "5":
					 exit = true;
					 System.out.println("프로그램 종료");
					break;	
					
				default:
					System.out.println("유효하지 않은 선택. 다시 시도하세요.");
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 1.데이터 보기
	private static void viewData(Connection connection) throws ClassNotFoundException, SQLException {
		Connection conn = DriverManager.getConnection(url, id, pass);
		Statement stmt = conn.createStatement();
		String sql = "select * from emp";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			System.out.print(rs.getInt("empno") + "\t");
			System.out.print(rs.getString("ename") + "\t");
			System.out.print(rs.getString("job") + "\t");
			System.out.print(rs.getInt("mgr") + "\t");
			System.out.print(rs.getString("hiredate") + "\t");
			System.out.print(rs.getDouble("sal") + "\t");
			System.out.print(rs.getString("comm") + "\t");
			System.out.println(rs.getInt("deptno"));
		}
	}

	// 2.데이터 삽입
	private static void insertData(Connection connection) throws SQLException {
		System.out.print("사번 : ");
		int empno = Integer.parseInt(scan.nextLine());
		System.out.print("이름 : ");
		String ename = scan.nextLine();
		System.out.print("직책 : ");
		String job = scan.nextLine();
		System.out.print("상사사번 : ");
		int mgr = Integer.parseInt(scan.nextLine());
		System.out.print("입사일 : ");
		String hiredate = scan.nextLine();
		System.out.print("급여 : ");
		double sal = Integer.parseInt(scan.nextLine());
		System.out.print("추가수당 : ");
		double comm = Integer.parseInt(scan.nextLine());
		System.out.print("부서번호 : ");
		int deptno = Integer.parseInt(scan.nextLine());

		Statement stmt = connection.createStatement();
		String sql = "insert into emp(empno, ename, job, mgr, hiredate, sal, comm, deptno) " + "values (" + empno
				+ ", '" + ename + "', '" + job + "', '" + mgr + "', '" + hiredate + "', '" + sal + "', '" + comm
				+ "', '" + deptno + "')";
		System.out.println(sql);
		int result = stmt.executeUpdate(sql);
		if (result == 1) {
			System.out.println("입력성공");
		} else {
			System.out.println("입력실패");
		}
	}

	// 3.데이터 수정
	private static void updateData(Connection connection) throws ClassNotFoundException, SQLException {
		Scanner scan = new Scanner(System.in);

		System.out.print("사번:");
		int empno = Integer.parseInt(scan.nextLine());

		System.out.print("이름:");
		String ename = scan.nextLine();
		System.out.print("직책:");
		String job = scan.nextLine();
		System.out.print("급여:");
		double sal = Double.parseDouble(scan.nextLine());
		System.out.print("추가수당:");
		double comm = Double.parseDouble(scan.nextLine());

		Connection conn = DriverManager.getConnection(url, id, pass);
		Statement stmt = conn.createStatement();

		String sql = "update emp set ename = '" + ename + "', job = '" + job + "', sal = " + sal + ", comm = " + comm
				+ " where empno = " + empno;

		System.out.println(sql);

		int result = stmt.executeUpdate(sql);
		if (result >= 1) {
			System.out.println("수정 성공!");
		} else {
			System.out.println("수정 실패!");
		}
	}

	// 4.데이터 삭제
	private static void deleteData(Connection connection) throws ClassNotFoundException, SQLException {
		Scanner scan = new Scanner(System.in);
		System.out.print("사번:");
		int empno = Integer.parseInt(scan.nextLine());

		Connection conn = DriverManager.getConnection(url, id, pass);
		Statement stmt = conn.createStatement();

		String sql = "delete from emp where empno = " + empno;

		int result = stmt.executeUpdate(sql);
		if (result >= 1) {
			System.out.println("삭제 성공!");
		} else {
			System.out.println("삭제 실패!");
		}

	}
}