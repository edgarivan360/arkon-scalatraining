package schema

import cats.effect.Effect
import model.ShopType
import repository.MasterRepository
import sangria.schema.{Field, IntType, ObjectType, StringType, fields}

object ShopTypeType {

  def apply[F[_]: Effect]: ObjectType[MasterRepository[F], ShopType] =
    ObjectType(
      name = "ShopType",
      fieldsFn = () => fields(

        Field(
          name = "id",
          fieldType = IntType,
          description = Some("Shop type ID"),
          resolve = _.value.id),

        Field(
          name = "name",
          fieldType = StringType,
          description = Some("Shop type name"),
          resolve = _.value.name)

      )
    )

}
