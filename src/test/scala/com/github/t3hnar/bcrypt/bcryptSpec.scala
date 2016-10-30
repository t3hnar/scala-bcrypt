package com.github.t3hnar.bcrypt

import org.scalatest.{Matchers, WordSpec}


class bcryptSpec extends WordSpec with Matchers {

  "bcrypt" should {
    "encrypt and check if bcrypted" in {
      val hash = "my password".bcrypt
      "my password".isBcrypted(hash) shouldEqual true
      "my new password".isBcrypted(hash) shouldEqual false
    }

    "encrypt with provided salt and check if bcrypted" in {
      val salt = BCrypt.gensalt()
      val hash = "password".bcrypt(salt)
      "password".isBcrypted(hash) shouldEqual true
      "my new password".isBcrypted(hash) shouldEqual false
    }
  }
}