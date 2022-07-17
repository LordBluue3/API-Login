package com.mikael.api.dao;

import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;
import com.mikael.api.user.User;

public class UserAdapter implements SQLResultAdapter<User> {

    @Override
    public User adaptResult(SimpleResultSet result) {
        return new User(result.get("email"), result.get("password"));
    }
}
