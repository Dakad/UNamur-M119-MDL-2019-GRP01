package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.User;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.PasswordChangeDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is responsible of all possible services related to the /api/user
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;


  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public boolean register(UserDTO newUser) {
    // TODO Check if the userDTO is not null

    // TODO Check if the UserDTO's email is already taken

    // TODO Check if the UserDTO's username is already taken

    // userRepository.save(newUser);
    return true;
  }


  @Override
  public boolean login(CredentialDTO userLogin) {
    UserDTO userEntity = userRepository.findByUsername(userLogin.getUsername()).toDTO();
    if (checkPassword(userLogin, userEntity)) {
      return true;
    }
    return false;
  }


  @Override
  public boolean changePassword(String username, PasswordChangeDTO passwordChangeDTO) {
    User user = userRepository.findByUsername(username);
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    if (encoder.matches(passwordChangeDTO.getOldPassword(), user.getPassword())) {
      user.setPassword(passwordChangeDTO.getNewPassword());
      return true;
    }
    return false;
  }


  private boolean checkPassword(CredentialDTO userLogin, UserDTO userEntity) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.matches(userLogin.getPassword(), userEntity.getPassword());
  }
}