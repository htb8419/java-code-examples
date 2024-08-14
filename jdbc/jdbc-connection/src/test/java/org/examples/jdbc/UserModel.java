package org.examples.jdbc;

import java.util.Map;

public record UserModel(String username, String password, boolean isEnabled, Map<String, Object> extraAttributes) {

}
