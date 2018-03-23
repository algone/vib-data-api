/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author A4372949
 */
public enum NinjaKey {
    AUTH_COOKIE_NAME("auth.cookie.name"),
    AUTH_COOKIE_EXPIRES("auth.cookie.expires"),
    AUTH_REDIRECT_URL("auth.redirect.url"),
    APPLICATION_SECRET("application.secret"),
    AUTHENTICATED_USER("authenticateduser"),
    DEFAULT_AUTH_COOKIE_NAME("ninja-authentication");

    private final String value;

    NinjaKey(String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }
}
