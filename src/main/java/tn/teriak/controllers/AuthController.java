package tn.teriak.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.teriak.model.Role;
import tn.teriak.model.User;
import tn.teriak.payload.request.LoginRequest;
import tn.teriak.payload.request.SignupRequest;
import tn.teriak.payload.response.JwtResponse;
import tn.teriak.payload.response.MessageResponse;
import tn.teriak.repository.RoleRepository;
import tn.teriak.repository.UserEmailRepository;
import tn.teriak.repository.UserRepository;
import tn.teriak.security.jwt.JwtUtils;
import tn.teriak.security.services.UserDetailsImpl;




@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;
  @Autowired
  UserEmailRepository useremailrepo;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         "Bearer", userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles,userDetails.getNom(),userDetails.getPrenom(),userDetails.getMatricule() ));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByMatricule(signUpRequest.getMatricule())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

  
    User user = new User(signUpRequest.getUsername(), signUpRequest.getMatricule(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()), signUpRequest.getNom(), signUpRequest.getPrenom());
    Integer intRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();
    Role userRole = roleRepository.findById(2)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
		/*
		 * if (strRoles == null) { Role userRole =
		 * roleRepository.findByName(ERole.ROLE_USER) .orElseThrow(() -> new
		 * RuntimeException("Error: Role is not found.")); roles.add(userRole); } else {
		 * strRoles.forEach(role -> { switch (role) { case "admin": Role adminRole =
		 * roleRepository.findByName(ERole.ROLE_ADMIN) .orElseThrow(() -> new
		 * RuntimeException("Error: Role is not found.")); roles.add(adminRole);
		 * 
		 * break; case "mod": Role modRole =
		 * roleRepository.findByName(ERole.ROLE_MODERATOR) .orElseThrow(() -> new
		 * RuntimeException("Error: Role is not found.")); roles.add(modRole); break;
		 * case "rh": Role rhRole = roleRepository.findByName(ERole.ROLE_RH)
		 * .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		 * roles.add(rhRole); break; default: Role userRole =
		 * roleRepository.findByName(ERole.ROLE_USER) .orElseThrow(() -> new
		 * RuntimeException("Error: Role is not found.")); roles.add(userRole); } }); }
		 */

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
  @GetMapping("/getall")
	public List<Role>  getall(){
		return (List <Role>) roleRepository.findAll();
	}
  @GetMapping("/getuser/{id}")
 	public Optional<User> getalluser(@PathVariable("id")int id){
 		return  userRepository.findById(id);
 	}
  @GetMapping("/getbonjour")
	public String getBonjour(){
		return  "bonjour";
	}
  @GetMapping("/getroless")
  public List<Role> getroles(){
	return roleRepository.findAll();
	  
  }
  @PostMapping("/signupt")
  public ResponseEntity<?> updateUclt(  @RequestBody SignupRequest signUpRequest) {
	  if (!userRepository.existsByEmail(signUpRequest.getEmail())) {
	      return ResponseEntity
	          .badRequest()
	          .body(new MessageResponse("Error: Email not exist!"));
	    }

	   
	     Optional<User> usData = userRepository.findByEmail(signUpRequest.getEmail());
	    if (usData.isPresent()) {
	    	User prodd = usData.get();
	    prodd.setActive(1);
	    prodd.setEmail(signUpRequest.getEmail());
	    prodd.setMatricule(signUpRequest.getMatricule());
	    prodd.setNom(signUpRequest.getNom());
	    prodd.setPassword(signUpRequest.getPassword());
	    prodd.setPrenom(signUpRequest.getPrenom());
	    Integer intRoles = signUpRequest.getRole();
	    Set<Role> roles = new HashSet<>();
	    Role userRole = roleRepository.findById(intRoles)
	            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	        roles.add(userRole);
	   
	     return new ResponseEntity<>(userRepository.save(prodd), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    } }
  @PostMapping("/signuptt33")
  public ResponseEntity<?> updatesgt( @RequestBody SignupRequest signUpRequest) {
	  if (!useremailrepo.existsByEmail(signUpRequest.getEmail())) {
	      return ResponseEntity
	          .badRequest()
	          .body(new MessageResponse("Error: Email not exist!"));
	    }

	   
	  User user = new User(signUpRequest.getUsername(), signUpRequest.getMatricule(),
	            signUpRequest.getEmail(),
	            encoder.encode(signUpRequest.getPassword()), signUpRequest.getNom(), signUpRequest.getPrenom());
	    Integer intRoles = signUpRequest.getRole();
	    Set<Role> roles = new HashSet<>();
	    Role userRole = roleRepository.findById(2)
	            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	        roles.add(userRole);
			/*
			 * if (strRoles == null) { Role userRole =
			 * roleRepository.findByName(ERole.ROLE_USER) .orElseThrow(() -> new
			 * RuntimeException("Error: Role is not found.")); roles.add(userRole); } else {
			 * strRoles.forEach(role -> { switch (role) { case "admin": Role adminRole =
			 * roleRepository.findByName(ERole.ROLE_ADMIN) .orElseThrow(() -> new
			 * RuntimeException("Error: Role is not found.")); roles.add(adminRole);
			 * 
			 * break; case "mod": Role modRole =
			 * roleRepository.findByName(ERole.ROLE_MODERATOR) .orElseThrow(() -> new
			 * RuntimeException("Error: Role is not found.")); roles.add(modRole); break;
			 * case "rh": Role rhRole = roleRepository.findByName(ERole.ROLE_RH)
			 * .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			 * roles.add(rhRole); break; default: Role userRole =
			 * roleRepository.findByName(ERole.ROLE_USER) .orElseThrow(() -> new
			 * RuntimeException("Error: Role is not found.")); roles.add(userRole); } }); }
			 */

	    user.setRoles(roles);
	    userRepository.save(user);

	    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	  }
}
