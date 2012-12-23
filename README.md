# Scala Bcrypt [![Build Status](https://secure.travis-ci.org/t3hnar/scala-bcrypt.png)](http://travis-ci.org/t3hnar/scala-bcrypt)

Scala Bcrypt is a scala friendly wrapper of [jBCRYPT](http://www.mindrot.org/projects/jBCrypt/)

## Example

```scala
    scala>  import ua.t3hnar.bcrypt._
    import ua.t3hnar.bcrypt._

    scala>  "password".bcrypt
    res1: String = $2a$10$iXIfki6AefgcUsPqR.niQ.FvIK8vdcfup09YmUxmzS/sQeuI3QOFG

    scala>  "password".isBcrypted("$2a$10$iXIfki6AefgcUsPqR.niQ.FvIK8vdcfup09YmUxmzS/sQeuI3QOFG")
    res2: Boolean = true
```

## Setup

1. Add this repository to your pom.xml:
```xml
    <repository>
        <id>thenewmotion</id>
        <name>The New Motion Repository</name>
        <url>http://nexus.thenewmotion.com/content/repositories/releases-public</url>
    </repository>
```

2. Add dependency to your pom.xml:
```xml
    <dependency>
        <groupId>ua.t3hnar.bcrypt</groupId>
        <artifactId>scala-bcrypt_2.10</artifactId>
        <version>2.0</version>
    </dependency>
```