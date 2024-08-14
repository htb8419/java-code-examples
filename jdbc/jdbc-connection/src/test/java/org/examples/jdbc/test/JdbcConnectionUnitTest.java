package org.examples.jdbc.test;

import org.examples.jdbc.UserModel;
import org.examples.jdbc.UserModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class JdbcConnectionUnitTest {
    private final Logger logger = LoggerFactory.getLogger(JdbcConnectionUnitTest.class);
    private static final Properties applicationProperties = new Properties();
    static UserModelMapper userModelMapper;
    static String findUserQuery;

    @BeforeAll
    public static void setup() throws IOException {
        try (InputStream is = ClassLoader.getSystemResourceAsStream("application.properties")) {
            applicationProperties.load(is);
        }
        try (InputStream is = ClassLoader.getSystemResourceAsStream("find-user-query.sql")) {
            findUserQuery = new String(Objects.requireNonNull(is).readAllBytes(), StandardCharsets.UTF_8);
        }
        userModelMapper = new UserModelMapper("USER_NAME", "PASSWORD", "ENABLED_FLG");
    }

    @ParameterizedTest
    @ValueSource(strings = "m_ghaderi")
    public void testFindUserByUsername(String username) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(findUserQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return;
            }

            UserModel userModel = userModelMapper.apply(resultSet);
            Assertions.assertNotNull(userModel.username());
            Assertions.assertNotNull(userModel.password());
            Assertions.assertTrue(userModel.isEnabled());
            Assertions.assertNotNull(userModel.extraAttributes());
            Assertions.assertEquals(3, userModel.extraAttributes().size());
            if (logger.isTraceEnabled()) {
                userModel.extraAttributes().forEach((key, val) -> {
                    logger.trace("user.extraAttribute >> [key={}, val={}] \n", key, val);

                });
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                applicationProperties.getProperty("db.url"),
                applicationProperties.getProperty("db.username"),
                applicationProperties.getProperty("db.password")
        );
    }
}
