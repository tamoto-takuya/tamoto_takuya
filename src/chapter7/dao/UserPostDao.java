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

public class UserPostDao {

	public List<User> posts(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM posts";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<User> ret = toPostList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//
	private List<User> toPostList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				int postId = rs.getInt("post_id");
				String postName = rs.getString("post_name");

				User user = new User();
				user.setPostId(postId);
				user.setPostName(postName);

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}



}
