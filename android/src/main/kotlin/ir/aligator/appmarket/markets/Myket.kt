package ir.aligator.appmarket.markets

import android.content.Context
import android.content.Intent
import android.net.Uri
import ir.aligator.appmarket.utils.MyketSupportHelper


class Myket(private val context: Context) : AppMarket {
    override val id: Int = 1
    override val name: String = "myket"

    override fun showAppPage(packageId: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("myket://details?id=$packageId")
        context.startActivity(intent)
    }

    override fun showAppComments(packageId: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("myket://comment?id=$packageId")
        context.startActivity(intent)
    }

    /*
    devId is the packageName of one of the developers apps
     */
    override fun showDeveloperApps(devId: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("myket://developer/$devId")
        context.startActivity(intent)
    }

    override fun getVersionCode(packageId: String): Long {
        val myketHelper = MyketSupportHelper(context)
        myketHelper.startSetup { result ->
            if (!result.isSuccess) {
                throw Exception("myket helper setup failed.")
            }
        }
        
        val update = myketHelper.appUpdateState
        myketHelper.dispose()
        
        return update.versionCode.toLong()
    }
}