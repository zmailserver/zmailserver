$global:zreq.ApiRequest = new-object Zmail.Client.Mail.GetFolderRequest
$global:zres = $global:zdisp.SendRequest( $global:zreq )

echo $global:zres