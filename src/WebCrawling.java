import java.io.IOException;
import java.util.*;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebCrawling 
{

	public static void RefreshGroups() 
	{
		ArrayList<group> groups = new ArrayList<>();
		String URL = "https://www.acmicpc.net/ranklist/school/";
		
		try {
			for(int i=1; i<=2; i++) 
			{
				Document doc = Jsoup.connect(URL+i).get();
				
				for(int j=1; j<=100; j++) 
				{
					Element name = doc.selectFirst("#ranklist > tbody > tr:nth-child("+j+") > td:nth-child(2) > a");
					Element solvednum = doc.selectFirst("#ranklist > tbody > tr:nth-child("+j+") > td:nth-child(4) > a");
					groups.add(new group(name.text(), Integer.parseInt(solvednum.text())));
				}	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Collections.sort(groups);
		ConnectMySQL.UpdateGroupRanking(groups);
		
	}

}
class group implements Comparable<group>
{
	String groupname;
	int solvednum;
	
	group(String groupname, int solvednum){
		this.groupname = groupname;
		this.solvednum = solvednum;
	}
	@Override
	public int compareTo(group o) {
		// TODO Auto-generated method stub
		return o.solvednum - solvednum;
	}
}
