package com.github.t3hnar

import org.mindrot.jbcrypt.{BCrypt => B}

import scala.util.{Failure, Try}

/**
 * @author Yaroslav Klymko
 */
package object bcrypt {

  private[this] val maxLength = 71 // max tested bytes that the library can handle before it stops working

  // Maybe consider moving the non deprecated methods no another package with the same method names (loose the "bounded")
  // This way the only change the developers would need to make is change the package
  implicit class BCryptStrOps(val pswrd: String) extends AnyVal {

    @deprecated("Use boundedBcrypt instead.\n" +
      "More information at https://github.com/t3hnar/scala-bcrypt/issues/23", "4.1.1")
    def bcrypt: String = doBcrypt

    def boundedBcrypt: String = {
      if(moreThanLength(maxLength)) throw illegalArgumentException
      else doBcrypt
    }

    def safeBoundedBcrypt: Try[String] = {
      if(moreThanLength(maxLength)) Failure(illegalArgumentException)
      else Try(doBcrypt)
    }

    // The defualt rounds in BCrypt.gensalt() is 10. This may lead to user confusion when using the api.
    // I suggest adding an explicit 1, or updating the documentation.
    private[this] def doBcrypt: String = B.hashpw(pswrd, BCrypt.gensalt())

    @deprecated("Use boundedBcrypt(rounds: Int) instead.\n" +
      "More information at https://github.com/t3hnar/scala-bcrypt/issues/23", "4.1.1")
    def bcrypt(rounds: Int): String = doBcrypt(rounds)

    def boundedBcrypt(rounds: Int): String = {
      if(moreThanLength(maxLength)) throw illegalArgumentException
      else doBcrypt(rounds)
    }

    def safeBoundedBcrypt(rounds: Int): Try[String] = {
      if(moreThanLength(maxLength)) Failure(illegalArgumentException)
      else Try(doBcrypt(rounds))
    }

    private[this] def doBcrypt(rounds: Int): String = B.hashpw(pswrd, BCrypt.gensalt(rounds))

    @deprecated("Use boundedBcrypt(salt: String) instead.\n" +
      "More information at https://github.com/t3hnar/scala-bcrypt/issues/23", "4.1.1")
    def bcrypt(salt: String): String = doBcrypt(salt)

    def boundedBcrypt(salt: String): String = {
      if(moreThanLength(maxLength)) throw illegalArgumentException
      else doBcrypt(salt)
    }

    def safeBoundedBcrypt(salt: String): Try[String] = {
      if(moreThanLength(maxLength)) Failure(illegalArgumentException)
      else Try(doBcrypt(salt))
    }

    private[this] def doBcrypt(salt: String): String = B.hashpw(pswrd, salt)

    @deprecated("Use isBoundedBcrypted(hash: String) instead.\n" +
      "More information at https://github.com/t3hnar/scala-bcrypt/issues/23", "4.1.1")
    def isBcrypted(hash: String): Boolean = doIsBcrypted(hash)

    def isBoundedBcrypted(hash: String): Boolean = {
      if(moreThanLength(maxLength)) throw illegalArgumentException
      else doIsBcrypted(hash)
    }

    private[this] def doIsBcrypted(hash: String): Boolean = B.checkpw(pswrd, hash)

    @deprecated("Use boundedBcryptSafe(rounds: Int) instead.\n" +
      "More information at https://github.com/t3hnar/scala-bcrypt/issues/23", "4.1.1")
    def bcryptSafe(rounds: Int): Try[String] = Try(doBcrypt(rounds))

    def boundedBcryptSafe(rounds: Int): Try[String] = {
      if(moreThanLength(maxLength)) Failure(illegalArgumentException)
      else Try(doBcrypt(rounds))
    }

    @deprecated("Use boundedBcryptSafe(salt: String) instead.\n" +
      "More information at https://github.com/t3hnar/scala-bcrypt/issues/23", "4.1.1")
    def bcryptSafe(salt: String): Try[String] = Try(doBcrypt(salt))

    def boundedBcryptSafe(salt: String): Try[String] = {
      if(moreThanLength(maxLength)) Failure(illegalArgumentException)
      else Try(doBcrypt(salt))
    }

    @deprecated("Use isBoundedBcryptedSafe(hash: String) instead.\n" +
      "More information at https://github.com/t3hnar/scala-bcrypt/issues/23", "4.1.1")
    def isBcryptedSafe(hash: String): Try[Boolean] = Try(doIsBcrypted(hash))

    def isBoundedBcryptedSafe(hash: String): Try[Boolean] = {
      if(moreThanLength(maxLength)) Failure(illegalArgumentException)
      else Try(doIsBcrypted(hash))
    }

    private[this] def illegalArgumentException = new IllegalArgumentException(s"$pswrd was more than $maxLength bytes long.")

    private[this] def moreThanLength(length: Int): Boolean = pswrd.length > length
  }

  def generateSalt: String = B.gensalt()
}
