package com.crediya.auth.r2dbc;

import com.crediya.auth.model.user.User;
import com.crediya.auth.model.user.gateways.UserRepository;
import com.crediya.auth.r2dbc.entity.UserEntity;
import com.crediya.auth.r2dbc.helper.ReactiveAdapterOperations;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
  User,
  UserEntity,
  Long,
  UserReactiveRepository
> implements UserRepository {

    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
      super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    public Mono<User> findByUserId(Long userId) {
        return super.findById(userId);
    }

    @Override
    public Mono<User> save(User user) {
        return super.save(user);
    }

    public Mono<Boolean> existsByEmail(String email) {
        return this.repository.existsByEmail(email);
    }

}
