function fetchFileList() {
    fetch('/api/fileList')
    .then(response => response.json())
    .then(data => {
        // Handle data
    })
    .catch(error => {
        // Handle error
    });
}

export {fetchFileList};