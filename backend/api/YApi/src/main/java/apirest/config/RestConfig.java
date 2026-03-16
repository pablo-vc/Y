package apirest.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.jackson.JacksonFeature;

import apirest.resources.Followers;
import apirest.resources.Notifications;
import apirest.resources.Posts;
import apirest.resources.Users;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest") // tu base URL
public class RestConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(JacksonFeature.class); // para que JSON funcione
        classes.add(Users.class);          // tu endpoint
        classes.add(Followers.class);
        classes.add(Posts.class);
        classes.add(Notifications.class);
        return classes;
    }
}