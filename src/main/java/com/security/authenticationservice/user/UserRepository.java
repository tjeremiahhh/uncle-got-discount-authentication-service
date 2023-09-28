package com.security.authenticationservice.user;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.security.authenticationservice.authentication.RegisterRequest;
import com.security.authenticationservice.common.repository.SimpleJdbcRepositoryImpl;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository extends SimpleJdbcRepositoryImpl {

    public User getUserByEmail(String email) {
        return null;
    }

    public User findByEmail(String email) {
        return null;
    }

    public String getUserDetails(String email) {
        StringBuilder sqlQuery = new StringBuilder(
            "SELECT "
            +"    NAME "
            +"FROM "
            +"    user "
            +"WHERE "
            +"    email_address = :email "
        );

        MapSqlParameterSource sqlParameters = new MapSqlParameterSource("email", email);

        return querySingleObject(sqlQuery.toString(), sqlParameters, String.class);
    }

    public long registerUser(RegisterRequest user) {
        StringBuilder sqlQuery = new StringBuilder(
            "INSERT INTO user"
            +"(name, email_address, password, phone_number, is_business_owner)"
            +"values"
            +"(:name, :emailAddress, :password, :phoneNumber, :isBusinessOwner)"
       );

       MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
       sqlParameters.addValue("name", user.getName());
       sqlParameters.addValue("emailAddress", user.getEmailAddress());
       sqlParameters.addValue("password", user.getPassword());
       sqlParameters.addValue("phoneNumber", user.getPhoneNumber());
       sqlParameters.addValue("isBusinessOwner", user.getIsBusinessOwner());

       return update(sqlQuery.toString(), sqlParameters);
    } 
}