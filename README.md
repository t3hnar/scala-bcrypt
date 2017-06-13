# Scala Bcrypt [![Build Status](https://secure.travis-ci.org/t3hnar/scala-bcrypt.svg)](http://travis-ci.org/t3hnar/scala-bcrypt) [![Version](https://img.shields.io/maven-central/v/com.github.t3hnar/scala-bcrypt_2.11.svg?label=version)](http://search.maven.org/#search%7Cga%7C1%7Cg%3Acom.github.t3hnar%20AND%20scala-bcrypt)

Scala Bcrypt is a scala friendly wrapper of [jBCRYPT](http://www.mindrot.org/projects/jBCrypt/)

## Examples

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
    res2: Try[Boolean] = Success(true)
```

#### Advanced usage

By default salt generated internally, and developer does not need to generate and store salt.
But if you decide that you need to manage salt, you can use `bcrypt` in the following way:

```scala
    scala>  val salt = generateSalt
    salt: String = $2a$10$8K1p/a0dL1LXMIgoEDFrwO

    scala>  "password".bcrypt(salt)
    res3: Try[String] = Success($2a$10$8K1p/a0dL1LXMIgoEDFrwOfMQbLgtnOoKsWc.6U6H0llP3puzeeEu)
```

## Setup

#### Sbt
```scala
libraryDependencies += "com.github.t3hnar" %% "scala-bcrypt" % "3.0"
```

#### Maven
```xml
<dependency>
    <groupId>com.github.t3hnar</groupId>
    <artifactId>scala-bcrypt_2.12</artifactId>
    <version>3.0</version>
</dependency>
```
