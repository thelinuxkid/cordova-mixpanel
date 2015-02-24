#import <Foundation/Foundation.h>
#import <Cordova/CDVPlugin.h>

@interface CDVPeople: CDVPlugin

- (void) identify:(CDVInvokedUrlCommand*)command;
- (void) set:(CDVInvokedUrlCommand*)command;
- (void) setOnce:(CDVInvokedUrlCommand*)command;
- (void) increment:(CDVInvokedUrlCommand*)command;
- (void) append:(CDVInvokedUrlCommand*)command;
- (void) union:(CDVInvokedUrlCommand*)command;
- (void) unset:(CDVInvokedUrlCommand*)command;
- (void) trackCharge:(CDVInvokedUrlCommand*)command;
- (void) clearCharges:(CDVInvokedUrlCommand*)command;
- (void) deleteUser:(CDVInvokedUrlCommand*)command;
- (void) initPushHandling:(CDVInvokedUrlCommand*)command;
- (void) setPushRegID:(CDVInvokedUrlCommand*)command;
- (void) clearPushRegID:(CDVInvokedUrlCommand*)command;
- (void) distinctID:(CDVInvokedUrlCommand*)command;

@end
