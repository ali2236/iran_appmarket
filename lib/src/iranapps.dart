import 'package:android_intent/android_intent.dart';
import 'package:iran_appmarket/iran_appmarket.dart';

class IranApps implements IAppMarket {
  static const iranapps = 'ir.tgbs.android.iranapp';

  @override
  void showAppPage(String packageName) {
    AndroidIntent(
      action: "android.intent.action.VIEW",
      data: 'iranapps://app/$packageName',
      arguments: {
        "android.intent.extra.PACKAGE_NAME" : iranapps,
      },
    ).launch();
  }

  @override
  void showAppComments(String packageName) {
    AndroidIntent(
      action: "android.intent.action.VIEW",
      data: 'iranapps://app/$packageName?a=comment&r=5',
      arguments: {
        "android.intent.extra.PACKAGE_NAME" : iranapps,
      },
    ).launch();
  }

  @override
  void showDeveloperApps(String developerId) {
    AndroidIntent(
      action: "android.intent.action.VIEW",
      data: 'iranapps://user/$developerId',
      arguments: {
        "android.intent.extra.PACKAGE_NAME" : iranapps,
      },
    ).launch();
  }
}