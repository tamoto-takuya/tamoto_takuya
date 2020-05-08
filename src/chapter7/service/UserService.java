package chapter7.service;

import static chapter7.utils.CloseableUtil.*;
import static chapter7.utils.DBUtil.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import chapter7.beans.User;
import chapter7.dao.UserDao;
import chapter7.utils.CipherUtil;


public class UserService {

	public int register(User user) throws SQLException {

		Connection connection = null;
		try {
			connection = getConnection();

			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);

			UserDao userDao = new UserDao();

			if (userDao.loginId(connection, user)==null) {
				userDao.insert(connection, user);
				commit(connection);
				return 1;
			} else {
				return 0;
			}


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

	private static final int LIMIT_NUM = 1000;

	public List<User> getUsers() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userListDao = new UserDao();
			List<User> ret = userListDao.getUsers(connection, LIMIT_NUM);

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

	public User getUser(int userId) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			User user = userDao.getUser(connection, userId);

			commit(connection);

			return user;
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

	public int update(User user) throws SQLException {

		Connection connection = null;
		try {
			connection = getConnection();
			if (StringUtils.isEmpty(user.getPassword()) == false) {
				String encPassword = CipherUtil.encrypt(user.getPassword());
				user.setPassword(encPassword);
			}

			UserDao userDao = new UserDao();
			if (userDao.editLoginId(connection, user)==null) {
				userDao.update(connection, user);
				commit(connection);
				return 1;
			} else {
				return 0;
			}

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

	public void updateStatus(User user) {
		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			userDao.updateStatus(connection, user);

			commit(connection);
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