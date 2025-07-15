package hexlet.code.controller;

import io.javalin.http.Context;

import java.sql.SQLException;

public interface Controller {
    void index(Context ctx) throws SQLException;
    void show(Context ctx) throws SQLException;
    void create(Context ctx) throws SQLException;
}
