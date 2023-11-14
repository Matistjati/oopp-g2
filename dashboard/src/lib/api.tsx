function fetchFileList() {
    fetch('/api/get')
    .then(response => response.json())
    .then(data => {
        // Handle data
    })
    .catch(error => {
        // Handle error
    });
}

export {fetchFileList};