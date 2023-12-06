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

function createRequestUrl(socketAddress: SocketAddress, endpoint: string) : string {
    return encodeURI(`http://${socketAddress.hostname}:${socketAddress.port}${endpoint}`)
}

async function fetchFileList(server: ServerInfo, directory: Array<string>): Promise<FsDirectoryList> {
    const requestUrl = createRequestUrl(
        server.socketAddress,
        `/api/fileList/${directory.join('/')}`
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

async function uploadFile(file: File, server: ServerInfo | null, dir: Array<string>, updateProgress: (progress: number) => void): Promise<void> {
    return new Promise<void>((resolve, reject) => {
        if (server === null) {
            reject(new Error("No server selected."));
            return;
        }
        const fd = new FormData();
        fd.append('file', file);
        const requestUrl = createRequestUrl(server.socketAddress, `/api/uploadFile/${dir.join('/')}`);
        axios.post(requestUrl, fd, {
            headers: {
                'Content-Type': 'multipart/form-data'
            },
            onUploadProgress: (progressEvent) => {
                const { loaded, total } = progressEvent;
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

export { fetchFileServerList, fetchFileList, uploadFile };