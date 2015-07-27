=======================
Cordova Mixpanel Plugin
=======================

This plugin defines a global `plugin.mixpanel` object, which allows access
to Mixpanel's native SDKs. Push notifications are supported and are
explained below. Although the object is in the global scope, it is not
available until after the `deviceready` event.

    document.addEventListener("deviceready", onDeviceReady, false);
    function onDeviceReady() {
        plugin.mixpanel.init(mixpanel_token);
    }

This guide assumes you are familiar with the Mixpanel native SDKs. For more
information visit https://mixpanel.com/help/reference.

Supported Platforms
===================

- Android
- iOS


Installation
============

   cordova plugin add https://github.com/thelinuxkid/cordova-mixpanel


Methods
=======

- plugin.mixpanel.init
- plugin.mixpanel.alias
- plugin.mixpanel.identify
- plugin.mixpanel.time_event
- plugin.mixpanel.track
- plugin.mixpanel.flush
- plugin.mixpanel.super_properties
- plugin.mixpanel.distinct_id
- plugin.mixpanel.register
- plugin.mixpanel.unregister
- plugin.mixpanel.register_once
- plugin.mixpanel.clear
- plugin.mixpanel.reset
- plugin.mixpanel.people.distinct_id
- plugin.mixpanel.people.set
- plugin.mixpanel.people.set_once
- plugin.mixpanel.people.increment
- plugin.mixpanel.people.append
- plugin.mixpanel.people.union
- plugin.mixpanel.people.unset
- plugin.mixpanel.people.track_charge
- plugin.mixpanel.people.clear_charges
- plugin.mixpanel.people.delete_user
- plugin.mixpanel.people.init_push_handling
- plugin.mixpanel.people.set_push_reg_id
- plugin.mixpanel.people.clear_push_reg_id
- plugin.mixpanel.people.add_push_token

plugin.mixpanel.init
====================

Initializes the library. Calls `MixpanelAPI.getInstance(this,
mixpanel_token)` on Android and `[Mixpanel
sharedInstanceWithToken:mixpanel_token]` on iOS.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.alias
=====================

Calls `alias`. On iOS, calls `createAlias`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.identify
========================

Calls `identify`.

Android quirks
--------------

Also calls `people.identify`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.time_event
==========================

Calls `timeEvent`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.track
=====================

Calls `track`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.flush
=====================

Calls `flush`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.super_properties
================================

Returns the user's super properties.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.distinct_id
===========================

Returns the user's distinct ID.

iOS Quirks
----------

- It's the same as `plugin.mixpanel.people.distinct_id`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.register
========================

Calls `registerSuperProperties`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.unregister
==========================

Calls `unregisterSuperProperty`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.register_once
=============================

Calls `registerSuperPropertiesOnce`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.clear
=====================

Calls `clearSuperProperties`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.reset
=====================

Calls `reset`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.people.distinct_id
==================================

Returns the user's distinct ID using the People class.

iOS Quirks
----------

- It's the same as `plugin.mixpanel.distinct_id`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.people.set
==========================

Calls `people.set`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.people.set_once
===============================

Calls `people.setOnce`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.people.increment
================================

Calls `people.increment`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.people.append
=============================

Calls `people.append`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.people.union
============================

Calls `people.union`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.people.unset
============================

Calls `people.unset`.

iOS Quirks
----------

- This method is missing in the iOS SDK.

Supported Platforms
-------------------

- Android

plugin.mixpanel.people.track_charge
===================================

Calls `people.trackCharge`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.people.clear_charges
====================================

Calls `people.clearCharges`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.people.delete_user
==================================

Calls `people.deleteUser`.

Supported Platforms
-------------------

- Android
- iOS

plugin.mixpanel.people.init_push_handling
=========================================

Calls `people.initPushHandling`.

Supported Platforms
-------------------

- Android

plugin.mixpanel.people.set_push_reg_id
======================================

Calls `people.setPushRegistrationId`.

Supported Platforms
-------------------

- Android

plugin.mixpanel.people.clear_push_reg_id
========================================

Calls `people.clearPushRegistrationId`.

Supported Platforms
-------------------

- Android

plugin.mixpanel.people.add_push_token
=====================================

Calls `people.addPushToken`.

Supported Platforms
-------------------

- iOS

Push Notifications
==================

This plugin can be used in conjunction with
[PushPlugin](https://github.com/thelinuxkid/PushPlugin) to receive push
notifications. Follow the PushPlugin
[README](https://github.com/thelinuxkid/PushPlugin/blob/master/README.md)
to setup the notifications, then, come back here to finish the setup for
Mixpanel. Make sure to install
https://github.com/thelinuxkid/PushPlugin.git and NOT
https://github.com/phonegap-build/PushPlugin.git, at least until the pull
request is merged.

Android setup
-------------

Mixpanel stores the actual notification message under the key
`mp_message`. So, it must explicitly be set in the `register` method::

    pushNotification.register(
        successHandler,
        errorHandler,
        {
            "senderID":"replace_with_sender_id",
            "ecb":"onNotification",
            "messageKey": "mp_message"
        });

Then, in the `ebc`, pass the reg ID to Mixpanel::

    case 'registered':
       if (e.regid.length > 0) {
           plugin.mixpanel.people.set_push_reg_id(e.regid);
       }


iOS setup
---------

Pass the push token to Mixpanel in the tokenHandler function. Call `flush`
to ensure the token is sent right away::

    function tokenHandler (result) {
         plugin.mixpanel.people.add_push_token(result);
         plugin.mixpanel.flush();
    }
