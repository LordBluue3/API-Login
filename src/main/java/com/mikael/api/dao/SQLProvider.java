package com.mikael.api.dao;

import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.connector.type.impl.MySQLDatabaseType;
import com.henryfabio.sqlprovider.connector.type.impl.SQLiteDatabaseType;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import lombok.Data;

import java.io.File;

import static java.text.Collator.PRIMARY;

@Data(staticConstructor = "of")
public class SQLProvider {

    public static SQLConnector connect() {
        return SQLiteDatabaseType.builder().file(new File("database/usuarios.db"))
        .build()
        .connect();
    }

    public static void createTable(SQLExecutor executor) {
        executor.updateQuery("CREATE TABLE IF NOT EXISTS user ("
                                  + "id       INTEGER PRIMARY KEY AUTOINCREMENT"
                                  + "               NOT NULL, "
                                  + "name     TEXT    NOT NULL, "
                                  + "email    TEXT    NOT NULL, "
                                  + "password TEXT    NOT NULL "
                              + ");");
        executor.updateQuery("INSERT INTO user(name, email , password) VALUES('mikael','mikael@gmail.com','entrar')");
    }
}
