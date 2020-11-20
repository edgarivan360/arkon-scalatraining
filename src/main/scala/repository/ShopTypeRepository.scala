package repository

import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import model.Stratum
import model.ShopType

trait ShopTypeRepository[F[_]] {
  def findAll: F[List[ShopType]]
  def findById(id: Int): F[Option[ShopType]]
}

object ShopTypeRepository {

  def fromTransactor[F[_] : Sync](transactor: Transactor[F]): ShopTypeRepository[F] =
    new ShopTypeRepository[F] {

      val selectStatement: Fragment =
        fr"""
          SELECT id, name
            FROM shop_type
        """

      override def findAll: F[List[ShopType]] =
        selectStatement.query[ShopType].to[List].transact(transactor)

      override def findById(id: Int): F[Option[ShopType]] =
        (selectStatement ++ sql"WHERE id = $id").query[ShopType].option.transact(transactor)
    }

}
