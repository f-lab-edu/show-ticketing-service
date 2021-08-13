package com.show.showticketingservice.model.user

import com.show.showticketingservice.model.enumerations.UserType

class UserResponse(

        val id: Int,

        val userId: String,

        val password: String,

        val name: String,

        val phoneNum: String,

        val email: String,

        val address: String,

        val userType: UserType

)