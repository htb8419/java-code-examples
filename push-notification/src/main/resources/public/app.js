const notifyButton = document.getElementById('notify');

// Register service worker
if ('serviceWorker' in navigator && 'PushManager' in window) {
    navigator.serviceWorker.register('sw.js?v='+Date.now())
        .then(registration => {
            console.log('Service Worker registered with scope:', registration.scope);
        })
        .catch(error => {
            console.error('Service Worker registration failed:', error);
        });
} else {
    console.error('Push messaging is not supported in this browser.');
}

// Request notification permission
notifyButton.addEventListener('click', async () => {
    const permission = await Notification.requestPermission();
    if (permission === 'granted') {
        subscribeToPush();
        console.log('Notification permission granted.');
    } else {
        console.log('Notification permission denied.');
    }
});

async function subscribeToPush() {
    const registration = await navigator.serviceWorker.ready;
    const subscription = await registration.pushManager.subscribe({
        userVisibleOnly: true,
        applicationServerKey: urlBase64ToUint8Array('BBYCxwATP2vVgw7mMPHJfT6bZrJP2iUV7OP_oxHzEcNFenrX66D8G34CdEmVULNg4WJXfjkeyT0AT9LwavpN8M4=')
    });

    // Send this subscription to your server to send notifications
    // e.g., via
    fetch('/api/push/subscribe', { method: 'POST',headers:{
            'Content-Type':'application/json'
        },
        body: JSON.stringify(subscription)
    }).then(()=>{
            console.log("subbbbbbbbbbbbbbbbbb")
    })
}
function createSubscription({endpoint,keys}) {
    return  {
        endpoint,
        keys,
        encoding: PushManager.supportedContentEncodings,
        /* other app-specific data, such as user identity */
    };
}

function urlBase64ToUint8Array(base64String) {
    const padding = '='.repeat((4 - base64String.length % 4) % 4);
    const base64 = (base64String + padding).replace(/-/g, '+').replace(/_/g, '/');
    const rawData = window.atob(base64);
    return Uint8Array.from([...rawData].map(char => char.charCodeAt(0)));
}
