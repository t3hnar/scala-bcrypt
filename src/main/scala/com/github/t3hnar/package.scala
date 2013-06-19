package com.github.t3hnar

import org.mindrot.jbcrypt.{BCrypt => B}

/**
 * @author Yaroslav Klymko
 */
package object bcrypt {

  implicit class Password(pswrd: String) {
    def bcrypt: String = B.hashpw(pswrd, BCrypt.gensalt())

    def bcrypt(rounds: Int): String = B.hashpw(pswrd, BCrypt.gensalt(rounds))

    def bcrypt(salt: String) = B.hashpw(pswrd, salt)

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
  
  def generateSalt: String = B.gensalt()
  
}