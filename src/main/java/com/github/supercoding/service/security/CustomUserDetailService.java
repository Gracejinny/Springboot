package com.github.supercoding.service.security;

import com.github.supercoding.respository.roles.Roles;
import com.github.supercoding.respository.userDetails.CustomUserDetails;
import com.github.supercoding.respository.userPrincipal.UserPrincipal;
import com.github.supercoding.respository.userPrincipal.UserPrincipalRepository;
import com.github.supercoding.respository.userPrincipal.UserPrincipalRoles;
import com.github.supercoding.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Primary
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserPrincipalRepository userPrincipalRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserPrincipal userPrincipal = userPrincipalRepository.findByEmailFetchJoin(email)
                .orElseThrow(()->new NotFoundException("email에 해당하는 UserPrincipal 없습니다."));

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .userId(userPrincipal.getUser()
                                        .getUserId())
                .email(userPrincipal.getEmail())
                .password(userPrincipal.getPassword())
                .authorities(userPrincipal.getUserPrincipalRoles()
                        .stream()
                        .map(UserPrincipalRoles::getRoles)
                        .map(Roles::getName)
                        .collect(Collectors.toList()))
                .build();

        return customUserDetails;
    }
}
