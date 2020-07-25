package com.github.t3hnar

import org.mindrot.jbcrypt.{BCrypt => B}

import scala.util.{Failure, Try}

/**
 * @author Yaroslav Klymko
 */
package object bcrypt {

  private val maxLength = 71 // max tested bytes that the library can handle before it stops working

  // Maybe consider moving the non deprecated methods no another package with the same method names (loose the "bounded")
  // This way the only change the developers would need to make is change the package
  implicit class BCryptStrOps(val pswrd: String) extends AnyVal {

    @Deprecated
    def bcrypt: String = {
      warn("bcrypt", "boundedBcrypt")
      doBcrypt
    }

    def boundedBcrypt: String = {
      if(moreThanLength(maxLength)) throw new IllegalArgumentException(s"$pswrd was more than $maxLength bytes long.")
      else doBcrypt
    }

    def safeBoundedBcrypt: Try[String] = {
      if(moreThanLength(maxLength)) Failure(new IllegalArgumentException(s"$pswrd was more than $maxLength bytes long."))
      else Try(doBcrypt)
    }

    // The defualt rounds in BCrypt.gensalt() is 10. This may lead to user confusion when using the api.
    // I suggest adding an explicit 1, or updating the documentation.
    private def doBcrypt: String = B.hashpw(pswrd, BCrypt.gensalt())

    @Deprecated
    def bcrypt(rounds: Int): String = {
      warn("bcrypt", "boundedBcrypt")
      doBcrypt(rounds)
    }

    def boundedBcrypt(rounds: Int): String = {
      if(moreThanLength(maxLength)) throw new IllegalArgumentException(s"$pswrd was more than $maxLength bytes long.")
      else doBcrypt(rounds)
    }

    def safeBoundedBcrypt(rounds: Int): Try[String] = {
      if(moreThanLength(maxLength)) Failure(new IllegalArgumentException(s"$pswrd was more than $maxLength bytes long."))
      else Try(doBcrypt(rounds))
    }

    private def doBcrypt(rounds: Int): String = B.hashpw(pswrd, BCrypt.gensalt(rounds))

    @Deprecated
    def bcrypt(salt: String): String = {
      warn("bcrypt", "boundedBcrypt")
      doBcrypt(salt)
    }

    def boundedBcrypt(salt: String): String = {
      if(moreThanLength(maxLength)) throw new IllegalArgumentException(s"$pswrd was more than $maxLength bytes long.")
      else doBcrypt(salt)
    }

    def safeBoundedBcrypt(salt: String): Try[String] = {
      if(moreThanLength(maxLength)) Failure(new IllegalArgumentException(s"$pswrd was more than $maxLength bytes long."))
      else Try(doBcrypt(salt))
    }

    private def doBcrypt(salt: String): String = B.hashpw(pswrd, salt)

    @Deprecated
    def isBcrypted(hash: String): Boolean = {
      warn("isBcrypted", "isBoundedBcrypted")
      doIsBcrypted(hash)
    }

    def isBoundedBcrypted(hash: String): Boolean = {
      if(moreThanLength(maxLength)) throw new IllegalArgumentException(s"$pswrd was more than $maxLength bytes long.")
      else doIsBcrypted(hash)
    }

    private def doIsBcrypted(hash: String): Boolean = B.checkpw(pswrd, hash)

    @Deprecated
    def bcryptSafe(rounds: Int): Try[String] = {
      warn("bcryptSafe", "boundedBcryptSafe")
      Try(doBcrypt(rounds))
    }

    def boundedBcryptSafe(rounds: Int): Try[String] = {
      if(moreThanLength(maxLength)) Failure(new IllegalArgumentException(s"$pswrd was more than $maxLength bytes long."))
      else Try(doBcrypt(rounds))
    }

    @Deprecated
    def bcryptSafe(salt: String): Try[String] = {
      warn("bcryptSafe", "boundedBcryptSafe")
      Try(doBcrypt(salt))
    }

    def boundedBcryptSafe(salt: String): Try[String] = {
      if(moreThanLength(maxLength)) Failure(new IllegalArgumentException(s"$pswrd was more than $maxLength bytes long."))
      else Try(doBcrypt(salt))
    }

    @Deprecated
    def isBcryptedSafe(hash: String): Try[Boolean] = {
      warn("isBcryptedSafe", "isBoundedBcryptedSafe")
      Try(doIsBcrypted(hash))
    }

    def isBoundedBcryptedSafe(hash: String): Try[Boolean] = {
      if(moreThanLength(maxLength)) Failure(new IllegalArgumentException(s"pswr was more than $maxLength bytes long."))
      else Try(doIsBcrypted(hash))
    }

    private def moreThanLength(length: Int): Boolean = pswrd.length > length

    private def warn(oldMethod: String, newMethod: String): Unit = {
      val message: String = s"[warn] method $oldMethod is deprecated. Use $newMethod instead.\n" +
        "To supress this warning, add a DEBUG environment variable to false.\n" +
        "More information at https://github.com/t3hnar/scala-bcrypt/issues/23"
      sys.env.get("DEBUG")
        .map(_ != false)
        .filter(a => a)
        .map(_ => println(message))
        .getOrElse(println(message))
    }
  }

  def generateSalt: String = B.gensalt()
}
