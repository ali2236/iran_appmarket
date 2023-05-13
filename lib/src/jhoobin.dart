import 'package:android_intent_plus/android_intent.dart';
import 'package:iran_appmarket/iran_appmarket.dart';

class Jhoobin implements IAppMarket {

  @override
  void showAppPage(String packageName) {
    AndroidIntent(
      action: "android.intent.action.VIEW",
      data: 'jhoobin://search?q=$packageName',
    ).launch();
  }

  @override
  void showAppComments(String packageName) {
    AndroidIntent(
      action: "android.intent.action.VIEW",
      data: 'jhoobin://comment?q=$packageName',
    ).launch();
  }


  @override
  void showDeveloperApps(String developerId) {
    AndroidIntent(
      action: "android.intent.action.VIEW",
      data: 'jhoobin://collection?type=APP&id=$developerId',
    ).launch();
  }

}