/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author algone
 */
public class SecureFilter implements Filter {

    /**
     * If a username is saved we assume the session is valid
     */
    public final String USERNAME = "userId";
    Logger LOG = LoggerFactory.getLogger(SecureFilter.class);

    @Override
    public Result filter(FilterChain chain, Context context) {

        // if we got no cookies we break:
        if (context.getRequestPath().equals("/user/logout")) {
            context.getSession().clear();
            return Results.forbidden().html().template("/views/system/logout.ftl.html");
        } else if (context.getRequestPath().equals("/form/login")) {
            return Results.forbidden().html().template("/views/layout/login.ftl.html");
        } else if (context.getRequestPath().equals("/form/register")) {
            return Results.forbidden().html().template("/views/layout/register.ftl.html");
        } else {
            if (context.getSession() == null
                    || context.getSession().get(USERNAME) == null) {

                if (context.getRequestPath().contains("assets") || context.getRequestPath().contains("favicon")) {
                    return chain.next(context);
                } else if (context.getRequestPath().contains("/user/login")) {
                    return chain.next(context);
                } else if (context.getRequestPath().contains("/user/register")) {
                    return chain.next(context);
                } else {
                    LOG.info("Trying to access: " + context.getRequestPath());
                    return Results.forbidden().html().template("/views/system/403forbidden.ftl.html");
                }

            } else {

                return chain.next(context);
            }
        }
    }
}
