package io.toolebox.gatlinglambdaextension.protocol

case class LambdaProtocolBuilder(
    awsAccessKeyId: Option[String] = None,
    awsSecretAccessKey: Option[String] = None,
    endpoint: Option[String] = None,
    region: Option[String] = None
) {
  def accessKey(key: String): LambdaProtocolBuilder =
    copy(awsAccessKeyId = Some(key))
  def secretKey(key: String): LambdaProtocolBuilder =
    copy(awsSecretAccessKey = Some(key))
  def endpoint(url: String): LambdaProtocolBuilder =
    copy(endpoint = Some(url))
  def region(region: String): LambdaProtocolBuilder =
    copy(region = Some(region))
  def build: LambdaProtocol =
    LambdaProtocol(awsAccessKeyId, awsSecretAccessKey, endpoint, region)
}
