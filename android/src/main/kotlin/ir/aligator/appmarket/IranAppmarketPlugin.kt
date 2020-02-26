package ir.aligator.appmarket

import android.content.Context
import androidx.annotation.Keep
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import ir.aligator.appmarket.markets.getMarketById

/** IranAppmarketPlugin */
public class IranAppmarketPlugin: FlutterPlugin, MethodCallHandler {
  private lateinit var channel : MethodChannel
  lateinit var context: Context

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "ir.aligator.iran.appmarket")
    channel.setMethodCallHandler(this)
    context = flutterPluginBinding.applicationContext
  }

  companion object {
    @Keep
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "ir.aligator.iran.appmarket")
      val plugin = IranAppmarketPlugin()
      plugin.context = registrar.context()
      channel.setMethodCallHandler(plugin)
    }
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    when(call.method){
      "showAppPage" -> {
        val marketId = call.argument<Long>("market_id")!!.toInt()
        val packageName = context.packageName

        val market = getMarketById(context,marketId)
        market.showAppPage(packageName)
      }
      "showAppComments" -> {
        val marketId = call.argument<Long>("market_id")!!.toInt()
        val packageName = context.packageName

        val market = getMarketById(context,marketId)
        market.showAppComments(packageName)
      }
      "showDeveloperApps" -> {
        val marketId = call.argument<Long>("market_id")!!.toInt()
        val developerId = call.argument<String>("developer_id")!!

        val market = getMarketById(context,marketId)
        market.showDeveloperApps(developerId)
      }
      "getFromBuildConfig" -> {
        val packageName = context.packageName
        val key = call.argument<String>("key")!!

        val className = packageName + "BuildConfig"
        val buildConfig = Class.forName(className)

        val field = buildConfig.getField(key)
        val value = field.get(null)

        result.success(value)
      }
      else -> result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
