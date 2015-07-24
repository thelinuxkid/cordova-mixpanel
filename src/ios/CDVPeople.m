#import "CDVPeople.h"
#import "CDVMixpanel.h"
#import "Mixpanel.h"

@interface CDVPeople ()

-(NSData *) dataFromHexString:(NSString *)hexStr;

@end

@implementation CDVPeople

- (void) distinct_id:(CDVInvokedUrlCommand*)command {
    if (![CDVMixpanel People]) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    Mixpanel* mixpanel = [Mixpanel sharedInstance];
    [CDVMixpanel result:command
        withDelegate:self.commandDelegate
        andString:mixpanel.distinctId];
}

- (void) set:(CDVInvokedUrlCommand*)command {
    if (![CDVMixpanel People]) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    NSDictionary* props = [command argumentAtIndex:0];
    if (!props) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"set properties not provided"];
        return;
    }
    [[CDVMixpanel People] set:props];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) set_once:(CDVInvokedUrlCommand*)command {
    if (![CDVMixpanel People]) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    NSDictionary* props = [command argumentAtIndex:0];
    if (!props) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"set once properties not provided"];
        return;
    }
    [[CDVMixpanel People] setOnce:props];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) increment:(CDVInvokedUrlCommand*)command {
    if (![CDVMixpanel People]) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    NSString* prop = [command argumentAtIndex:0];
    if (!prop) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"increment property not provided"];
        return;
    }
    NSNumber * value = [command argumentAtIndex:1];
    if (!value) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"increment value not provided"];
        return;
    }
    [[CDVMixpanel People] increment:prop by:value];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) append:(CDVInvokedUrlCommand*)command {
    if (![CDVMixpanel People]) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    NSString* prop = [command argumentAtIndex:0];
    if (!prop) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"append property not provided"];
        return;
    }
    NSDictionary * value = [command argumentAtIndex:1];
    if (!value) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"append value not provided"];
        return;
    }
    NSDictionary* values =  @{prop:value};
    [[CDVMixpanel People] append:values];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) union:(CDVInvokedUrlCommand*)command {
    if (![CDVMixpanel People]) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    NSString* prop = [command argumentAtIndex:0];
    if (!prop) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"union property not provided"];
        return;
    }
    NSDictionary * value = [command argumentAtIndex:1];
    if (!value) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"union value not provided"];
        return;
    }
    NSDictionary* values =  @{prop:value};
    [[CDVMixpanel People] union:values];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) unset:(CDVInvokedUrlCommand*)command {
    // TODO this method is missing in the iOS SDK
}

- (void) track_charge:(CDVInvokedUrlCommand*)command {
    if (![CDVMixpanel People]) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    NSNumber * value = [command argumentAtIndex:0];
    if (!value) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"track charge amount not provided"];
        return;
    }
    NSDictionary* props = [command argumentAtIndex:1];
    if (!props) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"track charge properties not provided"];
        return;
    }
    [[CDVMixpanel People] trackCharge:value withProperties:props];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) clear_charges:(CDVInvokedUrlCommand*)command {
    if (![CDVMixpanel People]) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    [[CDVMixpanel People] clearCharges];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) delete_user:(CDVInvokedUrlCommand*)command {
    if (![CDVMixpanel People]) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    [[CDVMixpanel People] deleteUser];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

- (void) add_push_token:(CDVInvokedUrlCommand*)command {
    if (![CDVMixpanel People]) {
        [CDVMixpanel resultUninitialized:command
            withDelegate:self.commandDelegate];
        return;
    }
    NSString * tokenStr = [command argumentAtIndex:0];
    if (!tokenStr) {
        [CDVMixpanel result:command
            withDelegate:self.commandDelegate
            andError:@"push device token not provided"];
        return;
    }
    NSData *token = [self dataFromHexString:tokenStr];
    [[CDVMixpanel People] addPushDeviceToken:token];
    [CDVMixpanel result:command withDelegate:self.commandDelegate];
}

-(NSData *) dataFromHexString:(NSString *) hexstr
{
    NSMutableData *data = [[NSMutableData alloc] init];
    NSString *inputStr = [hexstr uppercaseString];

    NSString *hexChars = @"0123456789ABCDEF";

    Byte b1,b2;
    b1 = 255;
    b2 = 255;
    for (int i=0; i<hexstr.length; i++) {
        NSString *subStr = [inputStr substringWithRange:NSMakeRange(i, 1)];
        NSRange loc = [hexChars rangeOfString:subStr];

        if (loc.location == NSNotFound) continue;

        if (255 == b1) {
            b1 = (Byte)loc.location;
        }else {
            b2 = (Byte)loc.location;

            //Appending the Byte to NSData
            Byte *bytes = malloc(sizeof(Byte) *1);
            bytes[0] = ((b1<<4) & 0xf0) | (b2 & 0x0f);
            [data appendBytes:bytes length:1];

            b1 = b2 = 255;
        }
    }

    return data;
}

@end
