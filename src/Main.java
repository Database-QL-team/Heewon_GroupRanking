
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("create table GroupRanking("
					+ "groupname varchar(60),"
					+ "solvednum int,"
					+ "ranking int primary key)");
		WebCrawling.RefreshGroups();
		int ranking = 1;
		for(group g : WebCrawling.groups)
			System.out.printf("insert into GroupRanking values(%s,%d,%d)\n", g.groupname, g.solvednum, ranking++);

		//ConnectMySQL.CreateTableGroupRanking();
		//WebCrawling.RefreshGroups();
		
		//String groupname = "이화여자대학교";
		//Object[] Rank = ConnectMySQL.GetRank(groupname);  // 순위, 문제수
		//System.out.println("우리 학교 순위: "+ Rank[0] + "위");
		
		//Object[] nextRank = ConnectMySQL.GetNextRank(groupname);  // 순위, 그룹이름, 문제수
		//System.out.println("다음 순위 "+nextRank[0]+"위 "+nextRank[1]+"까지 "
		//					+((int)nextRank[2]-(int)Rank[1])+"문제 남았습니다!\n");
		
		//System.out.println("전체 순위:");
		//ConnectMySQL.SelectAllfromGroupRanking();
		//ConnectMySQL.DropTableGroupRanking();
		
	}

}
