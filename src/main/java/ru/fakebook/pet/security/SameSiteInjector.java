package ru.fakebook.pet.security;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SameSiteInjector {
    private final ApplicationContext applicationContext;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        DefaultCookieSerializer cookieSerializer = applicationContext.getBean(DefaultCookieSerializer.class);
        cookieSerializer.setSameSite("Strict");
    }
}
