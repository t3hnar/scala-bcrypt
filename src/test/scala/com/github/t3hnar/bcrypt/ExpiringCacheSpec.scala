package com.github.t3hnar.bcrypt

import org.specs2.mutable.SpecificationWithJUnit
import java.util.concurrent.TimeUnit
import PasswordCache._

/**
 * @author Yaroslav Klymko
 */
class ExpiringCacheSpec extends SpecificationWithJUnit {
  "ExpiringCache" should {
    "clean expired values" >> {

      var current = 0L

      def entry(s: String) = CacheEntry(s, s)
      val e = entry("a")

      val queryOverflow = 5

      val cache = new ExpiringCache(1, TimeUnit.MINUTES, queryOverflow) {
        override def currentMillis = current
      }

      cache.put(e, false)
      cache.map must haveSize(1)

      (0 to queryOverflow).foreach(_ => cache.get(e))

      cache.map must haveSize(1)
      current = TimeUnit.MINUTES.toMillis(1)
      cache.get(e)
      cache.map must beEmpty
    }
  }
}
