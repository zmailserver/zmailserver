param (	[string] $token )

$global:zreq.ApiRequest = new-object Zmail.Client.Mail.SyncRequest
$global:zres = $global:zdisp.SendRequest( $global:zreq )

echo $zres.ApiResponse