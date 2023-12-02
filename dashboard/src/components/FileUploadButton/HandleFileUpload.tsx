const CHUNK_SIZE = 10 * 1024 * 50; // 10MB

const handleFileUpload = (file: File) => {
    let i = 0

    async function uploadFilePartial(file: File): Promise<void> {
        i+=1
        const endpoint = '/api/uploadfile';
        const chunkSize = 1024*100; // Adjust chunk size as needed
        let offset = 0;
        let sequence = 0;

        const readSlice = (file: Blob, start: number, end: number): Promise<Uint8Array> => {
            const slice = file.slice(start, end);
            return new Promise((resolve, reject) => {
                const reader = new FileReader();
                reader.onload = (event) => {
                    if (event.target?.result) {
                        resolve(new Uint8Array(event.target.result as ArrayBuffer));
                    }
                };
                reader.onerror = reject;
                reader.readAsArrayBuffer(slice);
            });
        };

        const fileSize = file.size;

        while (offset < fileSize) {
            const chunkEnd = Math.min(offset + chunkSize, fileSize);
            const chunk = await readSlice(file, offset, chunkEnd);
            const formData = new FormData();

            formData.append('file', new Blob([chunk], {
                type: file.type,
                //lastModified: file.lastModified,
            }), `${file.name}.${sequence}`);


            try {
                await fetch(endpoint, {
                    method: 'POST',
                    body: formData,
                });

                offset = chunkEnd;
                sequence++;

            } catch (error: any) {

                console.error('Error uploading chunk:', error, error.message, error.stack);
                //break;
            }
        }

        console.log('File upload complete');
    }
    // Start processing chunks
    uploadFilePartial(file);
};

export default handleFileUpload;
