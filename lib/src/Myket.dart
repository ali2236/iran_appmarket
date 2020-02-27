import 'package:intent/action.dart';
import 'package:intent/intent.dart';
import 'package:iran_appmarket/iran_appmarket.dart';

class Myket implements IAppMarket {

  @override
  void showAppPage(String packageName) {
    Intent()
      ..setAction(Action.ACTION_VIEW)
      ..setData(Uri.parse('myket://details?id=$packageName'))
      ..startActivity();
  }

  @override
  void showAppComments(String packageName) {
    Intent()
      ..setAction(Action.ACTION_VIEW)
      ..setData(Uri.parse('myket://comment?id=$packageName'))
      ..startActivity();
  }

  @override
  void showDeveloperApps(String developerId) {
    Intent()
      ..setAction(Action.ACTION_VIEW)
      ..setData(Uri.parse('myket://developer/$developerId'))
      ..startActivity();
  }

}