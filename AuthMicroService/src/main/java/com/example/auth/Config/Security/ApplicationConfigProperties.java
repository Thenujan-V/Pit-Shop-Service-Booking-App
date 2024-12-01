package com.example.auth.Config.Security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties("app")
@Configuration
public class ApplicationConfigProperties {


    private Jwt jwt;

    private Endpoint endpoint;

    private String baseUrl;

    private Crypto crypto;

    @Data
    public static class Jwt {

        private Long accessTokenExpiration;

        private String signingKey;

        private String authoritiesKey;

        private String tokenPrefix;

        private String headerString;

    }


    @Data
    public static class Endpoint {
        private String authBase;

        private String login;

        private String logout;

        private String getAllUsers;

        private String createPersonnel;

        private String getOfficers;

        private String courseBase;

        private String createCourse;

        private String getCourses;

        private String createCourseSubTopic;

        private String getCourseSubTopics;

        private String examBase;

        private String updateMyPassword;
        private String updatePassword;

    }

    @Data
    public static class Crypto {

        private String password;
        private String salt;

        private String refreshTokenSalt;

    }

}