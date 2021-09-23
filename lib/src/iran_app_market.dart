
import 'package:flutter/services.dart';
import 'package:iran_appmarket/src/app_market.dart';
import 'package:package_info_plus/package_info_plus.dart';

class IranAppMarket {

  static MethodChannel _channel = MethodChannel('ir.aligator.iran.appmarket');

  static const Map<AppMarket, int> _market_id = const {
    AppMarket.cafeBazaar : 0,
    AppMarket.myket : 1,
    AppMarket.iranApps : 2,
    AppMarket.jhoobin : 3,
  };

  static void showAppPage(AppMarket market) async{
    final packageName = (await PackageInfo.fromPlatform()).packageName;
    IAppMarket.ofType(market).showAppPage(packageName);
  }

  static void showAppComments(AppMarket market) async{
    final packageName = (await PackageInfo.fromPlatform()).packageName;
    IAppMarket.ofType(market).showAppComments(packageName);
  }

  static void showDeveloperApps(AppMarket market, String developerId) async{
    IAppMarket.ofType(market).showDeveloperApps(developerId);
  }

}