package vkode.scala.utils

trait CascadingActions {

  /**
   * Any type T can become an Actioneer[T], provided you call withAction or withActions on it.
   */
  implicit def tToActioneerT[T](t: T) = Actioneer(t)

  case class Actioneer[T](t: T) {

    def withAction(action: (T => Any)): T = withActions(action)

    def withActions(actions: (T => Any)*): T = {
      actions foreach (_(t))
      t
    }
  }

}

/**
 * Import CascadingActions._ if you don't want to go with the trait yourself.
 */
object CascadingActions extends CascadingActions
