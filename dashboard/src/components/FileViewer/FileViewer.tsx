import {useEffect, useState} from 'react';
import './FileViewer.css'
import RefreshButton from '../RefreshButton/RefreshButton';
import {fetchFileList} from '../../lib/api';
import FileRow from './FileRow/FileRow';

function FileViewer(props: {selectedServer: string, currentDirectory: Array<string>}) {
    const [fileList, setFileList] = useState<Array<IFile>>([]);

    const handleRefresh = () => {
        if (props.selectedServer == '') {
            return;
        }
        fetchFileList(props.selectedServer, props.currentDirectory)
            .then(serverList => {
                setFileList(serverList);
            })
            .catch(error => {
                console.error('Error fetching server list:', error);
            });
    };

    const fileEntries = fileList.map((file) => (
        <FileRow name={file.name} date={file.date} size={file.size} />
    ));

    return (
        <div>
            <RefreshButton onClick={handleRefresh} />
            <table style={{ width: '100%' }}>
                <tr><td></td><td>Name</td><td>Date</td><td>Size</td></tr>
                {fileEntries}
            </table>
        </div>
    )
}

export default FileViewer
