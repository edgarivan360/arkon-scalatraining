package repository

import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import model.ComercialActivity

trait ComercialActivityRepository[F[_]] {
  def findAll: F[List[ComercialActivity]]
  def findById(id: Int): F[Option[ComercialActivity]]
  def create(id: Int, name: String): F[Option[ComercialActivity]]
}

object ComercialActivityRepository {

  def fromTransactor[F[_]: Sync](transactor: Transactor[F]): ComercialActivityRepository[F] =
    new ComercialActivityRepository[F] {

      val selectStatement: Fragment =
        fr"""
          SELECT id, name
            FROM comercial_activity
        """

      override def findAll: F[List[ComercialActivity]] =
        selectStatement.query[ComercialActivity].to[List].transact(transactor)

      override def findById(id: Int): F[Option[ComercialActivity]] =
        (selectStatement ++ sql"WHERE id = $id").query[ComercialActivity].option.transact(transactor)

      override def create(id: Int, name: String): F[Option[ComercialActivity]] =
        {
          sql"INSERT INTO comercial_activity(id, name) VALUES($id, $name)".update.run *>
            (selectStatement ++ sql"WHERE id = $id").query[ComercialActivity].option
        }.transact(transactor)
    }

}
