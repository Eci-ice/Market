package sql;

import java.sql.SQLException;

import vo.user;

public interface usersql {
	public user search(String username) throws SQLException;
	public void modifypwd(String username,String pwd) throws SQLException;
}
