package com.github.t3hnar.bcrypt

import org.specs2.mutable.SpecificationWithJUnit

/**
 * @author Yaroslav Klymko
 */
class bcryptSpec extends SpecificationWithJUnit {

  "bcrypt" should {
    "encrypt and check if bcrypted" >> {
      val hash = "my password".bcrypt
      "my password".isBcrypted(hash) must beTrue
      "my new password".isBcrypted(hash) must beFalse
    }



    "use cache for often calls" >> {
      val hash = "my password".bcrypt

      def _measure = measure {
        "my password".isBcryptedWithCache(hash) must beTrue
      }

      val r1 = _measure
      val r2 = _measure

      r2 must beLessThan(r1 / 10)
    }

    "encrypt with provided salt and check if bcrypted" >> {
        val salt = BCrypt.gensalt()
        val hash = "password".bcrypt(salt)
        "password".isBcrypted(hash) must beTrue
        "my new password".isBcrypted(hash) must beFalse
    }
  }

  def measure[T](func: => T): Long = {
    val start = System.currentTimeMillis()
    func
    val duration = System.currentTimeMillis() - start
    duration
  }
}