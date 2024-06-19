package org.examples.config;

import org.jboss.aerogear.security.otp.Totp;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class TotpAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //if user totp not active call super's method
        String presentedPassword = authentication.getCredentials().toString();
        String userSecret = getUserSecret(userDetails);
        Totp totp = new Totp(userSecret);
        if (!isValidLong(presentedPassword) || !totp.verify(presentedPassword)) {
            throw new BadCredentialsException("Invalid verification code");
        }
    }
    private String getUserSecret(UserDetails userDetails) {
        //load from db
        return "JBSWY3DPEHPK3PXP";
    }
    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
