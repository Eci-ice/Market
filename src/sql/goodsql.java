package sql;

import java.sql.SQLException;
import java.util.List;

import vo.good;

public interface goodsql {
	public void add(good good) throws SQLException;
	public int remove(int goodid) throws SQLException;
	public int unique(String goodname) throws SQLException;
	public int oldunique() throws SQLException;
	public good search(int goodid) throws SQLException;
	public List<good> searchls(String keyword,int power) throws SQLException;
	public void modifystate(int goodid,int tostate) throws SQLException;
	public List<good> showall() throws SQLException;
	public List<good> showhistoryall() throws SQLException;
	public List<good> shownow() throws SQLException;
}
