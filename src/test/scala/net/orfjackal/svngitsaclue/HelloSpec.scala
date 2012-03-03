// Copyright © 2012 Esko Luontola <www.orfjackal.net>
// This software is released under the Apache License 2.0.
// The license text is at http://www.apache.org/licenses/LICENSE-2.0

package net.orfjackal.svngitsaclue

import org.junit.runner.RunWith
import net.orfjackal.specsy.{Spec, Specsy}
import org.fest.assertions.Assertions._

@RunWith(classOf[Specsy])
class HelloSpec extends Spec {

  "says a greeting" >> {
    val greeting = new Hello().sayHello("World")
    assertThat(greeting).isEqualTo("Hello, World.")
  }
}
