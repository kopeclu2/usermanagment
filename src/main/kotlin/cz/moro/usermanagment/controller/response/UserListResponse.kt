package cz.moro.usermanagment.controller.response

data class UserListResponse(
    val data: List<UserResponse>,
    val pageInfo: PageInfoResponse
)
