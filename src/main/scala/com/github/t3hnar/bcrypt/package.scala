package com.github.t3hnar

import org.mindrot.jbcrypt.{BCrypt => B}

import scala.util.{Failure, Try}

/**
 * @author Yaroslav Klymko
 */
package object bcrypt {

  // Maybe consider moving the non deprecated methods no another package with the same method names (loose the "bounded")
  // This way the only change the developers would need to make is change the package
  implicit class BCryptStrOps(val pswrd: String) extends AnyVal {

    @deprecated("Use boundedBcrypt instead.\nMore information at https://github.com/t3hnar/scala-bcrypt/issues/23", "4.3.0")
    def bcrypt: String = doBcrypt

    def boundedBcrypt: String = {
      if(moreThanLength()) throw illegalArgumentException
      else doBcrypt
    }

    // The defualt rounds in BCrypt.gensalt() is 10. This may lead to user confusion when using the api.
    // I suggest adding an explicit 1, or updating the documentation.
    private[this] def doBcrypt: String = B.hashpw(pswrd, BCrypt.gensalt())

    @deprecated("Use bcryptBounded(rounds: Int) instead.\nMore information at https://github.com/t3hnar/scala-bcrypt/issues/23", "4.3.0")
    def bcrypt(rounds: Int): String = doBcrypt(rounds)

    def bcryptBounded(rounds: Int): String = {
      if(moreThanLength()) throw illegalArgumentException
      else doBcrypt(rounds)
    }

    def bcryptSafeBounded: Try[String] = {
      if(moreThanLength()) Failure(illegalArgumentException)
      else Try(doBcrypt)
    }

    private[this] def doBcrypt(rounds: Int): String = B.hashpw(pswrd, BCrypt.gensalt(rounds))

    @deprecated("Use bcryptBounded(salt: String) instead.\nMore information at https://github.com/t3hnar/scala-bcrypt/issues/23", "4.3.0")
    def bcrypt(salt: String): String = doBcrypt(salt)

    def bcryptBounded(salt: String): String = {
      if(moreThanLength()) throw illegalArgumentException
      else doBcrypt(salt)
    }

    private[this] def doBcrypt(salt: String): String = B.hashpw(pswrd, salt)

    @deprecated("Use isBcryptedBounded(hash: String) instead.\nMore information at https://github.com/t3hnar/scala-bcrypt/issues/23", "4.3.0")
    def isBcrypted(hash: String): Boolean = doIsBcrypted(hash)

    def isBcryptedBounded(hash: String): Boolean = {
      if(moreThanLength()) throw illegalArgumentException
      else doIsBcrypted(hash)
    }

    private[this] def doIsBcrypted(hash: String): Boolean = B.checkpw(pswrd, hash)

    @deprecated("Use bcryptSafeBounded(rounds: Int) instead.\nMore information at https://github.com/t3hnar/scala-bcrypt/issues/23", "4.3.0")
    def bcryptSafe(rounds: Int): Try[String] = Try(doBcrypt(rounds))

    def bcryptSafeBounded(rounds: Int): Try[String] = {
      if(moreThanLength()) Failure(illegalArgumentException)
      else Try(doBcrypt(rounds))
    }

    @deprecated("Use bcryptSafeBounded(salt: String) instead.\nMore information at https://github.com/t3hnar/scala-bcrypt/issues/23", "4.3.0")
    def bcryptSafe(salt: String): Try[String] = Try(doBcrypt(salt))

    def bcryptSafeBounded(salt: String): Try[String] = {
      if(moreThanLength()) Failure(illegalArgumentException)
      else Try(doBcrypt(salt))
    }

    @deprecated("Use isBcryptedSafeBounded(hash: String) instead.\nMore information at https://github.com/t3hnar/scala-bcrypt/issues/23", "4.3.0")
    def isBcryptedSafe(hash: String): Try[Boolean] = Try(doIsBcrypted(hash))

    def isBcryptedSafeBounded(hash: String): Try[Boolean] = {
      if(moreThanLength()) Failure(illegalArgumentException)
      else Try(doIsBcrypted(hash))
    }

    private[this] def illegalArgumentException = new IllegalArgumentException(s"$pswrd was more than 71 bytes long.")

    private[this] def moreThanLength(length: Int = 71): Boolean = pswrd.length > length
  }

  def generateSalt: String = B.gensalt()
}
