package ua.t3hnar

/*
   Copyright 2011 Yaroslav Klymko

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

import org.mindrot.{BCrypt => B}

/**
 * @author Yaroslav Klymko
 */
package object bcrypt {
  implicit def stringWithBCrypt(password: String) = new {
    def bcrypt: String = B.hashpw(password, gensalt())

    def bcrypt(rounds: Int): String = B.hashpw(password, gensalt(rounds))

    def bcrypted_?(bcryptedPassword: String): Boolean =
      B.checkpw(password, bcryptedPassword)
  }

  def gensalt(rounds: Int = 10): String = B.gensalt(rounds)
}