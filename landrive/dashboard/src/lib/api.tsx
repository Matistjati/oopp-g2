import axios from "axios";

async function fetchFileServerList(): Promise<Array<ServerInfo>> {
    return fetch('/api/fileServers', {
        method: "GET"
    })
        .then(response => {
            if (!response.ok) {
                throw new Error();
            }
            return response.json();
        })
        .catch(error => {
            throw error;
        })
}

function createRequestUrl(socketAddress: SocketAddress, endpoint: string): string {
    return encodeURI(`http://${socketAddress.host}:${socketAddress.port}${endpoint}`)
}

async function fetchFileList(server: ServerInfo, dir: Array<string>): Promise<FsDirectoryList> {
    const requestUrl = createRequestUrl(
        server.socketAddress,
        `/api/fileList/${dir.join('/')}`
    )
    return fetch(requestUrl, {
        method: "GET"
    })
        .then(response => {
            if (!response.ok) {
                throw new Error();
            }
            return response.json();
        })
        .catch(error => {
            throw error;
        })
}

async function renameFile(file: FsEntryInfo, server: ServerInfo | null, dir: Array<string>, newName: string): Promise<void> {
    const requestUrl = createRequestUrl(
        server.socketAddress,
        `/api/renameFile/${dir.concat(file.name).join('/')}`
    )
    return fetch(requestUrl, {
        method: "POST",
        body: JSON.stringify(newName),
        headers : {
            'Content-Type' : 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error();
            }
        })
        .catch(error => {
            throw error;
        })
}

async function createFolder(server: ServerInfo | null, dir: Array<string>, name: string): Promise<void> {
    const requestUrl = createRequestUrl(
        server.socketAddress,
        `/api/createFolder/${dir.join('/')}`
    )
    return fetch(requestUrl, {
        method: "POST",
        body: JSON.stringify(name),
        headers : {
            'Content-Type' : 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error();
            }
        })
        .catch(error => {
            throw error;
        })
}

async function uploadFile(file: File, server: ServerInfo | null, dir: Array<string>, updateProgress: (progress: number) => void): Promise<void> {
    return new Promise<void>((resolve, reject) => {
        if (server === null) {
            reject(new Error("No server selected."));
            return;
        }
        const fd = new FormData();
        fd.append('file', file);
        const requestUrl = createRequestUrl(server.socketAddress, `/api/uploadFile/${dir.join('/')}`);
        /*fetch(requestUrl, {
            method: 'POST',
            body: fd
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error();
                }
                resolve()
            })
            .catch(error => {
                reject(error)
            })*/
        axios.post(requestUrl, fd, {
            headers: {
                'Content-Type': 'multipart/form-data'
            },
            onUploadProgress: (progressEvent) => {
                const {loaded, total} = progressEvent;
                if (total != undefined && total != 0) {
                    let progress = loaded / total;
                    updateProgress(progress)
                }
            },
        })
            .then((response) => {
                resolve();
            })
            .catch(error => {
                reject(error);
            });
    });
}

function downloadFile(file: FsEntryInfo, server: ServerInfo | null, dir: string[]) {
    window.location.href = createRequestUrl(
        server.socketAddress,
        `/api/downloadFile/${dir.concat(file.name).join('/')}`
    )
}
async function deleteFile(file: FsEntryInfo, server: ServerInfo | null, dir: Array<string>) {
    const requestUrl = createRequestUrl(
        server.socketAddress,
        `/api/deleteFile/${dir.concat(file.name).join('/')}`
    )
    return fetch(requestUrl, {
        method: "DELETE",
    })
        .then(response => {
            if (!response.ok) {
                throw new Error();
            }
        })
        .catch(error => {
            throw error;
        })
}

export {fetchFileServerList, fetchFileList, uploadFile, downloadFile, renameFile, createFolder, deleteFile };