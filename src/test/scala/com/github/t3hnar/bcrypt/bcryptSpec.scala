package com.github.t3hnar.bcrypt

import org.scalatest.{Matchers, WordSpec}

import scala.util.Success

class bcryptSpec extends WordSpec with Matchers {
  "safe APIs" should {
    "encrypt and check if bcrypted" in {
      val hash = "my password".bcrypt
      "my password".isBcryptedSafe(hash) shouldEqual Success(true)
      "my new password".isBcryptedSafe(hash) shouldEqual Success(false)
    }

    "encrypt with provided salt and check if bcrypted" in {
      val salt = BCrypt.gensalt()
      val hash = "password".bcryptSafe(salt)
      hash.isSuccess shouldEqual true
      val extractedHash = hash.get
      "password".isBcryptedSafe(extractedHash) shouldEqual Success(true)
      "my new password".isBcryptedSafe(extractedHash) shouldEqual Success(false)
    }

    "attempting to check isBcrypted against a non-bcrypted string will result in a scala.util.Failure" in {
      val result = "password".isBcryptedSafe("password")
      result.isFailure shouldEqual true
    }

    "attempting to use rounds > 30 will result in a scala.util.Failure" in {
      val result = "password".bcryptSafe(31)
      result.isFailure shouldEqual true
    }

    "attempting to use an invalid salt will result in a scala.util.Failure" in {
      val result = "password".bcryptSafe("invalid-salt")
      result.isFailure shouldEqual true
    }
  }

  "unsafe APIs" should {
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

    "throw an exception if bcrypt parameters are incorrect" in {
      val invalidSalt = "bad-salt"
      val caught = intercept[IllegalArgumentException] {
        "password".bcrypt(invalidSalt)
      }
      caught.getMessage shouldEqual "Invalid salt version"
    }
  }
}