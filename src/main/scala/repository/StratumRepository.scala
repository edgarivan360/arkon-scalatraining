package repository

import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import model.Stratum

trait StratumRepository[F[_]] {
  def findAll: F[List[Stratum]]
  def findById(id: Int): F[Option[Stratum]]
}

object StratumRepository {

  def fromTransactor[F[_] : Sync](transactor: Transactor[F]): StratumRepository[F] =
    new StratumRepository[F] {

      val selectStatement: Fragment =
        fr"""
          SELECT id, name
            FROM stratum
        """

      override def findAll: F[List[Stratum]] =
        selectStatement.query[Stratum].to[List].transact(transactor)

      override def findById(id: Int): F[Option[Stratum]] =
        (selectStatement ++ sql"WHERE id = $id").query[Stratum].option.transact(transactor)
    }

}
