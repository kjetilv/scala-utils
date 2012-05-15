package vkode.scala.utils

/**
 * <p>Simple and concise null input check.  Should be used either extending by this trait or
 * importing <code>ErrorHandling._</code> and invoking the
 * implicit conversion by calling the {@link #requiredAs(T) requiredAs} method:
 * </P>
 *
 * <P>Usage:</P>
 *
 * <pre>
 * import ErrorHandling._
 *
 * def pickyFunction(foo: Argument) = process(foo requiredAs "foo")
 * </pre>
 *
 * <p>This will produce a NullPointerException saying "Null argument: foo" if a null foo is passed.</p>
 */
trait ArgumentGuards {

  /**
   * <p>Implicitly wraps a required reference argument when {@link RequirementChecker#verifyThat)} is invoked.</p>
   */
  implicit def refVerifyChecker[T <: AnyRef](t: T): RequirementChecker[T] = RequirementChecker(t)

  /**
   * <p>Implicitly wraps a required reference argument when {@link RequiredAsChecker#requiredAs} is invoked.</p>
   */
  implicit def refRequiredAsChecker[T <: AnyRef](t: T): RequiredAsChecker[T] = RequiredAsChecker(t)

  implicit def stringAsNonEmptyChecker(string: String) = NonEmptyStringChecker(string)

  implicit def optionAsOptionChecker[T](ot: Option[T]) = RequiredOptionChecker(ot)

  case class RequirementChecker[T <: AnyRef](t: T) {

    def verifyThat(requirement: String, fun: T => Boolean) =
      if (fun(t)) t else throw new IllegalArgumentException("Requirement failed: " + requirement + ": " + t)

    def verifyThat(fun: T => Boolean) =
      if (fun(t)) t else throw new IllegalArgumentException("Illegal argument: " + t)
  }

  /**
   * <p>Wraps a value for input argument checking.</p>
   */
  case class RequiredAsChecker[T <: AnyRef](t: T) {

    /**
     * Returns the wrapped argument, or fails if it is null.  The name is used in the exception, if any
     */
    def requiredAs(name: String): T = if (t == null) throw new NullPointerException("Null argument: " + name) else t

    /**
     * For the lazy programmer - the same without an argument name.
     */
    def requiredAsNotNull(): T = if (t == null) throw new NullPointerException("Null argument") else t
  }

  case class RequiredOptionChecker[T](ot: Option[T]) {

    def asRequiredOptionValue(name: String): T = if (ot == null || ot == None || ot.get == null)
      throw new IllegalArgumentException("Option must be set: " + name)
    else
      ot.get
  }

  case class NonEmptyStringChecker(string: String) {

    def requiredAsNonEmptyTrimmed(name: String) = requiredAsNonEmpty(name).trim

    def requiredAsNonEmpty(name: String): String = Option(string).map(_.trim) match {
      case None => throw new IllegalArgumentException("String must be non-null: " + name)
      case Some("") => throw new IllegalArgumentException("String must be non-empty: " + name)
      case Some(validString) => string
    }
  }
}

object ArgumentGuards extends ArgumentGuards 

