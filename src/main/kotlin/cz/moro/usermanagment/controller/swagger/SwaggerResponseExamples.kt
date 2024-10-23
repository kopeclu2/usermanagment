package cz.moro.usermanagment.controller.swagger

const val UNAUTHORIZED_JSON_RESPONSE = """
{
  "type": "about:blank",
  "title": "Unauthorized",
  "status": 401,
  "detail": "Authentication token is not valid.",
  "instance": null,
  "properties": null
}
"""

const val USER_NOT_FOUND_RESPONSE = """
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "User has not been found by id: 2.",
  "instance": "/api/v1/users/2",
  "errorCode": "USER_NOT_FOUND"
}
"""

const val VALIDATION_ERRORS_RESPONSE = """
{
  "errors": [
    {
      "path": "name",
      "message": "Name must be between 2 and 255 characters",
      "value": "",
      "objectName": "userRequest"
    },
    {
      "path": "name",
      "message": "Name cannot be blank",
      "value": "",
      "objectName": "userRequest"
    }
  ]
}
"""