package ir.aligator.appmarket.markets

import android.app.Application
import android.content.ComponentName
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import ir.aligator.appmarket.exceptions.FeatureNotSupportedException

class Jhoobin(private val context: Context) : AppMarket {
    override val id: Int = 3
    override val name: String = "ایده پرداز ژوبین"

    override fun showAppPage(packageId: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("jhoobin://search?q=$packageId")
        context.startActivity(intent)
    }

    override fun showAppComments(packageId: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("jhoobin://comment?q=$packageId")
        context.startActivity(intent)
    }

    override fun showDeveloperApps(devId: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("jhoobin://collection?type=APP&id=$devId")
        context.startActivity(intent)
    }

    override fun getVersionCode(packageId: String): Long {
        throw FeatureNotSupportedException(name, "getVersionCode")
    }
}