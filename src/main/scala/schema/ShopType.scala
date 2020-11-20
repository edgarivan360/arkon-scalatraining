package schema

import cats.effect._
import cats.effect.implicits._
import model._
import repository._
import sangria.schema._

object ShopType {

  def apply[F[_]: Effect]: ObjectType[MasterRepository[F], Shop] =
    ObjectType(
      name = "Shop",
      fieldsFn = () => fields(

        Field(
          name = "id",
          fieldType = IntType,
          description = Some("Shop ID"),
          resolve = _.value.id),

        Field(
          name = "name",
          fieldType = StringType,
          description = Some("Shop name"),
          resolve = _.value.name),

        Field(
          name = "businessName",
          fieldType = OptionType(StringType),
          description = Some("Shop business name"),
          resolve = _.value.businessName),

        Field(
          name = "comercialActivity",
          fieldType = OptionType(ComercialActivityType[F]),
          description = Some("Shop comercial activity"),
          resolve = c => c.ctx.comercialActivity.findById(c.value.activityId.getOrElse(-1)).toIO.unsafeToFuture),

        Field(
          name = "stratum",
          fieldType = OptionType(StratumType[F]),
          description = Some("Shop stratum"),
          resolve = c => c.ctx.stratum.findById(c.value.stratumId.getOrElse(-1)).toIO.unsafeToFuture),

        Field(
          name = "address",
          fieldType = OptionType(StringType),
          description = Some("Shop address"),
          resolve = _.value.address),

        Field(
          name = "phoneNumber",
          fieldType = OptionType(StringType),
          description = Some("Shop phone number"),
          resolve = _.value.phoneNumber),

        Field(
          name = "email",
          fieldType = OptionType(StringType),
          description = Some("Shop emial"),
          resolve = _.value.email),

        Field(
          name = "website",
          fieldType = OptionType(StringType),
          description = Some("Shop website"),
          resolve = _.value.website),

        Field(
          name = "shopType",
          fieldType = OptionType(ShopTypeType[F]),
          description = Some("Shop type"),
          resolve = c => c.ctx.shopType.findById(c.value.shopTypeId.getOrElse(-1)).toIO.unsafeToFuture),

        Field(
          name = "lat",
          fieldType = FloatType,
          description = Some("Shop shop latitude"),
          resolve = _.value.lat.toDouble),

        Field(
          name = "long",
          fieldType = FloatType,
          description = Some("Shop shop longitude"),
          resolve = _.value.long.toDouble)

      )
    )

}
