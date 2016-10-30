package com.github.t3hnar

import org.mindrot.jbcrypt.{BCrypt => B}

/**
 * @author Yaroslav Klymko
 */
package object bcrypt {

  implicit class Password(val pswrd: String) extends AnyVal {
    def bcrypt: String = B.hashpw(pswrd, BCrypt.gensalt())

    def bcrypt(rounds: Int): String = B.hashpw(pswrd, BCrypt.gensalt(rounds))

    def bcrypt(salt: String): String = B.hashpw(pswrd, salt)

    def isBcrypted(hash: String): Boolean = B.checkpw(pswrd, hash)
  }

  def generateSalt: String = B.gensalt()
}