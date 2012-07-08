package ua.t3hnar.bcrypt

import collection.mutable
import java.util.concurrent.TimeUnit

/**
 * @author Yaroslav Klymko
 */
object PasswordCache {
  case class CacheEntry(password: String, hash: String)

  implicit val Default = new ExpiringCache(1, TimeUnit.DAYS)
}

trait PasswordCache {
  import PasswordCache._
  def get(entry: CacheEntry): Option[Boolean]
  def put(entry: CacheEntry, value: Boolean): Option[Boolean]
}

class MappedCache
  extends mutable.HashMap[PasswordCache.CacheEntry, Boolean]
  with mutable.SynchronizedMap[PasswordCache.CacheEntry, Boolean]
  with PasswordCache

class ExpiringCache(duration: Long, unit: TimeUnit) extends PasswordCache{
  import PasswordCache._

  case class ExpiringValue(value: Boolean, timestamp: Long)

  val map = new mutable.HashMap[CacheEntry, ExpiringValue]

  override def get(key: CacheEntry) = synchronized {
    increaseQueryCount()
    maybeCleanExpired()
    map.get(key).map(_.value)
  }

  private def currentMillis = System.currentTimeMillis()

  def put(entry: CacheEntry, value: Boolean) = synchronized {
    map.put(entry, ExpiringValue(value, currentMillis)).map(_.value)
  }

  private var queryCount = 0
  private val queryOverflow = 1000

  def increaseQueryCount() {
    queryCount = queryCount + 1
  }

  def maybeCleanExpired() {
    if (queryCount >= queryOverflow) cleanExpired()
  }

  def cleanExpired() {
    val expiration = unit.toMillis(duration)
    val current = currentMillis
    val expired = map.toList.collect {
      case (key, ExpiringValue(_, timestamp)) if (timestamp + expiration) <= current => key
    }
    expired.foreach(map.remove)
  }
}