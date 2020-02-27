import 'package:intent/action.dart';
import 'package:intent/extra.dart';
import 'package:intent/intent.dart';

import 'app_market.dart';

class CafeBazaar implements IAppMarket {
  static const bazaar = 'com.farsitel.bazaar';

  @override
  void showAppPage(String packageName) {
    Intent()
      ..setAction(Action.ACTION_VIEW)
      ..setData(Uri.parse('bazaar://details?id=$packageName'))
      ..putExtra(Extra.EXTRA_PACKAGE_NAME, bazaar)
      ..startActivity();
  }

  @override
  void showAppComments(String packageName) {
    Intent()
      ..setAction(Action.ACTION_EDIT)
      ..setData(Uri.parse('bazaar://details?id=$packageName'))
      ..putExtra(Extra.EXTRA_PACKAGE_NAME, bazaar)
      ..startActivity();
  }

  @override
  void showDeveloperApps(String developerId) {
    Intent()
      ..setAction(Action.ACTION_VIEW)
      ..setData(Uri.parse("bazaar://collection?slug=by_author&aid=$developerId"))
      ..putExtra(Extra.EXTRA_PACKAGE_NAME, bazaar)
      ..startActivity();
  }
}
