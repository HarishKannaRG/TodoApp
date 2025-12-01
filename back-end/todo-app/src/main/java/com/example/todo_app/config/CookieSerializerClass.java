package com.example.todo_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/***
 * When react app was hosted in netlify and spring boot on render, during login/signup the session id was not getting created in browser.
 * Due to which the authentication was failing. This happened because both were hosted in different websites and browsers do not allow cross-origin
 * session creation. To tackle that this class was created. However, if both react and spring boot are running in local, this issue will not araise.
 * Hence, when running in local comment this class
 */
@Configuration
public class CookieSerializerClass {
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setSameSite("None");
        cookieSerializer.setUseSecureCookie(true);
        return cookieSerializer;
    }

}
