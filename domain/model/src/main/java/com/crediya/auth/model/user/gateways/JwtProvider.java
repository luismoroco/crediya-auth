package com.crediya.auth.model.user.gateways;

import com.crediya.auth.model.user.User;

public interface JwtProvider {

  String generate(User user);
}
