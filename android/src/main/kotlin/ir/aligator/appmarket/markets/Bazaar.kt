package ir.aligator.appmarket.markets

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.IBinder
import com.farsitel.bazaar.IUpdateCheckService


class Bazaar(private val context: Context) : AppMarket {
    override val id: Int = 0
    override val name: String = "کافه بازار"

    override fun showAppPage(packageId: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("bazaar://details?id=$packageId")
        intent.setPackage("com.farsitel.bazaar")
        context.startActivity(intent)
    }

    override fun showAppComments(packageId: String) {
        val intent = Intent(Intent.ACTION_EDIT)
        intent.data = Uri.parse("bazaar://details?id=$packageId")
        intent.setPackage("com.farsitel.bazaar")
        context.startActivity(intent)
    }

    override fun showDeveloperApps(devId: String) {
        val intent = Intent(Intent.ACTION_VIEW);
        intent.data = Uri.parse("bazaar://collection?slug=by_author&aid=$devId")
        intent.setPackage("com.farsitel.bazaar");
        context.startActivity(intent)
    }

    override fun getVersionCode(packageId: String): Long {
        val connection = UpdateServiceConnection()
        val intent = Intent(
                "com.farsitel.bazaar.service.UpdateCheckService.BIND")
        intent.setPackage("com.farsitel.bazaar")
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        val versionCode = connection.getVersionCode(packageId)
        context.unbindService(connection)
        return versionCode
    }

   inner class UpdateServiceConnection : ServiceConnection {
        lateinit var updateService: IUpdateCheckService

        override fun onServiceConnected(name: ComponentName, boundService: IBinder) {
            updateService = IUpdateCheckService.Stub.asInterface(boundService)
        }

        override fun onServiceDisconnected(name: ComponentName) {}

       fun getVersionCode(packageId: String): Long = updateService.getVersionCode(packageId)

    }

}