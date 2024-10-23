package cz.moro.usermanagment.annotations

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("isFullyAuthenticated() and hasRole('ADMIN')")
annotation class IsAdminLogged
