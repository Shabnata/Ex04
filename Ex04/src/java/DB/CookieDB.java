
package DB;

import javax.servlet.http.*;

public class CookieDB {
    public static Cookie getCookieValue(Cookie[] cookies, String cookieName) {
        //String cookieValue = "";
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }
	
	
}



