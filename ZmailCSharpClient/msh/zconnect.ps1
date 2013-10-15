param (
	[string] $server = "localhost",
	[int] $port = 7070,
	[bool] $secure = $false,
	[bool] $faultToException = $true )

$global:zdisp = new-object Zmail.Client.Dispatcher $server, $port, $secure, $faultToException
echo $global:zdisp