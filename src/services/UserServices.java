package services;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.UserDTO;

public class UserServices {

	public void insertUser(String userName, String userNick, String userPassword, int roleCode) 
			throws SQLException, ClassNotFoundException{
		String query = "SELECT user__insert(?,?,?,?)";
		java.sql.Connection connection = ServicesLocator.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, userName);
		preparedStatement.setString(2, userNick);
		preparedStatement.setString(3, userPassword);
		preparedStatement.setInt(4, roleCode);
		preparedStatement.execute();
		preparedStatement.close();
		connection.close();
	}
	
	public void deleteUser(int userCode) throws SQLException, ClassNotFoundException{
		String query = "SELECT user__delete(?)";
		java.sql.Connection connection = ServicesLocator.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, userCode);
		preparedStatement.execute();
		preparedStatement.close();
		connection.close();
	}
	
	public void updateUser(int userCode, String userName, String userNick, String userPassword, int roleCode) 
			throws SQLException, ClassNotFoundException{
		String query = "SELECT user__update(?,?,?,?,?)";
		java.sql.Connection connection = ServicesLocator.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, userCode);
		preparedStatement.setString(2, userName);
		preparedStatement.setString(3, userNick);
		preparedStatement.setString(4, userPassword);
		preparedStatement.setInt(5, roleCode);
		preparedStatement.execute();
		preparedStatement.close();
		connection.close();
	}
	
	public UserDTO findUser(int userCode) throws SQLException, ClassNotFoundException{
		String query = "SELECT * FROM find_user_(?)";
		java.sql.Connection connection = ServicesLocator.getConnection();
		PreparedStatement preparedFunction = connection.prepareStatement(query);
		preparedFunction.setInt(1, userCode);
		preparedFunction.execute();
		ResultSet rs = preparedFunction.getResultSet();
		rs.next();
		UserDTO user = new UserDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
		rs.close();
		preparedFunction.close();
		connection.close();
		return user;
	}
	
	public ArrayList<UserDTO> selectAllUsers() throws SQLException, ClassNotFoundException{
		ArrayList<UserDTO> users = new ArrayList<UserDTO>();
		String function = "{?= call select_all_user_()}";
		java.sql.Connection connection = ServicesLocator.getConnection();
		connection.setAutoCommit(false);
		CallableStatement preparedFunction = connection.prepareCall(function);
		preparedFunction.registerOutParameter(1, java.sql.Types.OTHER);
		preparedFunction.execute();
		ResultSet rs = (ResultSet) preparedFunction.getObject(1);
		while (rs.next()){
			users.add(new UserDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
		}
		rs.close();
		preparedFunction.close();
		connection.close();
		return users;
	}
}
