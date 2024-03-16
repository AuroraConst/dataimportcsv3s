package org.aurora

import sttp.client4.{basicRequest,Response}
import sttp.client4.*
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit

def fetch = 

  import quick.* 
  val request =  quick.quickRequest.get(uri"https://httpbin.org/get")
  val backend = quick.backend

  val response = request.send()
  //note you cannot use Await in Scala Js.

  response

