package com.github.t3hnar.bcrypt

import org.scalatest.{Matchers, WordSpec}

import scala.util.Success


class bcryptSpec extends WordSpec with Matchers {

  "bcrypt" should {
    "encrypt and check if bcrypted" in {
      val hash = "my password".bcrypt
      "my password".isBcrypted(hash) shouldEqual Success(true)
      "my new password".isBcrypted(hash) shouldEqual Success(false)
    }

    "encrypt with provided salt and check if bcrypted" in {
      val salt = BCrypt.gensalt()
      val hash = "password".bcrypt(salt)
      hash.isSuccess shouldEqual true
      val extractedHash = hash.get
      "password".isBcrypted(extractedHash) shouldEqual Success(true)
      "my new password".isBcrypted(extractedHash) shouldEqual Success(false)
    }

    "attempting to check isBcrypted against a non-bcrypted string will result in a scala.util.Failure" in {
      val result = "password".isBcrypted("password")
      result.isFailure shouldEqual true
    }

    "attempting to use rounds > 30 will result in a scala.util.Failure" in {
      val result = "password".bcrypt(31)
      result.isFailure shouldEqual true
    }

    "attempting to use an invalid salt will result in a scala.util.Failure" in {
      val result = "password".bcrypt("invalid-salt")
      result.isFailure shouldEqual true
    }
  }
}