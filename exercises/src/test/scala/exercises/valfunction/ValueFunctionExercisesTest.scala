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
  ///////////////////////
  // Exercise 2: Point
  ///////////////////////

}
