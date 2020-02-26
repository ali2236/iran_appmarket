package ir.aligator.appmarket.markets

import android.content.Context

interface AppMarket {
    
    val id: Int
    val name: String
    
    fun showAppPage(packageId: String)
    
    fun showAppComments(packageId: String)
    
    fun showDeveloperApps(devId: String)
    
    fun getVersionCode(packageId: String) : Long
}

fun getMarketById(context: Context, id: Int): AppMarket{
    return when(id){
        0 -> Bazaar(context)
        1 -> Myket(context)
        2 -> IranApps(context)
        3 -> Jhoobin(context)
        else -> throw Exception("id must be a number between 0 and 3 (inclusive)")
    }
}