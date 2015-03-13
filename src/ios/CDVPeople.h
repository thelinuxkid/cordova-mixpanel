#import <Foundation/Foundation.h>
#import <Cordova/CDVPlugin.h>

@interface CDVPeople: CDVPlugin

- (void) distinct_id:(CDVInvokedUrlCommand*)command;
- (void) set:(CDVInvokedUrlCommand*)command;
- (void) set_once:(CDVInvokedUrlCommand*)command;
- (void) increment:(CDVInvokedUrlCommand*)command;
- (void) append:(CDVInvokedUrlCommand*)command;
- (void) union:(CDVInvokedUrlCommand*)command;
- (void) unset:(CDVInvokedUrlCommand*)command;
- (void) track_charge:(CDVInvokedUrlCommand*)command;
- (void) clear_charges:(CDVInvokedUrlCommand*)command;
- (void) delete_user:(CDVInvokedUrlCommand*)command;
- (void) add_push_token:(CDVInvokedUrlCommand*)command;

@end
