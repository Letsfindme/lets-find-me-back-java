package com.fadi.imhere.service;


import com.fadi.imhere.Utils.DtoUtils;
import com.fadi.imhere.config.services.UserPrinciple;
import com.fadi.imhere.dtos.UserDto;
import com.fadi.imhere.repository.UserRepository;
import com.fadi.imhere.model.User;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Fadi NOUFAL
 */
@Service("userService")
public class UserServiceImp implements UserDetailsService, UserService {


    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //@Override
    public UserDetails loadUserByUsername0(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(u -> new org.springframework.security.core.userdetails.User(
                        u.getUsername(),
                        u.getPassword(),
                        u.isActive(),
                        u.isActive(),
                        u.isActive(),
                        u.isActive(),
                        AuthorityUtils.createAuthorityList(
                                u.getRoles()
                                        .stream()
                                        .map(r -> "ROLE_" + r.getName())
                                        .collect(Collectors.toList())
                                        .toArray(new String[]{}))))
                .orElseThrow(() -> new UsernameNotFoundException("No user with "
                        + "the name " + username + "was found in the database"));
    }

    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
        return UserPrinciple.build(user);
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }


    public User findById(int id) {
        return null;
    }

    public User findById(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.isPresent() ? optionalUser.get() : null;
    }


    public UserDto updateProfile(UserDto userDTO) {
        User currentUser = userRepository.findByUsername(userDTO.getUsername()).get();
        ModelMapper mm = new ModelMapper();
        mm.map(userDTO, currentUser);

        UserDto userToReturn = null;
//        User userToSave = DtoUtils.convertUserToEntity(userDTO);
        userRepository.save(currentUser);
        userToReturn = DtoUtils.convertUserToDto(currentUser);
        return userToReturn;
    }

    public UserDto getProfile(String username) {
        UserDto userToReturn = null;
        userToReturn = DtoUtils.convertUserToDto(userRepository.findByUsername(username).get());
        return userToReturn;
    }


    /*@Override
    public UserDto update(UserDto userDTO) {
        User user = findAvgById(userDTO.getId());
        if(user != null) {
            BeanUtils.copyProperties(userDTO, user, "password");
            userRepository.save(user);
        }
        return userDTO;
    }
*/

/*
    public User save(UserDto user) {
        User newUser = new User();
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setAge(user.getAge());
        return userRepository.save(newUser);
    }





    @Override
    public void createUserAccount(UserDto accountDto) {
        final User user = new User();
        user.setName(accountDto.getFirstname());
        user.setLastname(accountDto.getLastname());
        user.setEmail(accountDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));
        user.setBio(accountDto.getBio());
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);

    }
    */
}



