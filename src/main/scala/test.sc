import io.circe.{ Json, JsonObject }

def formatString(s: String): Json = Json.obj(
  "errors" -> Json.arr(Json.obj(
    "message" -> Json.fromString(s))))

formatString("Hola Papu")