package com.edorm.utils;

import java.security.Principal;

public final class PrincipalUtil {
    
    public static long getUserId(Principal principal) {
        return Long.parseLong(principal.getName());
    }
    
}
