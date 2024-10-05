Notification.requestPermission().then((permission) => {
    if (permission === 'granted') {
        // Get the user's subscription
        navigator.serviceWorker.getRegistration().then((registration) => {
            registration.pushManager.getSubscription().then((subscription) => {
                if (!subscription) {
                    // Create a new subscription
                    registration.pushManager.subscribe({
                        userVisibleOnly: true,
                        applicationServerKey: 'YOUR_PUBLIC_KEY'
                    }).then((newSubscription) => {
                        // Send the subscription to the server
                        fetch('/api/subscribe', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(newSubscription)
                        });
                    });
                }
            });
        });
    }
});