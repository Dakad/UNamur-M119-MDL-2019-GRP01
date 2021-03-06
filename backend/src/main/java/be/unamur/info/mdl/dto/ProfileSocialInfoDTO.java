package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Model representing an user's profile social information")
public class ProfileSocialInfoDTO {

  @Builder.Default
  private String bio = "This user hasn't added a bio.";

  @JsonAlias("num_follows")
  private int numFollows;

  @JsonAlias("num_followers")
  private int numFollowers;

}
