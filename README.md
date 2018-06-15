# Scala Bcrypt [![Build Status](https://secure.travis-ci.org/t3hnar/scala-bcrypt.svg)](http://travis-ci.org/t3hnar/scala-bcrypt) [![Coverage Status](https://coveralls.io/repos/evolution-gaming/scala-bcrypt/badge.svg)](https://coveralls.io/r/evolution-gaming/scala-bcrypt) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/8e0fb26d880446428fd94b7e051e9cb0)](https://www.codacy.com/app/evolution-gaming/scala-bcrypt?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=t3hnar/scala-bcrypt&amp;utm_campaign=Badge_Grade) [![Version](https://img.shields.io/maven-central/v/com.github.t3hnar/scala-bcrypt_2.11.svg?label=version)](http://search.maven.org/#search%7Cga%7C1%7Cg%3Acom.github.t3hnar%20AND%20scala-bcrypt)

Scala Bcrypt is a scala friendly wrapper of [jBCRYPT](http://www.mindrot.org/projects/jBCrypt/)

## Examples

### Safe APIs
The safe APIs will result in `scala.util.Failure`s and `scala.util.Success`s when executing operations to explicitly 
indicate the possibility that certain bcrypt operations can fail due to providing incorrect salt versions or number of 
rounds (eg. > 30 rounds). 

#### Encrypt password

```scala
    scala>  import com.github.t3hnar.bcrypt._
    import com.github.t3hnar.bcrypt._

    scala>  "password".bcrypt
    res1: String = $2a$10$iXIfki6AefgcUsPqR.niQ.FvIK8vdcfup09YmUxmzS/sQeuI3QOFG
```

#### Validate password

```scala
    scala>  "password".isBcryptedSafe("$2a$10$iXIfki6AefgcUsPqR.niQ.FvIK8vdcfup09YmUxmzS/sQeuI3QOFG")
    res2: Try[Boolean] = Success(true)
```

#### Composition
Since `Try` is monadic, you can use a for-comprehension to compose operations that return `Success` or `Failure` with
fail-fast semantics. You can also use the desugared notation (`flatMap`s and `map`s) if you prefer
```scala
    scala>  val bcryptAndVerify = for {
      bcrypted <- "hello".bcrypt(12)
      result <- "hello".isBcryptedSafe(bcrypted)
    } yield result
    res: Try[Boolean] = Success(true)
```

#### Advanced usage

By default, the `salt` generated internally, and developer does not need to generate and store salt.
But if you decide that you need to manage salt, you can use `bcrypt` in the following way:

```scala
    scala>  val salt = generateSalt
    salt: String = $2a$10$8K1p/a0dL1LXMIgoEDFrwO

    scala>  "password".bcrypt(salt)
    res3: Try[String] = Success($2a$10$8K1p/a0dL1LXMIgoEDFrwOfMQbLgtnOoKsWc.6U6H0llP3puzeeEu)
```

### Unsafe APIs
The Unsafe APIs will result in Exceptions being thrown when executing operations as certain bcrypt operations can fail 
due to providing incorrect salt versions or number of rounds (eg. > 30 rounds). These Unsafe APIs are present for 
backwards compatibility reasons and should be avoided if possible

#### Encrypt password

```scala
    scala>  import com.github.t3hnar.bcrypt._
    import com.github.t3hnar.bcrypt._

    scala>  "password".bcrypt
    res1: String = $2a$10$iXIfki6AefgcUsPqR.niQ.FvIK8vdcfup09YmUxmzS/sQeuI3QOFG
```

#### Validate password

```scala
    scala>  "password".isBcrypted("$2a$10$iXIfki6AefgcUsPqR.niQ.FvIK8vdcfup09YmUxmzS/sQeuI3QOFG")
    res2: Boolean = true
```

#### Advanced usage

```scala
    scala>  val salt = generateSalt
    salt: String = $2a$10$8K1p/a0dL1LXMIgoEDFrwO

    scala>  "password".bcrypt(salt)
    res3: String = $2a$10$8K1p/a0dL1LXMIgoEDFrwOfMQbLgtnOoKsWc.6U6H0llP3puzeeEu
```

## Setup

#### SBT
```scala
libraryDependencies += "com.github.t3hnar" %% "scala-bcrypt" % "3.2"
```

#### Maven
```xml
<dependency>
    <groupId>com.github.t3hnar</groupId>
    <artifactId>scala-bcrypt_2.12</artifactId>
    <version>3.2</version>
</dependency>
```
