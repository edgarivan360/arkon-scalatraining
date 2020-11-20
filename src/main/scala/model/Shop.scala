package model

final case class Shop(
  id: Int,
  name: String,
  businessName: Option[String],
  activityId: Option[Int],
  stratumId: Option[Int],
  address: String,
  phoneNumber: Option[String],
  email: Option[String],
  website: Option[String],
  shopTypeId: Option[Int],
  lat: Float,
  long: Float
)
