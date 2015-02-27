#import "CDVMixpanel.h"
#import "Mixpanel.h"

@implementation CDVMixpanel

static Mixpanel* mixpanel;
static MixpanelPeople *people;

- (void) init:(CDVInvokedUrlCommand*)command {
    NSString* token = [command argumentAtIndex:0];
    if (!token) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"init token not provided"];
        return;
    }
    [Mixpanel sharedInstanceWithToken:token];
    mixpanel = [Mixpanel sharedInstance];
    people = mixpanel.people;
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) alias:(CDVInvokedUrlCommand*)command {
    if (!mixpanel) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    NSString* alias = [command argumentAtIndex:0];
    if (!alias) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"alias id not provided"];
        return;
    }
    NSString* original = [command argumentAtIndex:1];
    if (!original) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"alias original id not provided"];
        return;
    }
    [mixpanel createAlias:alias forDistinctID:original];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) identify:(CDVInvokedUrlCommand*)command {
    if (!mixpanel) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    NSString* distinctID = [command argumentAtIndex:0];
    if (!distinctID) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"identify distinct id not provided"];
        return;
    }
    [mixpanel identify:distinctID];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) time_event:(CDVInvokedUrlCommand*)command {
    if (!mixpanel) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    NSString* event = [command argumentAtIndex:0];
    if (!event) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"timeEvent event name not provided"];
        return;
    }
    [mixpanel timeEvent:event];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) track:(CDVInvokedUrlCommand*)command {
    if (!mixpanel) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    NSString* event = [command argumentAtIndex:0];
    if (!event) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"track event name not provided"];
        return;
    }
    NSDictionary* props = [
        command argumentAtIndex:1 withDefault:[
            [NSMutableDictionary alloc] init]];
    [mixpanel track:event properties:props];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) flush:(CDVInvokedUrlCommand*)command {
    if (!mixpanel) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    [self.commandDelegate runInBackground:^{
            [mixpanel flush];
            [CDVMixpanel result:command
                withDelegate:self.commandDelegate];
        }];
}

- (void) super_properties:(CDVInvokedUrlCommand*)command {
    if (!mixpanel) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    NSDictionary *props = [mixpanel currentSuperProperties];
    [CDVMixpanel result:command
        withDelegate:self.commandDelegate
        andDictionary:props];
}

- (void) distinct_id:(CDVInvokedUrlCommand*)command {
    if (!mixpanel) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    [CDVMixpanel result:command
        withDelegate:self.commandDelegate
        andString:mixpanel.distinctId];
}

- (void) register:(CDVInvokedUrlCommand*)command {
    if (!mixpanel) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    NSDictionary* props = [command argumentAtIndex:0];
    if (!props) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"register properties not provided"];
        return;
    }
    [mixpanel registerSuperProperties:props];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) unregister:(CDVInvokedUrlCommand*)command {
    if (!mixpanel) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    NSString* prop = [command argumentAtIndex:0];
    if (!prop) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"unregister property not provided"];
        return;
    }
    [mixpanel unregisterSuperProperty:prop];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) register_once:(CDVInvokedUrlCommand*)command {
    if (!mixpanel) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    NSDictionary* props = [command argumentAtIndex:0];
    if (!props) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"register once properties not provided"];
        return;
    }
    [mixpanel registerSuperPropertiesOnce:props];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) clear:(CDVInvokedUrlCommand*)command {
    if (!mixpanel) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    [mixpanel clearSuperProperties];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) reset:(CDVInvokedUrlCommand*)command {
    if (!mixpanel) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    [mixpanel reset];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

+ (MixpanelPeople*) People {
    return people;
}

+ (void) result:(CDVInvokedUrlCommand*)command
        withDelegate:(id <CDVCommandDelegate>)delegate {
    CDVPluginResult *result = [
        CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [delegate sendPluginResult: result callbackId:[
            command callbackId]];
}

+ (void) result:(CDVInvokedUrlCommand*)command
        withDelegate:(id <CDVCommandDelegate>)delegate
        andError:(NSString*)error {
    CDVPluginResult* result = [
        CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR
                                  messageAsString: error];
    [delegate sendPluginResult: result callbackId:[
            command callbackId]];
}

+ (void) result:(CDVInvokedUrlCommand*)command
        withDelegate:(id <CDVCommandDelegate>)delegate
        andString:(NSString*)prop {
    CDVPluginResult* result = [
        CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                                  messageAsString:prop];
    [delegate sendPluginResult: result callbackId:[
            command callbackId]];
}

+ (void) result:(CDVInvokedUrlCommand*)command
        withDelegate:(id <CDVCommandDelegate>)delegate
        andDictionary:(NSDictionary*)props {
    CDVPluginResult* result = [
        CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                                  messageAsDictionary:props];
    [delegate sendPluginResult: result callbackId:[
            command callbackId]];
}

+ (void) resultUninitialized:(CDVInvokedUrlCommand*)command
        withDelegate:(id <CDVCommandDelegate>)delegate {
    NSString* error = @"mixpanel is not initialized";
    CDVPluginResult* result = [
        CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR
                                  messageAsString: error];
    [delegate sendPluginResult: result callbackId:[
            command callbackId]];
}

@end
