import 'package:android_intent/android_intent.dart';
import 'app_market.dart';

class CafeBazaar implements IAppMarket {
  static const bazaar = 'com.farsitel.bazaar';

  @override
  void showAppPage(String packageName) {
    AndroidIntent(
      action: "android.intent.action.VIEW",
      data: 'bazaar://details?id=$packageName',
      arguments: {
        "android.intent.extra.PACKAGE_NAME" : bazaar,
      },
    ).launch();
  }

  @override
  void showAppComments(String packageName) {
    AndroidIntent(
      action: "android.intent.action.EDIT",
      data: 'bazaar://details?id=$packageName',
      arguments: {
        "android.intent.extra.PACKAGE_NAME" : bazaar,
      },
    ).launch();
  }

  @override
  void showDeveloperApps(String developerId) {
    AndroidIntent(
      action: "android.intent.action.VIEW",
      data: 'bazaar://collection?slug=by_author&aid=$developerId',
      arguments: {
        "android.intent.extra.PACKAGE_NAME" : bazaar,
      },
    ).launch();
  }
}
