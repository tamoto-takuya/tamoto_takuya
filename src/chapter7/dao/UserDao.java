package chapter7.dao;

import static chapter7.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import chapter7.beans.User;
import chapter7.exception.NoRowsUpdatedRuntimeException;
import chapter7.exception.SQLRuntimeException;

public class UserDao {

	public void insert(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users ( ");
			sql.append("login_id");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", post_id");
			sql.append(", created_date");
			sql.append(", updated_date");
			sql.append(") VALUES (");
			sql.append("?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP"); // created_date
			sql.append(", CURRENT_TIMESTAMP"); // updated_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranchId());
			ps.setInt(5, user.getPostId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<User> getUsers(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users";

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<User> ret = toUserList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<User> toUserList(ResultSet rs)
			throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				int id = rs.getInt("Id");
				String loginId = rs.getString("login_id");
				String name = rs.getString("name");
				int branchId = rs.getInt("branch_id");
				int postId = rs.getInt("post_id");
				Timestamp createdDate = rs.getTimestamp("created_date");
				Timestamp updatedDate = rs.getTimestamp("updated_date");

				User userList = new User();
				userList.setId(id);
				userList.setLoginId(loginId);
				userList.setName(name);
				userList.setBranchId(branchId);
				userList.setPostId(postId);
				userList.setCreatedDate(createdDate);
				userList.setUpdatedDate(updatedDate);

				ret.add(userList);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public User getUser(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void update(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();

			if (StringUtils.isEmpty(user.getPassword()) == true) {
				sql.append("UPDATE users SET");
				sql.append(" login_id = ?");
				sql.append(", name = ?");
				sql.append(", branch_id = ?");
				sql.append(", post_id = ?");
				sql.append(", updated_date = CURRENT_TIMESTAMP");
				sql.append(" WHERE");
				sql.append(" id = ?");

				ps = connection.prepareStatement(sql.toString());

				ps.setString(1, user.getLoginId());
				ps.setString(2, user.getName());
				ps.setInt(3, user.getBranchId());
				ps.setInt(4, user.getPostId());
				ps.setInt(5, user.getId());

			} else {
				sql.append("UPDATE users SET");
				sql.append(" login_id = ?");
				sql.append(", password = ?");
				sql.append(", name = ?");
				sql.append(", branch_id = ?");
				sql.append(", post_id = ?");
				sql.append(", updated_date = CURRENT_TIMESTAMP");
				sql.append(" WHERE");
				sql.append(" id = ?");

				ps = connection.prepareStatement(sql.toString());

				ps.setString(1, user.getLoginId());
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getName());
				ps.setInt(4, user.getBranchId());
				ps.setInt(5, user.getPostId());
				ps.setInt(6, user.getId());
			}

			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}


}



