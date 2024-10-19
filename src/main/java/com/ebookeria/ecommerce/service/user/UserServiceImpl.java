package com.ebookeria.ecommerce.service.user;

import com.ebookeria.ecommerce.dto.user.*;
import com.ebookeria.ecommerce.entity.Role;
import com.ebookeria.ecommerce.entity.User;
import com.ebookeria.ecommerce.exception.ResourceNotFoundException;
import com.ebookeria.ecommerce.repository.RoleRepository;
import com.ebookeria.ecommerce.repository.UserRepository;
import com.ebookeria.ecommerce.service.JWT.JWTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;


    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public UserResponse findAll(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortField).ascending():
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> users = userRepository.findAll(pageable);

        List<UserDTO> listOfUsers = users.stream().map(this::maptoDTO).toList();

        return new UserResponse(listOfUsers, users.getNumber(), users.getSize(), users.getNumberOfElements(), users.getTotalPages(), users.isLast());
    }


    @Override
    public UserDTO findById(int id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User with id: " + id + " not found"));
        return maptoDTO(user);
    }

    @Override
    public UserDTO save(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setFirstName(userCreateDTO.firstName());
        user.setLastName(userCreateDTO.lastName());
        user.setEmail(userCreateDTO.email());
        user.setPassword(encoder.encode(userCreateDTO.password()));
        user.setRegistrationDate(LocalDate.now());
        user.setEnabled(true);
        Role role = roleRepository.findByName("ROLE_USER");
        user.addRole(role);
        userRepository.save(user);
        return maptoDTO(user);


    }

    @Override
    public void update(UserUpdateDTO userUpdateDTO) {


        User user =getCurrentUser();

        if(!user.getEmail().equals(userUpdateDTO.email())) {
            user.setEmail(userUpdateDTO.email());
        }
        if(!user.getFirstName().equals(userUpdateDTO.firstName())) {
            user.setFirstName(userUpdateDTO.firstName());
        }
        if(!user.getLastName().equals(userUpdateDTO.lastName())) {
            user.setLastName(userUpdateDTO.lastName());
        }

         userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User with id: " + id + " not found"));
        userRepository.delete(user);
    }

    @Override
    public void changePassword(UserChangePasswordDTO userChangePasswordDTO) {
        User user = getCurrentUser();
        user.setPassword(encoder.encode(userChangePasswordDTO.password()));
        userRepository.save(user);

    }

    @Override
    public String verify(LoginUserDTO userDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.email(), userDTO.password()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(userDTO.email());
        }
        return "Fail";
    }

    @Override
    public User getCurrentUser() {
        String email =null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails){
            email = ((UserDetails)principal).getUsername();
        }
        String finalEmail = email;
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User with email:" + finalEmail + " not found"));
    }

    private UserDTO maptoDTO(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    public boolean isCurrentUserAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}
