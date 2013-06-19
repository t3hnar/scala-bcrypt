# Scala Bcrypt [![Build Status](https://secure.travis-ci.org/t3hnar/scala-bcrypt.png)](http://travis-ci.org/t3hnar/scala-bcrypt)

Scala Bcrypt is a scala friendly wrapper of [jBCRYPT](http://www.mindrot.org/projects/jBCrypt/)

## Example

```scala
    scala>  import com.github.t3hnar.bcrypt._
    import com.github.t3hnar.bcrypt._

    scala>  "password".bcrypt
    res1: String = $2a$10$iXIfki6AefgcUsPqR.niQ.FvIK8vdcfup09YmUxmzS/sQeuI3QOFG

    scala>  "password".isBcrypted("$2a$10$iXIfki6AefgcUsPqR.niQ.FvIK8vdcfup09YmUxmzS/sQeuI3QOFG")
    res2: Boolean = true

    scala>  val salt = generateSalt
    salt  : String = $2a$10$8K1p/a0dL1LXMIgoEDFrwO

    scala>	"password".bcrypt(salt)
    res3: String = $2a$10$8K1p/a0dL1LXMIgoEDFrwOfMQbLgtnOoKsWc.6U6H0llP3puzeeEu
```

## Setup

* Maven:
```xml
    <dependency>
        <groupId>com.github.t3hnar</groupId>
        <artifactId>scala-bcrypt_2.10</artifactId>
        <version>2.1</version>
    </dependency>
```

* Sbt
```scala
    libraryDependencies += "com.github.t3hnar" % "scala-bcrypt_2.10" % "2.1"
```
