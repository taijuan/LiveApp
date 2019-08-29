package com.live.utils

import com.alibaba.sdk.android.httpdns.HttpDns
import com.alibaba.sdk.android.httpdns.HttpDnsService
import com.live.app.app
import okhttp3.Dns
import java.net.InetAddress


val httpDns: HttpDnsService by lazy {
    HttpDns.getService(app, "153956", "7b26b647bc244b13e7db327b80277e06").apply {
        setPreResolveAfterNetworkChanged(true)
        setPreResolveHosts(arrayListOf("api.cdeclips.com"))
        setExpiredIPEnabled(true)
        setLogEnabled(true)
        setHTTPSRequestEnabled(true)
        "HttpDns 初始化成功".logE()
    }
}

class HttpDns : Dns {
    override fun lookup(hostname: String): List<InetAddress> {
        val ip = httpDns.getIpByHostAsync(hostname)
        return if (ip.isNullOrEmpty()) {
            Dns.SYSTEM.lookup(hostname)
        } else {
            "hostname -> $hostname".logE()
            "ip -> $ip".logE()
            arrayListOf(InetAddress.getByName(ip))
        }
    }

}