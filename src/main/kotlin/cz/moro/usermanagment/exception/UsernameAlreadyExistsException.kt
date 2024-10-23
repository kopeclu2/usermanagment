package cz.moro.usermanagment.exception

class UsernameAlreadyExistsException(message: String, val value:String) : Exception(message)
