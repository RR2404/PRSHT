package moe.prashast.context;

import moe.prashast.dto.UserSession;

public class UserContext {
    private static final ThreadLocal<UserSession> context = new ThreadLocal<>();

    public static UserSession getCurrentUser() {
        UserSession user = context.get();
        return (user != null) ? user : new UserSession();
    }

    public static void setCurrentUser(UserSession user) {
        context.set(user);
    }

    public static void clear() {
        context.remove();
    }

}
