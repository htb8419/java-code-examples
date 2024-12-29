self.addEventListener('push', event => {
    console.log("pussssssssh event",event)
    const data = event.data.text(); // Parse the notification data
    /*const data ={
        title:'sds',
        body:"salam"
    }*/
    self.registration.showNotification("title", {
        body: data
    });
});