import {useEffect, useState} from 'react';
import './FileViewer.css'
import RefreshButton from '../RefreshButton/RefreshButton';
import {fetchFilelist} from '../../lib/api';
import FileRow from './FileRow/FileRow';

function FileViewer() {
    const [fileList, setFileList] = useState<Array<String>>([]);
    useEffect(() => {
        handleRefresh()
    }, []);

    const handleRefresh = () => {
        fetchFilelist()
            .then(serverList => {
                setFileList(serverList);
            })
            .catch(error => {
                console.error('Error fetching server list:', error);
            });
    };

    const fileEntries = fileList.map((name) => (
        <FileRow fileName={name.split(" ")[0]} fileDate={name.split(" ")[1]} fileSize={name.split(" ")[2]} />
    ));


    return (
        <table style={{width: '100%'}}>
            <tr><td></td><td>Name</td><td>Date</td><td>Size</td>
                <RefreshButton onClick={handleRefresh} />
            </tr>

            {fileEntries}
        </table>
    )
}

export default FileViewer
