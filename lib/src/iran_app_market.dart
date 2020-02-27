
import 'package:flutter/services.dart';
import 'package:get_version/get_version.dart';
import 'package:iran_appmarket/src/app_market.dart';

class IranAppMarket {

  static MethodChannel _channel = MethodChannel('ir.aligator.iran.appmarket');

  static const Map<AppMarket, int> _market_id = const {
    AppMarket.cafeBazaar : 0,
    AppMarket.myket : 1,
    AppMarket.iranApps : 2,
    AppMarket.jhoobin : 3,
  };

  static void showAppPage(AppMarket market) async{
    final packageName = await GetVersion.appID;
    IAppMarket.ofType(market).showAppPage(packageName);
/*    _channel.invokeMethod("showAppPage",{
      'market_id' : _market_id[market],
    });*/
  }

  static void showAppComments(AppMarket market) async{
    final packageName = await GetVersion.appID;
    IAppMarket.ofType(market).showAppComments(packageName);
/*    _channel.invokeMethod("showAppComments",{
      'market_id' : _market_id[market],
    });*/
  }

  static void showDeveloperApps(AppMarket market, String developerId) async{
    IAppMarket.ofType(market).showDeveloperApps(developerId);
/*    _channel.invokeMethod("showDeveloperApps",{
      'market_id' : _market_id[market],
      'developer_id' : developerId,
    });*/
  }

}