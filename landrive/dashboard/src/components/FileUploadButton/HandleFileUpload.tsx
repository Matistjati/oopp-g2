const handleFileUpload = (file: File, fileServerName: string, dir: Array<string>) => {
    const fd = new FormData();
    fd.append('file', file);
    const url = '/api/uploadFiles/' + [fileServerName].concat(dir).join('/');
    fetch(url, {
        method: 'POST',
        body: fd
    })
    .then(response => {
        console.log('File uploaded successfully.');
    })
    .catch(error => {
        console.error('Error uploading file:', error);
    });
};

export default handleFileUpload;
