package hexlet.code.controller;

import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public abstract class ApplicationController implements Controller {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void index(Context ctx) throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'index'");
    }

    @Override
    public void create(Context ctx) throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void show(Context ctx) throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'show'");
    }
}
