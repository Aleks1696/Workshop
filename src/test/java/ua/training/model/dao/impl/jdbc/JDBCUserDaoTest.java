package ua.training.model.dao.impl.jdbc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.training.model.dao.UserDAO;
import ua.training.model.entity.User;
import ua.training.model.types.UserRole;
import javax.sql.DataSource;
import java.sql.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JDBCUserDaoTest {
    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement statement;
    @Mock
    private ResultSet resultSet;

    private User user;

    @Before
    public void initialize() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);

        user = new User();
        user.setId(2);
        user.setLogin("anna");
        user.setPassword("12anna");
        user.setRole(UserRole.CUSTOMER);
        user.setName("Anna");
        user.setName_ua("Анна");
        user.setSurname("Mulina");
        user.setSurname_ua("Mulina");
        user.setEmail("anna@gmail.com");
        user.setPhoneNumber("2453632");

        when(resultSet.getInt("id")).thenReturn(user.getId());
        when(resultSet.getString("login")).thenReturn(user.getLogin());
        when(resultSet.getString("password")).thenReturn(user.getPassword());
        when(resultSet.getString("role")).thenReturn(user.getRole().toString());
        when(resultSet.getString("name")).thenReturn(user.getName());
        when(resultSet.getString("name_ua")).thenReturn(user.getName_ua());
        when(resultSet.getString("surname")).thenReturn(user.getSurname());
        when(resultSet.getString("surname_ua")).thenReturn(user.getSurname_ua());
        when(resultSet.getString("email")).thenReturn(user.getEmail());
        when(resultSet.getString("phone_number")).thenReturn(user.getPhoneNumber());
    }

    @Test
    public void create() throws SQLException {
        UserDAO userDAO = new JDBCUserDao(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.getInt(anyInt())).thenReturn(1);
        User createdUser = userDAO.create(user);
        assertEquals(user, createdUser);
    }

    @Test
    public void findById() throws SQLException {
        UserDAO userDAO = new JDBCUserDao(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        User foundUser = userDAO.findById(user.getId());
        assertEquals(user, foundUser);
    }

    @Test
    public void findByLoginAndPasswordPositive() throws SQLException {
        UserDAO userDAO = new JDBCUserDao(connection);
        User userFromDB = userDAO.findByLoginAndPassword("anna", "12anna");
        assertEquals(user.getLogin(), userFromDB.getLogin());
        assertEquals(user.getPassword(), userFromDB.getPassword());
        assertEquals(user.getName(), userFromDB.getName());
        assertEquals(user.getName_ua(), userFromDB.getName_ua());
        assertEquals(user.getSurname(), userFromDB.getSurname());
        assertEquals(user.getSurname_ua(), userFromDB.getSurname_ua());
        assertEquals(user.getEmail(), userFromDB.getEmail());
        assertEquals(user.getRole(), userFromDB.getRole());
        assertEquals(user.getPhoneNumber(), userFromDB.getPhoneNumber());
    }
}