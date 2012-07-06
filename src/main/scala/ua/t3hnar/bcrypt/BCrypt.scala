package ua.t3hnar.bcrypt

import org.mindrot.jbcrypt.{BCrypt => B}

/**
 * @author Yaroslav Klymko
 */
object BCrypt {
  def gensalt(rounds: Int = 10): String = B.gensalt(rounds)
}

class Password(pswrd: String) {
  def bcrypt: String = B.hashpw(pswrd, BCrypt.gensalt())

  def bcrypt(rounds: Int): String = B.hashpw(pswrd, BCrypt.gensalt(rounds))

  @deprecated("Use `isBcrypted` method", "1.3")
  def bcrypted_?(bcryptedPassword: String): Boolean = isBcrypted(bcryptedPassword)

  def isBcryptedWithCache(hash: String)(implicit cache: PasswordCache): Boolean = {
    val entry = PasswordCache.CacheEntry(pswrd, hash)
    cache.get(entry) match {
      case Some(value) => value
      case None =>
        val value = isBcrypted(hash)
        cache.put(entry, value)
        value
    }
  }

  def isBcrypted(hash: String): Boolean = B.checkpw(pswrd, hash)
}