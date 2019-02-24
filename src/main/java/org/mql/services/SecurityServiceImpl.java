package org.mql.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService{
	
	Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	MemberService memberService;
	
	@Override
	public String findLoggedInUsername() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails).getUsername();
        }
        return null;
	}
	
	@Override
	public void autoLogin(String username, String password) {
		UserDetails userDetails = memberService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails , password, userDetails.getAuthorities());
        try {
        	authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (Exception e) {
			logger.info("Error at Authenticate");
			e.printStackTrace();
		}
        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.info("Authenticated");
        }else {
        	logger.info("Not Authenticated");
        }
	}
}
