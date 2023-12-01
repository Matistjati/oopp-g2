const CHUNK_SIZE = 10 * 1024 * 50; // 10MB

const handleFileUpload = (file: File) => {
    let start = 0;

    const processChunks = (start: number) => {
        if (start < file.size) {
            const end = Math.min(start + CHUNK_SIZE, file.size);
            const chunk = file.slice(start, end);

            const formData = new FormData();
            formData.append('file', chunk);

            const i = start / CHUNK_SIZE + 1;

            fetch('/api/uploadfile', {
                method: 'POST',
                body: formData,
                headers: {
                    'Content-Type': 'multipart/form-data',
                    'id': String(i),
                },
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return;
                })
                .then(data => {
                    // Handle the response data here
                    console.log('Server response:', data);

                    // Invoke the callback to process the next chunk
                    start = end;
                    if (start<file.size)
                    {
                        processChunks(start);
                    }
                })
                .catch(error => {
                    // Handle fetch or server-related errors here

                    console.error('Error:', error);
                    if (start<file.size)
                    {
                        processChunks(start);
                    }
                });
        }
    };

    // Start processing chunks
    processChunks(0);
};

export default handleFileUpload;
