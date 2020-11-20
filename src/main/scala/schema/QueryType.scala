package schema

import cats.effect._
import cats.effect.implicits._
import repository.MasterRepository
import sangria.schema._

object QueryType {

  val comercialActivityId: Argument[Int] =
    Argument(
      name = "comercialActivityId",
      argumentType = IntType,
      description = "ID for a comercial activity"
    )

  val stratumId: Argument[Int] =
    Argument(
      name = "stratumId",
      argumentType = IntType,
      description = "ID for a stratum"
    )

  val shopTypeId: Argument[Int] =
    Argument(
      name = "shopTypeId",
      argumentType = IntType,
      description = "ID for a shop type"
    )

  val shopId: Argument[Int] =
    Argument(
      name = "shopId",
      argumentType = IntType,
      description = "Shop ID"
    )

  def apply[F[_]: Effect]: ObjectType[MasterRepository[F], Unit] =
    ObjectType(
      name = "Query",
      fields = fields(

        Field(
          name = "comercialActivities",
          fieldType = ListType(ComercialActivityType[F]),
          description = Some("Returns all the comercial activities"),
          resolve = c => c.ctx.comercialActivity.findAll.toIO.unsafeToFuture
        ),

        Field(
          name = "comercialActivity",
          fieldType = OptionType(ComercialActivityType[F]),
          description = Some("Returns the comercial activity with the given id, if any."),
          arguments = List(comercialActivityId),
          resolve = c => c.ctx.comercialActivity.findById(c.arg(comercialActivityId)).toIO.unsafeToFuture
        ),

        Field(
          name = "stratums",
          fieldType = ListType(StratumType[F]),
          description = Some("Returns all the stratums"),
          resolve = c => c.ctx.stratum.findAll.toIO.unsafeToFuture
        ),

        Field(
          name = "stratum",
          fieldType = OptionType(StratumType[F]),
          description = Some("Returns the stratum with the given id, if any."),
          arguments = List(stratumId),
          resolve = c => c.ctx.stratum.findById(c.arg(stratumId)).toIO.unsafeToFuture
        ),

        Field(
          name = "shopTypes",
          fieldType = ListType(ShopTypeType[F]),
          description = Some("Returns all the shop types"),
          resolve = c => c.ctx.shopType.findAll.toIO.unsafeToFuture
        ),

        Field(
          name = "shopType",
          fieldType = OptionType(ShopTypeType[F]),
          description = Some("Returns the shop type with the given id, if any."),
          arguments = List(shopTypeId),
          resolve = c => c.ctx.shopType.findById(c.arg(shopTypeId)).toIO.unsafeToFuture
        ),

        Field(
          name = "shops",
          fieldType = ListType(ShopType[F]),
          description = Some("Returns all the shops"),
          resolve = c => c.ctx.shop.findAll.toIO.unsafeToFuture
        ),

        Field(
          name = "shop",
          fieldType = OptionType(ShopType[F]),
          description = Some("Returns the shop with the given id, if any."),
          arguments = List(shopId),
          resolve = c => c.ctx.shop.findById(c.arg(shopId)).toIO.unsafeToFuture
        )

      )
    )

  def schema[F[_]: Effect]: Schema[MasterRepository[F], Unit] =
    Schema(QueryType[F])

}
