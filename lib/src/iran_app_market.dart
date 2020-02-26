
import 'package:flutter/services.dart';
import 'package:iran_appmarket/src/app_market.dart';

class IranAppMarket {

  static MethodChannel _channel = MethodChannel('ir.aligator.iran.appmarket');

  static const Map<AppMarket, int> _market_id = const {
    AppMarket.cafeBazaar : 0,
    AppMarket.myket : 1,
    AppMarket.iranApps : 2,
    AppMarket.jhoobin : 3,
  };

  void showAppPage(AppMarket market){
    _channel.invokeMethod("showAppPage",{
      'market_id' : _market_id[market],
    });
  }

  void showAppComments(AppMarket market){
    _channel.invokeMethod("showAppComments",{
      'market_id' : _market_id[market],
    });
  }

  void showDeveloperApps(AppMarket market, String developerId){
    _channel.invokeMethod("showDeveloperApps",{
      'market_id' : _market_id[market],
      'developer_id' : developerId,
    });
  }

}