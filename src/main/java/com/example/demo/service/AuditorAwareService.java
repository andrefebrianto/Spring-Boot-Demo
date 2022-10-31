package com.example.demo.service;

import com.example.demo.common.constant.UserConstant;
import com.example.demo.model.entity.UserDetail;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("auditorProvider")
public class AuditorAwareService implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null
                && authentication.getPrincipal() != UserConstant.ANONYMOUS_USER.getName()) {
            UserDetail principal = (UserDetail) authentication.getPrincipal();
            return Optional.of(principal.getId());
        }
        return Optional.empty();
    }
}
