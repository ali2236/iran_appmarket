import 'package:android_intent_plus/android_intent.dart';
import 'package:iran_appmarket/iran_appmarket.dart';

class Myket implements IAppMarket {
  @override
  void showAppPage(String packageName) {
    AndroidIntent(
      action: "android.intent.action.VIEW",
      data: 'myket://details?id=$packageName',
    ).launch();
  }

  @override
  void showAppComments(String packageName) {
    AndroidIntent(
      action: "android.intent.action.VIEW",
      data: 'myket://comment?id=$packageName',
    ).launch();
  }

  @override
  void showDeveloperApps(String developerId) {
    AndroidIntent(
      action: "android.intent.action.VIEW",
      data: 'myket://developer/$developerId',
    ).launch();
  }
}
