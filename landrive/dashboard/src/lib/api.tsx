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
    return `http://${socketAddress.hostname}:${socketAddress.port}${endpoint}`
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

export { fetchFileServerList, fetchFileList };