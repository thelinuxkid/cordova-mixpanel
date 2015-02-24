#import "CDVMixpanel.h"
#import "Mixpanel.h"

@implementation CDVMixpanel

- (void) init:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) alias:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) identify:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) timeEvent:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) track:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) flush:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) superProperties:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) distinctID:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) register:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) unregister:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) registerOnce:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) clear:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) reset:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) deviceInfo:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

@end
