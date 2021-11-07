package exercises.valfunction

import exercises.valfunction.ValueFunctionExercises._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class ValueFunctionExercisesTest extends AnyFunSuite with ScalaCheckDrivenPropertyChecks {

  /////////////////////////////////////////////////////
  // Exercise 1: String API with higher-order functions
  /////////////////////////////////////////////////////

  // replace `ignore` by `test` to enable the test
  test("selectDigits examples") {
    assert(selectDigits("hello4world-80") == "480")
    assert(selectDigits("welcome") == "")
  }

  // replace `ignore` by `test` to enable the test
  test("selectDigits length is smaller") {
    forAll { (text: String) =>
      assert(selectDigits(text).length <= text.length)
    }
  }

  // replace `ignore` by `test` to enable the test
  test("selectDigits content all are digits") {
    forAll { (text: String) =>
      selectDigits(text).foreach(c => assert(c.isDigit)) // placeholder does not work here
    }
  }

  test("selectDigits creates a String with digits only, Sriram1") {
    forAll { (text: String) =>
      assert(selectDigits(text).forall(_.isDigit))
    }
  }

  test("selectDigits creates a String that has characters from the original, Sriram1") {
    forAll { (text: String) =>
      assert(selectDigits(text).forall(text.contains(_))) // we could use foreach like previously and put assert inside
    }
  }
  // replace `ignore` by `test` to enable the test
  test("secret content all are *") {
    forAll { (text: String) =>
      secret(text).foreach(c => assert(c == '*')) // placeholder does not work here
    }
  }

  // replace `ignore` by `test` to enable the test
  test("secret idempotence") {
    forAll { (text: String) =>
      val once  = secret(text)
      val twice = secret(secret(text))
      assert(once == twice)
    }
  }

  test("secret converts everything in input to asterisk, Sriram2") {
    assert(secret("Welcome123") == "**********")
  }

  test("secret takes an arbitrary string whose result should be * times the size, Sriram2") {
    forAll { (text: String) =>
      assert(secret(text) == "*" * text.length)
    }
  }

  test("isValidUsername") { // also can change case deleting some chars etc.
    forAll { (username: String) =>
      assert(isValidUsername(username.reverse) == isValidUsername(username))
    }
  }

  test("isValidUsername property checks, Sriram3") {
    forAll { (username: String) =>
      assert(isValidUsername(username) == isValidUsername(username.reverse))
      assert(isValidUsername(username) == isValidUsername(username.map(_.toUpper)))
      assert(isValidUsername(username) == isValidUsername(username.map(_.toLower)))
    }
  }
  ///////////////////////
  // Exercise 2: Point
  ///////////////////////

  test("Point isPositive") { // x.abs will fail property testing due to Int.MinValue
    forAll { (x: Int, y: Int, z: Int) =>
      assert(Point(x.max(0), y.max(0), z.max(0)).isPositive)
    }
  }

  test("isPositive Point max checks, Sriram4") {
    forAll { (x: Int, y: Int, z: Int) =>
      assert(Point(x.max(0), y.max(0), z.max(0)).isPositive) // Isn't this obvious though?
    }
  }

  // This flopped due to overflow and underflow
  test("isPositive Point property check by translation of positive points and negative points, Sriram4") {
    forAll { (x: Int, y: Int, z: Int) =>
      //  assert((Point(x, y, z).isPositive && Point(x + 1, y + 1, z + 1).isPositive) || !Point(x, y, z).isPositive)
      //assert((!Point(x, y, z).isPositive && !Point(x - 1, y - 1, z - 1).isPositive) || Point(x, y, z).isPositive)
    }
  }

  test("Point isEven") { //
    forAll { (x: Int, y: Int, z: Int) =>
      assert(Point(x, y, z).isEven == Point(x + 2, y + 2, z + 2).isEven)
    }
  }

  test("Point forAll") { //
    forAll { (x: Int, y: Int, z: Int, predicate: Int => Boolean) =>
      Point(x, y, z).forAll(predicate) == List(x, y, z).forall(predicate) // using collection forall
    }
  }

}
