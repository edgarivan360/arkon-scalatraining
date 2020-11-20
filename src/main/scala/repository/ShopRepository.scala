package repository

import cats.effect._
import doobie._
import doobie.implicits._
import model.Shop

trait ShopRepository[F[_]] {
  def findAll: F[List[Shop]]
  def findById(shopId: Int): F[Option[Shop]]
}

object ShopRepository {

  def fromTransactor[F[_]: Sync](transactor: Transactor[F]): ShopRepository[F] =
    new ShopRepository[F] {

      val selectStatement: Fragment =
        fr"""
          SELECT id, name, business_name, activity_id, stratum_id, address, phone_number, email, website,
                 shop_type_id, st_y(st_astext(position)) lat,  st_x(st_astext(position)) long
            FROM shop
        """

      override def findAll: F[List[Shop]] =
        selectStatement.query[Shop].to[List].transact(transactor)

      override def findById(shopId: Int): F[Option[Shop]] =
        (selectStatement ++ sql"WHERE id = $shopId").query[Shop].option.transact(transactor)
    }
}
