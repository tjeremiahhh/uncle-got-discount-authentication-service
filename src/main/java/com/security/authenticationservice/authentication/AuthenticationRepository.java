package com.security.authenticationservice.authentication;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.security.authenticationservice.common.repository.SimpleJdbcRepositoryImpl;
import com.security.authenticationservice.user.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthenticationRepository extends SimpleJdbcRepositoryImpl {

    public User getUserByEmail(String emailAddress) {
        StringBuilder sqlQuery = new StringBuilder(
            "SELECT "
            +"    * "
            +"FROM "
            +"    user "
            +"WHERE "
            +"    email_address = :emailAddress "
        );

        MapSqlParameterSource sqlParameters = new MapSqlParameterSource("emailAddress", emailAddress);

        return querySingleObject(sqlQuery.toString(), sqlParameters, User.class);
    }

    public User getUserDetails(String emailAddress) {
        StringBuilder sqlQuery = new StringBuilder(
            "SELECT "
            +"    * "
            +"FROM "
            +"    user "
            +"WHERE "
            +"    email_address = :emailAddress "
        );

        MapSqlParameterSource sqlParameters = new MapSqlParameterSource("emailAddress", emailAddress);

        return querySingleObject(sqlQuery.toString(), sqlParameters, User.class);
    }

    public long registerUser(User user) {
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
