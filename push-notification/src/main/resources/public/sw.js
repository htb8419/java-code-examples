self.addEventListener('push', event => {
    const data = event.data.json(); // Parse the notification data
    self.registration.showNotification(data.title, {
        body: data.body,
        icon: data.icon || 'icon.png'
    });
});