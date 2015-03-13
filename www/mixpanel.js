function Mixpanel() {}

Mixpanel.prototype.init = function(token, success, error) {
    cordova.exec(success, error, 'Mixpanel', 'init', [token]);
}

Mixpanel.prototype.alias = function(alias, original, success, error) {
    var args = [alias, original];
    cordova.exec(success, error, 'Mixpanel', 'alias', args);
}

Mixpanel.prototype.identify = function(id, success, error) {
    cordova.exec(success, error, 'Mixpanel', 'identify', [id]);
}

Mixpanel.prototype.time_event = function(event, success, error) {
    cordova.exec(success, error, 'Mixpanel', 'time_event', [event]);
}

Mixpanel.prototype.track = function(event, props, success, error) {
    var args = [event, props];
    cordova.exec(success, error, 'Mixpanel', 'track', args);
}

Mixpanel.prototype.flush = function(success, error) {
    cordova.exec(success, error, 'Mixpanel', 'flush', []);
}

Mixpanel.prototype.super_properties = function(success, error) {
    cordova.exec(success, error, 'Mixpanel', 'super_properties', []);
}

Mixpanel.prototype.distinct_id = function(success, error) {
    cordova.exec(success, error, 'Mixpanel', 'distinct_id', []);
}

Mixpanel.prototype.register = function(props, success, error) {
    cordova.exec(success, error, 'Mixpanel', 'register', [props]);
}

Mixpanel.prototype.unregister = function(prop, success, error) {
    cordova.exec(success, error, 'Mixpanel', 'unregister', [prop]);
}

Mixpanel.prototype.register_once = function(props, success, error) {
    var args = [props];
    cordova.exec(success, error, 'Mixpanel', 'register_once', args);
}

Mixpanel.prototype.clear = function(success, error) {
    cordova.exec(success, error, 'Mixpanel', 'clear', []);
}

Mixpanel.prototype.reset = function(success, error) {
    cordova.exec(success, error, 'Mixpanel', 'reset', []);
}

function People() {}

People.prototype.distinct_id = function(success, error) {
    cordova.exec(success, error, 'People', 'distinct_id', []);
}

People.prototype.set = function(props, success, error) {
    cordova.exec(success, error, 'People', 'set', [props]);
}

People.prototype.set_once = function(props, success, error) {
    cordova.exec(success, error, 'People', 'set_once', [props]);
}

People.prototype.increment = function(prop, value, success, error) {
    var args = [prop, value];
    cordova.exec(success, error, 'People', 'increment', args);
}

People.prototype.append = function(prop, value, success, error) {
    var args = [prop, value];
    cordova.exec(success, error, 'People', 'append', args);
}

People.prototype.union = function(prop, value, success, error) {
    var args = [prop, value];
    cordova.exec(success, error, 'People', 'union', args);
}

// Android only (method is missing in iOS)
People.prototype.unset = function(prop, success, error) {
    cordova.exec(success, error, 'People', 'unset', prop);
}
//

People.prototype.track_charge = function(amount, props, success, error) {
    var args = [amount, props];
    cordova.exec(success, error, 'People', 'track_charge', args);
}

People.prototype.clear_charges = function(success, error) {
    cordova.exec(success, error, 'People', 'clear_charges', []);
}

People.prototype.delete_user = function(success, error) {
    cordova.exec(success, error, 'People', 'delete_user', []);
}

// Android only
People.prototype.init_push_handling = function(id, success, error) {
    cordova.exec(success, error, 'People', 'init_push_handling', [id]);
}

People.prototype.set_push_reg_id = function(id, success, error) {
    cordova.exec(success, error, 'People', 'set_push_reg_id', [id]);
}

People.prototype.clear_push_reg_id = function(success, error) {
    cordova.exec(success, error, 'People', 'clear_push_reg_id', []);
}
//

// iOS only
People.prototype.add_push_token = function(token, success, error) {
    cordova.exec(success, error, 'People', 'add_push_token', [token]);
}
//

module.exports = new Mixpanel();
module.exports.people = new People();
