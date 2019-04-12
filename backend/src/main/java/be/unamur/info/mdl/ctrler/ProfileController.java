package be.unamur.info.mdl.ctrler;

import be.unamur.info.mdl.config.security.SecurityUtils;
import be.unamur.info.mdl.dto.*;
import be.unamur.info.mdl.service.ProfileService;
import be.unamur.info.mdl.service.SearchService;
import be.unamur.info.mdl.service.UserService;
import be.unamur.info.mdl.service.exceptions.InvalidCredentialException;
import be.unamur.info.mdl.service.exceptions.RegistrationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/profile")
public class ProfileController {

  @Autowired
  private ProfileService profileService;

  @RequestMapping(path="/basic", method = RequestMethod.GET)
  public ResponseEntity getBasicInfo(@RequestParam(required = true) String username){
    if(username == "") return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please specify a username");
    ProfileBasicInfoDTO dto = profileService.getBasicInfo(username);
    if(dto==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username does not exist");
    return ResponseEntity.status(HttpStatus.OK).body(dto);
  }
}
