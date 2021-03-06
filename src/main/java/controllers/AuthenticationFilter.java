/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.inject.Inject;
import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.NinjaDefault;
import ninja.Result;
import ninja.Results;
import ninja.utils.NinjaProperties;
import org.apache.commons.lang3.StringUtils;
import services.NinjaKey;
import services.VibandaAuthService;

/**
 *
 * @author A4372949
 */
public class AuthenticationFilter implements Filter {

    @Inject
    private NinjaProperties ninjaProperties;

    @Inject
    private VibandaAuthService authentications;

    @Inject
    private NinjaDefault ninjaDefault;

    @Override
    public Result filter(FilterChain filterChain, Context context) {

        System.out.println("PATH: " + context.getRequestPath());
        if (context.getRequestPath().equals("/user/logout")) {
            System.out.println("Loging out PATH: " + context.getRequestPath());
            authentications.logout(context);
            return Results.forbidden().html().template("/views/system/logout.ftl.html");
        }
        if (StringUtils.isBlank(StringUtils.trimToNull(authentications.getAuthenticatedUser(context)))) {
            String redirect = ninjaProperties.get(NinjaKey.AUTH_REDIRECT_URL.get());

            if (context.getRequestPath().startsWith("/form")) {
                System.out.println("PATH: " + context.getRequestPath() + "   REDIRECTED TO:   " + redirect);
                return (StringUtils.isBlank(redirect)) ? ninjaDefault.getUnauthorizedResult(context) : Results.redirect(redirect);

            } else {
                System.out.println("PATH: " + context.getRequestPath() + "   Forwarded");
                return filterChain.next(context);
            }
        }

//        if (context.getRequestPath().startsWith("/api")) {
//            System.out.println("API .....pass through: " + context.getRequestPath());
//            return filterChain.next(context);
//        } else if (context.getRequestPath().equals("/user/logout")) {
//            authentications.logout(context);
//            return Results.forbidden().html().template("/views/system/logout.ftl.html");
//        }
//
//        System.out.println("PASS THOUGH PATH: " + context.getRequestPath());
        return filterChain.next(context);
    }
}
