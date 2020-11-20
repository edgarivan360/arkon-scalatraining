// Copyright (c) 2018 by Rob Norris
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package schema

import cats.effect._
import repository.MasterRepository
import sangria.execution.deferred.{Deferred, DeferredResolver}

import scala.concurrent.{ExecutionContext, _}

object DeferredResolverINEGI {

  def apply[F[_]: Effect]: DeferredResolver[MasterRepository[F]] =
    new DeferredResolver[MasterRepository[F]] {

      def resolve(
        deferred:   Vector[Deferred[Any]],
        ctx:        MasterRepository[F],
        queryState: Any
      )(
        implicit ec: ExecutionContext
      ): Vector[Future[Any]] = {

        val promises: Map[Deferred[Any], Promise[Any]] =
          deferred.map(d => d -> Promise[Any]()).toMap

        deferred.map(promises(_).future)

      }

    }

}