package ir.aligator.appmarket.markets

import android.content.Context
import android.content.Intent
import android.net.Uri
import ir.aligator.appmarket.exceptions.FeatureNotSupportedException


class IranApps(private val context: Context) : AppMarket {
    override val id: Int = 2
    override val name: String = "iranApps"

    override fun showAppPage(packageId: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("iranapps://app/$packageId")
        intent.data = Uri.parse("")
        context.startActivity(intent)
    }

    override fun showAppComments(packageId: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("ir.tgbs.android.iranapp")
        intent.data = Uri.parse("iranapps://app/$packageId?a=comment&r=5")
        context.startActivity(intent)
    }

    override fun showDeveloperApps(devId: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("ir.tgbs.android.iranapp")
        intent.data = Uri.parse("iranapps://user/$devId")
        context.startActivity(intent)
    }

    override fun getVersionCode(packageId: String): Long {
        throw FeatureNotSupportedException(name, "getVersionCode")
    }
}