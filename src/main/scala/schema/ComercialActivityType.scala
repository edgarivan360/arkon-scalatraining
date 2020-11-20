package schema

import cats.effect.Effect
import model.ComercialActivity
import repository.MasterRepository
import sangria.schema.{Field, IntType, ObjectType, StringType, fields}

object ComercialActivityType {

  def apply[F[_]: Effect]: ObjectType[MasterRepository[F], ComercialActivity] =
    ObjectType(
      name = "ComercialActivity",
      fieldsFn = () => fields(

        Field(
          name = "id",
          fieldType = IntType,
          description = Some("Comercial activity ID"),
          resolve = _.value.id),

        Field(
          name = "name",
          fieldType = StringType,
          description = Some("Comercial activity name"),
          resolve = _.value.name)

      )
    )

}
