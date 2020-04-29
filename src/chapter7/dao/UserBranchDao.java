package chapter7.dao;

import static chapter7.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import chapter7.beans.User;
import chapter7.exception.SQLRuntimeException;

public class UserBranchDao {

	public List<User> branches(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM branches";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toBranchList(rs);
			return userList;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<User> toBranchList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				int branchId = rs.getInt("branch_id");
				String branchName = rs.getString("branch_name");

				User user = new User();
				user.setBranchId(branchId);
				user.setBranchName(branchName);

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}
}