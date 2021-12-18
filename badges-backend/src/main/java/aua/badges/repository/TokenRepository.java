package aua.badges.repository;

import aua.badges.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author davitv
 */

@Transactional
@Repository
public interface TokenRepository extends MongoRepository<Token,String> {
Optional<Token> findByToken(String token);
    Optional<Token> findByUserId(String userId);
}
