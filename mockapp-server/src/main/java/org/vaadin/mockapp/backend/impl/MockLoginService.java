package org.vaadin.mockapp.backend.impl;

import org.jetbrains.annotations.NotNull;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.LoginService;
import org.vaadin.mockapp.backend.authentication.AuthenticationHolder;
import org.vaadin.mockapp.backend.authentication.Roles;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Very simple implementation of {@link org.vaadin.mockapp.backend.LoginService}. <b>Do not use in real applications!</b>
 *
 * @author petter@vaadin.com
 */
@WebListener
public class MockLoginService implements LoginService, ServletContextListener {

    @Override
    public boolean login(@NotNull String username, @NotNull String password) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            AuthenticationHolder.setAuthentication(new SimpleAuthentication("admin", Roles.ROLE_ADMIN));
            return true;
        } else if ("user".equals(username) && "user123".equals(password)) {
            AuthenticationHolder.setAuthentication(new SimpleAuthentication("user", Roles.ROLE_USER));
            return true;
        }
        Logger.getLogger(MockLoginService.class.getCanonicalName()).log(Level.WARNING, "Login failed for user {0}", username);
        return false;
    }

    @Override
    public void logout() {
        AuthenticationHolder.setAuthentication(null);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Services.register(this, LoginService.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Services.remove(LoginService.class);
    }
}