package com.github.supercoding.respository.users;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcTemplateDao implements UserRepository{
    private JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateDao(@Qualifier("jdbcTemplate2") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static RowMapper<UserEntity> userEntityRowMapper = ( (rs, rowNum)->
            new UserEntity.UserEntityBuilder()
                    .userId(rs.getInt("user_id"))
                    .userName(rs.getNString("user_name"))
                    .likeTravelPlace(rs.getNString("like_travel_place"))
                    .phoneNum(rs.getNString("phone_num"))
                    .build());

    @Override
    public UserEntity findUserById(Integer userId) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = ?", userEntityRowMapper, userId);
    }
}
