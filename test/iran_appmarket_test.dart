import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:iran_appmarket/iran_appmarket.dart';

void main() {
  const MethodChannel channel = MethodChannel('iran_appmarket');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await IranAppmarket.platformVersion, '42');
  });
}
