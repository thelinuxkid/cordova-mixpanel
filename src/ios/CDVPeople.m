#import "CDVPeople.h"
#import "Mixpanel.h"

@implementation CDVPeople

- (void) identify:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) set:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) setOnce:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) increment:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) append:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) union:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) unset:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) trackCharge:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) clearCharges:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) deleteUser:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) initPushHandling:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) setPushRegID:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) clearPushRegID:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

- (void) distinctID:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
            NSDictionary* args = [command.arguments objectAtIndex:0];
        }];
}

@end
