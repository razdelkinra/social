package com.otus.social.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class SocialUserDetails(
        var id: Long = 0,
        username: String?,
        password: String?,
        enabled: Boolean,
        accountNonExpired: Boolean,
        credentialsNonExpired: Boolean,
        accountNonLocked: Boolean,
        authorities: MutableCollection<out GrantedAuthority>?) :
        User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities)
