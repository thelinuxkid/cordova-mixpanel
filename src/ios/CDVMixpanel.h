#import <Foundation/Foundation.h>
#import <Cordova/CDVPlugin.h>

@interface CDVMixpanel: CDVPlugin

- (void) init:(CDVInvokedUrlCommand*)command;
- (void) alias:(CDVInvokedUrlCommand*)command;
- (void) identify:(CDVInvokedUrlCommand*)command;
- (void) timeEvent:(CDVInvokedUrlCommand*)command;
- (void) track:(CDVInvokedUrlCommand*)command;
- (void) flush:(CDVInvokedUrlCommand*)command;
- (void) superProperties:(CDVInvokedUrlCommand*)command;
- (void) distinctID:(CDVInvokedUrlCommand*)command;
- (void) register:(CDVInvokedUrlCommand*)command;
- (void) unregister:(CDVInvokedUrlCommand*)command;
- (void) registerOnce:(CDVInvokedUrlCommand*)command;
- (void) clear:(CDVInvokedUrlCommand*)command;
- (void) reset:(CDVInvokedUrlCommand*)command;
- (void) deviceInfo:(CDVInvokedUrlCommand*)command;

@end
