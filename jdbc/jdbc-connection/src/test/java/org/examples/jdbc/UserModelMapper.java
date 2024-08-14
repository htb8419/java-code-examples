package org.examples.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class UserModelMapper implements Function<ResultSet, UserModel> {

    private final String usernameColumn;
    private final String passwordColumn;
    private final String enabledColumn;

    public UserModelMapper(String usernameColumn, String passwordColumn, String enabledColumn) {
        this.usernameColumn = usernameColumn;
        this.passwordColumn = passwordColumn;
        this.enabledColumn = enabledColumn;
    }

    @Override
    public UserModel apply(ResultSet resultSet) {
        try {
            return mapping(resultSet);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private UserModel mapping(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        UserModelBuilder builder = UserModelBuilder.newInstance();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = resultSetMetaData.getColumnName(i);
            if (usernameColumn.equalsIgnoreCase(columnName)) {
                builder.setUsername(resultSet.getString(i));
            } else if (passwordColumn.equalsIgnoreCase(columnName)) {
                builder.setPassword(resultSet.getString(i));
            } else if (enabledColumn.equalsIgnoreCase(columnName)) {
                builder.setEnabled(resultSet.getBoolean(i));
            } else {
                builder.addExtraAttributes(columnName, resultSet.getObject(i));
            }
        }

        return builder.build();
    }

    static class UserModelBuilder {
        String username = null;
        String password = null;
        boolean enabled = false;
        Map<String, Object> extraAttributes = new HashMap<>();

        static UserModelBuilder newInstance() {
            return new UserModelBuilder();
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void addExtraAttributes(String key, Object val) {
            this.extraAttributes.put(key, val);
        }
        public UserModel build(){
            return new UserModel(username,password,enabled,extraAttributes);
        }
    }
}
