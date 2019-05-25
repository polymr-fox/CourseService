package com.mrfox.senyast4745.coursesevice.security.jwt;

import org.springframework.security.core.AuthenticationException;

class InvalidJwtAuthenticationException extends AuthenticationException {
    InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}
