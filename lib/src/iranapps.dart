import 'package:intent/action.dart';
import 'package:intent/extra.dart';
import 'package:intent/intent.dart';
import 'package:iran_appmarket/iran_appmarket.dart';

class IranApps implements IAppMarket {
  static const iranapps = 'ir.tgbs.android.iranapp';

  @override
  void showAppPage(String packageName) {
    Intent()
      ..setAction(Action.ACTION_VIEW)
      ..setData(Uri.parse('iranapps://app/$packageName'))
      ..putExtra(Extra.EXTRA_PACKAGE_NAME, iranapps)
      ..startActivity();
  }

  @override
  void showAppComments(String packageName) {
    Intent()
      ..setAction(Action.ACTION_VIEW)
      ..setData(Uri.parse('iranapps://app/$packageName?a=comment&r=5'))
      ..putExtra(Extra.EXTRA_PACKAGE_NAME, iranapps)
      ..startActivity();
  }

  @override
  void showDeveloperApps(String developerId) {
    Intent()
      ..setAction(Action.ACTION_VIEW)
      ..setData(Uri.parse("iranapps://user/$developerId"))
      ..putExtra(Extra.EXTRA_PACKAGE_NAME, iranapps)
      ..startActivity();
  }
}