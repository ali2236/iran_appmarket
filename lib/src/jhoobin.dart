import 'package:intent/action.dart';
import 'package:intent/intent.dart';
import 'package:iran_appmarket/iran_appmarket.dart';

class Jhoobin implements IAppMarket {

  @override
  void showAppPage(String packageName) {
    Intent()
      ..setAction(Action.ACTION_VIEW)
      ..setData(Uri.parse('jhoobin://search?q=$packageName'))
      ..startActivity();
  }

  @override
  void showAppComments(String packageName) {
    Intent()
      ..setAction(Action.ACTION_VIEW)
      ..setData(Uri.parse('jhoobin://comment?q=$packageName'))
      ..startActivity();
  }


  @override
  void showDeveloperApps(String developerId) {
    Intent()
      ..setAction(Action.ACTION_VIEW)
      ..setData(Uri.parse('jhoobin://collection?type=APP&id=$developerId'))
      ..startActivity();
  }

}