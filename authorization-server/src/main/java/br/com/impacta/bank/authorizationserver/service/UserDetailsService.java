package br.com.impacta.bank.authorizationserver.service;

import br.com.impacta.bank.authorizationserver.domain.Authorities;
import br.com.impacta.bank.authorizationserver.dto.UserDto;
import br.com.impacta.bank.authorizationserver.model.Authority;
import br.com.impacta.bank.authorizationserver.model.User;
import br.com.impacta.bank.authorizationserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{
	
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
								.map(user -> new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user)))
								.orElseThrow(() -> new UsernameNotFoundException("User "+username+" Not found"));
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	private Collection<GrantedAuthority> getGrantedAuthorities(User user){
    	Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : user.getAuthorities()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
            grantedAuthorities.add(grantedAuthority);
        }
        
        return grantedAuthorities;
    }

    public UserDto saveUser(UserDto userDto) {

		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setEmail(userDto.getEmail());
		Set<Authority> set = new HashSet<Authority>(){{
			add(new Authority("ROLE_USER"));
		}};
		user.setAuthorities(set);
		this.userRepository.save(user);
		userDto.setPassword(null);
		return userDto;
	}
}
