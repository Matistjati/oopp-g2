async function fetchFileServerList() : Promise<Array<String>> {
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

export {fetchFileServerList};


async function fetchFilelist() : Promise<Array<String>> {
    return fetch('/api/filelist', {
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

export {fetchFilelist};