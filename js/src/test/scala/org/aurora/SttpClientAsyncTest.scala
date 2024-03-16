package org.aurora

import org.scalatest._
import wordspec._
import matchers._


class SttpClientAsyncAsyncTest extends AsyncWordSpec with should.Matchers{
  implicit override def executionContext = scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
  "this" should {
    "work" in {
      val response = fetch
      response.map{x => info(s"$x"); true should be(true)}
    }
  }
}
