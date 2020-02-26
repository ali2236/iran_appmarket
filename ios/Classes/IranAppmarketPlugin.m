#import "IranAppmarketPlugin.h"
#if __has_include(<iran_appmarket/iran_appmarket-Swift.h>)
#import <iran_appmarket/iran_appmarket-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "iran_appmarket-Swift.h"
#endif

@implementation IranAppmarketPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftIranAppmarketPlugin registerWithRegistrar:registrar];
}
@end
