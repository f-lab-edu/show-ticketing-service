package com.show.showticketingservice.model.user

import com.show.showticketingservice.model.enumerations.UserType

class UserSession(
        private val userId: Int,
        private val userType: UserType
)