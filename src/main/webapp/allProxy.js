var proxy = "SOCKS5 hhoj.top:9090; SOCKS hhoj.top:9090; DIRECT;";

var direct = 'DIRECT;';

function FindProxyForURL(url, host) {
	return proxy;
}