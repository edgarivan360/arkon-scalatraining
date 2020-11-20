package repository

import cats.effect.Sync
import doobie.Transactor

final case class MasterRepository[F[_]](
  comercialActivity: ComercialActivityRepository[F],
  stratum: StratumRepository[F],
  shopType: ShopTypeRepository[F],
  shop: ShopRepository[F]
)

object MasterRepository {

  def fromTransactor[F[_]: Sync](transactor: Transactor[F]): MasterRepository[F] =
    MasterRepository(
      ComercialActivityRepository.fromTransactor(transactor),
      StratumRepository.fromTransactor(transactor),
      ShopTypeRepository.fromTransactor(transactor),
      ShopRepository.fromTransactor(transactor))

}
