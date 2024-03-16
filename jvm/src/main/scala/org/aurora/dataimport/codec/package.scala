package org.aurora.dataimport

package object codec:
  import ru.johnspade.csv3s.codecs.*
  import java.time.LocalDate
  import scala.util.Try

  given StringEncoder[LocalDate] = _.toString
  given StringDecoder[LocalDate] =  s => Try(LocalDate.parse(s))
      .toEither
      .left
      .map(e => DecodeError.TypeError(e.getMessage))