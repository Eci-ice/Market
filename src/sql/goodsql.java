package sql;

import java.sql.SQLException;
import java.util.List;

import vo.good;

public interface goodsql {
	public void add(good good) throws SQLException;
	public void addtocart(int goodid,int buyer) throws SQLException ;
	public void modifybuynumber(int buyingid,int number) throws SQLException ;
	public int remove(int goodid) throws SQLException;
	public int unique(String goodname) throws SQLException;
	public int findcart(int goodid,int buyer) throws SQLException;
	public int oldunique() throws SQLException;
	public good search(int goodid) throws SQLException;
	public List<good> searchls(String keyword,String kind,int power,int userid,int ishistory) throws SQLException;
	public void modifystate(int goodid,int tostate) throws SQLException;
	public List<good> showall(int userid) throws SQLException;
	public List<good> showhistoryall(int userid) throws SQLException;
	public List<good> showbuyerall(int userid) throws SQLException;
	public List<good> shownow() throws SQLException;
	public good getGoodById(int goodId) throws SQLException;
    public boolean updateGood(good good) throws SQLException;
}
