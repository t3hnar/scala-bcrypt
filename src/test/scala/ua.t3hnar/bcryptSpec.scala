package ua.t3hnar

import org.specs2.mutable.SpecificationWithJUnit
import bcrypt._

/**
 * @author Yaroslav Klymko
 */
class bcryptSpec extends SpecificationWithJUnit {

  "bcrypt" should {
    "stringWithBCrypt" >> {
      val encrypted = "my bad password".bcrypt
      "my bad password".bcrypted_?(encrypted) must beTrue
      "my new password".bcrypted_?(encrypted) must beFalse
    }
  }
}