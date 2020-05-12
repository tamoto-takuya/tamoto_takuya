package chapter7.service;

import static chapter7.utils.CloseableUtil.*;
import static chapter7.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import chapter7.beans.User;
import chapter7.dao.UserPostDao;

public class PostService {

	private static final int LIMIT_NUM = 1000;

	public List<User> getPosts() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserPostDao postListDao = new UserPostDao();
			List<User> ret = postListDao.posts(connection, LIMIT_NUM);

			commit(connection);
			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}


}
