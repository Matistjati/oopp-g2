async function fetchFileServerList(): Promise<Array<String>> {
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

async function fetchFileList(fileServerName: string, directory: Array<string>): Promise<Array<IFile>> {
    return fetch(`/api/filelist/${fileServerName}/${directory.join('/')}`, {
        method: "GET"
    })
        .then(response => {
            console.log(response);
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