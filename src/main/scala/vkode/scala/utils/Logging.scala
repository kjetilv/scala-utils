package vkode.scala.utils

import org.slf4j._

/**
 * <p>
 *   Mixin this trait to get Log4J access.  By default, uses the logger of the instance's real class. If this is
 *   not desirable, override the logClass field in your subclass.
 *   </p>
 *
 * <p>
 *   All message arguments are named arguments, i.e. actually functions.  These function are not evaluated
 *   before the log level is checked.  Hence, it is not necessary to qualify eg. calls to <code>debugLog</code>
 *   with the isDebugEnabled check.
 * </p>
 */
trait Logging {

  /**
   * This field may be overridden to specify the desired log class.
   */
  protected val logClass = this.getClass

  /**
   * This field may also be overridden to provide a specific logger.
   */
  protected val logger = {
    val n = logClass.getName
    LoggerFactory getLogger (if (isObject(n)) objectName(n) else n)
  }

  /**
   * Convenience method for more comprehensive logging actions qualified by a log level.  Eg.:
   *
   * <pre>
   * ifTrace {
   * items foreach (traceLog (_))
   * }
   * </pre>
   */
  def ifTrace(logAction: => Any) = if (isTraceEnabled) logAction

  /**
   * @see ifTrace
   */
  def ifDebug(logAction: => Any) = if (isDebugEnabled) logAction

  /**
   * @see ifTrace
   */
  def ifInfo(logAction: => Any) = if (isInfoEnabled) logAction

  /**
   * @see ifTrace
   */
  def ifWarn(logAction: => Any) = if (isWarnEnabled) logAction

  /**
   * @see ifTrace
   */
  def ifError(logAction: => Any) = if (isErrorEnabled) logAction

  /**
   * Query method for log level.
   */
  def isTraceEnabled = logger.isTraceEnabled

  /**
   * @see isTraceEnabled
   */
  def isDebugEnabled = logger.isDebugEnabled

  /**
   * @see isTraceEnabled
   */
  def isInfoEnabled = logger.isInfoEnabled

  /**
   * @see isTraceEnabled
   */
  def isWarnEnabled = logger.isWarnEnabled

  /**
   * @see isTraceEnabled
   */
  def isErrorEnabled = logger.isErrorEnabled

  def traceLog(msg: => String) {
    logger.trace(msg)
  }

  def debugLog(msg: => String) {
    logger.debug(msg)
  }

  def infoLog(msg: => String) {
    logger.info(msg)
  }

  def warnLog(msg: => String) {
    logger.warn(msg)
  }

  def errorLog(msg: => String) {
    logger.error(msg)
  }

  def traceLog(msg: => String, t: Throwable) {
    logger.trace(msg, t)
  }

  def debugLog(msg: => String, t: Throwable) {
    logger.debug(msg, t)
  }

  def infoLog(msg: => String, t: Throwable) {
    logger.info(msg, t)
  }

  def warnLog(msg: => String, t: Throwable) {
    logger.warn(msg, t)
  }

  def errorLog(msg: => String, t: Throwable) {
    logger.error(msg, t)
  }

  private def objectName(n: String) = n.substring(0, n.length - 1) + "/object"

  private def isObject(n: String) = n endsWith "$"
}
