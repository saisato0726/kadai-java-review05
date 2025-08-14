package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

	public static void main(String[] args) {
		//変数の宣言
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//ドライバのクラスをJava上で読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//DBと接続する
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "saiharu0308");
			
			//DBとの窓口preparedStatementオブジェクトの作成
			String sql = "SELECT * FROM person WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			
			//SELECT文の実行と結果の表示
			System.out.print("検索キーワードを入力してください >");
			int input = keyInNum(); //数字で取得（数字でなかった場合を下に書く）
			
			// PreparedStatementオブジェクトの?に値をセット
			pstmt.setInt(1, input);
			rs = pstmt.executeQuery();
			
			//結果を表示
			while(rs.next()) {
				String name = rs.getString("name");
				int age = rs.getInt("age");
				
				System.out.println(name);
				System.out.println(age);
			}
		} catch (ClassNotFoundException e) {
			System.err.println("JDBCドライバのロードに失敗しました。");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("データベースに異常が発生しました。");
			e.printStackTrace();
		} finally { //接続を閉じる
			if (rs != null) {
				try {
					rs.close();					
				} catch (SQLException e) {
					System.err.println("ResultSetを閉じるときにエラーが発生しました。");
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					System.err.println("preparedStatementを閉じるときにエラーが発生しました。");
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.err.println("データベースの切断時にエラーが発生しました。");					
				}
			}
		}
	}
//キーボード入力された値をintで返す（数字が入るまでエラー表示を繰り返す）
	private static int keyInNum() {
		int result = 0;
			try {
				BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
	            String line = key.readLine(); // 1行取得したのを
	            return Integer.parseInt(line); // 数値変換して返す
			} catch (NumberFormatException e) {
				System.err.println("数字で入力してください。");
			} catch (IOException e) {
				System.err.println("入力のエラーが発生しました。");
			}
			return result;
	}
}

