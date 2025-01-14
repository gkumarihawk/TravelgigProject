
package com.synex.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.synex.domain.User;
import com.synex.repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User save(User u) {
	    String password = u.getUserPassword();
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String hashedPassword = passwordEncoder.encode(password);
	    u.setUserPassword(hashedPassword); // Set the hashed password back to the user object
	    User user = userRepository.save(u);
	    return user;
	}


	@Override
	public User findByUserId(long uId) {
		Optional<User> u = userRepository.findById(uId);
		if(u.isPresent()) {
			return u.get();
		}else
		return null;
	}

	@Override
	public void deleteUserById(long uId) {
		userRepository.deleteById(uId);
		
	}

	@Override
	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
	
	public String findEmailByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user != null) {
            return user.getEmail();
        } else {
            return null;
        }
    }

}
