import java.sql.*;
import java.util.ArrayList;

public class ConnectMySQL {
	
	static String address = "jdbc:mysql://localhost/db2276274";
	static String userid = "root";
	static String passwd = "cs10120";
	
	public static void CreateTableGroupRanking() {
		
		try(Connection conn = DriverManager.getConnection(address, userid, passwd);
				Statement stmt = conn.createStatement();
			)
		{
			stmt.executeUpdate("create table GroupRanking("
					+ "groupname varchar(60),"
					+ "solvednum int,"
					+ "ranking int primary key)");
		} 
		catch (SQLException e) {
			System.out.println(e);
		}
	}

	public static void UpdateGroupRanking(ArrayList<group> groups) {
		
		try(Connection conn = DriverManager.getConnection(address, userid, passwd);
				Statement stmt = conn.createStatement(); 
				PreparedStatement pstmt = conn.prepareStatement("insert into GroupRanking values(?,?,?)");
			)
		{
			conn.setAutoCommit(false);
			stmt.executeUpdate("delete from GroupRanking");
			int ranking = 1;
			for(group g : groups) {
				//System.out.println(g.groupname);
				pstmt.setString(1,g.groupname);
				pstmt.setInt(2,g.solvednum);
				pstmt.setInt(3,ranking++);
				pstmt.executeUpdate();
			}
			conn.commit();
			conn.setAutoCommit(true);
		} 
		catch (SQLException e) {
			System.out.println(e);
		}

	}
	
	public static void SelectAllfromGroupRanking() {
		
		try(Connection conn = DriverManager.getConnection(address, userid, passwd);
				PreparedStatement pstmt = conn.prepareStatement(
						"select ranking, groupname, solvednum from GroupRanking");
			)
		{
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
				System.out.printf("%10d   %-40s %,6d\n",rs.getInt(1), rs.getString(2), rs.getInt(3));
		} 
		catch (SQLException e) {
			System.out.println(e);
		}

	}
	
	public static Object[] GetRank(String groupname) {
		try(Connection conn = DriverManager.getConnection(address, userid, passwd);
				PreparedStatement pstmt = conn.prepareStatement(
						"select ranking, solvednum "
						+ "from GroupRanking "
						+ "where groupname = ?");
			)
		{
			pstmt.setString(1, groupname);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return new Object[] {rs.getInt(1), rs.getInt(2)};
		} 
		catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}
	
	public static Object[] GetNextRank(String groupname) {
		try(Connection conn = DriverManager.getConnection(address, userid, passwd);
				PreparedStatement pstmt = conn.prepareStatement(
						"select ranking, groupname, solvednum "
						+ "from GroupRanking "
						+ "where ranking = (select ranking from GroupRanking where groupname = ?) - 1");
			)
		{
			pstmt.setString(1, groupname);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return new Object[] {rs.getInt(1), rs.getString(2), rs.getInt(3)};
		} 
		catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}
	
	public static void DropTableGroupRanking() {
		try(Connection conn = DriverManager.getConnection(address, userid, passwd);
				Statement stmt = conn.createStatement();
			)
		{
			stmt.executeUpdate("drop table GroupRanking");
		} 
		catch (SQLException e) {
			System.out.println(e);
		}
	}
}
