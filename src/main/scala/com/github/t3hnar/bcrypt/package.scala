package com.github.t3hnar

import org.mindrot.jbcrypt.{BCrypt => B}

import scala.util.Try

/**
 * @author Yaroslav Klymko
 */
package object bcrypt {

  implicit class Password(val pswrd: String) extends AnyVal {
    def bcrypt: String = B.hashpw(pswrd, BCrypt.gensalt())

    def bcrypt(rounds: Int): Try[String] = Try(B.hashpw(pswrd, BCrypt.gensalt(rounds)))

    def bcrypt(salt: String): Try[String] = Try(B.hashpw(pswrd, salt))

    def isBcrypted(hash: String): Try[Boolean] = Try(B.checkpw(pswrd, hash))
  }

  def generateSalt: String = B.gensalt()
}