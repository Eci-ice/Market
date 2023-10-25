package sql;

import java.sql.SQLException;
import java.util.List;

import vo.good;

public interface goodsql {
	public void add(good good) throws SQLException;
	public int remove(int goodid) throws SQLException;
	public int unique() throws SQLException;
	public good search(int goodid) throws SQLException;
	public void modifystate(int goodid,int tostate) throws SQLException;
	public List<good> showall() throws SQLException;
	public List<good> shownow() throws SQLException;
}
