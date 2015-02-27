#import <Foundation/Foundation.h>
#import <Cordova/CDVPlugin.h>
#import "Mixpanel.h"

@interface CDVMixpanel: CDVPlugin

- (void) init:(CDVInvokedUrlCommand*)command;
- (void) alias:(CDVInvokedUrlCommand*)command;
- (void) identify:(CDVInvokedUrlCommand*)command;
- (void) time_event:(CDVInvokedUrlCommand*)command;
- (void) track:(CDVInvokedUrlCommand*)command;
- (void) flush:(CDVInvokedUrlCommand*)command;
- (void) super_properties:(CDVInvokedUrlCommand*)command;
- (void) distinct_id:(CDVInvokedUrlCommand*)command;
- (void) register:(CDVInvokedUrlCommand*)command;
- (void) unregister:(CDVInvokedUrlCommand*)command;
- (void) register_once:(CDVInvokedUrlCommand*)command;
- (void) clear:(CDVInvokedUrlCommand*)command;
- (void) reset:(CDVInvokedUrlCommand*)command;

+ (MixpanelPeople*) People;
+ (void) result:(CDVInvokedUrlCommand*)command
        withDelegate:(id <CDVCommandDelegate>)delegate;
+ (void) result:(CDVInvokedUrlCommand*)command
        withDelegate:(id <CDVCommandDelegate>)delegate
        andError:(NSString*)error;
+ (void) result:(CDVInvokedUrlCommand*)command
        withDelegate:(id <CDVCommandDelegate>)delegate
        andString:(NSString*)prop;
+ (void) result:(CDVInvokedUrlCommand*)command
        withDelegate:(id <CDVCommandDelegate>)delegate
        andDictionary:(NSDictionary*)props;
+ (void) resultUninitialized:(CDVInvokedUrlCommand*)command
        withDelegate:(id <CDVCommandDelegate>)delegate;

@end
