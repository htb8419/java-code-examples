self.addEventListener('push', event => {
    console.log("pussssssssh event",event)
    const data = event.data.json(); // Parse the notification data
    /*const data ={
        title:'sds',
        body:"salam"
    }*/
    self.registration.showNotification(data.title, {
        body: data.body
    });
});