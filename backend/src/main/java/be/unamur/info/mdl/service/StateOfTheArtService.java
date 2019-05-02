package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.exceptions.ArticleNotFoundException;
import be.unamur.info.mdl.service.exceptions.SotaAlreadyExistException;
import be.unamur.info.mdl.service.exceptions.SotaNotFoundException;
import be.unamur.info.mdl.service.exceptions.UsernameNotFoundException;

public interface StateOfTheArtService {


  /**
   * Retrieve a SoTA specified by the provided reference
   *
   * @param reference The provided reference
   * @return The data of the matching SoTA.
   * @throws IllegalArgumentException if the provided reference is null.
   * @throws SotaNotFoundException The referenced SoTA is not found in persistence.
   */
  StateOfTheArtDTO getSotaByReference(String reference) throws SotaNotFoundException;


  /**
   * Persist a new SoTA.
   *
   * @param data The data of the new SoTA.
   * @param creator The creator of this SoTA.
   * @return The sucessfuly persisted SoTA
   * @throws SotaAlreadyExistException The provided SoTA data is already saved.
   * @throws ArticleNotFoundException If the provided article reference is not in persistence.
   */
  StateOfTheArtDTO create(StateOfTheArtDTO data, UserDTO creator)
    throws SotaAlreadyExistException, ArticleNotFoundException;

  boolean delete (String reference, String username)
    throws  UsernameNotFoundException;

  StateOfTheArtDTO put (String reference, String username, StateOfTheArtDTO data)
  throws  UsernameNotFoundException;

}

