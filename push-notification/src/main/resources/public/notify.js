/**
 * Step one: run a function on load (or whenever is appropriate for you)
 * Function run on load sets up the service worker if it is supported in the
 * browser. Requires a serviceworker in a `sw.js`. This file contains what will
 * happen when we receive a push notification.
 * If you are using webpack, see the section below.
 */

if ('serviceWorker' in navigator &&
    'PushManager' in window &&
    'showNotification' in ServiceWorkerRegistration.prototype) {
    navigator.serviceWorker.register('/sw.js?v=' + Date.now())
     //   .then(initialiseState);
} else {
    console.warn('Service workers are not supported in this browser.');
}

document.getElementById("notify").addEventListener('click', async () => {
    const permission = await Notification.requestPermission();
    if (permission === 'granted') {
        subscribe();
        console.log('Notification permission granted.');
    } else {
        console.log('Notification permission denied.');
    }
});
/**
 * Step two: The serviceworker is registered (started) in the browser. Now we
 * need to check if push messages and notifications are supported in the browser
 */
async function initialiseState() {
    const permission = await Notification.requestPermission();
    if (permission === 'granted') {
        subscribe()
    }
    /*
     navigator.serviceWorker.ready.then(serviceWorkerRegistration => {

            // Get the push notification subscription object
                serviceWorkerRegistration.pushManager.getSubscription().then(subscription => {

                // If this is the user's first visit we need to set up
                // a subscription to push notifications
                if (!subscription) {
                    subscribe();
                    return;
                }
                // Update the server state with the new subscription
                sendSubscriptionToServer(subscription);
            }).catch(function (err) {
                // Handle the error - show a notification in the GUI
                console.warn('Error during getSubscription()', err);
            });
        });*/
}
/**
 * Step three: Create a subscription. Contact the third party push server (for
 * example mozilla's push server) and generate a unique subscription for the
 * current browser.
 */
function subscribe() {
    navigator.serviceWorker.ready.then(function (serviceWorkerRegistration) {
        // Contact the third party push server. Which one is contacted by
        // pushManager is  configured internally in the browser, so we don't
        // need to worry about browser differences here.
        //
        // When .subscribe() is invoked, a notification will be shown in the
        // user's browser, asking the user to accept push notifications from
        // <yoursite.com>. This is why it is async and requires a catch.
        serviceWorkerRegistration.pushManager.subscribe({userVisibleOnly: true})
            .then(function (subscription) {
                // Update the server state with the new subscription
                return sendSubscriptionToServer(subscription);
            }).catch(function (e) {
            if (Notification.permission === 'denied') {
                console.warn('Permission for Notifications was denied');
            } else {
                console.error('Unable to subscribe to push.', e);
            }
        });
    });
}

/**
 * Step four: Send the generated subscription object to our server.
 */
function sendSubscriptionToServer(subscription) {
    return fetch('/api/push/subscribe', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(subscription)
    });
}