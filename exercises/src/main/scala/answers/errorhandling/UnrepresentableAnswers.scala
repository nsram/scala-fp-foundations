package answers.errorhandling

import java.time.{Duration, Instant}

import cats.data.NonEmptyList
import eu.timepit.refined.auto._
import eu.timepit.refined.types.numeric.{PosDouble, PosInt}
import exercises.errorhandling.UnrepresentableExercises.{Item, Order}
import toimpl.errorhandling.UnrepresentableToImpl

object UnrepresentableAnswers extends UnrepresentableToImpl {

  ////////////////////////
  // 1. Order
  ////////////////////////

  val redBook = Item("12345", 2, 17.99)

  def totalItem(item: Item): Double = item.quantity * item.unitPrice

  case class Item_V2(id: String, quantity: PosInt, unitPrice: PosDouble)
  val redBook_v2 = Item_V2("12345", 2, 17.99)

  def totalItem_v2(item: Item_V2): PosDouble = ???

  def checkout(order: Order): Order =
    order.status match {
      case "Draft" =>
        if (order.items.nonEmpty) order.copy(status = "Checkout")
        else throw new Exception("Cannot checkout order with an empty basket")
      case other => throw new Exception(s"Invalid status to checkout $other")
    }

  def submit(order: Order, now: Instant): Order =
    order.status match {
      case "Checkout" =>
        if (order.deliveryAddress.isDefined && order.items.nonEmpty)
          order.copy(status = "Submitted", submittedAt = Some(now))
        else if (order.items.isEmpty)
          throw new Exception("Cannot submit order with an empty basket")
        else
          throw new Exception("Cannot submit order with no delivery address")
      case other => throw new Exception(s"Invalid status to submit $other")
    }

  def deliver(order: Order, now: Instant): (Order, Duration) =
    order.status match {
      case "Submitted" =>
        order.submittedAt match {
          case Some(x) =>
            val duration = Duration.between(x, now)
            (order.copy(status = "Delivered", deliveredAt = Some(now)), duration)
          case None =>
            throw new Exception("Invalid state, delivered without submittedAt")
        }
      case other => throw new Exception(s"Invalid status to submit $other")
    }

  sealed trait Order_V2

  object Order_V2 {
    case class PreCheckout(id: String, items: List[Item])                                       extends Order_V2
    case class Checkout(id: String, items: NonEmptyList[Item], deliveryAddress: Option[String]) extends Order_V2
    case class Submitted(id: String, items: NonEmptyList[Item], deliveryAddress: String, submittedAt: Instant)
        extends Order_V2
    case class Delivered(id: String, items: NonEmptyList[Item], deliveryAddress: String, submittedAt: Instant)
        extends Order_V2
    case class Cancel(previousState: Either[Submitted, Delivered], cancelledAt: Instant) extends Order_V2
  }
}