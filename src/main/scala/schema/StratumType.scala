package schema

import cats.effect.Effect
import model.Stratum
import repository.MasterRepository
import sangria.schema.{Field, IntType, ObjectType, StringType, fields}

object StratumType {

  def apply[F[_]: Effect]: ObjectType[MasterRepository[F], Stratum] =
    ObjectType(
      name = "Stratum",
      fieldsFn = () => fields(

        Field(
          name = "id",
          fieldType = IntType,
          description = Some("Stratum ID"),
          resolve = _.value.id),

        Field(
          name = "name",
          fieldType = StringType,
          description = Some("Stratum name"),
          resolve = _.value.name)

      )
    )

}
