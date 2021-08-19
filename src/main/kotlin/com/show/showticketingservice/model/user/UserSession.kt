package com.show.showticketingservice.model.user

import com.show.showticketingservice.model.enumerations.UserType

data class UserSession(

        val userId: Int,

        val userType: UserType

)