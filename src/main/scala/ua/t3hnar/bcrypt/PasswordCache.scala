package ua.t3hnar.bcrypt

import collection.mutable

/**
 * @author Yaroslav Klymko
 */
object PasswordCache {

  case class CacheEntry(password: String, hash: String)

  implicit val Default = new MappedCache
}

trait PasswordCache {
  import PasswordCache._
  def get(entry: CacheEntry): Option[Boolean]
  def put(entry: CacheEntry, value: Boolean): Option[Boolean]
  def clear()
}

class MappedCache
  extends mutable.HashMap[PasswordCache.CacheEntry, Boolean]
  with mutable.SynchronizedMap[PasswordCache.CacheEntry, Boolean]
  with PasswordCache
