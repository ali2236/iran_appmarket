import 'package:iran_appmarket/src/Myket.dart';
import 'package:iran_appmarket/src/bazaar.dart';
import 'package:iran_appmarket/src/iranapps.dart';
import 'package:iran_appmarket/src/jhoobin.dart';

enum AppMarket{
  cafeBazaar,
  myket,
  jhoobin,
  iranApps,
}

abstract class IAppMarket {

  factory IAppMarket.ofType(AppMarket appMarket){
    switch(appMarket){
      case AppMarket.cafeBazaar:
        return CafeBazaar();
      case AppMarket.myket:
        return Myket();
      case AppMarket.jhoobin:
        return Jhoobin();
      case AppMarket.iranApps:
        return IranApps();
    }
  }

  void showAppPage(String packageName);
  void showAppComments(String packageName);
  void showDeveloperApps(String developerId);
}

String appMarketName(AppMarket appMarket){
  const names = {
    AppMarket.cafeBazaar : 'CafeBazaar',
    AppMarket.myket : 'Myket',
    AppMarket.jhoobin: 'Jhoobin',
    AppMarket.iranApps: 'IranApps'
  };
  return names[appMarket]!;
}