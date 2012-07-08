package ua.t3hnar

/**
 * @author Yaroslav Klymko
 */
package object bcrypt {
  implicit def string2Password(password: String) = new Password(password)
}