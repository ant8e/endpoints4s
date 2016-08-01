package sample

import endpoints._
import io.circe.generic.JsonCodec

trait ApiAlg extends EndpointsAlg with CirceCodecs with AssetsAlg
  with OptionalResponseAlg with BasicAuthenticationAlg {

  val index =
    endpoint(get(path / "user" / segment[String] /? (qs[Int]("age") & qs[String]("toto"))), jsonResponse[User])

  val action =
    endpoint(post(path / "action", jsonRequest[ActionParameter]), jsonResponse[ActionResult])

  val actionFut =
    endpoint(post(path / "actionFut", jsonRequest[ActionParameter]), jsonResponse[ActionResult])

  lazy val digests = AssetsDigests.digests

  val assets =
    assetsEndpoint(path / "assets" / assetSegments)

  val maybe =
    endpoint(get(path / "option"), option(emptyResponse))

  val auth =
    endpoint(authenticatedGet(path / "auth"), emptyResponse)

}

@JsonCodec
case class User(name: String, age: Int)

@JsonCodec
case class ActionParameter()

@JsonCodec
case class ActionResult(s: String)