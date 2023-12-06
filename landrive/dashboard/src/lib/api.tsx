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

const uploadFile = (file: File, server: ServerInfo | null, dir: Array<string>, onComplete: () => void) => {
    if (server == null) {
        return;
    }
    const fd = new FormData();
    fd.append('file', file);
    const requestUrl = createRequestUrl(server.socketAddress, `/api/uploadFile/${dir.join('/')}`)
    fetch(requestUrl, {
        method: 'POST',
        body: fd
    })
        .then(response => {
            if (!response.ok) {
                throw new Error();
            }
            onComplete();
        })
        .catch(error => {
            throw error
        })
};


export { fetchFileServerList, fetchFileList, uploadFile };