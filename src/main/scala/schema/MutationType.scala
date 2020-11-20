// Copyright (c) 2018 by Rob Norris
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package schema

import cats.effect._
import cats.effect.implicits._
import model.ComercialActivity
import repository.MasterRepository
import sangria.schema._

object MutationType {

  val comercialActivityId: Argument[Int] =
    Argument(
      name = "comercialActivityId",
      argumentType = IntType,
      description = "ID for the new comercial activity."
    )

  val comercialActivityName: Argument[String] =
    Argument(
      name = "comercialActivityName",
      argumentType = StringType,
      description = "Name for the new comercial activity.",
    )

  def apply[F[_]: Effect]: ObjectType[MasterRepository[F], Unit] =
    ObjectType(
      name = "Mutation",
      fields = fields(

        Field(
          name = "createComercialActivity",
          fieldType = OptionType(ComercialActivityType[F]),
          description = Some("Create a new comercial activity."),
          arguments = List(comercialActivityId, comercialActivityName),
          resolve = c => c.ctx.comercialActivity.create(c.arg(comercialActivityId), c.arg(comercialActivityName)).toIO.unsafeToFuture
        )

      )
    )

}
